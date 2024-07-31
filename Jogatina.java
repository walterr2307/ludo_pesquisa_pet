import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Jogatina {
    private int qtd_jogs, indice_atual, valor_dados;
    private boolean jogada_finalizada;
    private Jogador jog;
    private ArrayList<Jogador> jogs;

    public Jogatina(ArrayList<Jogador> jogs, int qtd_jogs) {
        this.qtd_jogs = qtd_jogs;
        this.jogs = jogs;
        this.jog = jogs.get(0);
        this.indice_atual = 0;
        this.jogada_finalizada = false;
    }

    public void AtualizarJogador() {
        indice_atual = (indice_atual + 1) % qtd_jogs;
        jog = jogs.get(indice_atual);
    }

    public void ajustarJogadores(Pane root, String[] cores, int largura, Tabuleiro tabuleiro)
            throws FileNotFoundException {

        for (int i = 0; i < qtd_jogs; i++) {
            jogs.add(new Jogador(cores[i]));
            jogs.get(i).setXQuadBrancos(tabuleiro.getXQuadBrancos());
            jogs.get(i).setYQuadBrancos(tabuleiro.getYQuadBrancos());
            jogs.get(i).setXBases(tabuleiro.getXBases());
            jogs.get(i).setYBases(tabuleiro.getYBases());
            jogs.get(i).gerarPecas(root, largura);
        }
    }

    public void intercalarJogadores(Tabuleiro tabuleiro) {
        // Obtém o botão girar dados do tabuleiro
        Button btn_girar_dados = tabuleiro.getBotaoGirarDados();

        // Cria uma timeline que executa uma ação a cada 1 segundo
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), evento1 -> {

            // Verifica se alguma peça do jogador atual pode fazer uma jogada válida com o
            // valor dos dados
            for (int i = 0; i < 4; i++) {
                if (!jog.getPeca(i).verificarJogadaDisponivel(valor_dados)) {
                    jogada_finalizada = true;
                    break;
                }
            }

            // Define a ação a ser executada quando o botão girar dados for clicado
            btn_girar_dados.setOnAction(evento2 -> {
                // O jogador atual gira os dados e armazena o valor resultante
                valor_dados = jog.girarDados();

                // Atualiza o estilo do botão
                tabuleiro.atualizarBotao(valor_dados, jog.getCorHexadecimal());

                // Habilita os botões das peças do jogador atual para permitir a seleção de uma
                // peça
                for (int i = 0; i < 4; i++)
                    jog.getPeca(i).getBotao().setDisable(false);

                // Desabilita o botão girar dados enquanto a jogada está em andamento
                btn_girar_dados.setDisable(true);
            });

            // Define a ação a ser executada quando um botão de peça é clicado
            jog.getPeca(0).getBotao().setOnAction(evento3 -> {
                // Move a peça selecionada pelo valor dos dados e marca a jogada como finalizada
                jog.getPeca(0).mover(valor_dados);
                jogada_finalizada = true;
            });
            jog.getPeca(1).getBotao().setOnAction(evento3 -> {
                // Move a peça selecionada pelo valor dos dados e marca a jogada como finalizada
                jog.getPeca(1).mover(valor_dados);
                jogada_finalizada = true;
            });
            jog.getPeca(2).getBotao().setOnAction(evento3 -> {
                // Move a peça selecionada pelo valor dos dados e marca a jogada como finalizada
                jog.getPeca(2).mover(valor_dados);
                jogada_finalizada = true;
            });
            jog.getPeca(3).getBotao().setOnAction(evento3 -> {
                // Move a peça selecionada pelo valor dos dados e marca a jogada como finalizada
                jog.getPeca(3).mover(valor_dados);
                jogada_finalizada = true;
            });

            // Verifica se a jogada foi finalizada
            if (jogada_finalizada) {
                // Desabilita os botões das peças do jogador atual após a jogada ser finalizada
                for (int i = 0; i < 4; i++)
                    jog.getPeca(i).getBotao().setDisable(true);

                // Habilita o botão girar dados para o próximo jogador
                btn_girar_dados.setDisable(false);

                // Atualiza o índice para o próximo jogador
                this.AtualizarJogador();

                // Reseta a variável de controle de jogada finalizada
                jogada_finalizada = false;
            }
        }));

        // Define a timeline para executar indefinidamente
        timeline.setCycleCount(Animation.INDEFINITE);
        // Inicia a execução da timeline
        timeline.play();
    }

}