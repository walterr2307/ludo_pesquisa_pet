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
        this.pecas = this.gerarPecas(root, largura, tabuleiro);
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

    private Peca[] gerarPecas(Pane root, int largura, Tabuleiro tabuleiro) throws FileNotFoundException {
        int i;
        Peca pecas[] = new Peca[4];

        for (i = 0; i < 4; i++) {
            pecas[i] = new Peca(root, cor, this.x_bases.get(i), this.y_bases.get(i), this.x_quad_brancos,
                    this.y_quad_brancos, tabuleiro.getXQuadFinais(), tabuleiro.getYQuadFinais(), largura);
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

    public void encontrarPecasIguais(Peca peca) {
        int i, j = 0;
        Peca pecas_registradas[] = new Peca[3];

        for (i = 0; i < 4; i++) {
            if (this.pecas[i] != peca) {
                pecas_registradas[j] = this.pecas[i];
                ++j;
            }
        }

        for (i = 0; i < 3; i++) {
            if (this.PosicaoIgual(peca, pecas_registradas)) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                peca.moverComPulo();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                break;
            }
        }
    }

    private boolean PosicaoIgual(Peca peca, Peca[] pecas_registradas) {
        boolean igual = false;

        for (int i = 0; i < 3; i++) {
            if (peca.getPos() == pecas_registradas[i].getPos()
                    && peca.getTipoPos().equals(pecas_registradas[i].getTipoPos())
                    && !peca.getTipoPos().equals("linha_chegada")) {
                igual = true;
                break;
            }
        }

        return igual;
    }

    public void encontrarPecasDiferentes(Peca peca, Peca[] pecas_registradas) {
        int tempo_pausa;

        if (pecas_registradas[peca.getPos()] != null && peca.getTipoPos().equals("quad_branco")) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tempo_pausa = pecas_registradas[peca.getPos()].moverSemPulo();

            try {
                Thread.sleep(tempo_pausa);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            peca.setJogarDeNovo(true);
        }
    }

    public void registrarMovimento(Peca peca, Peca[] pecas_registradas) {
        if (peca.getTipoAnterior().equals("quad_branco"))
            pecas_registradas[peca.getPosAnterior()] = null;
        if (peca.getTipoPos().equals("quad_branco"))
            pecas_registradas[peca.getPos()] = peca;
    }

    public String getCor() {
        return this.cor;
    }
}
