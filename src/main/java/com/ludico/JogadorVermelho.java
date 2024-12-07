package com.ludico;

import javafx.scene.shape.Circle;

public class JogadorVermelho extends Jogador {

    public JogadorVermelho(String nome) {
        super(nome);
    }

    @Override
    protected float getAuxiliarX() {
        return largura * (39f / 48f);
    }

    @Override
    protected float getAuxiliarY() {
        return largura / 48f;
    }

    @Override
    protected String definirCor() {
        return "vermelho";
    }

    @Override
    protected String definirCorHexadecimal() {
        return "#FF0000";
    }

    @Override
    protected String definirCorEscura() {
        return "#8B0000";
    }

    @Override
    protected float[] definirXBases() {
        return tabuleiro.getXBases(2);
    }

    @Override
    protected float[] definirYBases() {
        return tabuleiro.getYBases(2);
    }

    @Override
    protected Circle definirCirculoGrande() {
        return tabuleiro.getCirculoGrande(2);
    }

    @Override
    protected Circle[] definirCirculosPequenos() {
        return tabuleiro.getCirculosPequenos(2);
    }

    @Override
    protected Peca[] gerarPecas() {
        return new Peca[] {new PecaVermelha(), new PecaVermelha(), new PecaVermelha(), new PecaVermelha()};
    }
}
