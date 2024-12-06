package com.ludico;

public class Tabuleiro {
    private static Tabuleiro instancia;
    private Quadrados quadrados = new Quadrados();

    private Tabuleiro() {
    }

    public static Tabuleiro instanciar() {
        if (instancia == null)
            instancia = new Tabuleiro();

        return instancia;
    }

    public float[] getXQuadBrancos() {
        return quadrados.getXQuadBrancos();
    }

    public float[] getYQuadBrancos() {
        return quadrados.getXQuadBrancos();
    }
}
