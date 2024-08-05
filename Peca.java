import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Peca {
    int pos_inicial, pos_atual, x_inicial, y_inicial, valor_dado, pos_anterior;
    int pos_final, x_quad_finais[], y_quad_finais[];
    float constante, tempo_pausa;
    double x_base, y_base;
    boolean jogada_finalizada, minha_vez, jogar_dnv;
    String cor, tipo_pos, caminho, caminho_fundo, tipo_anterior;
    Image img_sem_fundo, img_com_fundo;
    ImageView img;
    Button botao;
    ArrayList<Integer> x_quad_brancos, y_quad_brancos;

    public Peca(Pane root, String cor, double x_base, double y_base, ArrayList<Integer> x_quad_brancos,
            ArrayList<Integer> y_quad_brancos, ArrayList<Integer> x_quad_finais, ArrayList<Integer> y_quad_finais,
            int largura) throws FileNotFoundException {
        this.cor = cor;
        this.x_base = x_base;
        this.y_base = y_base;
        this.x_quad_brancos = x_quad_brancos;
        this.y_quad_brancos = y_quad_brancos;
        this.tempo_pausa = 0f;
        this.tipo_pos = "base";
        this.jogada_finalizada = false;
        this.minha_vez = false;
        this.jogar_dnv = false;
        this.constante = this.definirConstante(largura);
        this.img = this.definirImg(root, largura);
        this.botao = this.definirBotao(root, largura);
        this.definirXYIniciais();
        this.definirXYfinais(x_quad_finais, y_quad_finais);

        // Ouvinte para mostrar o botão quando o mouse estiver sobre a peça
        this.botao.setOnMouseEntered(evento -> {
            if (minha_vez)
                this.img.setImage(img_com_fundo);
        });

        // Ouvinte para esconder o botão quando o mouse sair da peça
        this.botao.setOnMouseExited(evento -> {
            if (minha_vez)
                this.img.setImage(img_sem_fundo);
        });

        this.botao.setOnAction(evento -> {
            this.tempo_pausa = this.mover();
        });
    }

    private void definirXYIniciais() {

        if (this.cor.equals("verde")) {
            x_inicial = x_quad_brancos.get(2);
            y_inicial = y_quad_brancos.get(2);
            pos_inicial = 2;
            pos_final = 0;
        } else if (this.cor.equals("amarelo")) {
            x_inicial = x_quad_brancos.get(15);
            y_inicial = y_quad_brancos.get(15);
            pos_inicial = 15;
            pos_final = 13;
        } else if (this.cor.equals("vermelho")) {
            x_inicial = x_quad_brancos.get(28);
            y_inicial = y_quad_brancos.get(28);
            pos_inicial = 28;
            pos_final = 26;
        } else {
            x_inicial = x_quad_brancos.get(41);
            y_inicial = y_quad_brancos.get(41);
            pos_inicial = 41;
            pos_final = 39;
        }
    }

    private void definirXYfinais(ArrayList<Integer> x_quad_finais, ArrayList<Integer> y_quad_finais) {
        int i, j = 0, inicio, fim;

        this.x_quad_finais = new int[6];
        this.y_quad_finais = new int[6];

        if (this.cor.equals("verde"))
            inicio = 0;
        else if (this.cor.equals("amarelo"))
            inicio = 6;
        else if (this.cor.equals("vermelho"))
            inicio = 12;
        else
            inicio = 18;

        fim = inicio + 6;

        for (i = inicio; i < fim; i++) {
            this.x_quad_finais[j] = x_quad_finais.get(i);
            this.y_quad_finais[j] = y_quad_finais.get(i);
            ++j;
        }
    }

    private float definirConstante(int largura) {
        float dist_max = largura * 0.875f, tempo_max = largura / 300f, constante = tempo_max / dist_max;

        return constante;
    }

    private ImageView definirImg(Pane root, int largura) throws FileNotFoundException {
        ImageView img;

        if (this.cor.equals("verde")) {
            caminho = "imagens/peca_verde.png";
            caminho_fundo = "imagens/peca_verde-fundo_prata.png";
        } else if (this.cor.equals("amarelo")) {
            caminho = "imagens/peca_amarela.png";
            caminho_fundo = "imagens/peca_amarela-fundo_prata.png";
        } else if (this.cor.equals("vermelho")) {
            caminho = "imagens/peca_vermelha.png";
            caminho_fundo = "imagens/peca_vermelha-fundo_prata.png";
        } else {
            caminho = "imagens/peca_azul.png";
            caminho_fundo = "imagens/peca_azul-fundo_prata.png";
        }

        img_sem_fundo = new Image(new FileInputStream(caminho));
        img_com_fundo = new Image(new FileInputStream(caminho_fundo));

        img = new ImageView(img_sem_fundo);
        img.setFitWidth(largura / 24);
        img.setFitHeight(largura / 24);
        img.setLayoutX(this.x_base);
        img.setLayoutY(this.y_base);
        root.getChildren().add(img);

        return img;
    }

    public Button definirBotao(Pane root, int largura) {
        Button botao = new Button();

        botao.setPrefSize(largura / 24, largura / 24);
        botao.setLayoutX(this.x_base);
        botao.setLayoutY(this.y_base);
        botao.setDisable(true);
        botao.setOpacity(0f);
        root.getChildren().add(botao);

        return botao;
    }

    public float mover() {
        if (!this.tipo_pos.equals("linha_chegada")) {
            if (this.tipo_pos.equals("base") && valor_dado == 6) {
                pos_anterior = pos_atual;
                tipo_anterior = tipo_pos;

                this.sairDaBase();
                this.tipo_pos = "quad_branco";
                this.jogada_finalizada = true;

                return 0.75f;
            } else if (tipo_pos.equals("quad_branco") || tipo_pos.equals("quad_final") && valor_dado + pos_atual <= 5) {
                pos_anterior = pos_atual;
                tipo_anterior = tipo_pos;

                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
                    this.moverComPulo();
                }));

                timeline.setCycleCount(valor_dado);
                timeline.play();
                this.jogada_finalizada = true;

                return 0.5f * valor_dado;
            }
        }

        return 0f;
    }

    private void sairDaBase() {
        float distancia, dx, dy;
        TranslateTransition movimento1, movimento2;

        dx = (float) (x_inicial - x_base);
        dy = (float) (y_inicial - y_base);
        distancia = (float) Math.sqrt(dx * dx + dy * dy);

        movimento1 = new TranslateTransition(Duration.seconds(constante * distancia), img);
        movimento2 = new TranslateTransition(Duration.seconds(constante * distancia), botao);

        movimento1.setByX(dx);
        movimento1.setByY(dy);
        movimento1.play();

        movimento2.setByX(dx);
        movimento2.setByY(dy);
        movimento2.play();

        pos_atual = pos_inicial;
    }

    public int moverSemPulo() {
        float distancia, dx, dy;
        TranslateTransition movimento1, movimento2;

        dx = (float) (x_base - x_quad_brancos.get(pos_atual));
        dy = (float) (y_base - y_quad_brancos.get(pos_atual));
        distancia = (float) Math.sqrt(dx * dx + dy * dy);

        movimento1 = new TranslateTransition(Duration.seconds(constante * distancia), img);
        movimento2 = new TranslateTransition(Duration.seconds(constante * distancia), botao);

        movimento1.setByX(dx);
        movimento1.setByY(dy);
        movimento1.play();

        movimento2.setByX(dx);
        movimento2.setByY(dy);
        movimento2.play();

        tipo_pos = "base";
        return (int) (constante * distancia * 1000f + 250f);
    }

    private void animarImagem() {
        // Primeiro ScaleTransition para expandir a imagem
        ScaleTransition expandTransition = new ScaleTransition();
        expandTransition.setNode(this.img);
        expandTransition.setDuration(Duration.seconds(0.125));
        expandTransition.setToX(1.5);
        expandTransition.setToY(1.5);
        expandTransition.setCycleCount(1);
        expandTransition.setAutoReverse(false);

        // Segundo ScaleTransition para diminuir a imagem de volta
        ScaleTransition shrinkTransition = new ScaleTransition();
        shrinkTransition.setNode(this.img);
        shrinkTransition.setDuration(Duration.seconds(0.125));
        shrinkTransition.setToX(1.0); // Voltar para o tamanho original
        shrinkTransition.setToY(1.0);
        shrinkTransition.setCycleCount(1);
        shrinkTransition.setAutoReverse(false);

        // SequentialTransition para executar as transições em sequência
        SequentialTransition sequentialTransition = new SequentialTransition(expandTransition, shrinkTransition);
        sequentialTransition.play();
    }

    public void moverComPulo() {
        int prox_pos = (pos_atual + 1) % 52;
        double dx, dy;
        TranslateTransition movimento1, movimento2;

        if (pos_atual == pos_final && tipo_pos.equals("quad_branco")) {
            dx = (x_quad_finais[0] - x_quad_brancos.get(pos_atual));
            dy = (y_quad_finais[0] - y_quad_brancos.get(pos_atual));
            tipo_pos = "quad_final";
            System.out.println(tipo_pos);
        } else if (tipo_pos.equals("quad_final")) {
            dx = (x_quad_finais[prox_pos] - x_quad_finais[pos_atual]);
            dy = (y_quad_finais[prox_pos] - y_quad_finais[pos_atual]);
        } else {
            dx = (x_quad_brancos.get(prox_pos) - x_quad_brancos.get(pos_atual));
            dy = (y_quad_brancos.get(prox_pos) - y_quad_brancos.get(pos_atual));
        }

        movimento1 = new TranslateTransition(Duration.seconds(0.25), img);
        movimento2 = new TranslateTransition(Duration.seconds(0.25), botao);

        movimento1.setByX(dx);
        movimento1.setByY(dy);
        movimento1.play();

        movimento2.setByX(dx);
        movimento2.setByY(dy);
        movimento2.play();

        this.animarImagem();

        if (pos_atual == pos_final)
            pos_atual = 0;
        else
            pos_atual = prox_pos;

        if (tipo_pos.equals("quad_final") && pos_atual == 5)
            finalizar();
    }

    private void finalizar() {
        this.jogar_dnv = true;
        this.tipo_pos = "linha_chegada";
        this.img.setVisible(false);
        this.botao.setVisible(false);
        this.botao.setDisable(true);
    }

    public Button getBotao() {
        return this.botao;
    }

    public boolean verificarJogadaDisponivel() {
        if (tipo_pos.equals("linha_chegada"))
            return false;
        else if (tipo_pos.equals("base") && valor_dado < 6)
            return false;
        else if (tipo_pos.equals("quad_final") && valor_dado + pos_atual > 5)
            return false;
        else
            return true;
    }

    public void setValorDado(int valor_dado) {
        this.valor_dado = valor_dado;
    }

    public void zerarTempoPausa() {
        this.tempo_pausa = 0f;
    }

    public float getTempoPausa() {
        return this.tempo_pausa;
    }

    public void setJogadaFinalizada(boolean jogada_finalizada) {
        this.jogada_finalizada = jogada_finalizada;
    }

    public boolean getJogadaFinalizada() {
        return jogada_finalizada;
    }

    public void setMinhaVez(boolean minha_vez) {
        this.minha_vez = minha_vez;
    }

    public int getPos() {
        return this.pos_atual;
    }

    public String getCor() {
        return this.cor;
    }

    public String getTipoPos() {
        return this.tipo_pos;
    }

    public int getPosFinal() {
        return pos_final;
    }

    public int getPosAnterior() {
        return this.pos_anterior;
    }

    public String getTipoAnterior() {
        return this.tipo_anterior;
    }

    public void setJogarDeNovo(boolean jogar_dnv) {
        this.jogar_dnv = jogar_dnv;
    }

    public boolean getJogarDeNovo() {
        return this.jogar_dnv;
    }
}
