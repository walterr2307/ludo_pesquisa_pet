package com.ludico;

import javafx.scene.shape.Circle;

public class JogadorAmarelo extends Jogador {

    public JogadorAmarelo(String nome) {
        super(nome);
    }

    @Override
    protected float getAuxiliarX() {
        return largura / 16f;
    }

    @Override
    protected float getAuxiliarY() {
        return largura / 48f;
    }

    @Override
    protected String definirCor() {
        return "amarelo";
    }

    @Override
    protected String definirCorHexadecimal() {
        return "#FFFF00";
    }

    @Override
    protected String definirCorEscura() {
        return "#B8860B";
    }

    @Override
    protected float[] definirXBases() {
        return tabuleiro.getXBases(1);
    }

    @Override
    protected float[] definirYBases() {
        return tabuleiro.getYBases(1);
    }

    @Override
    protected Circle definirCirculoGrande() {
        return tabuleiro.getCirculoGrande(1);
    }

    @Override
    protected Circle[] definirCirculosPequenos() {
        return tabuleiro.getCirculosPequenos(1);
    }

    @Override
    protected Peca[] gerarPecas() {
        return new Peca[] {new PecaAmarela(), new PecaAmarela(), new PecaAmarela(), new PecaAmarela()};
    }
}
