import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The BulldogGUI class represents the main GUI for the Bulldog dice game.
 * It allows users to add players, start the game, and interact with the game through a graphical interface.
 */
public class BulldogMVC extends JFrame implements PlayerListListener {
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

    public BulldogMVC() {
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
        String[] playerTypes = {"AiHumanPlayer", "AiRandomPlayer", "AiFifteenPlayer", "AiUniquePlayer", "WimpPlayer", "BoldPlayer"};
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
                    JOptionPane.showMessageDialog(BulldogMVC.this, "Please add at least one player.", "Error", JOptionPane.ERROR_MESSAGE);
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
            case "AiHumanPlayer":
                player = new AiHumanPlayerMVC(playerName, this, this); // Pass 'this' as the parent frame and BulldogGUI
                break;
            case "AiRandomPlayer":
                player = new AiRandomPlayer(playerName);
                break;
            case "AiFifteenPlayer":
                player = new AiFifteenPlayer(playerName);
                break;
            case "AiUniquePlayer":
                player = new AiUniquePlayer(playerName);
                break;
            case "WimpPlayer":
                player = new WimpPlayer(playerName);
                break;
            case "BoldPlayer":
                player = new BoldPlayer(playerName, WINNING_SCORE);
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
        // Use a SwingWorker to run the game logic in a separate thread
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                boolean gameOver = false;
                while (!gameOver) {
                    for (int i = 0; i < players.getPlayerCount(); i++) {
                        currentPlayer = players.getPlayers().get(i);
                        turnScore = 0;

                        // Enable buttons if it's a human player's turn
                        if (currentPlayer instanceof AiHumanPlayerMVC) {
                            SwingUtilities.invokeLater(() -> {
                                rollButton.setEnabled(true);
                                endButton.setEnabled(true);
                            });
                        }

                        publish("\nPlayer " + currentPlayer.getName() + "'s turn:\n");

                        // Handle human player's turn
                        if (currentPlayer instanceof AiHumanPlayerMVC) {
                            // Wait for the human player to roll or end their turn
                            while (true) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (turnScore == -1) { // End turn signaled
                                    break;
                                }
                            }
                            // The score has already been updated by handleEndTurn()
                        } else {
                            // Handle AI player's turn (existing code)
                            int score = currentPlayer.play();
                            players.setPlayerScore(i, players.getPlayerScore(i) + score);
                            publish("   Scored: " + score + " this turn.\n");
                        }

                        // Disable buttons after the turn
                        SwingUtilities.invokeLater(() -> {
                            rollButton.setEnabled(false);
                            endButton.setEnabled(false);
                        });

                        // Add a small pause between turns
                        try {
                            Thread.sleep(500); // 0.5-second pause
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (players.getPlayerScore(i) >= WINNING_SCORE) {
                            publish("\nPlayer " + currentPlayer.getName() + " wins with a score of " + 
                                   players.getPlayerScore(i) + "!\n");
                            gameOver = true;
                            SwingUtilities.invokeLater(() -> {
                                returnButton.setEnabled(true); // Enable the return button
                            });
                            break;
                        }
                    }
                }
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                // Update the game log area in real time
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
        if (currentPlayer instanceof AiHumanPlayerMVC) {
            ((AiHumanPlayerMVC) currentPlayer).play(); // Delegate roll handling to AiHumanPlayer
        }
    }

    /**
     * Handles the end turn action for the current player.
     */
    private void handleEndTurn() {
        gameLogArea.append("   Turn ended. Scored: " + turnScore + " this turn.\n");
        // Find the current player's index and update their score
        int playerIndex = players.getPlayers().indexOf(currentPlayer);
        if (playerIndex != -1) {
            players.setPlayerScore(playerIndex, players.getPlayerScore(playerIndex) + turnScore);
        }
        turnScore = -1; // Signal to end the turn
    }

    /**
     * Resets the game to its initial state.
     */
    private void resetGame() {
        players = new PlayerList(); // Create a new PlayerList
        players.addListener(this); // Re-register the listener
        playerPanel.removeAll();
        playerPanel.revalidate();
        playerPanel.repaint();
        gameLogArea.setText("");
        returnButton.setEnabled(false); // Disable the return button after reset
        
        // Create a new scoreboard with the new player list
        scoreboard = new Scoreboard(players);
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
                new BulldogMVC();
            }
        });
    }
}