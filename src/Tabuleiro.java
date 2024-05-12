import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tabuleiro extends JFrame {

    private JButton[][] playerGrid; // Tabuleiro do jogador
    private JButton[][] enemyGrid;  // Tabuleiro do inimigo
    private final int SIZE = 15; // Tamanho dos tabuleiros

    // Construtor da classe TESTE
    public Tabuleiro() {
        setTitle("Batalha Naval");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Painel principal com layout de grade para os tabuleiros do jogador e do inimigo
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        JPanel playerPanel = new JPanel(new GridLayout(SIZE, SIZE)); // Painel do tabuleiro do jogador
        JPanel enemyPanel = new JPanel(new GridLayout(SIZE, SIZE)); // Painel do tabuleiro do inimigo

        playerGrid = new JButton[SIZE][SIZE]; // Inicialização do tabuleiro do jogador
        enemyGrid = new JButton[SIZE][SIZE];  // Inicialização do tabuleiro do inimigo

        // Configurar os tabuleiros do jogador e do inimigo
        configureGrid(playerGrid, true); // Configuração do tabuleiro do jogador
        configureGrid(enemyGrid, false);  // Configuração do tabuleiro do inimigo

        // Adicionar botões aos painéis dos tabuleiros
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                playerPanel.add(playerGrid[i][j]); // Adicionar botão do jogador ao painel do jogador
                enemyPanel.add(enemyGrid[i][j]);   // Adicionar botão do inimigo ao painel do inimigo
            }
        }

        // Adicionar os painéis dos tabuleiros ao painel principal
        mainPanel.add(playerPanel);
        mainPanel.add(enemyPanel);

        getContentPane().add(mainPanel); // Adicionar o painel principal ao conteúdo do frame

        setSize(1200, 800); // Definir o tamanho do frame
        setLocationRelativeTo(null); // Centralizar o frame na tela
        setVisible(true); // Tornar o frame visível
    }

    // Método para configurar os tabuleiros
    private void configureGrid(JButton[][] grid, boolean playerGrid) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new JButton(); // Criar um novo botão
                grid[i][j].setBackground(Color.BLUE); // Definir a cor de fundo do botão como azul

                if (playerGrid) { // Se for o tabuleiro do jogador

                    // Adicionar ação de clique para posicionar navios
                    grid[i][j].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JButton button = (JButton) e.getSource();
                            // Aqui você pode adicionar lógica para posicionar os navios
                            // por exemplo, mudar a cor do botão para indicar que um navio foi colocado
                            button.setBackground(Color.GREEN);
                        }
                    });
                } else { // Se for o tabuleiro do inimigo

                    // Adicionar ação de clique para ataques
                    grid[i][j].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JButton button = (JButton) e.getSource();
                            // Aqui você pode adicionar lógica para atacar
                            // por exemplo, verificar se houve um acerto ou um erro
                            button.setBackground(Color.RED);
                        }
                    });
                }
            }
        }
    }

    // Método principal
    public static void main(String[] args) {
        // Criar e exibir a interface gráfica Swing em uma thread de despacho de eventos
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Tabuleiro(); // Instanciar a classe TESTE
            }
        });
    }
}