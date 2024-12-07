package com.ludico;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Circulos {
    private final static float largura = Main.getLargura(), altura = Main.getAltura();
    private final static Pane root = Main.getRoot();
    private final float[][] x_bases = new float[4][6], y_bases = new float[4][6];
    private final Circle[] circ_grandes = new Circle[4];
    private final Circle[][] circ_pequenos = new Circle[4][4];

    public Circulos() {
        colocarCirculos();
    }

    private void colocarCirculos() {
        float x = largura * 0.3125f, y = altura * (25f / 32f), raio = largura * (5f / 48f);
        String cor;
        Circle circulo;

        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                cor = "#008C00";
                x -= 0;
            } else if (i == 1) {
                cor = "#B8860B";
                y -= largura * 0.375f;
            } else if (i == 2) {
                cor = "#8C0000";
                x += largura * 0.375f;
            } else {
                cor = "#00008C";
                y += largura * 0.375f;
            }

            circulo = new Circle(x, y, raio);
            circulo.setFill(Color.web(cor));
            circulo.setStroke(Color.BLACK);
            circulo.setStrokeWidth(4);
            circ_grandes[i] = circulo;
            root.getChildren().add(circulo);

            adicionarBases(i, x, y, raio);
        }
    }

    private void adicionarBases(int i, double x, double y, double raio) {
        double baseRaio = raio / 4, offset = raio / 2.5;

        Circle circulo1 = new Circle(x - offset, y - offset, baseRaio);
        circulo1.setFill(Color.TRANSPARENT);
        circulo1.setStroke(Color.BLACK);
        circulo1.setStrokeWidth(3);
        x_bases[i][0] = (float) (x - offset - largura / 48f);
        y_bases[i][0] = (float) (x - offset - largura / 48f);

        Circle circulo2 = new Circle(x + offset, y - offset, baseRaio);
        circulo2.setFill(Color.TRANSPARENT);
        circulo2.setStroke(Color.BLACK);
        circulo2.setStrokeWidth(3);
        x_bases[i][1] = (float) (x + offset - largura / 48f);
        y_bases[i][1] = (float) (x - offset - largura / 48f);

        Circle circulo3 = new Circle(x - offset, y + offset, baseRaio);
        circulo3.setFill(Color.TRANSPARENT);
        circulo3.setStroke(Color.BLACK);
        circulo3.setStrokeWidth(3);
        x_bases[i][2] = (float) (x - offset - largura / 48f);
        y_bases[i][2] = (float) (x + offset - largura / 48f);

        Circle circulo4 = new Circle(x + offset, y + offset, baseRaio);
        circulo4.setFill(Color.TRANSPARENT);
        circulo4.setStroke(Color.BLACK);
        circulo4.setStrokeWidth(3);
        x_bases[i][0] = (float) (x + offset - largura / 48f);
        y_bases[i][0] = (float) (x + offset - largura / 48f);

        circ_pequenos[i][0] = circulo1;
        circ_pequenos[i][1] = circulo2;
        circ_pequenos[i][2] = circulo3;
        circ_pequenos[i][3] = circulo4;

        root.getChildren().addAll(circulo1, circulo2, circulo3, circulo4);
    }

    public float[] getXBases(int i) {
        return x_bases[i];
    }

    public float[] getYBases(int i) {
        return y_bases[i];
    }

    public Circle getCirculoGrande(int i) {
        return circ_grandes[i];
    }

    public Circle[] getCirculosPequenos(int i) {
        return circ_pequenos[i];
    }
}
