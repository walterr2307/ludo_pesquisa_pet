import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.layout.Pane;

public class Jogador {
    private String cor, cor_hexa;
    private Peca pecas[];
    private ArrayList<Integer> x_quad_brancos, y_quad_brancos;
    private ArrayList<Double> x_bases, y_bases;

    public Jogador(String cor) throws FileNotFoundException {
        this.cor = cor;
        this.pecas = new Peca[4];
        this.x_bases = new ArrayList<Double>();
        this.y_bases = new ArrayList<Double>();
        this.cor_hexa = this.definirCorHexadecimal();
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

    public void setXQuadBrancos(ArrayList<Integer> x_quad_brancos) {
        this.x_quad_brancos = x_quad_brancos;
    }

    public void setYQuadBrancos(ArrayList<Integer> y_quad_brancos) {
        this.y_quad_brancos = y_quad_brancos;
    }

    public void setXBases(ArrayList<Double> x_bases_tabuleiro) {
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

    public void setYBases(ArrayList<Double> y_bases_tabuleiro) {
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

    public void gerarPecas(Pane root, int largura) throws FileNotFoundException {
        int i;
        pecas = new Peca[4];

        for (i = 0; i < 4; i++) {
            pecas[i] = new Peca(root, cor, this.x_bases.get(i), this.y_bases.get(i), x_quad_brancos, y_quad_brancos,
                    largura);
        }
    }

    public int girarDados() {
        int valor_dados = (int) (Math.random() * 6 + 1);

        return valor_dados;
    }

    public Peca getPeca(int i) {
        return pecas[i];
    }

    public String getCorHexadecimal() {
        return this.cor_hexa;
    }

}
