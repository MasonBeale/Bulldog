import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The BulldogGUI class represents the main GUI for the Bulldog dice game.
 * It allows users to add players, start the game, and interact with the game through a graphical interface.
 */
public class BulldogGUI extends JFrame {
    private static final int WINNING_SCORE = 104; // Constant for the winning score

    private JComboBox<String> playerTypeComboBox; // Dropdown for selecting player type
    private JTextField playerNameField; // Text field for entering player names
    private JButton addPlayerButton; // Button to add a player
    private JButton startGameButton; // Button to start the game
    private JTextArea gameLogArea; // Text area for displaying game logs
    private ArrayList<Player> players; // List of players in the game
    private JPanel playerPanel; // Panel to display the list of players
    private JPanel scorePanel; // Panel to display player scores
    private CardLayout cardLayout; // Layout manager for switching screens
    private JPanel cardPanel; // Main panel for holding screens
    private JButton rollButton; // Button to roll the dice
    private JButton endButton; // Button to end the current turn
    private JButton returnButton; // Button to return to the start screen
    private Player currentPlayer; // The player whose turn it is
    private int turnScore; // The score accumulated during the current turn

    /**
     * Constructs a new BulldogGUI and initializes the game interface.
     */
    public BulldogGUI() {
        // Set up the JFrame
        setTitle("Bulldog Game");
        setSize(800, 500); // Adjusted size for a more compact layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the players list
        players = new ArrayList<>();

        // Create the card layout and card panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add screens to the card panel
        cardPanel.add(createStartScreen(), "StartScreen");
        cardPanel.add(createGameScreen(), "GameScreen");

        // Add the card panel to the frame
        add(cardPanel);

        // Display the frame
        setVisible(true);
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
                if (players.isEmpty()) {
                    JOptionPane.showMessageDialog(BulldogGUI.this, "Please add at least one player.", "Error", JOptionPane.ERROR_MESSAGE);
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
        gamePanel.setBackground(new Color(173, 216, 230)); // Light blue background

        // Title panel with "Bulldog" in large text
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(173, 216, 230)); // Light blue background
        JLabel titleLabel = new JLabel("Bulldog");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 0, 139)); // Dark blue text
        titlePanel.add(titleLabel);
        gamePanel.add(titlePanel, BorderLayout.NORTH);

        // Score panel (left column)
        scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.setBorder(BorderFactory.createTitledBorder("Scores"));
        scorePanel.setBackground(new Color(173, 216, 230)); // Light blue background
        JScrollPane scoreScrollPane = new JScrollPane(scorePanel);
        scoreScrollPane.setPreferredSize(new Dimension(150, 300)); // Smaller scroll pane
        gamePanel.add(scoreScrollPane, BorderLayout.WEST);

        // Game log area (center)
        gameLogArea = new JTextArea();
        gameLogArea.setEditable(false);
        gameLogArea.setBackground(Color.WHITE); // White background
        JScrollPane gameLogScrollPane = new JScrollPane(gameLogArea);
        gameLogScrollPane.setBorder(BorderFactory.createTitledBorder("Game Log"));
        gamePanel.add(gameLogScrollPane, BorderLayout.CENTER);

        // Button panel (bottom)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(173, 216, 230)); // Light blue background

        // Roll and End Turn buttons
        JPanel turnButtonPanel = new JPanel();
        turnButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Reduced vertical spacing
        turnButtonPanel.setBackground(new Color(173, 216, 230)); // Light blue background

        rollButton = new JButton("Roll");
        rollButton.setBackground(new Color(0, 0, 139)); // Dark blue button
        rollButton.setForeground(Color.WHITE); // White text
        rollButton.setEnabled(false); // Disabled by default
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRoll();
            }
        });
        turnButtonPanel.add(rollButton);

        endButton = new JButton("End Turn");
        endButton.setBackground(new Color(0, 0, 139)); // Dark blue button
        endButton.setForeground(Color.WHITE); // White text
        endButton.setEnabled(false); // Disabled by default
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEndTurn();
            }
        });
        turnButtonPanel.add(endButton);

        buttonPanel.add(turnButtonPanel);

        // Return to start screen button
        returnButton = new JButton("Return to Start Screen");
        returnButton.setBackground(new Color(0, 0, 139)); // Dark blue button
        returnButton.setForeground(Color.WHITE); // White text
        returnButton.setEnabled(false); // Disabled by default
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                cardLayout.show(cardPanel, "StartScreen");
            }
        });

        // Center the return button
        JPanel returnButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        returnButtonPanel.setBackground(new Color(173, 216, 230)); // Light blue background
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
            playerName = "Player " + (players.size() + 1); // Default name: Player 1, Player 2, etc.
        }

        Player player = null;
        switch (playerType) {
            case "AiHumanPlayer":
                player = new AiHumanPlayer(playerName, this, this); // Pass 'this' as the parent frame and BulldogGUI
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
            players.add(player);
            JLabel playerLabel = new JLabel(playerName + " (" + playerType + ")");
            playerLabel.setForeground(new Color(0, 0, 139)); // Dark blue text
            playerPanel.add(playerLabel);
            playerPanel.revalidate();
            playerPanel.repaint();

            // Add player to the score panel
            JLabel scoreLabel = new JLabel(playerName + ": 0");
            scoreLabel.setForeground(new Color(0, 0, 139)); // Dark blue text
            scorePanel.add(scoreLabel);
            scorePanel.revalidate();
            scorePanel.repaint();

            // Clear the text box after adding a player
            playerNameField.setText("");
        }
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
                    for (Player player : players) {
                        currentPlayer = player;
                        turnScore = 0;

                        // Enable buttons if it's a human player's turn
                        if (player instanceof AiHumanPlayer) {
                            SwingUtilities.invokeLater(() -> {
                                rollButton.setEnabled(true);
                                endButton.setEnabled(true);
                            });
                        }

                        publish("\nPlayer " + player.getName() + "'s turn:\n");

                        // Handle human player's turn
                        if (player instanceof AiHumanPlayer) {
                            // Wait for the human player to roll or end their turn
                            while (true) {
                                try {
                                    Thread.sleep(100); // Polling to check for button clicks
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (turnScore == -1) { // End turn signaled
                                    break;
                                }
                            }
                        } else {
                            // Handle AI player's turn
                            int score = player.play();
                            player.setScore(player.getScore() + score);
                            publish("   Scored: " + score + " this turn.\n");
                        }

                        // Disable buttons after the turn
                        SwingUtilities.invokeLater(() -> {
                            rollButton.setEnabled(false);
                            endButton.setEnabled(false);
                        });

                        // Update the score panel
                        updateScorePanel();

                        // Add a small pause between turns
                        try {
                            Thread.sleep(500); // 0.5-second pause
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (player.getScore() >= WINNING_SCORE) {
                            publish("\nPlayer " + player.getName() + " wins with a score of " + player.getScore() + "!\n");
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
        if (currentPlayer instanceof AiHumanPlayer) {
            ((AiHumanPlayer) currentPlayer).play(); // Delegate roll handling to AiHumanPlayer
        }
    }

    /**
     * Handles the end turn action for the current player.
     */
    private void handleEndTurn() {
        gameLogArea.append("   Turn ended. Scored: " + turnScore + " this turn.\n");
        currentPlayer.setScore(currentPlayer.getScore() + turnScore);
        updateScorePanel();
        turnScore = -1; // Signal to end the turn
    }

    /**
     * Updates the score panel with the current scores of all players.
     */
    private void updateScorePanel() {
        // Clear the score panel and re-add updated scores
        scorePanel.removeAll();
        for (Player player : players) {
            JLabel scoreLabel = new JLabel(player.getName() + ": " + player.getScore());
            scoreLabel.setForeground(new Color(0, 0, 139)); // Dark blue text
            scorePanel.add(scoreLabel);
        }
        scorePanel.revalidate();
        scorePanel.repaint();
    }

    /**
     * Resets the game to its initial state.
     */
    private void resetGame() {
        players.clear();
        playerPanel.removeAll();
        playerPanel.revalidate();
        playerPanel.repaint();
        scorePanel.removeAll();
        scorePanel.revalidate();
        scorePanel.repaint();
        gameLogArea.setText("");
        returnButton.setEnabled(false); // Disable the return button after reset
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
                new BulldogGUI();
            }
        });
    }
}