import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Jogador {
    private int indice_img;
    private boolean pergunta_acertada;
    private String cor, cor_hexa;
    private Circle circ_grande, circ_pequenos[];
    private Peca pecas[];
    private TelaPerguntas tela_perguntas;
    private ImageView imgs_chegada[];
    private ArrayList<Integer> x_quad_brancos, y_quad_brancos;
    private ArrayList<Double> x_bases, y_bases;

    public Jogador(Pane root, int largura, String cor, Tabuleiro tabuleiro, TelaPerguntas tela_perguntas)
            throws FileNotFoundException {
        this.indice_img = 0;
        this.pergunta_acertada = true;
        this.cor = cor;
        this.tela_perguntas = tela_perguntas;
        this.cor_hexa = this.definirCorHexadecimal();
        this.imgs_chegada = this.gerarImagensChegada(root, largura);
        this.x_bases = new ArrayList<Double>();
        this.y_bases = new ArrayList<Double>();

        this.setXBases(tabuleiro.getXBases());
        this.setYBases(tabuleiro.getYBases());
        this.setXQuadBrancos(tabuleiro.getXQuadBrancos());
        this.setYQuadBrancos(tabuleiro.getYQuadBrancos());

        this.circ_pequenos = this.setCirculosPequenos(tabuleiro.getCirculosPequenos());
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
            cor_hexa = "#1E81B0";

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

    private Circle[] setCirculosPequenos(ArrayList<Circle> circulos) {
        int i, j = 0, inicio, fim;
        Circle circ_pequenos[] = new Circle[4];

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
            circ_pequenos[j] = circulos.get(i);
            ++j;
        }

        return circ_pequenos;
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
        Peca pecas[] = new Peca[4];

        for (int i = 0; i < 4; i++) {
            pecas[i] = new Peca(root, cor, this.x_bases.get(i), this.y_bases.get(i), this.x_quad_brancos,
                    this.y_quad_brancos, tabuleiro.getXQuadFinais(), tabuleiro.getYQuadFinais(), largura, this,
                    tela_perguntas);
        }

        return pecas;
    }

    private ImageView[] gerarImagensChegada(Pane root, int largura) throws FileNotFoundException {
        ImageView imgs_chegada[] = new ImageView[4];

        for (int i = 0; i < 4; i++) {
            String caminho = String.format("imagens\\chegada_%s%d.png", cor, i + 1);
            imgs_chegada[i] = new ImageView(new Image(new FileInputStream(caminho)));
            imgs_chegada[i].setFitWidth(largura / 8f);
            imgs_chegada[i].setFitHeight(largura / 8f);
            imgs_chegada[i].setLayoutX(largura * 0.4375f);
            imgs_chegada[i].setLayoutY(largura * (13f / 48f));
            imgs_chegada[i].setVisible(false);
            root.getChildren().add(imgs_chegada[i]);
        }

        return imgs_chegada;
    }

    public void mostrarImagemChegada() {
        this.imgs_chegada[this.indice_img].setVisible(true);
        ++this.indice_img;
    }

    public void pintarBordaBranco(boolean pintar) {
        if (pintar) {
            circ_grande.setStroke(Color.WHITE);

            for (int i = 0; i < 4; i++)
                circ_pequenos[i].setStroke(Color.WHITE);
        } else {
            circ_grande.setStroke(Color.BLACK);

            for (int i = 0; i < 4; i++)
                circ_pequenos[i].setStroke(Color.BLACK);
        }
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
                    Thread.sleep(500);
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

    public boolean mostrarTela() {
        tela_perguntas.gerarTela(cor);
        pergunta_acertada = tela_perguntas.getPerguntaAcertada();

        return pergunta_acertada;
    }

    public void encontrarPecasDiferentes(Peca peca1, Peca peca2, Jogatina jogatina) {
        int tempo_pausa;
        String cor1 = peca1.getCor(), cor2 = peca2.getCor();
        Peca copia;
        CountDownLatch latch = new CountDownLatch(1);

        try {
            // Pausa de 500 milissegundos antes de iniciar a verificação
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // Tratamento de exceção em caso de interrupção da thread
            e.printStackTrace();
        }

        if (cor1.equals(cor2))
            return;

        Platform.runLater(() -> {
            try {
                // Código que manipula a UI
                mostrarTela();
            } finally {
                latch.countDown(); // Libera a thread que está aguardando
            }
        });

        try {
            latch.await(); // Faz a thread atual aguardar até que countDown() seja chamado
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (pergunta_acertada) {
            peca1.setJogarDeNovo(true);
        } else {
            pergunta_acertada = true;
            jogatina.setPecaVencedora(peca2);
            copia = peca1;
            peca1 = peca2;
            peca2 = copia;
        }

        // Move a segunda peça sem pulo e armazena o tempo de pausa necessário
        tempo_pausa = peca2.moverSemPulo();

        try {
            Thread.sleep(tempo_pausa); // Pausa a thread pelo tempo necessário para mover a peça
        } catch (InterruptedException e) {
            e.printStackTrace(); // Tratamento de exceção em caso de interrupção da thread
        }
    }

    public void registrarMovimento(Peca peca, Peca peca_vencedora, Peca[] pecas_registradas) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (peca.getTipoAnterior().equals("quad_branco"))
            pecas_registradas[peca.getPosAnterior()] = null;
        if (peca.getTipoPos().equals("quad_branco"))
            pecas_registradas[peca_vencedora.getPos()] = peca_vencedora;

        peca.getImagem().setViewOrder(0f);
    }

    public String getCor() {
        return this.cor;
    }

    public void setPerguntaAcertada(boolean pergunta_acertada) {
        this.pergunta_acertada = pergunta_acertada;
    }

    public boolean getPerguntaAcertada() {
        return this.pergunta_acertada;
    }
}
