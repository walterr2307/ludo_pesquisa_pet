package com.ludico;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class RetasFinais {
    private final float[][] x_quad_finais = new float[4][6], y_quad_finais = new float[4][6];
    private final static float largura = Main.getLargura(), altura = Main.getAltura();
    private final static Pane root = Main.getRoot();

    public RetasFinais() {
        colocarRetasFinais();
    }

    private void colocarRetasFinais() {
        int k = 0;
        float x = largura * (23f / 48f), y = altura * 0.84375f, tam_quad = largura / 24f;
        ArrayList<Float> x_quad_finais = new ArrayList<>(), y_quad_finais = new ArrayList<>();
        Rectangle quad = new Rectangle();

        for (int i = 0; i < 6; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x);
            quad.setY(y - i * tam_quad);
            quad.setFill(Color.web("#008C00"));
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_finais.add(x);
            y_quad_finais.add(y - i * tam_quad);
        }

        x -= largura / 4f;
        y -= altura * 0.375f;

        for (int i = 0; i < 6; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x + i * tam_quad);
            quad.setY(y);
            quad.setFill(Color.web("#B8860B"));
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_finais.add(x + i * tam_quad);
            y_quad_finais.add(y);
        }

        x += largura / 4f;
        y -= altura * 0.375f;

        for (int i = 0; i < 6; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x);
            quad.setY(y + i * tam_quad);
            quad.setFill(Color.web("#8C0000"));
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_finais.add(x);
            y_quad_finais.add(y + i * tam_quad);
        }

        x += largura / 4f;
        y += altura * 0.375f;

        for (int i = 0; i < 6; i++) {
            quad = new Rectangle();
            quad.setWidth(tam_quad);
            quad.setHeight(tam_quad);
            quad.setX(x - i * tam_quad);
            quad.setY(y);
            quad.setFill(Color.web("#00008C"));
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2);
            root.getChildren().add(quad);
            x_quad_finais.add(x - i * tam_quad);
            y_quad_finais.add(y);
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                this.x_quad_finais[i][j] = x_quad_finais.get(k);
                this.y_quad_finais[i][j] = y_quad_finais.get(k);
                ++k;
            }
        }
    }

    public float[] getXQuadFinais(int i) {
        return x_quad_finais[i];
    }

    public float[] getYQuadFinais(int i) {
        return y_quad_finais[i];
    }
}
