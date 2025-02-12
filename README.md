# Lúdico - Jogo Educativo de Computação

Bem-vindo ao repositório do **Lúdico**, um jogo inovador desenvolvido para ensinar conceitos de computação de maneira interativa e divertida. Inspirado no tradicional jogo de Ludo, o Lúdico integra desafios e perguntas sobre programação, banco de dados, testes de software e outros tópicos essenciais para os cursos de Ciência da Computação, proporcionando uma experiência de aprendizado gamificada.

Este repositório reúne não apenas o código-fonte do jogo, mas também uma série de gráficos e imagens complementares que não puderam ser incluídos no artigo publicado sobre o Lúdico.

![LOGO](https://github.com/user-attachments/assets/be6b4218-13b6-4009-a802-b4ee92a9ddfd)

---

## Sobre o Projeto

O **Lúdico** foi concebido com o objetivo de democratizar o ensino da computação, tornando-o mais acessível a diversos públicos. Com uma mecânica que combina a movimentação de peças com desafios de perguntas e respostas, o jogo permite que os jogadores aprendam e revisem conteúdos de forma dinâmica, promovendo o engajamento e a competitividade saudável.

### Principais Características

- **Jogabilidade Interativa:** Suporta partidas para 2 ou 4 jogadores, onde cada participante avança pelo tabuleiro ao responder corretamente a perguntas sobre temas da computação.
- **Abordagem Personalizável:** As perguntas e respostas são configuradas via um arquivo JSON, possibilitando a adaptação do conteúdo conforme as necessidades do público.
- **Tecnologias Utilizadas:**  
  - **Java** para o desenvolvimento da lógica do jogo.
  - **JavaFX** para a construção da interface gráfica.
  - **Gson** para a manipulação de dados em formato JSON.

---

## Estrutura do Repositório

- **`src/`**: Código-fonte do projeto desenvolvido em Java.
- **`imagens/`**: Pasta que contém gráficos, diagramas e imagens complementares que detalham aspectos do desenvolvimento, aplicação e avaliação do Lúdico.
- **`dados/`**: Arquivo JSON com as perguntas e respostas utilizadas no jogo.
- **`docs/`**: Documentação adicional e material de apoio relacionado ao projeto.

---

## Gráficos e Imagens Complementares

A seguir, você encontrará uma seleção de imagens e gráficos que enriquecem as informações apresentadas no artigo original, oferecendo uma visão mais detalhada do funcionamento e dos resultados obtidos com o Lúdico:

### 1. Arquitetura Geral do Lúdico
![Arquitetura Geral do Lúdico](images/figura1-arquitetura.png)  
*Figura 1 – Diagrama ilustrando a arquitetura geral do jogo, demonstrando a integração entre os módulos de interface, lógica e manipulação de dados.*

### 2. Interface do Jogo
![Interface do Lúdico](images/figura2-interface.png)  
*Figura 2 – Exemplo da interface gráfica do Lúdico, evidenciando as peças e o tabuleiro interativo.*

### 3. Mecânica de Perguntas para Movimentação
![Mecânica de Perguntas](images/figura3.png)  
*Figura 3 – Fluxograma que detalha a mecânica de perguntas, onde a resposta correta permite a movimentação das peças e a captura de adversários.*

### 4. Estrutura do JSON de Perguntas
![Estrutura do JSON](images/figura4-json.png)  
*Figura 4 – Exemplo da organização do arquivo JSON que armazena as perguntas e respostas do jogo.*

### 5. Feedback e Avaliação dos Usuários – Disciplina de Banco de Dados
![Feedback dos Participantes](images/fig5.pdf)  
*Figura 5 – Gráficos que demonstram a percepção dos alunos quanto à didática, dificuldade e engajamento durante as partidas do Lúdico na disciplina de Banco de Dados.*

### 6. Feedback e Avaliação dos Usuários – Semana Universitária
![Feedback Semana Universitária](images/figura6.png)  
*Figura 6 – Resultados da oficina “Testes Exploratórios na Prática” na Semana Universitária, evidenciando a receptividade e as sugestões dos participantes.*

> **Nota:** Essas imagens complementam o artigo "Lúdico: O Jogo que ensina sobre T.I", ampliando a visão sobre o desenvolvimento, aplicação e os resultados obtidos com o jogo.

---

## Como Executar o Projeto

### Pré-requisitos

- **Java 11** (ou superior) instalado.
- **JavaFX** configurado em seu ambiente.
- **Gson** para manipulação de JSON (disponível via [GitHub](https://github.com/google/gson)).

### Passos para Execução

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/ludico.git
   cd ludico


## Contribuições
Contribuições são sempre bem-vindas! Se você deseja melhorar o Lúdico, corrigir algum bug ou sugerir novas funcionalidades, sinta-se à vontade para abrir uma issue ou enviar um pull request.

## Licença
Este projeto é licenciado sob a MIT License.
