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
    int pos_inicial, pos_atual, x_inicial, y_inicial, valor_dado;
    float constante, tempo_pausa;
    double x_base, y_base;
    boolean jogada_finalizada, minha_vez;
    String cor, tipo_pos, caminho, caminho_fundo;
    Image img_sem_fundo, img_com_fundo;
    ImageView img;
    Button botao;
    ArrayList<Integer> x_quad_brancos, y_quad_brancos;

    public Peca(Pane root, String cor, double x_base, double y_base, ArrayList<Integer> x_quad_brancos,
            ArrayList<Integer> y_quad_brancos, int largura) throws FileNotFoundException {
        this.cor = cor;
        this.x_base = x_base;
        this.y_base = y_base;
        this.x_quad_brancos = x_quad_brancos;
        this.y_quad_brancos = y_quad_brancos;
        this.tempo_pausa = 0f;
        this.tipo_pos = "base";
        this.jogada_finalizada = false;
        this.minha_vez = false;
        this.constante = this.definirConstante(largura);
        this.img = this.definirImg(root, largura);
        this.botao = this.definirBotao(root, largura);
        this.definirXYIniciais();

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
        } else if (this.cor.equals("amarelo")) {
            x_inicial = x_quad_brancos.get(15);
            y_inicial = y_quad_brancos.get(15);
            pos_inicial = 15;
        } else if (this.cor.equals("vermelho")) {
            x_inicial = x_quad_brancos.get(28);
            y_inicial = y_quad_brancos.get(28);
            pos_inicial = 28;
        } else {
            x_inicial = x_quad_brancos.get(41);
            y_inicial = y_quad_brancos.get(41);
            pos_inicial = 41;
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

        img = new ImageView(new Image(new FileInputStream(caminho)));
        img.setFitWidth(largura / 24);
        img.setFitHeight(largura / 24);
        img.setLayoutX(this.x_base);
        img.setLayoutY(this.y_base);
        root.getChildren().add(img);

        img_sem_fundo = new Image(new FileInputStream(caminho));
        img_com_fundo = new Image(new FileInputStream(caminho_fundo));

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

        if (this.tipo_pos.equals("base") && valor_dado == 6) {
            this.sairDaBase();
            this.tipo_pos = "quad_branco";
            this.jogada_finalizada = true;

            return 0.5f;
        } else if (tipo_pos.equals("quad_branco")) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
                this.moverComPulo();
            }));

            timeline.setCycleCount(valor_dado);
            timeline.play();
            this.jogada_finalizada = true;

            return 0.5f * valor_dado + 0.25f;
        } else {
            return 0f;
        }
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

    /*
     * private void moverSemPulo() {
     * float distancia, dx, dy;
     * TranslateTransition movimento1, movimento2;
     * 
     * dx = (float) (x_base - x_quad_brancos.get(pos_atual));
     * dy = (float) (y_base - y_quad_brancos.get(pos_atual));
     * distancia = (float) Math.sqrt(dx * dx + dy * dy);
     * 
     * movimento1 = new TranslateTransition(Duration.seconds(constante * distancia),
     * img);
     * movimento2 = new TranslateTransition(Duration.seconds(constante * distancia),
     * botao);
     * 
     * movimento1.setToX(dx);
     * movimento1.setToY(dy);
     * movimento1.play();
     * 
     * movimento2.setToX(dx);
     * movimento2.setToY(dy);
     * movimento2.play();
     * }
     */

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

    private void moverComPulo() {
        int prox_pos = (pos_atual + 1) % 52;
        double dx, dy;
        TranslateTransition movimento1, movimento2;

        dx = (x_quad_brancos.get(prox_pos) - x_quad_brancos.get(pos_atual));
        dy = (y_quad_brancos.get(prox_pos) - y_quad_brancos.get(pos_atual));

        movimento1 = new TranslateTransition(Duration.seconds(0.25), img);
        movimento2 = new TranslateTransition(Duration.seconds(0.25), botao);

        movimento1.setByX(dx);
        movimento1.setByY(dy);
        movimento1.play();

        movimento2.setByX(dx);
        movimento2.setByY(dy);
        movimento2.play();

        this.animarImagem();

        pos_atual = prox_pos;
    }

    public Button getBotao() {
        return this.botao;
    }

    public boolean verificarJogadaDisponivel() {
        if (tipo_pos.equals("base") && valor_dado != 6)
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

}
