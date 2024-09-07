import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class LeitorJson {
    private int num_perguntas; // Armazena o número de perguntas encontradas
    private String temas[], perguntas[], resp_corretas[], respostas[][];

    // Construtor da classe, inicializa os arrays e define o número de perguntas com
    // base no JSON
    public LeitorJson(String temas[]) {
        String json_str = this.getJsonStr(); // Lê o conteúdo do arquivo JSON como uma string

        this.temas = temas; // Armazena os temas fornecidos
        this.num_perguntas = this.definirNumeroPerguntas(json_str); // Define o número de perguntas relevantes com base
                                                                    // nos temas
        this.perguntas = new String[num_perguntas]; // Inicializa o array de perguntas com o tamanho correto
        this.resp_corretas = new String[num_perguntas]; // Inicializa o array de respostas corretas
        this.respostas = new String[4][num_perguntas]; // Inicializa a matriz de respostas com 4 opções por pergunta
        this.gerarPerguntas(json_str); // Gera as perguntas e respostas a partir do JSON
        this.embaralharPerguntas(); // Embaralha as perguntas e as respostas
    }

    // Verifica se um determinado tema está presente no array de temas fornecidos
    private boolean getTemaEncontrado(String tema) {
        boolean tema_encontrado = false;

        for (int i = 0; i < temas.length; i++) {
            if (temas[i].equals(tema)) {
                tema_encontrado = true;
                break; // Interrompe o loop se o tema for encontrado
            }
        }

        return tema_encontrado; // Retorna verdadeiro se o tema foi encontrado, caso contrário, falso
    }

    // Conta o número de perguntas no JSON que correspondem aos temas fornecidos
    private int definirNumeroPerguntas(String json_str) {
        int num_perguntas = 0;
        Gson gson = new Gson();
        Type list_type = new TypeToken<List<Map<String, String>>>() {
        }.getType();
        List<Map<String, String>> lista_mapas = gson.fromJson(json_str, list_type);

        // Itera sobre o mapa de perguntas e conta quantas são do tema desejado
        for (Map<String, String> mapa : lista_mapas) {
            if (getTemaEncontrado(mapa.get("tema")))
                ++num_perguntas; // Incrementa o contador para cada pergunta encontrada
        }

        return num_perguntas; // Retorna o número total de perguntas encontradas
    }

    // Lê o conteúdo do arquivo JSON e retorna como uma string
    private String getJsonStr() {
        String json_str, linha;
        StringBuilder json_str_builder = new StringBuilder();

        // Usa BufferedReader para ler o arquivo JSON linha por linha
        try (BufferedReader file = new BufferedReader(new FileReader("perguntas.json"))) {
            while ((linha = file.readLine()) != null)
                json_str_builder.append(linha).append("\n"); // Concatena cada linha ao StringBuilder

            file.close(); // Fecha o arquivo
        } catch (IOException e) {
            e.printStackTrace(); // Trata exceções de I/O
        }

        json_str = json_str_builder.toString(); // Converte o StringBuilder para string
        return json_str; // Retorna a string contendo o conteúdo do JSON
    }

    // Gera as perguntas, respostas e respostas corretas a partir do JSON
    private void gerarPerguntas(String json_str) {
        int i = 0;
        Gson gson = new Gson();
        Type list_type = new TypeToken<List<Map<String, String>>>() {
        }.getType();
        List<Map<String, String>> lista_mapas = gson.fromJson(json_str, list_type);

        // Itera sobre a lista de mapas para preencher os arrays de perguntas e
        // respostas
        for (Map<String, String> mapa : lista_mapas) {
            if (getTemaEncontrado(mapa.get("tema"))) { // Verifica se o tema da pergunta é relevante
                perguntas[i] = mapa.get("pergunta"); // Armazena a pergunta
                respostas[0][i] = mapa.get("0"); // Armazena a primeira resposta
                respostas[1][i] = mapa.get("1"); // Armazena a segunda resposta
                respostas[2][i] = mapa.get("2"); // Armazena a terceira resposta
                respostas[3][i] = mapa.get("3"); // Armazena a quarta resposta
                resp_corretas[i] = respostas[0][i]; // Define a primeira resposta como a correta por padrão
                ++i;
            }
        }
    }

    // Embaralha as perguntas e as respostas para que a ordem não seja previsível
    private void embaralharPerguntas() {
        int i, j;

        for (i = num_perguntas - 1; i > 0; i--) {
            j = (int) (Math.random() * (i + 1)); // Gera um índice aleatório para troca

            embaralharRespostas(respostas, i); // Embaralha as respostas para a pergunta atual

            trocarValores(perguntas, i, j); // Troca a posição das perguntas
            trocarValores(resp_corretas, i, j); // Troca a posição das respostas corretas
            trocarValores(respostas[0], i, j); // Troca as respostas nas quatro opções
            trocarValores(respostas[1], i, j);
            trocarValores(respostas[2], i, j);
            trocarValores(respostas[3], i, j);
        }
    }

    // Troca os valores de dois elementos em um array
    private void trocarValores(String vetor[], int i, int j) {
        String copia;

        copia = vetor[i]; // Armazena temporariamente o valor do índice i
        vetor[i] = vetor[j]; // Substitui o valor do índice i pelo valor do índice j
        vetor[j] = copia; // Atribui ao índice j o valor armazenado temporariamente
    }

    // Embaralha as respostas para uma pergunta específica
    private void embaralharRespostas(String[][] respostas, int indice) {
        int i, j;
        String copia;

        for (i = 0; i < 4; i++) {
            j = (int) (Math.random() * (i + 1)); // Gera um índice aleatório para troca
            copia = respostas[i][indice]; // Armazena temporariamente a resposta
            respostas[i][indice] = respostas[j][indice]; // Troca as respostas
            respostas[j][indice] = copia; // Substitui pela resposta armazenada
        }
    }

    // Retorna o array de perguntas
    public String[] getPerguntas() {
        return this.perguntas;
    }

    // Retorna o array de respostas corretas
    public String[] getRespostasCorretas() {
        return this.resp_corretas;
    }

    // Retorna a matriz de respostas
    public String[][] getRespostas() {
        return respostas;
    }

    // Retorna o número de perguntas
    public int getNumeroPerguntas() {
        return this.num_perguntas;
    }
}
