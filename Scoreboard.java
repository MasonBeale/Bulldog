import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A viewer class that displays player scores in a scoreboard format.
 * Implements PlayerListListener to receive updates when the player data changes.
 */
public class Scoreboard extends JPanel implements PlayerListListener {
    private PlayerList playerList;
    private JTextArea scoreboardText;

    public Scoreboard(PlayerList playerList) {
        this.playerList = playerList;
        playerList.addListener(this);
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Scoreboard"));
        setPreferredSize(new Dimension(300, 200));

        scoreboardText = new JTextArea();
        scoreboardText.setEditable(false);
        scoreboardText.setFont(new Font("Monospaced", Font.BOLD, 14));
        scoreboardText.setBackground(new Color(240, 240, 240)); // Light gray background
        JScrollPane scrollPane = new JScrollPane(scoreboardText);

        add(scrollPane, BorderLayout.CENTER);
        updateScoreboard();
    }

    /**
     * Updates the scoreboard display with current player data.
     */
    private void updateScoreboard() {
        StringBuilder sb = new StringBuilder();
        
        if (playerList.getPlayerCount() == 0) {
            sb.append("No players in game\n");
            sb.append("-----------------\n");
            sb.append("Add players to begin");
        } else {
            sb.append(String.format("%-20s %10s\n", "Player Name", "Score"));
            sb.append("-------------------- -----------\n");

            List<Player> players = playerList.getPlayers();
            for (int i = 0; i < players.size(); i++) {
                String name = playerList.getPlayerName(i);
                int score = playerList.getPlayerScore(i);
                sb.append(String.format("%-20s %10d\n", name, score));
            }
        }

        scoreboardText.setText(sb.toString());
    }

    /**
     * Clears the scoreboard display and resets the player list reference.
     */
    public void reset(PlayerList newPlayerList) {
        this.playerList = newPlayerList;
        playerList.addListener(this);
        updateScoreboard();
    }

    @Override
    public void playerListChanged() {
        updateScoreboard();
    }
}