import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    // Define a largura e altura da janela da aplicação
    private int largura = 720, altura = (int) (largura / 1.5f), qtd_jogs = 4;
    private Jogatina jogatina;

    // Método principal de inicialização da aplicação JavaFX
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        String cores[] = { "verde", "amarelo", "vermelho", "azul" };
        Pane root = new Pane();
        Scene scene = new Scene(root, largura, altura);
        Tabuleiro tabuleiro = new Tabuleiro(root, largura, altura);

        // Inicializa a instância de Jogatina com a lista de jogadores e a quantidade de jogadores
        jogatina = new Jogatina(qtd_jogs);

        // Ajusta os jogadores no tabuleiro
        jogatina.ajustarJogadores(root, cores, largura, tabuleiro);

        // Configura a cena no palco, define o título e exibe a janela
        stage.setScene(scene);
        stage.setTitle("Ludo");
        stage.show();

        // Inicia a alternância de jogadores em um novo thread para não bloquear o JavaFX Application Thread
        new Thread(() -> jogatina.intercalarJogadores(tabuleiro)).start();
    }

    // Método principal para lançar a aplicação
    public static void main(String[] args) {
        launch(args);
    }
}