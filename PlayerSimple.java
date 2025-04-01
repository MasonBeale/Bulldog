/**
 * Represents a player with a name and score.
 */
class PlayerSimple {
    /** The player's name */
    private String name;
    
    /** The player's current score */
    private int score;

    /**
     * Constructs a new Player with the specified name and score.
     *
     * @param name the player's name
     * @param score the player's initial score
     * @throws IllegalArgumentException if name is null or empty
     */
    public PlayerSimple(String name, int score) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        this.name = name;
        this.score = score;
    }

    /**
     * Returns the player's name.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name.
     *
     * @param name the new name for the player
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Returns the player's current score.
     *
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score.
     *
     * @param score the new score for the player
     */
    public void setScore(int score) {
        this.score = score;
    }
}