import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    // Define a largura e altura da janela da aplicação
    private int largura = 960, altura = (int) (largura / 1.5f), qtd_jogs = 4;

    // Método principal de inicialização da aplicação JavaFX
    public void start(Stage stage) throws FileNotFoundException {
        String cores[] = { "verde", "amarelo", "vermelho", "azul" };
        Pane root = new Pane();
        Scene scene = new Scene(root, largura, altura);
        Tabuleiro tabuleiro = new Tabuleiro(root, largura, altura);
        Jogatina jogatina;
        ArrayList<Jogador> jogs = new ArrayList<Jogador>();

        // Adiciona jogadores à lista de acordo com as cores definidas
        for (int i = 0; i < qtd_jogs; i++)
            jogs.add(new Jogador(cores[i]));

        // Inicializa a instância de Jogatina com a lista de jogadores e a quantidade de jogadores
        jogatina = new Jogatina(jogs, qtd_jogs);
        
        // Ajusta os jogadores no tabuleiro
        jogatina.ajustarJogadores(root, cores, largura, tabuleiro);
        
        // Configura a alternância de jogadores
        jogatina.intercalarJogadores(tabuleiro);

        // Configura a cena no palco, define o título e exibe a janela
        stage.setScene(scene);
        stage.setTitle("Ludo");
        stage.show();
    }

    // Método principal para lançar a aplicação
    public static void main(String[] args) {
        launch(args);
    }
}