package com.ludico;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main {
    private final static float largura = 800f, altura = largura / 1.5f;
    private final static Pane root = new Pane();

    public static float getLargura() {
        return largura;
    }

    public static float getAltura() {
        return altura;
    }

    public static Pane getRoot() {
        return root;
    }

    public static void iniciarTabuleiro(String[] nomes, String[] temas, String[] cores) {
        Scene scene = new Scene(root, largura, altura);
        Stage stage = new Stage();

        Tabuleiro.instanciar();

        stage.setTitle("Lúdico");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        TelaInicial.main(args);
    }
}
