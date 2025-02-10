import java.util.*;

public class Game {

    private ArrayList<Player> players = new ArrayList<Player>();
    
    public Game (){
        Scanner s = new Scanner(System.in);
        System.out.print("How many players? : ");
        int numPlayers = s.nextInt();
        s.nextLine();
        System.out.println("Select player types:");
        for (int i = 1; i <= numPlayers; i++){
            System.out.println("    Types of player:\n      A. Human\n      B. Random\n      C. Fifteen\n      D. Bold\n      E. Wimp");
            System.out.print("Player "+ i + " Type: ");
            String type = s.nextLine();
            switch (type) {
                case "A":
                this.players.add(new HumanPlayer());
                break;
                case "B":
                this.players.add(new RandomPlayer());
                break;
                case "C":
                this.players.add(new FifteenPlayer());
                break;
                case "D":
                this.players.add(new BoldPlayer());
                break;
                case "E":
                this.players.add(new WimpPlayer());
                break;
                default:
                System.out.println("    Incorrect type. Options are A, B, C, D, E.");
                i--;
                
            }
        }
    }

    public void getScores(){
        System.out.println("Scores:");
        for (int i = 1; i <= this.players.size(); i++){
            System.out.println("Player "+ i + " : " + players.get(i-1).getScore() + ".");
        }
    }

    public void playGame(){
        System.out.println("Starting game\n");
        int currentPlayer = 1;
        boolean playerWon = false;
        while (!playerWon) {
            Player p = this.players.get(currentPlayer-1);
            System.out.println("Player " + currentPlayer);
            p.setScore(p.getScore() + p.play());
            if (p.getScore() >= 104){
                System.out.println("Player "+ currentPlayer+ " has won with a score of " + p.getScore());
                getScores();
                playerWon = true;
                break;
            }else if (currentPlayer == this.players.size()){
                getScores();
                currentPlayer = 1;
            }else{
                currentPlayer++;
            }
        }
        
    }

    public static void main(String[] args) {
        Game test = new Game();
        test.playGame();
    }
}
