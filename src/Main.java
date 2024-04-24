import java.util.Scanner;
import java.util.Random;

public class Main {
    // Variáveis Globais.
    static int altura, largura, navio;
    static int tabuleiro[][];
    static int jogadas = 0;
    static Scanner input = new Scanner(System.in);

    // Variável Teste.
    static int alternativa;

    public static void main(String[] args) {
        controleDeDificuldade(); // Chamando a Função (controleDeDificuldade).
//        System.out.println("Altura: " + altura);
//        System.out.println("Largura: " + largura);
//        System.out.println("Navios: " + navio);

        inserirOsNavioNoTabuleiro(); // Chamando a Função (inserirOsNavioNoTabuleiro).

        boolean jogo = true;
        do {
            exibirTabuleiro(); // Chamando a Função (exibirTabuleiro).
            jogo = comandoDeAtaque(); // Chamando a Função (comandoDeAtaque).
            if(navio == 0){
                jogo = false;
            }
        } while (jogo);
        clearScreen();
        System.out.println("\nFIM DO JOGO!");
        System.out.println("\nNUEMRO DE JOGADAS: " + jogadas);

        input.close(); // Fechamento do Scanner
    }

    public static String valorDigitado() {
        System.out.println("\nLARGURA X ALTURA  \nEx.: 'L13'\n");
        System.out.println("Coordenadas do ATAQUE: ");
        return input.next();
    } //Entrada do Valor.

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

    public static void contagemDeNavio(){
        System.out.println("Navios Restantes: " + navio);
    }

    public static boolean comandoDeAtaque() {
        String comandoDeTiro = valorDigitado(); // Lê a entrada.

        if (verificarValor(comandoDeTiro)) {
            jogadas++;
            clearScreen();
            int[] posicoes = retonanarPosicao(comandoDeTiro);
            if (verficacaoDePosicao(posicoes)) {
                if (tabuleiro[posicoes[0]][posicoes[1]] == 1) {
                    tabuleiro[posicoes[0]][posicoes[1]] = 3; // Marcar como navio atingido
                    System.out.println("FOGO!");
                    navio--;
                    contagemDeNavio();
                } else {
                    tabuleiro[posicoes[0]][posicoes[1]] = 2; // Marcar como tiro na água
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
    } //Exibir no Console.log, de solicitação de coordenadas do Tabuleiro.

    public static boolean verficacaoDePosicao(int[] posicoes) {
        int x = posicoes[1];
        int y = posicoes[0];
        if (x < 0 || x >= altura) {
            System.out.println("Coordenada Incorreta(LETRA): " + ((char) ('A' + x))); //Verificar a posição da Letra (Lagura).
            return false;
        }
        if (y < 0 || y >= largura) {
            System.out.println("Coordenada Incorreta(NUMERO): " + (y + 1)); //Verificar a Posição do Numero (Altura).
            return false;
        }
        return true;
    }// Verificar Posição da Coordenadas no Comando(Console.log).

    public static void exibirTabuleiro() {
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
                        linhaDoTabuleiro += "N|";
                        break;

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
    } // Inserir Os Navio no Tabuleiro.

    public static int[][] retornarPreparoTabuleiro() {
        return new int[altura][largura];
    } // Retorno da Codenadas do Tabuleiro (Altura|Largura).

    public static int[][] posicaoDosNavioNoTabuleiro() {
        int preparandoTabuleiro[][] = retornarPreparoTabuleiro();
        Random blocoAleatorio = new Random(); // Gerando um Numero Aleatorio.
        int naviosInseridos = 0;

        while (naviosInseridos < navio) {
            int x = blocoAleatorio.nextInt(altura);
            int y = blocoAleatorio.nextInt(largura);

            if (preparandoTabuleiro[x][y] != 1) { // Se a posição não estiver ocupada por um navio
                preparandoTabuleiro[x][y] = 1; // Coloca um navio na posição
                naviosInseridos++; // Incrementa o contador de navios inseridos
            }
        }

        return preparandoTabuleiro;
    } // Definindo a Posições (Randômico) dos Navios.

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
        tabuleiro = retornarPreparoTabuleiro();
    } // Escala do tabuleiro fica por 8x8 - Navios 20.

    public static void dificuldadeMedio() {
        altura = 10;
        largura = 10;
        navio = 32;
        tabuleiro = retornarPreparoTabuleiro();
    } // Escala do tabuleiro fica por 10x10 - Navios 32.

    public static void dificuldadeDificil() {
        altura = 15;
        largura = 15;
        navio = 75;
        tabuleiro = retornarPreparoTabuleiro();
    } // Escala do tabuleiro fica por 15x15 - Navio 75.

    public static void dificuldadeTeste() {
        altura = 2;
        largura = 2;
        navio = 1;
        tabuleiro = retornarPreparoTabuleiro();
    } // Escala do tabuleiro fica por 2X2 - Navio 1.

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception ex) {
            System.out.println("Erro ao limpar a tela: " + ex.getMessage());
        }
    }
}
