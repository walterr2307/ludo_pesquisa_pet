import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaPerguntas {
    // Variáveis de instância para armazenar propriedades da tela e informações
    // sobre as perguntas
    private int largura, altura, indice_pergunta, num_perguntas;
    private String resposta, temas[], perguntas[], resp_corretas[], respostas[][];
    private LeitorJson leitor_json;
    private Stage stage;

    // Construtor que inicializa as variáveis com os valores recebidos e carrega os
    // dados das perguntas
    public TelaPerguntas(int largura, int altura, String temas[]) {
        this.largura = largura;
        this.altura = altura;
        this.temas = temas;
        this.indice_pergunta = 0; // Índice da pergunta atual
        this.resposta = null;
        this.leitor_json = new LeitorJson(temas); // Instancia um objeto LeitorJson para carregar as perguntas
        this.num_perguntas = this.leitor_json.getNumeroPerguntas(); // Número total de perguntas
        this.perguntas = this.leitor_json.getPerguntas(); // Perguntas carregadas
        this.resp_corretas = this.leitor_json.getRespostasCorretas(); // Respostas corretas associadas às perguntas
        this.respostas = this.leitor_json.getRespostas(); // Opções de respostas para cada pergunta
    }

    // Método para gerar a tela onde as perguntas e respostas são exibidas
    public void gerarTela(String cor) {
        float y_botao = altura * 0.4f; // Coordenada Y inicial para o posicionamento dos botões de resposta
        String formato;
        Button caixa_pergunta = new Button(), btn_respostas[] = new Button[4];
        Pane root = new Pane(); // Painel principal onde os elementos da UI são adicionados
        Scene scene = new Scene(root, largura, altura); // Cena que contém o layout da tela
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        cor = retornarCorHexadecimal(cor); // Converte o nome da cor para seu equivalente hexadecimal
        stage.setResizable(false); // Impede a redimensionamento da janela

        // Impede o fechamento da janela quando o usuário tenta fechá-la
        stage.setOnCloseRequest(event -> {
            event.consume();
        });

        // Configura a caixa de pergunta (tamanho, posição e estilo)
        caixa_pergunta.setPrefSize(largura * 0.8f, altura * 0.3f);
        caixa_pergunta.setLayoutX(largura / 10f);
        caixa_pergunta.setLayoutY(altura / 20f);
        caixa_pergunta.setText(perguntas[indice_pergunta]); // Define o texto da pergunta atual
        caixa_pergunta.setWrapText(true); // Permite que o texto da pergunta quebre em várias linhas
        caixa_pergunta.setStyle(
                "-fx-background-color: blue; -fx-text-fill: white; -fx-border-color: #C0C0C0; -fx-border-width: 2px;");
        root.getChildren().add(caixa_pergunta); // Adiciona a caixa de pergunta ao painel

        // Cria e configura os botões de resposta
        for (int i = 0; i < 4; i++) {
            formato = String.format(
                    "-fx-background-color: %s; -fx-text-fill: white;  -fx-border-color: #C0C0C0; -fx-border-width: 2px;",
                    cor);

            btn_respostas[i] = new Button();
            btn_respostas[i].setPrefSize(largura * 0.8f, altura * 0.1f);
            btn_respostas[i].setLayoutX(largura / 10f);
            btn_respostas[i].setLayoutY(y_botao);
            btn_respostas[i].setText(respostas[i][indice_pergunta]); // Define o texto da resposta atual
            btn_respostas[i].setStyle(formato); // Aplica o estilo ao botão

            root.getChildren().add(btn_respostas[i]); // Adiciona o botão ao painel
            y_botao += altura * 0.15f; // Atualiza a coordenada Y para o próximo botão
        }

        criarEventosBotoes(btn_respostas); // Associa eventos aos botões de resposta

        this.stage = stage; // Armazena a referência à janela principal

        stage.setTitle("Perguntas"); // Define o título da janela
        stage.setScene(scene); // Define a cena que será exibida na janela
    }

    // Método que verifica se a resposta escolhida pelo usuário está correta
    public boolean getPerguntaAcertada() {
        boolean pergunta_acertada;

        stage.showAndWait();

        // Compara a resposta do usuário com a resposta correta
        if (resposta.equals(resp_corretas[indice_pergunta]))
            pergunta_acertada = true;
        else
            pergunta_acertada = false;

        ++indice_pergunta; // Passa para a próxima pergunta
        resposta = null;

        // Reinicia o LeitorJson quando todas as perguntas forem respondidas
        if (indice_pergunta == num_perguntas)
            reiniciarLeitorJson();

        return pergunta_acertada; // Retorna o resultado
    }

    // Método que converte o nome de uma cor para seu valor hexadecimal
    private String retornarCorHexadecimal(String cor) {
        if (cor.equals("verde"))
            return "#008B00";
        else if (cor.equals("amarelo"))
            return "#B8860B";
        else if (cor.equals("vermelho"))
            return "#8B0000";
        else
            return "#00008B";
    }

    // Método que associa ações aos botões de resposta
    private void criarEventosBotoes(Button botoes[]) {
        for (int i = 0; i < 4; i++) {
            final int indice_botao = i;

            botoes[i].setOnAction(evento -> {
                resposta = respostas[indice_botao][indice_pergunta]; // Armazena a resposta selecionada

                // Desativa todos os botões após uma resposta ser escolhida
                for (int j = 0; j < 4; j++)
                    botoes[j].setDisable(true);

                try {
                    Thread.sleep(500); // Pausa a execução temporariamente
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                stage.close(); // Fecha a janela
            });
        }
    }

    // Método que reinicializa o LeitorJson e recarrega as perguntas
    private void reiniciarLeitorJson() {
        indice_pergunta = 0; // Reseta o índice para a primeira pergunta
        leitor_json = new LeitorJson(temas); // Reinstancia o objeto LeitorJson
        num_perguntas = leitor_json.getNumeroPerguntas(); // Recarrega o número de perguntas
        perguntas = leitor_json.getPerguntas(); // Recarrega as perguntas
        resp_corretas = leitor_json.getRespostasCorretas(); // Recarrega as respostas corretas
        respostas = leitor_json.getRespostas(); // Recarrega as opções de respostas
    }
}
