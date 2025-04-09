import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The BulldogGUI class represents the main GUI for the Bulldog dice game.
 * It allows users to add players, start the game, and interact with the game through a graphical interface.
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
    private Scoreboard scoreboard; // Now part of the main panel
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

        // Initialize the scoreboard (now a panel)
        scoreboard = new Scoreboard(players);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createStartScreen(), "StartScreen");
        cardPanel.add(createGameScreen(), "GameScreen");

        add(cardPanel);
        setVisible(true);
    }


    /**
     * Called when the player list changes to update the display.
     */
    @Override
    public void playerListChanged() {
        updatePlayerListDisplay();
    }

    /**
     * Creates the start screen where players can be added and the game can be started.
     *
     * @return The start screen panel.
     */
    private JPanel createStartScreen() {
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());
        startPanel.setBackground(new Color(173, 216, 230)); // Light blue background

        // Title panel with "Bulldog" in large text
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(173, 216, 230)); // Light blue background
        JLabel titleLabel = new JLabel("Bulldog");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 0, 139)); // Dark blue text
        titlePanel.add(titleLabel);
        startPanel.add(titlePanel, BorderLayout.NORTH);

        // Center panel for player controls and player list
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(173, 216, 230)); // Light blue background

        // Top panel for adding players
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Reduced vertical spacing
        topPanel.setBackground(new Color(173, 216, 230)); // Light blue background

        // Player type dropdown
        String[] playerTypes = {"HumanPlayer", "RandomPlayer", "FifteenPlayer", "UniquePlayer", "WimpPlayer", "BoldPlayer"};
        playerTypeComboBox = new JComboBox<>(playerTypes);
        playerTypeComboBox.setBackground(Color.WHITE);
        topPanel.add(new JLabel("Player Type:"));
        topPanel.add(playerTypeComboBox);

        // Player name text field
        playerNameField = new JTextField(10); // Smaller text field
        playerNameField.setBackground(Color.WHITE);
        topPanel.add(new JLabel("Player Name:"));
        topPanel.add(playerNameField);

        // Add player button
        addPlayerButton = new JButton("Add Player");
        addPlayerButton.setBackground(new Color(0, 0, 139)); // Dark blue button
        addPlayerButton.setForeground(Color.WHITE); // White text
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer();
            }
        });
        topPanel.add(addPlayerButton);

        // Start game button
        startGameButton = new JButton("Start Game");
        startGameButton.setBackground(new Color(0, 0, 139)); // Dark blue button
        startGameButton.setForeground(Color.WHITE); // White text
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

        // Add the top panel to the center panel
        centerPanel.add(topPanel);

        // Player list panel
        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createTitledBorder("Players"));
        playerPanel.setBackground(new Color(173, 216, 230)); // Light blue background
        JScrollPane playerScrollPane = new JScrollPane(playerPanel);
        playerScrollPane.setPreferredSize(new Dimension(200, 150)); // Smaller scroll pane
        centerPanel.add(playerScrollPane);

        // Add the center panel to the start screen
        startPanel.add(centerPanel, BorderLayout.CENTER);

        return startPanel;
    }

    /**
     * Creates the game screen where the game is played.
     *
     * @return The game screen panel.
     */
    private JPanel createGameScreen() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(173, 216, 230));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(173, 216, 230));
        JLabel titleLabel = new JLabel("Bulldog");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 0, 139));
        titlePanel.add(titleLabel);
        gamePanel.add(titlePanel, BorderLayout.NORTH);

        // Main content panel with scoreboard and game log
        JPanel contentPanel = new JPanel(new GridLayout(1, 2));
        contentPanel.setBackground(new Color(173, 216, 230));

        // Add the scoreboard to the left
        scoreboard.setPreferredSize(new Dimension(300, 300));
        contentPanel.add(scoreboard);

        // Game log area on the right
        gameLogArea = new JTextArea();
        gameLogArea.setEditable(false);
        gameLogArea.setBackground(Color.WHITE);
        JScrollPane gameLogScrollPane = new JScrollPane(gameLogArea);
        gameLogScrollPane.setBorder(BorderFactory.createTitledBorder("Game Log"));
        contentPanel.add(gameLogScrollPane);

        gamePanel.add(contentPanel, BorderLayout.CENTER);

        // Button panel at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(173, 216, 230));

        // Roll and End Turn buttons
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

        // Return to start screen button
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

    /**
     * Adds a player to the game based on the selected player type and name.
     * If no name is entered, a default name is assigned.
     */
    private void addPlayer() {
        String playerType = (String) playerTypeComboBox.getSelectedItem();
        String playerName = playerNameField.getText().trim();

        // Assign a default name if no name is entered
        if (playerName.isEmpty()) {
            playerName = "Player " + (players.getPlayerCount() + 1); // Default name: Player 1, Player 2, etc.
        }

        Player player = null;
        switch (playerType) {
            case "HumanPlayer":
                player = new HumanPlayer(playerName, dice, this, this); // Pass 'this' as the parent frame and BulldogGUI
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
            // Clear the text box after adding a player
            playerNameField.setText("");
        }
    }

    /**
     * Updates the player list display in the start screen.
     */
    private void updatePlayerListDisplay() {
        playerPanel.removeAll();
        List<Player> playerList = players.getPlayers();
        for (Player player : playerList) {
            JLabel playerLabel = new JLabel(player.getName() + " (" + player.getClass().getSimpleName() + ")");
            playerLabel.setForeground(new Color(0, 0, 139)); // Dark blue text
            playerPanel.add(playerLabel);
        }
        playerPanel.revalidate();
        playerPanel.repaint();
    }

    /**
     * Starts the game and handles the game logic in a separate thread.
     */
    private void startGame() {
        // Set the winning score before starting the game
        Referee.getInstance().setWinningScore(WINNING_SCORE);

        // Create a SwingWorker to run the game in the background
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Use the Referee singleton to play the game
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
                            turnScore = 0;
                            
                            // Enable buttons for human player
                            SwingUtilities.invokeLater(() -> {
                                rollButton.setEnabled(true);
                                endButton.setEnabled(true);
                            });
                            
                            // Wait for human to finish turn
                            while (turnScore >= 0) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            
                            // Disable buttons after turn
                            SwingUtilities.invokeLater(() -> {
                                rollButton.setEnabled(false);
                                endButton.setEnabled(false);
                            });
                            
                            return -turnScore - 1; // Convert the signal back to actual score
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

    /**
     * Handles the roll action for the current player.
     */
    private void handleRoll() {
        if (currentPlayer instanceof HumanPlayer) {
            ((HumanPlayer) currentPlayer).play(); // Delegate roll handling to HumanPlayer
        }
    }

    /**
     * Handles the end turn action for the current player.
     */
    private void handleEndTurn() {
        gameLogArea.append("   Turn ended. Scored: " + turnScore + " this turn.\n");
        int playerIndex = players.getPlayers().indexOf(currentPlayer);
        if (playerIndex != -1) {
            players.setPlayerScore(playerIndex, players.getPlayerScore(playerIndex) + turnScore);
        }
        turnScore = -turnScore - 1; // Signal end of turn with encoded score
    }

    /**
     * Resets the game to its initial state.
     */
    private void resetGame() {
        players = new PlayerList(); // Create a new PlayerList
        players.addListener(this); // Re-register the listener
        scoreboard.reset(players); // Reset the scoreboard with new player list
        playerPanel.removeAll();
        playerPanel.revalidate();
        playerPanel.repaint();
        gameLogArea.setText("");
        returnButton.setEnabled(false);
    }

    /**
     * Appends a message to the game log area.
     *
     * @param message The message to append.
     */
    public void appendToGameLog(String message) {
        gameLogArea.append(message);
    }

    /**
     * Sets the turn score.
     *
     * @param score The new turn score.
     */
    public void setTurnScore(int score) {
        this.turnScore = score;
    }

    /**
     * Gets the current turn score.
     *
     * @return The current turn score.
     */
    public int getTurnScore() {
        return turnScore;
    }

    /**
     * Signals the end of the current turn.
     */
    public void endTurn() {
        turnScore = -1; // Signal to end the turn
    }

    /**
     * The main method to launch the Bulldog game GUI.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Bulldog();
            }
        });
    }
}