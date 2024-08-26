import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Jogatina {
    private int qtd_jogs, indice_atual;
    private String primeira_cor, nomes[];
    private Stage stage;
    private Peca peca_vencedora, pecas_registradas[] = new Peca[52];
    private TelaPerguntas tela_perguntas;
    private Jogador jog;
    private ArrayList<Jogador> jogs;

    public Jogatina(int qtd_jogs, String primeira_cor, String nomes[], Stage stage, TelaPerguntas tela_Perguntas) {
        this.tela_perguntas = tela_Perguntas;
        this.nomes = nomes;
        this.jogs = new ArrayList<Jogador>();
        this.qtd_jogs = qtd_jogs;
        this.primeira_cor = primeira_cor;
        this.stage = stage;
        this.indice_atual = 0;

        for (int i = 0; i < 52; i++)
            this.pecas_registradas[i] = null;
    }

    private int getTempoPausa() {
        float tempo_pausa = 0f;

        for (int i = 0; i < 4; i++)
            tempo_pausa += jog.getPeca(i).getTempoPausa();

        return (int) (tempo_pausa * 1000);
    }

    private void reiniciarPecas() {
        for (int i = 0; i < 4; i++) {
            jog.getPeca(i).setJogadaFinalizada(false);
            jog.getPeca(i).getBotao().setDisable(true);
        }
    }

    private void reajustarBotao(Tabuleiro tabuleiro) {
        tabuleiro.getBotao().setDisable(false);
        tabuleiro.setBotaoAtivado(true);
        tabuleiro.getRetangulo().setOpacity(1);
    }

    private void ajustarVez(boolean minha_vez) {
        for (int i = 0; i < 4; i++)
            jog.getPeca(i).setMinhaVez(minha_vez);
    }

    private void atualizarJogador(Tabuleiro tabuleiro, Peca peca) {
        for (int i = 0; i < 4; i++)
            jog.getPeca(i).zerarTempoPausa();

        if (peca != null) {
            if (peca.getJogarDeNovo()) {
                peca.setJogarDeNovo(false);
                return;
            }
        }

        if (tabuleiro.getValorDado() != 6) {
            this.ajustarVez(false);
            jog.pintarBordaBranco(false);
            indice_atual = (indice_atual + 1) % qtd_jogs;

            jog = jogs.get(indice_atual);
            this.ajustarVez(true);
            jog.pintarBordaBranco(true);
            tabuleiro.setJogador(jog);
        }
    }

    private void verificarVencedor() {
        boolean venceu = true;

        for (int i = 0; i < 4; i++) {
            if (!jog.getPeca(i).getTipoPos().equals("linha_chegada")) {
                venceu = false;
                break;
            }
        }

        if (venceu) {
            Platform.runLater(() -> {
                stage.close();

                GameOver game_over = new GameOver(jog);
                game_over.mostrarTela();
            });
        }
    }

    public void ajustarJogadores(Pane root, String[] cores, int largura, Tabuleiro tabuleiro)
            throws FileNotFoundException {

        if (qtd_jogs == 4) {
            if (primeira_cor.equals("verde"))
                indice_atual = 0;
            if (primeira_cor.equals("amarelo"))
                indice_atual = 1;
            if (primeira_cor.equals("vermelho"))
                indice_atual = 2;
            if (primeira_cor.equals("azul"))
                indice_atual = 0;
        }

        for (int i = 0; i < qtd_jogs; i++) {
            jogs.add(new Jogador(root, largura, cores[i], nomes[i], tabuleiro, tela_perguntas));
        }
    }

    public void setPecaVencedora(Peca peca_vencedora) {
        this.peca_vencedora = peca_vencedora;
    }

    public void intercalarJogadores(Tabuleiro tabuleiro) {
        // Obtém o primeiro jogador da lista de jogadores
        jog = jogs.get(indice_atual);

        // Define o jogador no tabuleiro e pinta a borda de branco
        tabuleiro.setJogador(jog);
        jog.pintarBordaBranco(true);
        this.ajustarVez(true);

        // Loop principal do jogo
        while (true) {
            int i, tempo_pausa;
            boolean jogadas_disponiveis = false;
            Peca peca = null, peca2;

            // Espera até que o botão no tabuleiro seja desativado
            while (tabuleiro.getBotaoAtivado()) {
                // Loop vazio aguardando desativação do botão
                try {
                    Thread.sleep(100); // Pequena pausa para evitar uso excessivo de CPU
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Verifica se o jogador tem alguma jogada disponível
            for (i = 0; i < 4; i++) {
                if (jog.getPeca(i).verificarJogadaDisponivel()) {
                    jogadas_disponiveis = true;
                    break; // Sai do loop assim que encontrar uma jogada disponível
                }
            }

            // Se há jogadas disponíveis
            if (jogadas_disponiveis) {
                // Espera até que todas as jogadas do jogador sejam finalizadas
                while (!jog.getPeca(0).getJogadaFinalizada()
                        && !jog.getPeca(1).getJogadaFinalizada()
                        && !jog.getPeca(2).getJogadaFinalizada()
                        && !jog.getPeca(3).getJogadaFinalizada()) {
                    // Loop vazio aguardando finalização das jogadas
                    try {
                        Thread.sleep(100); // Pequena pausa para evitar uso excessivo de CPU
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (!jog.getPerguntaAcertada()) {
                    jog.setPerguntaAcertada(true);
                    this.reiniciarPecas();
                    this.reajustarBotao(tabuleiro);
                    this.atualizarJogador(tabuleiro, peca);
                    continue;
                }

                for (i = 0; i < 4; i++) {
                    if (jog.getPeca(i).getJogadaFinalizada()) {
                        peca = jog.getPeca(i);
                        break;
                    }
                }

                peca_vencedora = peca;
                tempo_pausa = this.getTempoPausa();
                this.reiniciarPecas();

                try {
                    Thread.sleep(tempo_pausa);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                jog.encontrarPecasIguais(peca);
                peca2 = pecas_registradas[peca.getPos()];

                try {
                    // Pausa de 250 milissegundos antes de iniciar a verificação
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    // Tratamento de exceção em caso de interrupção da thread
                    e.printStackTrace();
                }

                if (peca.getTipoPos().equals("quad_branco") && peca2 != null)
                    jog.encontrarPecasDiferentes(peca, peca2, this);

                jog.registrarMovimento(peca, peca_vencedora, pecas_registradas);
            } else {
                this.reiniciarPecas();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.reajustarBotao(tabuleiro);
            this.verificarVencedor();
            this.atualizarJogador(tabuleiro, peca);
        }
    }
}