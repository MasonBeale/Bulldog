/********************************************************/
/* Mason Beale                                          */
/* Login ID: mason.beale@maine.edu                      */
/* COS 420, Spring 2025                                 */
/* BUlldog Part 1                                       */
/* BoldPlayer class: extends Player class               */
/*          A BoldPlayer stops if they win              */
/*          (surpassing winning score)                  */
/********************************************************/

public class BoldPlayer extends Player {
    private int winningScore; // Winning score passed from BulldogGUI

	/********************************************************/
	/* Constructor: BoldPlayer                              */
	/* Purpose: Create a default BoldPlayer                 */
	/* Parameters:                                          */
	/*   none                                               */
	/********************************************************/
	public BoldPlayer () {
		this("Bold", 104); // Default winning score (can be overridden)
	}

	/********************************************************/
	/* Constructor: BoldPlayer                              */
	/* Purpose: Create a new BoldPlayer object              */
	/* Parameters:                                          */
	/*   String name:  the name of the Player being created */
	/********************************************************/
	public BoldPlayer (String name, int winningScore) {
		super(name);
        this.winningScore = winningScore; // Set the winning score
	}

	/********************************************************/
	/* Method:  play                                        */
	/* Purpose: Take one turn for this Player               */
	/*          One turn for a BoldPlayer is                */
    /*          ended when they win or roll a 6             */
	/* Parameters:                                          */
	/*   none                                               */
	/* Returns:                                             */
	/*   the score earned by the player on this turn,       */
	/*       which will be zero if a six was rolled         */
	/********************************************************/
	public int play() {
        boolean win = false;
        int total = 0;
        while (!win) {
            int roll = (int) (Math.random()*6 + 1);
            System.out.println("   Player " + getName() + " rolled " + roll );
            if (roll != 6) {
                total = total + roll;
                if (total < winningScore) { // Use the winning score from BulldogGUI
                    System.out.println("   Total is " + total + ". Rolling Again.");
                }else{
                    System.out.println("   Total is " + total + ".");
                    win = true;
                }
            } else {
                total = 0;
                System.out.println("   and scored 0 for the turn.");
                win = true;
                return total;
            }  
        }
        System.out.println("   Player " + getName() + " scored " + total + " for their turn");
		return total;
	}
}

