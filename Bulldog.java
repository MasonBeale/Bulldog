import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The BulldogGUI class represents the main GUI for the Bulldog dice game.
 * It allows users to add players, start the game, and interact with the game through a graphical interface.
 * @author Mason Beale with DeepSeek
 */
public class Bulldog extends JFrame implements PlayerListListener {
    private static final int WINNING_SCORE = 104; // Constant for the winning score

    private JComboBox<String> playerTypeComboBox;
    private JTextField playerNameField;
    private JButton addPlayerButton;
    private JButton startGameButton;
    private JTextArea gameLogArea;
    private PlayerList players;
    private JPanel playerPanel;
    private Scoreboard scoreboard;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton rollButton;
    private JButton endButton;
    private JButton returnButton;
    private Player currentPlayer;
    private int turnScore;
    private RandomDice dice = new Dice(6);

    public Bulldog() {
        setTitle("Bulldog Game");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        players = new PlayerList();
        players.addListener(this);

        scoreboard = new Scoreboard(players);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createStartScreen(), "StartScreen");
        cardPanel.add(createGameScreen(), "GameScreen");

        add(cardPanel);
        setVisible(true);
    }

    @Override
    public void playerListChanged() {
        updatePlayerListDisplay();
    }

    private JPanel createStartScreen() {
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());
        startPanel.setBackground(new Color(173, 216, 230));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(173, 216, 230));
        JLabel titleLabel = new JLabel("Bulldog");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 0, 139));
        titlePanel.add(titleLabel);
        startPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(173, 216, 230));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        topPanel.setBackground(new Color(173, 216, 230));

        String[] playerTypes = {"HumanPlayer", "RandomPlayer", "FifteenPlayer", "UniquePlayer", "WimpPlayer", "BoldPlayer"};
        playerTypeComboBox = new JComboBox<>(playerTypes);
        playerTypeComboBox.setBackground(Color.WHITE);
        topPanel.add(new JLabel("Player Type:"));
        topPanel.add(playerTypeComboBox);

        playerNameField = new JTextField(10);
        playerNameField.setBackground(Color.WHITE);
        topPanel.add(new JLabel("Player Name:"));
        topPanel.add(playerNameField);

        addPlayerButton = new JButton("Add Player");
        addPlayerButton.setBackground(new Color(0, 0, 139));
        addPlayerButton.setForeground(Color.WHITE);
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer();
            }
        });
        topPanel.add(addPlayerButton);

        startGameButton = new JButton("Start Game");
        startGameButton.setBackground(new Color(0, 0, 139));
        startGameButton.setForeground(Color.WHITE);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (players.getPlayerCount() == 0) {
                    JOptionPane.showMessageDialog(Bulldog.this, "Please add at least one player.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    cardLayout.show(cardPanel, "GameScreen");
                    startGame();
                }
            }
        });
        topPanel.add(startGameButton);

        centerPanel.add(topPanel);

        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createTitledBorder("Players"));
        playerPanel.setBackground(new Color(173, 216, 230));
        JScrollPane playerScrollPane = new JScrollPane(playerPanel);
        playerScrollPane.setPreferredSize(new Dimension(200, 150));
        centerPanel.add(playerScrollPane);

        startPanel.add(centerPanel, BorderLayout.CENTER);

        return startPanel;
    }

    private JPanel createGameScreen() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(173, 216, 230));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(173, 216, 230));
        JLabel titleLabel = new JLabel("Bulldog");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 0, 139));
        titlePanel.add(titleLabel);
        gamePanel.add(titlePanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(1, 2));
        contentPanel.setBackground(new Color(173, 216, 230));

        scoreboard.setPreferredSize(new Dimension(300, 300));
        contentPanel.add(scoreboard);

        gameLogArea = new JTextArea();
        gameLogArea.setEditable(false);
        gameLogArea.setBackground(Color.WHITE);
        JScrollPane gameLogScrollPane = new JScrollPane(gameLogArea);
        gameLogScrollPane.setBorder(BorderFactory.createTitledBorder("Game Log"));
        contentPanel.add(gameLogScrollPane);

        gamePanel.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(173, 216, 230));

        JPanel turnButtonPanel = new JPanel();
        turnButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        turnButtonPanel.setBackground(new Color(173, 216, 230));

        rollButton = new JButton("Roll");
        rollButton.setBackground(new Color(0, 0, 139));
        rollButton.setForeground(Color.WHITE);
        rollButton.setEnabled(false);
        rollButton.addActionListener(e -> handleRoll());
        turnButtonPanel.add(rollButton);

        endButton = new JButton("End Turn");
        endButton.setBackground(new Color(0, 0, 139));
        endButton.setForeground(Color.WHITE);
        endButton.setEnabled(false);
        endButton.addActionListener(e -> handleEndTurn());
        turnButtonPanel.add(endButton);

        buttonPanel.add(turnButtonPanel);

        returnButton = new JButton("Return to Start Screen");
        returnButton.setBackground(new Color(0, 0, 139));
        returnButton.setForeground(Color.WHITE);
        returnButton.setEnabled(false);
        returnButton.addActionListener(e -> {
            resetGame();
            cardLayout.show(cardPanel, "StartScreen");
        });

        JPanel returnButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        returnButtonPanel.setBackground(new Color(173, 216, 230));
        returnButtonPanel.add(returnButton);
        buttonPanel.add(returnButtonPanel);

        gamePanel.add(buttonPanel, BorderLayout.SOUTH);

        return gamePanel;
    }

    private void addPlayer() {
        String playerType = (String) playerTypeComboBox.getSelectedItem();
        String playerName = playerNameField.getText().trim();

        if (playerName.isEmpty()) {
            playerName = "Player " + (players.getPlayerCount() + 1);
        }

        Player player = null;
        switch (playerType) {
            case "HumanPlayer":
                player = new HumanPlayer(playerName, dice, this, this);
                break;
            case "RandomPlayer":
                player = new RandomPlayer(playerName, dice);
                break;
            case "FifteenPlayer":
                player = new FifteenPlayer(playerName, dice);
                break;
            case "UniquePlayer":
                player = new UniquePlayer(playerName, dice);
                break;
            case "WimpPlayer":
                player = new WimpPlayer(playerName, dice);
                break;
            case "BoldPlayer":
                player = new BoldPlayer(playerName, dice, WINNING_SCORE);
                break;
        }

        if (player != null) {
            players.addPlayer(player);
            playerNameField.setText("");
        }
    }

    private void updatePlayerListDisplay() {
        playerPanel.removeAll();
        List<Player> playerList = players.getPlayers();
        for (Player player : playerList) {
            JLabel playerLabel = new JLabel(player.getName() + " (" + player.getClass().getSimpleName() + ")");
            playerLabel.setForeground(new Color(0, 0, 139));
            playerPanel.add(playerLabel);
        }
        playerPanel.revalidate();
        playerPanel.repaint();
    }

    private void startGame() {
        Referee.getInstance().setWinningScore(WINNING_SCORE);

        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                Referee.getInstance().playGame(players, 
                    new GameLogCallback() {
                        @Override
                        public void appendToGameLog(String message) {
                            publish(message);
                        }
                    },
                    new TurnCallback() {
                        @Override
                        public int handleHumanTurn(Player player) {
                            currentPlayer = player;
                            turnScore = 0; // Reset turn score at start of turn
                            
                            SwingUtilities.invokeLater(() -> {
                                rollButton.setEnabled(true);
                                endButton.setEnabled(true);
                            });
                            
                            while (turnScore >= 0) { // Wait for turn to end (turnScore set to -1)
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            
                            SwingUtilities.invokeLater(() -> {
                                rollButton.setEnabled(false);
                                endButton.setEnabled(false);
                            });
                            
                            return -turnScore - 1; // Convert back to actual score
                        }
                        
                        @Override
                        public void gameEnded() {
                            SwingUtilities.invokeLater(() -> {
                                returnButton.setEnabled(true);
                            });
                        }
                    });
                return null;
            }
            
            @Override
            protected void process(List<String> chunks) {
                for (String message : chunks) {
                    gameLogArea.append(message);
                }
            }
        };
        
        worker.execute();
    }

    private void handleRoll() {
        if (currentPlayer instanceof HumanPlayer) {
            GameStatus gameStatus = new GameStatus(players, currentPlayer);
            gameStatus.setCurrentTurnScore(turnScore);
            int rollResult = ((HumanPlayer) currentPlayer).play(gameStatus);
            
            if (rollResult == 0) { // Rolled a 6
                turnScore = -1; // Signal end of turn
            } else {
                turnScore += rollResult; // Add to current turn score
            }
        }
    }

    private void handleEndTurn() {
        // Find current player index and update their score
        int playerIndex = players.getPlayers().indexOf(currentPlayer);
        if (playerIndex != -1) {
            players.setPlayerScore(playerIndex, players.getPlayerScore(playerIndex) + turnScore);
        }
        gameLogArea.append("   Turn ended. Scored: " + turnScore + " this turn.\n");
        turnScore = -1; // Signal end of turn
    }

    private void resetGame() {
        players = new PlayerList();
        players.addListener(this);
        scoreboard.reset(players);
        playerPanel.removeAll();
        playerPanel.revalidate();
        playerPanel.repaint();
        gameLogArea.setText("");
        returnButton.setEnabled(false);
    }

    public void appendToGameLog(String message) {
        gameLogArea.append(message);
    }

    public void setTurnScore(int score) {
        this.turnScore = score;
    }

    public int getTurnScore() {
        return turnScore;
    }

    public void endTurn() {
        turnScore = -1;
    }

    public boolean shouldContinueTurn() {
        return turnScore >= 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Bulldog();
            }
        });
    }
}