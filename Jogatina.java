import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Jogatina {
    private int qtd_jogs, indice_atual;
    private Jogador jog;
    private ArrayList<Jogador> jogs;
    private final Object lock = new Object();

    public Jogatina(int qtd_jogs) {
        this.jogs = new ArrayList<Jogador>();
        this.qtd_jogs = qtd_jogs;
        this.indice_atual = 0;
    }

    private float getTempoPausa() {
        float tempo_pausa = 0f;

        for (int i = 0; i < 4; i++)
            tempo_pausa += jog.getPeca(i).getTempoPausa();

        return tempo_pausa;
    }

    private void reiniciarPecas() {
        for (int i = 0; i < 4; i++) {
            jog.getPeca(i).setJogadaFinalizada(false);
            jog.getPeca(i).getBotao().setDisable(true);
        }
    }

    private void reajustarBotao(float tempo_pausa, Tabuleiro tabuleiro) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(tempo_pausa), evento -> {
            tabuleiro.getBotao().setDisable(false);
            tabuleiro.setBotaoAtivado(true);
            synchronized (lock) {
                lock.notify();
            }
        }));

        timeline.setCycleCount(1);
        timeline.play();
    }

    private void ajustarVez(boolean minha_vez) {
        for (int i = 0; i < 4; i++)
            jog.getPeca(i).setMinhaVez(minha_vez);
    }

    private void atualizarJogador(Tabuleiro tabuleiro) {
        if (tabuleiro.getValorDado() != 6) {
            this.ajustarVez(false);
            jog.pintarBordaBranco(false);
            indice_atual = (indice_atual + 1) % qtd_jogs;

            for (int i = 0; i < 4; i++)
                jog.getPeca(i).zerarTempoPausa();

            jog = jogs.get(indice_atual);
            this.ajustarVez(true);
            jog.pintarBordaBranco(true);
            tabuleiro.setJogador(jog);
        }
    }

    public void ajustarJogadores(Pane root, String[] cores, int largura, Tabuleiro tabuleiro)
            throws FileNotFoundException {

        for (int i = 0; i < qtd_jogs; i++)
            jogs.add(new Jogador(root, largura, cores[i], tabuleiro));
    }

    public void intercalarJogadores(Tabuleiro tabuleiro) {
        // Obtém o primeiro jogador da lista de jogadores
        jog = jogs.get(0);

        // Define o jogador no tabuleiro e pinta a borda de branco
        tabuleiro.setJogador(jog);
        jog.pintarBordaBranco(true);
        this.ajustarVez(true);

        // Loop principal do jogo
        while (true) {
            int i;
            float tempo_pausa;
            boolean jogadas_disponiveis = false;

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

                tempo_pausa = this.getTempoPausa();
            } else {
                tempo_pausa = 0.5f;
            }

            this.reiniciarPecas();
            this.reajustarBotao(tempo_pausa, tabuleiro);

            synchronized (lock) {
                try {
                    lock.wait(); // Espera até que a Timeline chame notify
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.atualizarJogador(tabuleiro);
        }
    }

}