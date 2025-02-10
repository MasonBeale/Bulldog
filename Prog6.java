import java.util.ArrayList;
import java.util.Scanner;

public class Prog6 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        ArrayList<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            System.out.println("Choose player type for player " + i + ":");
            System.out.println("1. HumanPlayer");
            System.out.println("2. RandomPlayer");
            System.out.println("3. FifteenPlayer");
            System.out.println("4. UniquePlayer");
            System.out.println("5. WimpPlayer");
            System.out.println("6. BoldPlayer");
            System.out.print("Enter choice (1-6): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter player name: ");
            String name = scanner.nextLine();

            switch (choice) {
                case 1:
                    players.add(new AiHumanPlayer(name));
                    break;
                case 2:
                    players.add(new AiRandomPlayer(name));
                    break;
                case 3:
                    players.add(new AiFifteenPlayer(name));
                    break;
                case 4:
                    players.add(new AiUniquePlayer(name));
                    break;
                case 5:
                    players.add(new WimpPlayer(name));
                    break;
                case 6:
                    players.add(new BoldPlayer(name));
                    break;
                default:
                    System.out.println("Invalid choice. Defaulting to RandomPlayer.");
                    players.add(new AiRandomPlayer(name));
                    break;
            }
        }

        boolean gameOver = false;
        while (!gameOver) {
            for (Player player : players) {
                System.out.println("\nPlayer " + player.getName() + "'s turn:");
                int turnScore = player.play();
                player.setScore(player.getScore() + turnScore);
                System.out.println("   Total score: " + player.getScore());

                if (player.getScore() >= 104) {
                    System.out.println("\nPlayer " + player.getName() + " wins with a score of " + player.getScore() + "!");
                    gameOver = true;
                    break;
                }
            }
        }
    }
}