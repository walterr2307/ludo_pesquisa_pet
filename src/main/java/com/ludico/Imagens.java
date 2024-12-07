package com.ludico;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Imagens {
    private final static float largura = Main.getLargura(), altura = Main.getAltura();
    private final static Pane root = Main.getRoot();
    private int valor_dado;
    private Button btn_girar_dado;
    private Rectangle ret_dado;
    private ImageView img_btn;
    private Image[] imgs_dados;
    private boolean btn_ativado = true;
    private Jogador jog;

    public Imagens() {
        colocarPapelParede();
        gerarCaixaDados();
        btn_girar_dado.setOnAction(evento -> this.girarDados());
    }

    private void colocarPapelParede() {
        try {
            ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/madeira.jpg")));
            img.setFitWidth(largura);
            img.setFitHeight(altura);
            root.getChildren().add(img);
        } catch (NullPointerException e) {
            System.out.println("Imagem não encontrada no diretório resources!");
            e.printStackTrace();
        }
    }

    private void gerarCaixaDados() {
        try {
            imgs_dados = new Image[6];

            for (int i = 0; i < 6; i++) {
                String caminho = String.format("/dado%d.png", i + 1);
                imgs_dados[i] = new Image(getClass().getResourceAsStream(caminho));
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
        } catch (NullPointerException e) {
            System.out.println("Erro ao carregar as imagens dos dados do diretório resources!");
            e.printStackTrace();
        }
    }

    public void colocarCentro() {
        try {
            float x = largura * 0.4375f - 1, y = altura * 0.40625f - 1;
            Rectangle borda = new Rectangle();
            ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/centro.png")));

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
        } catch (NullPointerException e) {
            System.out.println("Erro ao carregar a imagem centro.png do diretório resources!");
            e.printStackTrace();
        }
    }

    private void girarDados() {
        /*valor_dado = (int) (Math.random() * 6 + 1);

        img_btn.setImage(imgs_dados[valor_dado - 1]);
        ret_dado.setFill(Color.web(jog.getCorHexadecimal()));
        ret_dado.setOpacity(0.5);
        btn_girar_dado.setDisable(true);
        btn_ativado = false;

        for (int i = 0; i < 4; i++) {
            jog.getPeca(i).setValorDado(valor_dado);
            jog.getPeca(i).getBotao().setDisable(false);
        }*/
    }

    public Button getBotaoGirarDado() {
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

    public int getValorDado() {
        return this.valor_dado;
    }

    public Rectangle getRetanguloDado() {
        return this.ret_dado;
    }
}
