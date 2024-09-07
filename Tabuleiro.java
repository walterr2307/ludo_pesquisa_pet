import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Tabuleiro {
    private int valor_dado;
    private boolean btn_ativado;
    private Jogador jog;
    private Button btn_girar_dado;
    private Circle circ_grandes[];
    private Rectangle ret_dado;
    private ImageView img_btn;
    private Image imgs_dados[];
    private ArrayList<Integer> x_quad_brancos, y_quad_brancos;
    private ArrayList<Integer> x_quad_finais, y_quad_finais;
    private ArrayList<Double> x_bases, y_bases;
    private ArrayList<Circle> circ_pequenos;

    public Tabuleiro(Pane root, int largura, int altura) throws FileNotFoundException {
        this.x_quad_brancos = new ArrayList<Integer>();
        this.y_quad_brancos = new ArrayList<Integer>();
        this.x_quad_finais = new ArrayList<Integer>();
        this.y_quad_finais = new ArrayList<Integer>();
        this.x_bases = new ArrayList<Double>();
        this.y_bases = new ArrayList<Double>();
        this.circ_grandes = new Circle[4];
        this.circ_pequenos = new ArrayList<Circle>();

        this.colocarPapelParede(root, largura, altura);
        this.colocarQuadBrancos(root, largura, altura);
        this.colocarRetasFinais(root, largura, altura);
        this.colocarCentro(root, largura, altura);
        this.colocarCirculos(root, largura, altura);
        this.gerarCaixaDados(root, largura, altura);
        this.btn_ativado = true;

        this.btn_girar_dado.setOnAction(evento -> {
            this.girarDados(largura);
        });
    }

    private void colocarPapelParede(Pane root, int largura, int altura) throws FileNotFoundException {
        ImageView img = new ImageView(new Image(new FileInputStream("imagens\\madeira.jpg")));
        img.setFitWidth(largura);
        img.setFitHeight(altura);
        root.getChildren().add(img);
    }

    private void colocarQuadBrancos(Pane root, int largura, int altura) {
        int i, tam_quad = (int) (largura / 24f), x = (int) (largura * (23f / 48f)), y = (int) (altura * 0.90625f);
        String cor;
        Rectangle quad = new Rectangle();

        quad.setWidth(tam_quad);
        quad.setHeight(tam_quad);
        quad.setX(x);
        quad.setY(y);
        quad.setFill(Color.WHITE);
        quad.setStroke(Color.BLACK);
        quad.setStrokeWidth(2);
        root.getChildren().add(quad);
        x_quad_brancos.add(x);
        y_quad_brancos.add(y);

        x -= tam_quad;

        for (i = 0; i < 6; i++) {
            if (i == 1)
                cor = "#008C00";
            else
                cor = "#FFFFFF";

            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x);
            quad.setY(y - i * tam_quad);
            quad.setFill(Color.web(cor));
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_brancos.add(x);
            y_quad_brancos.add(y - i * tam_quad);
        }

        x -= tam_quad;
        y -= tam_quad * 6;

        for (i = 0; i < 6; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x - i * tam_quad);
            quad.setY(y);
            quad.setFill(Color.WHITE);
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_brancos.add(x - i * tam_quad);
            y_quad_brancos.add(y);
        }

        x -= tam_quad * 5;
        y -= tam_quad;

        for (i = 0; i < 2; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x);
            quad.setY(y - i * tam_quad);
            quad.setFill(Color.WHITE);
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_brancos.add(x);
            y_quad_brancos.add(y - i * tam_quad);
        }

        x += tam_quad;
        y -= tam_quad;

        for (i = 0; i < 5; i++) {
            if (i == 0)
                cor = "#B8860B";
            else
                cor = "#FFFFFF";

            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x + i * tam_quad);
            quad.setY(y);
            quad.setFill(Color.web(cor));
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_brancos.add(x + i * tam_quad);
            y_quad_brancos.add(y);
        }

        x += tam_quad * 5;
        y -= tam_quad;

        for (i = 0; i < 6; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x);
            quad.setY(y - i * tam_quad);
            quad.setFill(Color.WHITE);
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_brancos.add(x);
            y_quad_brancos.add(y - i * tam_quad);
        }

        x += tam_quad;
        y -= tam_quad * 5;

        for (i = 0; i < 2; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x + i * tam_quad);
            quad.setY(y);
            quad.setFill(Color.WHITE);
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_brancos.add(x + i * tam_quad);
            y_quad_brancos.add(y);
        }

        x += tam_quad;
        y += tam_quad;

        for (i = 0; i < 5; i++) {
            if (i == 0)
                cor = "#8C0000";
            else
                cor = "#FFFFFF";

            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x);
            quad.setY(y + i * tam_quad);
            quad.setFill(Color.web(cor));
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_brancos.add(x);
            y_quad_brancos.add(y + i * tam_quad);
        }

        x += tam_quad;
        y += tam_quad * 5;

        for (i = 0; i < 6; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x + i * tam_quad);
            quad.setY(y);
            quad.setFill(Color.WHITE);
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_brancos.add(x + i * tam_quad);
            y_quad_brancos.add(y);
        }

        x += tam_quad * 5;
        y += tam_quad;

        for (i = 0; i < 2; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x);
            quad.setY(y + i * tam_quad);
            quad.setFill(Color.WHITE);
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_brancos.add(x);
            y_quad_brancos.add(y + i * tam_quad);
        }

        y += tam_quad;
        x -= tam_quad;

        for (i = 0; i < 5; i++) {
            if (i == 0)
                cor = "#00008C";
            else
                cor = "#FFFFFF";

            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x - i * tam_quad);
            quad.setY(y);
            quad.setFill(Color.web(cor));
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_brancos.add(x - i * tam_quad);
            y_quad_brancos.add(y);
        }

        x -= tam_quad * 5;
        y += tam_quad;

        for (i = 0; i < 6; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x);
            quad.setY(y + i * tam_quad);
            quad.setFill(Color.WHITE);
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_brancos.add(x);
            y_quad_brancos.add(y + i * tam_quad);
        }
    }

    private void colocarCentro(Pane root, int largura, int altura) throws FileNotFoundException {
        int x = (int) (largura * 0.4375f - 1), y = (int) (altura * 0.40625f - 1);
        Rectangle borda = new Rectangle();
        ImageView img = new ImageView(new Image(new FileInputStream("imagens\\centro.png")));

        borda.setWidth(largura / 8);
        borda.setHeight(largura / 8);
        borda.setLayoutX(x);
        borda.setLayoutY(y);

        x += 2;
        y += 2;

        img.setFitWidth(largura / 8 - 2);
        img.setFitHeight(largura / 8 - 2);
        img.setLayoutX(x);
        img.setLayoutY(y);

        root.getChildren().addAll(borda, img);
    }

    private void colocarRetasFinais(Pane root, int largura, int altura) {
        int i, x = (int) (largura * (23f / 48f)), y = (int) (altura * 0.84375f), tam_quad = largura / 24;
        Rectangle quad = new Rectangle();

        for (i = 0; i < 6; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x);
            quad.setY(y - i * tam_quad);
            quad.setFill(Color.web("#008C00"));
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            this.x_quad_finais.add(x);
            this.y_quad_finais.add(y - i * tam_quad);
        }

        x -= (int) (largura / 4f);
        y -= (int) (altura * 0.375f);

        for (i = 0; i < 6; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x + i * tam_quad);
            quad.setY(y);
            quad.setFill(Color.web("#B8860B"));
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            this.x_quad_finais.add(x + i * tam_quad);
            this.y_quad_finais.add(y);
        }

        x += (int) (largura / 4f);
        y -= (int) (altura * 0.375f);

        for (i = 0; i < 6; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x);
            quad.setY(y + i * tam_quad);
            quad.setFill(Color.web("#8C0000"));
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            this.x_quad_finais.add(x);
            this.y_quad_finais.add(y + i * tam_quad);
        }

        x += (int) (largura / 4f);
        y += (int) (altura * 0.375f);

        for (i = 0; i < 6; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x - i * tam_quad);
            quad.setY(y);
            quad.setFill(Color.web("#00008C"));
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            this.x_quad_finais.add(x - i * tam_quad);
            this.y_quad_finais.add(y);
        }
    }

    private void colocarCirculos(Pane root, int largura, int altura) {
        int i, x = (int) (largura * 0.3125f), y = (int) (altura * (25f / 32f)), raio = (int) (largura * (5f / 48f));
        String cor;
        Circle circulo;

        for (i = 0; i < 4; i++) {
            if (i == 0) {
                cor = "#008C00";
                x -= 0;
            } else if (i == 1) {
                cor = "#B8860B";
                y -= largura * 0.375;
            } else if (i == 2) {
                cor = "#8C0000";
                x += largura * 0.375;
            } else {
                cor = "#00008C";
                y += largura * 0.375;
            }

            circulo = new Circle(x, y, raio);
            circulo.setFill(Color.web(cor));
            circulo.setStroke(Color.BLACK);
            circulo.setStrokeWidth(4);
            this.circ_grandes[i] = circulo;
            root.getChildren().add(circulo);

            this.adicionarBases(root, x, y, raio, largura);
        }
    }

    private void adicionarBases(Pane root, double x, double y, double raio, int largura) {
        double baseRaio = raio / 4;
        double offset = raio / 2.5;

        Circle circulo1 = new Circle(x - offset, y - offset, baseRaio);
        circulo1.setFill(Color.TRANSPARENT);
        circulo1.setStroke(Color.BLACK);
        circulo1.setStrokeWidth(3);
        this.x_bases.add(x - offset - largura / 48);
        this.y_bases.add(y - offset - largura / 48);

        Circle circulo2 = new Circle(x + offset, y - offset, baseRaio);
        circulo2.setFill(Color.TRANSPARENT);
        circulo2.setStroke(Color.BLACK);
        circulo2.setStrokeWidth(3);
        this.x_bases.add(x + offset - largura / 48);
        this.y_bases.add(y - offset - largura / 48);

        Circle circulo3 = new Circle(x - offset, y + offset, baseRaio);
        circulo3.setFill(Color.TRANSPARENT);
        circulo3.setStroke(Color.BLACK);
        circulo3.setStrokeWidth(3);
        this.x_bases.add(x - offset - largura / 48);
        this.y_bases.add(y + offset - largura / 48);

        Circle circulo4 = new Circle(x + offset, y + offset, baseRaio);
        circulo4.setFill(Color.TRANSPARENT);
        circulo4.setStroke(Color.BLACK);
        circulo4.setStrokeWidth(3);
        this.x_bases.add(x + offset - largura / 48);
        this.y_bases.add(y + offset - largura / 48);

        circ_pequenos.add(circulo1);
        circ_pequenos.add(circulo2);
        circ_pequenos.add(circulo3);
        circ_pequenos.add(circulo4);

        root.getChildren().addAll(circulo1, circulo2, circulo3, circulo4);
    }

    private void gerarCaixaDados(Pane root, int largura, int altura) throws FileNotFoundException {
        imgs_dados = new Image[6];

        for (int i = 0; i < 6; i++) {
            String caminho = String.format("imagens\\dado%d.png", i + 1);
            imgs_dados[i] = new Image(new FileInputStream(caminho));
        }

        img_btn = new ImageView(imgs_dados[5]);
        img_btn.setFitWidth(largura / 16f);
        img_btn.setFitHeight(largura / 16f);
        img_btn.setLayoutX(largura / 16f);
        img_btn.setLayoutY(altura * (29f / 64f));

        btn_girar_dado = new Button();
        btn_girar_dado.setOpacity(0f);
        btn_girar_dado.setPrefSize(largura / 16f, largura / 16f);
        btn_girar_dado.setLayoutX(largura / 16f);
        btn_girar_dado.setLayoutY(altura * (29f / 64f));

        ret_dado = new Rectangle();
        ret_dado.setFill(Color.LIGHTGRAY);
        ret_dado.setWidth(largura / 16f);
        ret_dado.setHeight(largura / 16f);
        ret_dado.setStroke(Color.BLACK);
        ret_dado.setStrokeWidth(largura / 240f);
        ret_dado.setLayoutX(largura / 16f);
        ret_dado.setLayoutY(altura * (29f / 64f));

        root.getChildren().addAll(ret_dado, img_btn, btn_girar_dado);
    }

    private void girarDados(int largura) {
        valor_dado = (int) (Math.random() * 6 + 1);

        img_btn.setImage(imgs_dados[valor_dado - 1]);
        ret_dado.setFill(Color.web(jog.getCorHexadecimal()));
        ret_dado.setOpacity(0.5);
        btn_girar_dado.setDisable(true);
        btn_ativado = false;

        for (int i = 0; i < 4; i++) {
            jog.getPeca(i).setValorDado(valor_dado);
            jog.getPeca(i).getBotao().setDisable(false);
        }

    }

    public ArrayList<Integer> getXQuadBrancos() {
        return this.x_quad_brancos;
    }

    public ArrayList<Integer> getYQuadBrancos() {
        return this.y_quad_brancos;
    }

    public ArrayList<Double> getXBases() {
        return this.x_bases;
    }

    public ArrayList<Double> getYBases() {
        return this.y_bases;
    }

    public Button getBotao() {
        return this.btn_girar_dado;
    }

    public void setJogador(Jogador jog) {
        this.jog = jog;
    }

    public void setBotaoAtivado(Boolean btn_ativado) {
        this.btn_ativado = btn_ativado;
    }

    public boolean getBotaoAtivado() {
        return this.btn_ativado;
    }

    public Circle[] getCirculosGrandes() {
        return this.circ_grandes;
    }

    public int getValorDado() {
        return this.valor_dado;
    }

    public ArrayList<Integer> getXQuadFinais() {
        return this.x_quad_finais;
    }

    public ArrayList<Integer> getYQuadFinais() {
        return this.y_quad_finais;
    }

    public Rectangle getRetangulo() {
        return this.ret_dado;
    }

    public ArrayList<Circle> getCirculosPequenos() {
        return this.circ_pequenos;
    }
}