import java.util.ArrayList;
import java.util.List;

/**
 * A class that encapsulates and manages a list of Player objects.
 * Provides methods to add, retrieve, and modify player information,
 * and supports the Model-View-Controller pattern through listener notifications.
 * @author Mason Beale with DeepSeek
 */
public class PlayerList {
    /** The list of players maintained by this class */
    private List<Player> players;
    
    /** List of registered listeners to be notified of changes */
    private List<PlayerListListener> listeners;

    /**
     * Constructs an empty PlayerList with no players and no listeners.
     */
    public PlayerList() {
        this.players = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    /**
     * Adds a new player to the list and notifies all registered listeners.
     *
     * @param player the Player object to be added to the list
     * @throws IllegalArgumentException if the player parameter is null
     */
    public void addPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        players.add(player);
        notifyListeners();
    }

    /**
     * Retrieves the name of the player at the specified position in the list.
     *
     * @param index the index of the player whose name is to be returned
     * @return the name of the player at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
     */
    public String getPlayerName(int index) throws IndexOutOfBoundsException {
        return players.get(index).getName();
    }

    /**
     * Retrieves the score of the player at the specified position in the list.
     *
     * @param index the index of the player whose score is to be returned
     * @return the score of the player at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
     */
    public int getPlayerScore(int index) throws IndexOutOfBoundsException {
        return players.get(index).getScore();
    }

    /**
     * Updates the score of the player at the specified position in the list
     * and notifies all registered listeners of the change.
     *
     * @param index the index of the player whose score is to be updated
     * @param newScore the new score value to be set
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
     */
    public void setPlayerScore(int index, int newScore) throws IndexOutOfBoundsException {
        players.get(index).setScore(newScore);
        notifyListeners();
    }

    /**
     * Returns the number of players currently in the list.
     *
     * @return the number of players in the list
     */
    public int getPlayerCount() {
        return players.size();
    }

    /**
     * Registers a listener to be notified whenever the player list changes.
     *
     * @param listener the PlayerListListener to be registered
     * @throws IllegalArgumentException if the listener parameter is null
     */
    public void addListener(PlayerListListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }
        listeners.add(listener);
    }

    /**
     * Unregisters a listener so it will no longer receive change notifications.
     *
     * @param listener the PlayerListListener to be unregistered
     */
    public void removeListener(PlayerListListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all registered listeners that the player list has changed.
     * This method is called automatically whenever the list is modified.
     */
    private void notifyListeners() {
        for (PlayerListListener listener : listeners) {
            listener.playerListChanged();
        }
    }

    /**
     * Returns an unmodifiable view of the players list.
     * This prevents external modification while allowing read access.
     *
     * @return an unmodifiable List containing all players
     */
    public List<Player> getPlayers() {
        return List.copyOf(players);
    }
}

/**
 * An interface for objects that need to be notified when the PlayerList changes.
 * Typically implemented by View components in the MVC pattern.
 */
interface PlayerListListener {
    /**
     * Called whenever the PlayerList is modified.
     * Implementing classes should update their display or take other appropriate action.
     */
    void playerListChanged();
}