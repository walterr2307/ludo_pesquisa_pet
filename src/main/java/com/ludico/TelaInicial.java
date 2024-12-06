package com.ludico;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TelaInicial extends Application {

    // Definindo variáveis para controle de cores, tamanho da janela e quantidade de
    // jogadores
    private int indice_cor = 0, qtd_jogs = 4;
    private String cor_primeiro_jog;
    private String[] nomes, temas, cores = {"verde", "amarelo", "vermelho", "azul"};
    private final TextField[] nameFields = new TextField[4];
    private final Label[] warningLabels = new Label[4];

    // Método principal para iniciar a aplicação
    public void start(Stage primaryStage) {
        // Criação dos CheckBoxes para opções de temas
        CheckBox bdCheckBox = new CheckBox("Banco de Dados");
        CheckBox pooCheckBox = new CheckBox("POO");
        CheckBox progBasicaCheckBox = new CheckBox("Programação Básica");
        CheckBox testesExploratoriosCheckBox = new CheckBox("Testes Exploratórios"); // Novo CheckBox

        // Empacotando os CheckBoxes em uma VBox
        VBox checkBoxesBox = new VBox(10, bdCheckBox, pooCheckBox, progBasicaCheckBox, testesExploratoriosCheckBox); // Adicionado
        // novo
        // CheckBox
        checkBoxesBox.setAlignment(Pos.CENTER_LEFT);
        checkBoxesBox.setPadding(new Insets(0, 0, 0, 20)); // Afastando da borda esquerda

        // Botão de confirmação
        Button confirmButton = new Button("Confirmar");
        confirmButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        confirmButton.setOnAction(e -> {
            // Verifica se pelo menos uma opção foi selecionada
            if (!bdCheckBox.isSelected() && !pooCheckBox.isSelected() && !progBasicaCheckBox.isSelected()
                    && !testesExploratoriosCheckBox.isSelected()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Você deve escolher no mínimo uma opção!");
                alert.showAndWait();
            } else {
                // Adiciona as opções selecionadas a uma lista
                ArrayList<String> lista_temas = new ArrayList<String>();
                if (bdCheckBox.isSelected())
                    lista_temas.add("banco_dados");
                if (pooCheckBox.isSelected())
                    lista_temas.add("poo");
                if (progBasicaCheckBox.isSelected())
                    lista_temas.add("programacao_basica");
                if (testesExploratoriosCheckBox.isSelected()) // Verifica se a opção de Testes Exploratórios foi
                    // selecionada
                    lista_temas.add("testes_exploratorios");

                temas = new String[lista_temas.size()];

                // Converte a lista para um array
                for (int i = 0; i < temas.length; i++)
                    temas[i] = lista_temas.get(i);

                primaryStage.close();
                chamarQtdJogadores(); // Chama o método para escolher a quantidade de jogadores
            }
        });

        // Layout principal da cena
        VBox mainLayout = new VBox(20, checkBoxesBox, confirmButton);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER); // Centralização do botão de confirmação

        // Cena
        Scene scene = new Scene(mainLayout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu de Opções");
        primaryStage.show();
    }

    // Ajusta as cores baseadas na cor do primeiro jogador
    private String[] ajustarCores() {
        cores = new String[2];

        // Define a ordem das cores restantes com base na cor do primeiro jogador
        if (cor_primeiro_jog.equals("verde")) {
            cores[0] = "verde";
            cores[1] = "vermelho";
        } else if (cor_primeiro_jog.equals("amarelo")) {
            cores[0] = "amarelo";
            cores[1] = "azul";
        } else if (cor_primeiro_jog.equals("vermelho")) {
            cores[0] = "vermelho";
            cores[1] = "verde";
        } else {
            cores[0] = "azul";
            cores[1] = "amarelo";
        }

        return cores;
    }

    // Exibe a tela para entrada dos nomes dos jogadores
    private void showColorInputScreen() {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        // Cria rótulo e campo de texto para entrada do nome
        Label instructionLabel = new Label("Digite o nome do jogador " + cores[indice_cor] + ":");
        TextField nameField = new TextField();
        nameFields[indice_cor] = nameField;

        Label warningLabel = new Label();
        warningLabels[indice_cor] = warningLabel;

        // Botão para avançar para o próximo jogador
        Button nextButton = new Button("Próximo");
        nextButton.setOnAction(e -> {
            if (validateName(nameField.getText(), warningLabel)) {
                nomes[indice_cor] = nameField.getText().trim().toUpperCase();

                if (indice_cor < cores.length - 1) {
                    indice_cor++;
                    stage.close();
                    showColorInputScreen(); // Exibe a tela para o próximo jogador
                } else {
                    stage.close();
                    Main.iniciarTabuleiro(nomes, temas, cores);
                }
            }
        });

        root.getChildren().addAll(instructionLabel, nameField, warningLabel, nextButton);

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    // Valida o nome inserido pelo jogador
    private boolean validateName(String name, Label warningLabel) {
        if (name.trim().isEmpty()) {
            warningLabel.setText("O nome não pode estar vazio.");
            return false;
        }
        if (name.length() > 15) {
            warningLabel.setText("O nome não pode ter mais de 15 caracteres.");
            return false;
        }
        warningLabel.setText("");
        return true;
    }

    // Exibe a tela para escolher a quantidade de jogadores
    private void chamarQtdJogadores() {
        Stage primaryStage = new Stage();
        Label title = new Label("Escolha a quantidade de jogadores");

        // Botões para escolher a quantidade de jogadores
        Button twoPlayersButton = new Button("2 Jogadores");
        Button fourPlayersButton = new Button("4 Jogadores");

        // Ações dos botões
        twoPlayersButton.setOnAction(event -> {
            primaryStage.close();
            try {
                askFirstPlayerColor(2); // Chama o método para escolher a cor do primeiro jogador para 2 jogadores
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        fourPlayersButton.setOnAction(event -> {
            primaryStage.close();
            try {
                askFirstPlayerColor(4); // Chama o método para escolher a cor do primeiro jogador para 4 jogadores
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

    // Exibe a tela para escolher a cor do primeiro jogador
    private void askFirstPlayerColor(int numPlayers) throws FileNotFoundException {
        Stage colorStage = new Stage();
        Label colorLabel = new Label("Escolha a cor do primeiro jogador");

        qtd_jogs = numPlayers;
        nomes = new String[qtd_jogs];

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

            // Ajusta as cores se houver 2 jogadores
            if (qtd_jogs == 2)
                cores = ajustarCores();

            showColorInputScreen(); // Exibe a tela para entrada dos nomes dos jogadores
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

    public static void main(String[] args) {
        launch(args);
    }
}