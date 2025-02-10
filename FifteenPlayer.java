/********************************************************/
/* Mason Beale                                          */
/* Login ID: mason.beale@maine.edu                      */
/* COS 420, Spring 2025                                 */
/* BUlldog Part 1                                       */
/* FifteenPlayer class: extends Player class            */
/*          A FifteenPlayer stops when scoring above 15 */
/********************************************************/

public class FifteenPlayer extends Player {

	/********************************************************/
	/* Constructor: FifteenPlayer                           */
	/* Purpose: Create a default FifteenPlayer              */
	/* Parameters:                                          */
	/*   none                                               */
	/********************************************************/
	public FifteenPlayer () {
		this("Fifteen");
	}

	/********************************************************/
	/* Constructor: FifteenPlayer                           */
	/* Purpose: Create a new FifteenPlayer object           */
	/* Parameters:                                          */
	/*   String name:  the name of the Player being created */
	/********************************************************/
	public FifteenPlayer (String name) {
		super(name);
	}

	/********************************************************/
	/* Method:  play                                        */
	/* Purpose: Take one turn for this Player               */
	/*          One turn for a FifteenPlayer is             */
    /*          ended when score is 15 or above             */
	/* Parameters:                                          */
	/*   none                                               */
	/* Returns:                                             */
	/*   the score earned by the player on this turn,       */
	/*       which will be zero if a six was rolled         */
	/********************************************************/
	public int play() {
        int total = 0;
        while (total <= 15) {
            int roll = (int) (Math.random()*6 + 1);
            System.out.println("   Player " + getName() + " rolled " + roll );
            if (roll != 6) {
                total = total + roll;
                if (total <= 15) {
                    System.out.println("   Total is " + total + ". Rolling Again.");
                }else{
                    System.out.println("   Total is " + total + ".");
                }
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
        FifteenPlayer play = new FifteenPlayer();
        play.play();
    }
}

