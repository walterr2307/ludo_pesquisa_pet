package com.ludico;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class QuadradosBrancos {
    private final float[] x_quad_brancos = new float[52], y_quad_brancos = new float[52];
    private final static float largura = Main.getLargura(), altura = Main.getAltura();
    private final static Pane root = Main.getRoot();

    public QuadradosBrancos() {
        colocarQuadBrancos();
    }

    private void colocarQuadBrancos() {
        float tam_quad = largura / 24f, x = largura * (23f / 48f), y = altura * 0.90625f;
        String cor;
        Rectangle quad = new Rectangle();
        ArrayList<Float> x_quad_brancos = new ArrayList<>(), y_quad_brancos = new ArrayList<>();

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

        for (int i = 0; i < 6; i++) {
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

        for (int i = 0; i < 6; i++) {
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

        for (int i = 0; i < 2; i++) {
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

        for (int i = 0; i < 5; i++) {
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

        for (int i = 0; i < 6; i++) {
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

        for (int i = 0; i < 2; i++) {
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

        for (int i = 0; i < 5; i++) {
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

        for (int i = 0; i < 6; i++) {
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

        for (int i = 0; i < 2; i++) {
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

        for (int i = 0; i < 5; i++) {
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

        for (int i = 0; i < 6; i++) {
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

        for (int i = 0; i < 52; i++) {
            this.x_quad_brancos[i] = x_quad_brancos.get(i);
            this.y_quad_brancos[i] = y_quad_brancos.get(i);
        }
    }

    public float[] getXQuadBrancos() {
        return x_quad_brancos;
    }

    public float[] getY_quad_brancos() {
        return y_quad_brancos;
    }
}
