import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BulldogGUI extends JFrame {
    private JComboBox<String> playerTypeComboBox;
    private JTextField playerNameField;
    private JButton addPlayerButton;
    private JButton startGameButton;
    private JTextArea gameLogArea;
    private ArrayList<Player> players;
    private JPanel playerPanel;
    private JPanel scorePanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton rollButton;
    private JButton endButton;
    private JButton returnButton;
    private Player currentPlayer;
    private int turnScore;

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
                player = new AiHumanPlayer(playerName, this); // Pass 'this' as the parent frame
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
                player = new BoldPlayer(playerName);
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
                            publish("   Total score: " + player.getScore() + "\n");
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
                            Thread.sleep(500); // 1-second pause
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
    
                        if (player.getScore() >= 104) {
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

    private void handleRoll() {
        int roll = (int) (Math.random() * 6 + 1);
        gameLogArea.append("   Player " + currentPlayer.getName() + " rolled a " + roll + "\n");
        if (roll == 6) {
            gameLogArea.append("   Rolled a 6! Turn over. Score for this turn: 0\n");
            turnScore = 0;
            currentPlayer.setScore(currentPlayer.getScore() + turnScore);
            updateScorePanel();
            turnScore = -1; // Signal to end the turn
        } else {
            turnScore += roll;
            gameLogArea.append("   Current turn score: " + turnScore + "\n");
        }
    }

    private void handleEndTurn() {
        gameLogArea.append("   Turn ended. Score for this turn: " + turnScore + "\n");
        currentPlayer.setScore(currentPlayer.getScore() + turnScore);
        updateScorePanel();
        turnScore = -1; // Signal to end the turn
    }

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