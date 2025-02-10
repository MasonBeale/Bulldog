/********************************************************/
/* Mason Beale                                          */
/* Login ID: mason.beale@maine.edu                      */
/* COS 420, Spring 2025                                 */
/* BUlldog Part 1                                       */
/* HumanPlayer class: extends Player class              */
/*          A HumanPlayer takes input from the user     */
/********************************************************/
import java.util.*;

public class HumanPlayer extends Player {

	/********************************************************/
	/* Constructor: HumanPlayer                             */
	/* Purpose: Create a default HumanPlayer                */
	/* Parameters:                                          */
	/*   none                                               */
	/********************************************************/
	public HumanPlayer () {
		this("Human");
	}

	/********************************************************/
	/* Constructor: HumanPlayer                             */
	/* Purpose: Create a new HumanPlayer object             */
	/* Parameters:                                          */
	/*   String name:  the name of the Player being created */
	/********************************************************/
	public HumanPlayer (String name) {
		super(name);
	}

	/********************************************************/
	/* Method:  play                                        */
	/* Purpose: Take one turn for this Player               */
	/*          One turn for a HumanPlayer is               */
    /*          determined by user inputs                   */
	/* Parameters:                                          */
	/*   none                                               */
	/* Returns:                                             */
	/*   the score earned by the player on this turn,       */
	/*       which will be zero if a six was rolled         */
	/********************************************************/
	public int play() {
        Scanner s = new Scanner(System.in);
        int total = 0;
        String answer = "y";
        while (answer.equals("y")) {
            int roll = (int) (Math.random()*6 + 1);
            System.out.println("   Player " + getName() + " rolled " + roll );
            if (roll != 6) {
                total = total + roll;
                System.out.print("   Total is " + total + ".\n   Roll again? (y/n): ");
                answer = s.nextLine();
            } else {
                answer = "n";
                total = 0;
                System.out.println("   and scored 0 for the turn.");
                return total;
            }  
        }
        System.out.println("   Player " + getName() + " scored " + total + " for their turn");
		return total;
	}

    public static void main(String[] args){
        HumanPlayer play = new HumanPlayer();
        play.play();
    }
}

