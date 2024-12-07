package com.ludico;

import javafx.scene.shape.Circle;

public class JogadorAzul extends Jogador {

    public JogadorAzul(String nome) {
        super(nome);
    }

    @Override
    protected float getAuxiliarX() {
        return largura * (39f / 48f);
    }

    @Override
    protected float getAuxiliarY() {
        return largura * (59f / 96f);
    }

    @Override
    protected String definirCor() {
        return "azul";
    }

    @Override
    protected String definirCorHexadecimal() {
        return "#1E81B0";
    }

    @Override
    protected String definirCorEscura() {
        return "#00008B";
    }

    @Override
    protected float[] definirXBases() {
        return tabuleiro.getXBases(3);
    }

    @Override
    protected float[] definirYBases() {
        return tabuleiro.getYBases(3);
    }

    @Override
    protected Circle definirCirculoGrande() {
        return tabuleiro.getCirculoGrande(3);
    }

    @Override
    protected Circle[] definirCirculosPequenos() {
        return tabuleiro.getCirculosPequenos(3);
    }

    @Override
    protected Peca[] gerarPecas() {
        return new Peca[] {new PecaAzul(), new PecaAzul(), new PecaAzul(), new PecaAzul()};
    }
}
