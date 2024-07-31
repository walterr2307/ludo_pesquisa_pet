import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Peca {
    int pos_atual, x_inicial, y_inicial;
    float constante;
    double x_base, y_base;
    String cor, tipo_pos;
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
        this.pos_atual = 0;
        this.tipo_pos = "base";
        this.constante = this.definirConstante(largura);
        this.botao = this.definirBotao(root, largura);
        this.img = this.definirImg(root, largura);
        this.definirXYIniciais();
    }

    private void definirXYIniciais() {

        if (this.cor.equals("verde")) {
            x_inicial = x_quad_brancos.get(2);
            y_inicial = y_quad_brancos.get(2);
        } else if (this.cor.equals("amarelo")) {
            x_inicial = x_quad_brancos.get(15);
            y_inicial = y_quad_brancos.get(15);
        } else if (this.cor.equals("vermelho")) {
            x_inicial = x_quad_brancos.get(28);
            y_inicial = y_quad_brancos.get(28);
        } else {
            x_inicial = x_quad_brancos.get(41);
            y_inicial = y_quad_brancos.get(41);
        }
    }

    private float definirConstante(int largura) {
        float dist_max = largura * 0.875f, tempo_max = largura / 300f, constante = tempo_max / dist_max;

        return constante;
    }

    private ImageView definirImg(Pane root, int largura) throws FileNotFoundException {
        String caminho;
        ImageView img;

        if (this.cor.equals("verde"))
            caminho = "imagens/peca_verde.png";
        else if (this.cor.equals("amarelo"))
            caminho = "imagens/peca_amarela.png";
        else if (this.cor.equals("vermelho"))
            caminho = "imagens/peca_vermelha.png";
        else
            caminho = "imagens/peca_azul.png";

        img = new ImageView(new Image(new FileInputStream(caminho)));
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
        botao.setVisible(false);
        botao.setDisable(true);
        root.getChildren().add(botao);

        return botao;
    }

    public void mover(int valor_dados) {
        if (tipo_pos.equals("base") && valor_dados == 6) {
            this.sairDaBase();
            this.tipo_pos = "quad_branco";
        } else if (tipo_pos.equals("quad_branco")) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
                this.moverComPulo();
            }));

            timeline.setCycleCount(valor_dados);
            timeline.play();
        }
    }

    private void sairDaBase() {
        float distancia, dx, dy;
        TranslateTransition movimento1, movimento2;

        dx = (float) (x_base - x_inicial);
        dy = (float) (y_base - y_inicial);
        distancia = (float) Math.sqrt(dx * dx + dy * dy);

        movimento1 = new TranslateTransition(Duration.seconds(constante * distancia), img);
        movimento2 = new TranslateTransition(Duration.seconds(constante * distancia), botao);

        movimento1.setToX(dx);
        movimento1.setToY(dy);
        movimento1.play();

        movimento2.setToX(dx);
        movimento2.setToY(dy);
        movimento2.play();
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

    private void moverComPulo() {
        int prox_pos = (pos_atual + 1) % 52;
        float dx, dy;
        TranslateTransition movimento1, movimento2;

        dx = (float) (x_quad_brancos.get(prox_pos) - x_quad_brancos.get(pos_atual));
        dy = (float) (y_quad_brancos.get(prox_pos) - y_quad_brancos.get(pos_atual));

        movimento1 = new TranslateTransition(Duration.seconds(0.25), img);
        movimento2 = new TranslateTransition(Duration.seconds(0.25), botao);

        movimento1.setToX(dx);
        movimento1.setToY(dy);
        movimento1.play();

        movimento2.setToX(dx);
        movimento2.setToY(dy);
        movimento2.play();

        pos_atual = prox_pos;
    }

    public Button getBotao() {
        return this.botao;
    }

    public boolean verificarJogadaDisponivel(int valor_dados) {
        if (tipo_pos.equals("base") && valor_dados != 6)
            return false;
        else
            return true;
    }
}
