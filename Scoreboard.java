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

    /**
     * Constructs a Scoreboard viewer for the specified PlayerList.
     * @param playerList the PlayerList model to display
     */
    public Scoreboard(PlayerList playerList) {
        this.playerList = playerList;
        playerList.addListener(this);
        createGUI();
    }

    /**
     * Creates the GUI for the scoreboard.
     */
    private void createGUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Scoreboard"));

        scoreboardText = new JTextArea();
        scoreboardText.setEditable(false);
        scoreboardText.setFont(new Font("Monospaced", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(scoreboardText);

        add(scrollPane, BorderLayout.CENTER);
        updateScoreboard();
    }

    /**
     * Updates the scoreboard display with current player data.
     */
    private void updateScoreboard() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %10s\n", "Player Name", "Score"));
        sb.append("-------------------- -----------\n");

        List<Player> players = playerList.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            String name = playerList.getPlayerName(i);
            int score = playerList.getPlayerScore(i);
            sb.append(String.format("%-20s %10d\n", name, score));
        }

        scoreboardText.setText(sb.toString());
    }

    /**
     * Called when the player list changes, triggering a scoreboard update.
     */
    @Override
    public void playerListChanged() {
        updateScoreboard();
    }
}