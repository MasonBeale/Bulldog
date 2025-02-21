import javax.swing.*;

public class AiHumanPlayer extends Player {
    private JFrame parentFrame;
    
    public AiHumanPlayer(String name, JFrame parentFrame) {
        super(name);
        this.parentFrame = parentFrame;
    }

    @Override
    public int play() {
        int turnScore = 0;
        while (true) {
            int roll = (int) (Math.random() * 6 + 1);
            String message = "Player " + getName() + " rolled a " + roll + "\n";
            if (roll == 6) {
                message += "Rolled a 6! Turn over. Score for this turn: 0";
                showDialog(message, "Turn Over", JOptionPane.INFORMATION_MESSAGE);
                return 0;
            } else {
                turnScore += roll;
                message += "Current turn score: " + turnScore + "\n";
                int choice = showConfirmDialog(message + "Continue rolling?", "Continue?", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION) {
                    message = "Turn ended. Score for this turn: " + turnScore;
                    showDialog(message, "Turn Ended", JOptionPane.INFORMATION_MESSAGE);
                    return turnScore;
                }
            }
        }
    }

    private void showDialog(String message, String title, int messageType) {
        JOptionPane pane = new JOptionPane(message, messageType);
        JDialog dialog = pane.createDialog(parentFrame, title);
        dialog.setLocation(parentFrame.getX() + parentFrame.getWidth(), parentFrame.getY()); // Position on the right
        dialog.setVisible(true);
    }

    private int showConfirmDialog(String message, String title, int optionType) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, optionType);
        JDialog dialog = pane.createDialog(parentFrame, title);
        dialog.setLocation(parentFrame.getX() + parentFrame.getWidth(), parentFrame.getY()); // Position on the right
        dialog.setVisible(true);
        Object selectedValue = pane.getValue();
        if (selectedValue instanceof Integer) {
            return (Integer) selectedValue;
        }
        return JOptionPane.CLOSED_OPTION;
    }
}