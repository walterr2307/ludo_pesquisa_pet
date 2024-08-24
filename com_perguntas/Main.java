import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

public class Main extends Application {
    // Define a largura e altura da janela da aplicação
    private int largura = 960, altura = (int) (largura / 1.5f), qtd_jogs = 4;
    private String cor_primeiro_jog;

    @Override
    public void start(Stage primaryStage) {
        // Título do menu
        Label title = new Label("Escolha a quantidade de jogadores");

        // Botões para escolher a quantidade de jogadores
        Button twoPlayersButton = new Button("2 Jogadores");
        Button fourPlayersButton = new Button("4 Jogadores");

        // Ações dos botões
        twoPlayersButton.setOnAction(event -> {
            primaryStage.close();
            try {
                askFirstPlayerColor(2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        fourPlayersButton.setOnAction(event -> {
            primaryStage.close();
            try {
                askFirstPlayerColor(4);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        // Layout VBox
        VBox vbox = new VBox(20); // Espaçamento de 20 entre os elementos
        vbox.getChildren().addAll(title, twoPlayersButton, fourPlayersButton);
        vbox.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Configuração da cena e exibição
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ludo");
        primaryStage.show();
    }

    private void askFirstPlayerColor(int numPlayers) throws FileNotFoundException {
        // Nova janela para selecionar a cor do primeiro jogador
        Stage colorStage = new Stage();
        Label colorLabel = new Label("Escolha a cor do primeiro jogador");

        qtd_jogs = numPlayers;

        // ComboBox para selecionar a cor
        ComboBox<String> colorComboBox = new ComboBox<>();
        colorComboBox.getItems().addAll("Verde", "Amarelo", "Vermelho", "Azul");
        colorComboBox.setValue("Verde"); // Define a cor padrão como "Verde"

        // Botão de confirmação
        Button confirmButton = new Button("Confirmar");
        confirmButton.setOnAction(event -> {
            String selectedColor = colorComboBox.getValue();
            cor_primeiro_jog = selectedColor.toLowerCase();
            colorStage.close();

            try {
                iniciarTabuleiro();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        // Layout VBox
        VBox colorVBox = new VBox(20);
        colorVBox.getChildren().addAll(colorLabel, colorComboBox, confirmButton);
        colorVBox.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Configuração da nova cena e exibição
        Scene colorScene = new Scene(colorVBox, 300, 200);
        colorStage.setScene(colorScene);
        colorStage.setTitle("Cor do Primeiro Jogador");
        colorStage.show();
    }

    // Método principal de inicialização da aplicação JavaFX
    private void iniciarTabuleiro() throws FileNotFoundException {
        int largura_menor = (int) (largura / 1.5f), altura_menor = (int) (altura / 1.5f);
        String cores[] = { "verde", "amarelo", "vermelho", "azul" };
        String temas[] = { "programacao_basica", "poo", "banco_dados" };
        Pane root = new Pane();
        Stage stage = new Stage();
        Scene scene = new Scene(root, largura, altura);
        TelaPerguntas tela_perguntas = new TelaPerguntas(largura_menor, altura_menor, temas);
        Tabuleiro tabuleiro = new Tabuleiro(root, largura, altura);
        Jogatina jogatina = new Jogatina(qtd_jogs, cor_primeiro_jog, stage, tela_perguntas);

        // Ajusta os jogadores no tabuleiro
        jogatina.ajustarJogadores(root, cores, largura, tabuleiro);

        // Configura a cena no palco, define o título e exibe a janela
        stage.setScene(scene);
        stage.setTitle("Ludo");
        stage.setResizable(false);
        stage.show();

        // Inicia a alternância de jogadores em um novo thread para não bloquear o
        // JavaFX Application Thread
        new Thread(() -> jogatina.intercalarJogadores(tabuleiro)).start();
    }

    // Método principal para lançar a aplicação
    public static void main(String[] args) {
        launch(args);
    }
}