/********************************************************/
/* Mason Beale                                          */
/* Login ID: mason.beale@maine.edu                      */
/* COS 420, Spring 2025                                 */
/* BUlldog Part 1                                       */
/* RandomPlayer class: extends Player class             */
/*          A RandomPlayer uses 50/50 odds for play     */
/********************************************************/
import java.util.*;

public class RandomPlayer extends Player {

	/********************************************************/
	/* Constructor: RandomPlayer                            */
	/* Purpose: Create a default RandomPlayer               */
	/* Parameters:                                          */
	/*   none                                               */
	/********************************************************/
	public RandomPlayer () {
		this("Random");
	}

	/********************************************************/
	/* Constructor: RandomPlayer                            */
	/* Purpose: Create a new RandomPlayer object            */
	/* Parameters:                                          */
	/*   String name:  the name of the Player being created */
	/********************************************************/
	public RandomPlayer (String name) {
		super(name);
	}

	/********************************************************/
	/* Method:  play                                        */
	/* Purpose: Take one turn for this Player               */
	/*          One turn for a RandomPlayer is              */
    /*          determined by 50/50 odds every roll         */
	/* Parameters:                                          */
	/*   none                                               */
	/* Returns:                                             */
	/*   the score earned by the player on this turn,       */
	/*       which will be zero if a six was rolled         */
	/********************************************************/
	public int play() {
        Random r = new Random();
        int total = 0;
        boolean playAgain = true;
        while (playAgain) {
            int roll = (int) (Math.random()*6 + 1);
            System.out.println("   Player " + getName() + " rolled " + roll );
            if (roll != 6) {
                total = total + roll;
                playAgain = r.nextBoolean();
                System.out.println("   Total is " + total + ".   Rolling again: "+ playAgain);
            } else {
                total = 0;
                System.out.println("   and scored 0 for the turn.");
                return total;
            }  
        }

        System.out.println("   Player " + getName() + " scored " + total + " for their turn");
		return total;
	}

    public static void main(String[] args){
        RandomPlayer play = new RandomPlayer();
        play.play();
        Random r = new Random();
        for(int i = 0; i < 10; i++){
            System.out.println(r.nextBoolean());
        }
    }
}

