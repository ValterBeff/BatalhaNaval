import java.util.Scanner;
import java.util.Random;

public class Main {
    // Variáveis Globais.
    static String player1;
    static String player2;
    static int altura, largura, navio;
    static int tabuleiro[][], tabuleiro2[][];
    static int jogadas = 0;
    static boolean turnoPlayer = true; // Variável para controlar o turno dos jogadores
    static Scanner input = new Scanner(System.in);
    static int alternativa;
    static int alternativaDeCensura;

    public static void main(String[] args) {
        jogadores(); // Chamando a Função (jogadores) - exibindo o nome dos Jogadaor 1 e Jogador 2;
        controleDeDificuldade(); // Chamando a Função (controleDeDificuldade).
        controleDeCensura(); // Chamando a Função (controleDeCensura).

        inserirOsNavioNoTabuleiro(); // Chamando a Função (inserirOsNavioNoTabuleiro).

        boolean jogo = true;
        do {
            exibirTabuleiro(alternativaDeCensura); // Chamando a Função (exibirTabuleiro).
            if (turnoPlayer) {
                System.out.println("\n" + player1 + ", é sua vez.");
                jogo = comandoDeAtaque(tabuleiro2); // Jogador 1 ataca Tabuleiro 2
            } else {
                System.out.println("\n" + player2 + ", é sua vez.");
                jogo = comandoDeAtaque(tabuleiro); // Jogador 2 ataca Tabuleiro 1
            }
            if (navio == 0) {
                jogo = false;

                if (turnoPlayer) {
                    System.out.println("\nParabéns, " + player1 + "! Você venceu!");
                } else {
                    System.out.println("\nParabéns, " + player2 + "! Você venceu!");
                }
            }
            turnoPlayer = !turnoPlayer; // Alternar entre os jogadores a cada rodada
        } while (jogo); //Loop

        System.out.println("\nFIM DO JOGO!");
        System.out.println("\nNUMERO DE JOGADAS: " + jogadas);

        input.close(); // Fechamento do Scanner
    } // Função Principal

    public static String valorDigitado() {
        System.out.println("\nLARGURA X ALTURA  \nEx.: 'L13'\n");
        System.out.println("Coordenadas do ATAQUE: ");
        return input.next();
    } // Entrada do Valor.

    public static boolean verificarValor(String comandoDeTiro) {
        // Verificação - Regex
        int quantidadeDeNumero = (largura > 10) ? 2 : 1; // Se for maior que 10, a Qnt. = 2, caso contrário, Qnt. = 1.
        String expressaoDeVerificarLetra = "^[A-Za-z]{" + quantidadeDeNumero + "}[0-9]$"; // Verificar a Letra A - Z.
        return comandoDeTiro.matches(expressaoDeVerificarLetra);
    } // Validar o Valor.

    public static int[] retonanarPosicao(String comandoDeTiro) {
        int[] posicao = new int[2];
        posicao[1] = comandoDeTiro.charAt(0) - 'A';
        posicao[0] = Integer.parseInt(comandoDeTiro.substring(1)) - 1;
        return posicao;
    } // Retonar Posições do Tabuleiro.

    public static void contagemDeNavio() {
        System.out.println("Navios Restantes: " + navio);
    }// Contagem de Navios.

    public static boolean comandoDeAtaque(int[][] tabuleiroAlvo) {
        String comandoDeTiro = valorDigitado(); // Lê a entrada.

        if (verificarValor(comandoDeTiro)) {
            jogadas++;

            int[] posicoes = retonanarPosicao(comandoDeTiro);
            if (verficacaoDePosicao(posicoes)) {
                if (tabuleiroAlvo[posicoes[0]][posicoes[1]] == 1) {
                    tabuleiroAlvo[posicoes[0]][posicoes[1]] = 3; // Marcar como navio atingido
                    System.out.println("FOGO!");
                    navio--;
                    contagemDeNavio();
                } else {
                    tabuleiroAlvo[posicoes[0]][posicoes[1]] = 2; // Marcar como tiro na água
                    System.out.println("Quase...");
                    contagemDeNavio();
                }
            } else {
                System.out.println("\nCoordenada ERRADA\n");
            }
            return true; // Comando válido
        } else {
            System.out.println("\nCoordenada INVALIDA\n");
            return false; // Comando inválido
        }
    } // Exibir no Console.log, de solicitação de coordenadas do Tabuleiro.

    public static boolean verficacaoDePosicao(int[] posicoes) {
        int x = posicoes[1];
        int y = posicoes[0];
        if (x < 0 || x >= altura) {
            System.out.println("Coordenada Incorreta(LETRA): " + ((char) ('A' + x))); // Verificar a posição da Letra (Lagura).
            return false;
        }
        if (y < 0 || y >= largura) {
            System.out.println("Coordenada Incorreta(NUMERO): " + (y + 1)); // Verificar a Posição do Numero (Altura).
            return false;
        }
        return true;
    }// Verificar Posição da Coordenadas no Comando(Console.log).

    public static void exibirTabuleiro(int alternativaDeCensura) {

        if (alternativaDeCensura == 1) {
            Tabuleiro(player1, tabuleiro, false);
            Tabuleiro(player2, tabuleiro2, false);
        } else {
            Tabuleiro(player1, tabuleiro, true);
            Tabuleiro(player2, tabuleiro2, true);
        }
    }// exibir Tabulerio, variando pro jogadores - com condicionais de Censura.

    public static void Tabuleiro(String jogadores, int[][] tabuleiro, boolean censura) {
        System.out.println("-----" + jogadores + "-----");

        String linhaDoTabuleiro = ""; // Classe de Linha do Tabuleiro (Console.log).

        char letraDaColuna = 'A'; // Variável da Letra nas Coluna/Largura.

        String colunaDoTabuleiro = " "; // Classe de Coluna do Tabuleiro (Console.log).
        if (largura < 10) {
            colunaDoTabuleiro = "   |";
        } else {
            colunaDoTabuleiro = "   |";
        }

        for (int i = 0; i < largura; i++) {
            colunaDoTabuleiro += (char) (letraDaColuna + i) + "|";
        }
        System.out.println(colunaDoTabuleiro);
        int numeroDaLinha = 1; // Variável do Numero da Linha.

        for (int[] linha : tabuleiro) {
            if (numeroDaLinha < 10) {
                linhaDoTabuleiro = (numeroDaLinha++) + "  |";
            } else {
                linhaDoTabuleiro = (numeroDaLinha++) + " |";
            }

            for (int coluna : linha) {
                switch (coluna) {
                    case 0: // Vazio.
                        linhaDoTabuleiro += " |";
                        break;

                    case 1: // Navio.
                        if (censura) {
                            linhaDoTabuleiro += "N|";
                            break;
                        } else {
                            linhaDoTabuleiro += " |";
                            break;
                        }


                    case 2: // Errou ou Acertou na Aqua | Agua.
                        linhaDoTabuleiro += "X|";
                        break;

                    case 3: // Acertou ou Afundou o Navio.
                        linhaDoTabuleiro += "*|";
                        break;
                } // Verificação do Estado da Coluna/Bloco do Tabuleiro.
            }
            System.out.println(linhaDoTabuleiro); // Exibindo Tabuleiro (Console.log).
        }
    } // Exibindo no Console.Log o Tabuleiro - A numeração (Altura) e as Letras (lagura).

    /*
     * Na Função (exibirTabuleiro), foram colocados os símbolos em cada "Bloco", de acordo com os valores inseridos na Função (inserirOsNavioNoTabuleiro).
     * Apresentando os seguintes Símbolos:
     * - Vazio     | | = (Quando não tem Navio no Bloco).
     * - Com Navio |N| = (Quando tem Navio no Bloco - temporario).
     * - Errou     |X|
     * - Acertou   |*|
     * */

    public static void inserirOsNavioNoTabuleiro() {
        tabuleiro = posicaoDosNavioNoTabuleiro();
        tabuleiro2 = posicaoDosNavioNoTabuleiro();
    } // Inserir Os Navio no Tabuleiro.

    public static int[][] prepararTabuleiro() {
        return new int[altura][largura];
    } // Retorno da Codenadas do Tabuleiro (Altura|Largura).

    public static int[][] posicaoDosNavioNoTabuleiro() {
        int[][] tabuleiroPreparado = prepararTabuleiro();
        Random blocoAleatorio = new Random(); // Gerando um Numero Aleatorio.
        int naviosInseridos = 0;

        while (naviosInseridos < navio) {
            int x = blocoAleatorio.nextInt(altura);
            int y = blocoAleatorio.nextInt(largura);

            if (tabuleiroPreparado[x][y] != 1) { // Se a posição não estiver ocupada por um navio
                tabuleiroPreparado[x][y] = 1; // Coloca um navio na posição
                naviosInseridos++; // Incrementa o contador de navios inseridos
            }
        }

        return tabuleiroPreparado;
    } // Definindo a Posições (Randômico) dos Navios.

    public static void controleDeCensura() {
        System.out.println("Esccolha se o Tabuleiro mostra os Navios 'N' ou não:");
        System.out.println("1 - Com Censura \n2 - Sem Censura");
        alternativaDeCensura = input.nextInt();
    }// Controle de Censura ou Sem pro Tabuleiro.

    public static void controleDeDificuldade() {
        System.out.println("Escolha o Nível de Dificuldade.");
        System.out.println("1 - Fácil\n2 - Médio\n3 - Difícil");
        alternativa = input.nextInt();

        if (alternativa == 1) {
            dificuldadeFacil(); // Chamando a Função (dificuldadeFacil).
        } else if (alternativa == 2) {
            dificuldadeMedio(); // Chamando a Função (dificuldadeMedio).
        } else if (alternativa == 3) {
            dificuldadeDificil(); // Chamando a Função (dificuldadeDificil).
        } else {
            dificuldadeTeste();
        }
    } // Controle de Dificuldade por: Escala e Navios (Os Navio inseridos no Jogo e de Acordo com a Altura * Largura / 3 (Arredondando para Baixo)).

    public static void dificuldadeFacil() {
        altura = 8;
        largura = 8;
        navio = 20;
        tabuleiro = prepararTabuleiro();
        tabuleiro2 = prepararTabuleiro();
    } // Escala do tabuleiro fica por 8x8 - Navios 20.

    public static void dificuldadeMedio() {
        altura = 10;
        largura = 10;
        navio = 32;
        tabuleiro = prepararTabuleiro();
        tabuleiro2 = prepararTabuleiro();
    } // Escala do tabuleiro fica por 10x10 - Navios 32.

    public static void dificuldadeDificil() {
        altura = 15;
        largura = 15;
        navio = 75;
        tabuleiro = prepararTabuleiro();
        tabuleiro2 = prepararTabuleiro();
    } // Escala do tabuleiro fica por 15x15 - Navio 75.

    public static void dificuldadeTeste() {
        altura = 2;
        largura = 2;
        navio = 1;
        tabuleiro = prepararTabuleiro();
        tabuleiro2 = prepararTabuleiro();
    } // Escala do tabuleiro fica por 2X2 - Navio 1.

    public static void jogadores() {
        player1 = "Jogador 1";
        player2 = "Jogador 2";
    }// Metodo pra "Nomes dos Jogadores"
}
