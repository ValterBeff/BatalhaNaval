//Biblioteca
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main extends JFrame {
    //Declaração da Classe:
    // Declaração de variáveis estáticas para configurações do jogo
    private static String player1 = "Jogador 1";
    private static String player2 = "Jogador 2";
    private static int altura = 5; // Altura do tabuleiro
    private static int largura = 5; // Largura do tabuleiro
    private static int navio1 = 8; // Número de navios para o Jogador 1.
    private static int navio2 = 8; // Número de navios para o Jogador 2.
    private static int[][] tabuleiro1, tabuleiro2; // Representação dos tabuleiros de cada jogador.
    private static boolean turnoPlayer = true; // Variável para controlar o turno dos jogadores

    // Componentes da interface gráfica

    private JButton[][] buttons1 = new JButton[altura][largura];// Botões para o tabuleiro do Jogador 1
    private JButton[][] buttons2 = new JButton[altura][largura];// Botões para o tabuleiro do Jogador 2
    private JLabel infoLabel; // Rótulo para exibir informações sobre o turno atual
    private JLabel naviosLabel; // Rótulo para exibir o número de navios restantes para cada jogador
    private JButton resetButton; // Botão para reiniciar o jogo

    // Construtor da classe
    // Método para criar o tabuleiro

    public Main() {

        // Configurações da janela

        setTitle("Batalha Naval");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel principal dividido em dois para os tabuleiros dos jogadores

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel boardPanel1 = criarPainelTabuleiro(player1, buttons1);// Cria o painel para o tabuleiro do Jogador 1
        JPanel boardPanel2 = criarPainelTabuleiro(player2, buttons2);// Cria o painel para o tabuleiro do Jogador 2
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));// Painel para exibir informações do jogo

        // Componentes de informação e controle do jogo

        infoLabel = new JLabel(player1 + ", é sua vez.", JLabel.CENTER);
        naviosLabel = new JLabel("Navios Restantes - " + player1 + ": " + navio1 + " | " + player2 + ": " + navio2, JLabel.CENTER);
        resetButton = new JButton("Reiniciar Jogo");
        resetButton.addActionListener(e -> resetGame());// Adiciona um ouvinte de ação para reiniciar o jogo

        // Adiciona os componentes ao painel de informações

        infoPanel.add(infoLabel);
        infoPanel.add(naviosLabel);
        infoPanel.add(resetButton);

        // Inicializa os tabuleiros com os navios posicionados

        inserirOsNavioNoTabuleiro();

        // Adiciona os painéis ao layout da janela

        mainPanel.add(boardPanel1);
        mainPanel.add(boardPanel2);
        add(mainPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        setVisible(true);// Torna a janela visível
    }

    //Método para criar o painel do tabuleiro

    private JPanel criarPainelTabuleiro(String player, JButton[][] buttons) {
        JPanel boardPanel = new JPanel(new GridLayout(altura + 1, largura + 1));
        boardPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), player, TitledBorder.CENTER, TitledBorder.TOP));

        // Adiciona as coordenadas

        boardPanel.add(new JLabel(""));
        for (int j = 0; j < largura; j++) {
            boardPanel.add(new JLabel(String.valueOf(j), JLabel.CENTER));
        }
        for (int i = 0; i < altura; i++) {
            boardPanel.add(new JLabel(String.valueOf(i), JLabel.CENTER));
            for (int j = 0; j < largura; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.BLUE);
                buttons[i][j].setMargin(new Insets(0, 0, 0, 0));
                buttons[i][j].setActionCommand(i + "," + j);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j, buttons == buttons1));
                boardPanel.add(buttons[i][j]);
            }
        }
        return boardPanel;
    }

    // Classe interna para lidar com cliques nos botões do tabuleiro

    private class ButtonClickListener implements ActionListener {
        private int x, y;
        private boolean isPlayer1;

        // Construtor
        public ButtonClickListener(int x, int y, boolean isPlayer1) {
            this.x = x;
            this.y = y;
            this.isPlayer1 = isPlayer1;
        } // Método chamado quando um botão é clicado

        @Override

        public void actionPerformed(ActionEvent e) {
            if (turnoPlayer && !isPlayer1) { // Jogador 1 ataca no campo do Jogador 2
                processarJogada(tabuleiro2, buttons2, x, y, false);// Processa o ataque do Jogador 1
            } else if (!turnoPlayer && isPlayer1) { // Jogador 2 ataca no campo do Jogador 1
                processarJogada(tabuleiro1, buttons1, x, y, true);// Processa o ataque do Jogador 2
            }
        }
    }

    // Método para processar uma jogada

    private void processarJogada(int[][] tabuleiroAlvo, JButton[][] buttons, int x, int y, boolean isPlayer1) {
        // Verifica se o alvo é um navio ou água
        if (tabuleiroAlvo[x][y] == 1) {// Se for navio
            buttons[x][y].setBackground(Color.RED);// Muda a cor do botão para indicar um navio atingido
            buttons[x][y].setText("N");// Exibe "N" no botão para indicar navio atingido
            tabuleiroAlvo[x][y] = 3; // Marcar como navio atingido
            if (isPlayer1) {
                navio1--;// Decrementa o número de navios do Jogador 1
            } else {
                navio2--;// Decrementa o número de navios do Jogador 2
            }
        } else {// Se for água
            buttons[x][y].setBackground(Color.WHITE);// Muda a cor do botão para indicar tiro na água
            buttons[x][y].setText("X");// Exibe "X" no botão para indicar tiro na água
            tabuleiroAlvo[x][y] = 2; // Marcar como tiro na água
        }

        buttons[x][y].setEnabled(false);// Desativa o botão para evitar cliques adicionais no mesmo local

        turnoPlayer = !turnoPlayer; // Alternar entre os jogadores a cada rodada
        String jogador = turnoPlayer ? player1 : player2;// Determina o jogador atual
        infoLabel.setText(jogador + ", é sua vez.");// Atualiza o rótulo de informações com o jogador atual
        naviosLabel.setText("Navios Restantes - " + player1 + ": " + navio1 + " | " + player2 + ": " + navio2);// Atualiza o rótulo com o número de navios restantes para cada jogador

        // Verifica se algum jogador venceu

        if (navio1 == 0) {
            JOptionPane.showMessageDialog(null, "Parabéns, " + player2 + "! Você venceu!");// Exibe mensagem de vitória para o Jogador 2
            resetGame();// Reinicia o jogo
        } else if (navio2 == 0) {
            JOptionPane.showMessageDialog(null, "Parabéns, " + player1 + "! Você venceu!");// Exibe mensagem de vitória para o Jogador 1
            resetGame();// Reinicia o jogo
        }
    }

    // Método para reiniciar o jogo

    private void resetGame() {
        // Reinicia o tabuleiro e os navios para reiniciar o jogo
        navio1 = 8;
        navio2 = 8;
        turnoPlayer = true;
        infoLabel.setText(player1 + ", é sua vez.");// Atualiza o rótulo de informações
        naviosLabel.setText("Navios Restantes - " + player1 + ": " + navio1 + " | " + player2 + ": " + navio2);// Atualiza o rótulo de navios restantes

        // Reinicia os botões dos tabuleiros
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                buttons1[i][j].setEnabled(true);
                buttons1[i][j].setText("");
                buttons1[i][j].setBackground(Color.BLUE);
                buttons2[i][j].setEnabled(true);
                buttons2[i][j].setText("");
                buttons2[i][j].setBackground(Color.BLUE);
            }
        }

        // Reposiciona os navios nos tabuleiros
        inserirOsNavioNoTabuleiro();
    }

    // Método para posicionar os navios nos tabuleiros

    public static void inserirOsNavioNoTabuleiro() {
        tabuleiro1 = posicaoDosNavioNoTabuleiro();// Posiciona os navios para o Jogador 1
        tabuleiro2 = posicaoDosNavioNoTabuleiro();// Posiciona os navios para o Jogador 2
    }

    // Método para criar e retornar um tabuleiro vazio

    public static int[][] prepararTabuleiro() {
        return new int[altura][largura];
    }

    // Método para posicionar os navios aleatoriamente no tabuleiro

    public static int[][] posicaoDosNavioNoTabuleiro() {
        int[][] tabuleiroPreparado = prepararTabuleiro();// Cria um tabuleiro vazio
        Random blocoAleatorio = new Random();// Gerador de números aleatórios
        int naviosInseridos = 0;

        // Insere os navios aleatoriamente no tabuleiro
        while (naviosInseridos < 8) { // Ajuste o número de navios conforme necessário
            int x = blocoAleatorio.nextInt(altura);// Gera uma posição aleatória para o navio
            int y = blocoAleatorio.nextInt(largura);

            if (tabuleiroPreparado[x][y] != 1) { // Se a posição não estiver ocupada por um navio
                tabuleiroPreparado[x][y] = 1; // Coloca um navio na posição
                naviosInseridos++;// Incrementa o contador de navios inseridos
            }
        }

        return tabuleiroPreparado;
    }// Retorna o tabuleiro com os navios posicionados

    // Método principal para iniciar o jogo

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }// Cria e exibe a interface gráfica do jogo
}