package com.ludico;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.InputStream;

public abstract class Jogador {
    protected final static float largura = Main.getLargura(), altura = Main.getAltura();
    protected final static Pane root = Main.getRoot();
    protected final Tabuleiro tabuleiro = Tabuleiro.instanciar();
    protected final float[] x_quad_brancos = tabuleiro.getXQuadBrancos(), y_quad_brancos = tabuleiro.getYQuadBrancos();
    protected final float[] x_bases = definirXBases(), y_bases = definirYBases();
    protected final String cor = definirCor(), cor_hexa = definirCorHexadecimal(), cor_escura = definirCorEscura();
    protected final Button caixa_nome;
    protected boolean pergunta_acertada = true;
    protected int indice_img = 0;
    protected String formato_caixa_branca, formato_caixa_preta;
    protected Circle circ_grande = definirCirculoGrande();
    protected Circle[] circ_pequenos = definirCirculosPequenos();
    protected Peca[] pecas = gerarPecas();
    protected ImageView[] imgs_chegada = gerarImagensChegada();

    public Jogador(String nome) {
        caixa_nome = definirCaixaNome(nome);
        definirFormatos();
    }

    protected abstract float getAuxiliarX();

    protected abstract float getAuxiliarY();

    protected abstract String definirCor();

    protected abstract String definirCorHexadecimal();

    protected abstract String definirCorEscura();

    protected abstract float[] definirXBases();

    protected abstract float[] definirYBases();

    protected abstract Circle definirCirculoGrande();

    protected abstract Circle[] definirCirculosPequenos();

    protected abstract Peca[] gerarPecas();

    protected void definirFormatos() {
        float tam = largura / 480f;

        formato_caixa_preta = String.format(
                "-fx-background-color: %s; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: %fpx;",
                cor_escura, tam);
        formato_caixa_branca = String.format(
                "-fx-background-color: %s; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: %fpx;",
                cor_escura, tam);
    }

    protected ImageView[] gerarImagensChegada() {
        ImageView[] imgs_chegada = new ImageView[4];

        for (int i = 0; i < 4; i++) {
            String caminho = String.format("/chegada_%s%d.png", cor, i + 1);
            InputStream inputStream = getClass().getResourceAsStream(caminho);

            if (inputStream == null) {
                throw new RuntimeException("Arquivo não encontrado: " + caminho);
            }

            imgs_chegada[i] = new ImageView(new Image(inputStream));
            imgs_chegada[i].setFitWidth(largura / 8f);
            imgs_chegada[i].setFitHeight(largura / 8f);
            imgs_chegada[i].setLayoutX(largura * 0.4375f);
            imgs_chegada[i].setLayoutY(largura * (13f / 48f));
            imgs_chegada[i].setVisible(false);
            root.getChildren().add(imgs_chegada[i]);
        }

        return imgs_chegada;
    }

    private Button definirCaixaNome(String nome) {
        float tam = largura / 480f, x = getAuxiliarX(), y = getAuxiliarY();
        Button caixa_nome = new Button(nome);
        String formato = String.format(
                "-fx-background-color: %s; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: %fpx;",
                cor_escura, tam);

        caixa_nome.setPrefSize(largura / 8f, largura / 32f);
        caixa_nome.setLayoutX(x);
        caixa_nome.setLayoutY(y);
        caixa_nome.setFont(Font.font(largura / 80f));
        caixa_nome.setStyle(formato);
        root.getChildren().add(caixa_nome);

        return caixa_nome;
    }

    public void mostrarImagemChegada() {
        this.imgs_chegada[this.indice_img].setVisible(true);
        ++this.indice_img;
    }

    public void pintarBordaBranco(boolean pintar) {
        if (pintar) {
            circ_grande.setStroke(Color.WHITE);

            for (int i = 0; i < 4; i++)
                circ_pequenos[i].setStroke(Color.WHITE);

            caixa_nome.setStyle(formato_caixa_branca);
        } else {
            circ_grande.setStroke(Color.BLACK);

            for (int i = 0; i < 4; i++)
                circ_pequenos[i].setStroke(Color.BLACK);

            caixa_nome.setStyle(formato_caixa_preta);
        }
    }

    public Peca getPeca(int i) {
        return pecas[i];
    }

    public String getCorHexadecimal() {
        return this.cor_hexa;
    }

    public String getCor() {
        return this.cor;
    }

    public void setPerguntaAcertada(boolean pergunta_acertada) {
        this.pergunta_acertada = pergunta_acertada;
    }

    public boolean getPerguntaAcertada() {
        return pergunta_acertada;
    }
}
