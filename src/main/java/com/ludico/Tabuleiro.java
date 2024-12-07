package com.ludico;

import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Tabuleiro {
    private static Tabuleiro instancia;
    private final Imagens imagens = new Imagens();
    private final QuadradosBrancos quadrados_brancos = new QuadradosBrancos();
    private final RetasFinais retas_finais = new RetasFinais();
    private final Circulos circulos = new Circulos();

    private Tabuleiro() {
        imagens.colocarCentro();
    }

    public static Tabuleiro instanciar() {
        if (instancia == null)
            instancia = new Tabuleiro();

        return instancia;
    }

    public float[] getXQuadBrancos() {
        return quadrados_brancos.getXQuadBrancos();
    }

    public float[] getYQuadBrancos() {
        return quadrados_brancos.getXQuadBrancos();
    }

    public float[] getXQuadFinais(int i) {
        return retas_finais.getXQuadFinais(i);
    }

    public float[] getYQuadFinais(int i) {
        return retas_finais.getYQuadFinais(i);
    }

    public Button getBotaoGirarDado() {
        return imagens.getBotaoGirarDado();
    }

    public void setJogador(Jogador jog) {
        imagens.setJogador(jog);
    }

    public void setBotaoAtivado(Boolean btn_ativado) {
        imagens.setBotaoAtivado(btn_ativado);
    }

    public boolean getBotaoAtivado() {
        return imagens.getBotaoAtivado();
    }

    public int getValorDado() {
        return imagens.getValorDado();
    }

    public Rectangle getRetanguloDado() {
        return imagens.getRetanguloDado();
    }

    public float[] getXBases(int i) {
        return circulos.getXBases(i);
    }

    public float[] getYBases(int i) {
        return circulos.getXBases(i);
    }

    public Circle getCirculoGrande(int i) {
        return getCirculoGrande(i);
    }

    public Circle[] getCirculosPequenos(int i) {
        return getCirculosPequenos(i);
    }
}
