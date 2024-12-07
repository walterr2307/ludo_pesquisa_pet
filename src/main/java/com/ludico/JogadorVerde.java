package com.ludico;

import javafx.scene.shape.Circle;

public class JogadorVerde extends Jogador {

    public JogadorVerde(String nome) {
        super(nome);
    }

    @Override
    protected float getAuxiliarX() {
        return largura / 16f;
    }

    @Override
    protected float getAuxiliarY() {
        return largura * (59f / 96f);
    }

    @Override
    protected String definirCor() {
        return "verde";
    }

    @Override
    protected String definirCorHexadecimal() {
        return "#00FF00";
    }

    @Override
    protected String definirCorEscura() {
        return "#008B00";
    }

    @Override
    protected float[] definirXBases() {
        return tabuleiro.getXBases(0);
    }

    @Override
    protected float[] definirYBases() {
        return tabuleiro.getYBases(0);
    }

    @Override
    protected Circle definirCirculoGrande() {
        return tabuleiro.getCirculoGrande(0);
    }

    @Override
    protected Circle[] definirCirculosPequenos() {
        return tabuleiro.getCirculosPequenos(0);
    }

    @Override
    protected Peca[] gerarPecas() {
        return new Peca[] {new PecaVerde(), new PecaVerde(), new PecaVerde(), new PecaVerde()};
    }
}
