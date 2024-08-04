import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Jogador {
    private String cor, cor_hexa;
    private Circle circ_grande;
    private Peca pecas[];
    private ArrayList<Integer> x_quad_brancos, y_quad_brancos;
    private ArrayList<Double> x_bases, y_bases;

    public Jogador(Pane root, int largura, String cor, Tabuleiro tabuleiro) throws FileNotFoundException {
        this.cor = cor;
        this.cor_hexa = this.definirCorHexadecimal();
        this.x_bases = new ArrayList<Double>();
        this.y_bases = new ArrayList<Double>();

        this.setXBases(tabuleiro.getXBases());
        this.setYBases(tabuleiro.getYBases());
        this.setXQuadBrancos(tabuleiro.getXQuadBrancos());
        this.setYQuadBrancos(tabuleiro.getYQuadBrancos());

        this.circ_grande = this.setCirculoGrande(tabuleiro.getCirculosGrandes());
        this.pecas = this.gerarPecas(root, largura);
    }

    private String definirCorHexadecimal() {
        String cor_hexa;

        if (this.cor.equals("verde"))
            cor_hexa = "#00FF00";
        else if (this.cor.equals("amarelo"))
            cor_hexa = "#FFFF00";
        else if (this.cor.equals("vermelho"))
            cor_hexa = "#FF0000";
        else
            cor_hexa = "#0000FF";

        return cor_hexa;
    }

    private void setXQuadBrancos(ArrayList<Integer> x_quad_brancos) {
        this.x_quad_brancos = x_quad_brancos;
    }

    private void setYQuadBrancos(ArrayList<Integer> y_quad_brancos) {
        this.y_quad_brancos = y_quad_brancos;
    }

    private void setXBases(ArrayList<Double> x_bases_tabuleiro) {
        int i, inicio, fim;

        if (this.cor.equals("verde"))
            inicio = 0;
        else if (this.cor.equals("amarelo"))
            inicio = 4;
        else if (this.cor.equals("vermelho"))
            inicio = 8;
        else
            inicio = 12;

        fim = inicio + 4;

        for (i = inicio; i < fim; i++) {
            this.x_bases.add(x_bases_tabuleiro.get(i));
        }
    }

    private void setYBases(ArrayList<Double> y_bases_tabuleiro) {
        int i, inicio, fim;

        if (this.cor.equals("verde"))
            inicio = 0;
        else if (this.cor.equals("amarelo"))
            inicio = 4;
        else if (this.cor.equals("vermelho"))
            inicio = 8;
        else
            inicio = 12;

        fim = inicio + 4;

        for (i = inicio; i < fim; i++) {
            this.y_bases.add(y_bases_tabuleiro.get(i));
        }
    }

    private Circle setCirculoGrande(Circle[] circ_grandes) {
        Circle circ_grande;

        if (this.cor.equals("verde"))
            circ_grande = circ_grandes[0];
        else if (this.cor.equals("amarelo"))
            circ_grande = circ_grandes[1];
        else if (this.cor.equals("vermelho"))
            circ_grande = circ_grandes[2];
        else
            circ_grande = circ_grandes[3];

        return circ_grande;
    }

    private Peca[] gerarPecas(Pane root, int largura) throws FileNotFoundException {
        int i;
        Peca pecas[] = new Peca[4];

        for (i = 0; i < 4; i++) {
            pecas[i] = new Peca(root, cor, this.x_bases.get(i), this.y_bases.get(i), this.x_quad_brancos,
                    this.y_quad_brancos, largura);
        }

        return pecas;
    }

    public void pintarBordaBranco(boolean pintar) {
        if (pintar)
            circ_grande.setStroke(Color.WHITE);
        else
            circ_grande.setStroke(Color.BLACK);
    }

    public Peca getPeca(int i) {
        return pecas[i];
    }

    public String getCorHexadecimal() {
        return this.cor_hexa;
    }

}
