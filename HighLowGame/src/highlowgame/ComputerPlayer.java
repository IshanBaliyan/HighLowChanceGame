package assessment;

public class ComputerPlayer extends HLPlayer {

	//Declaring variable to track the number of computer players
	private static int computerPlayers = 1;
	
	//Default name of the computer player
	private final String DEFAULT_NAME = "Computer Player " + computerPlayers;
	
	//The amount of points the computer will risk
	private static final double RISK_PERCENTAGE = 0.30;
	
	/**
	 * Constructor used for creating a new default computer
	 */
	public ComputerPlayer() {
		super();
		
		//Giving computer player its computer values
		super.changeName(DEFAULT_NAME);
		super.changeComputer(true);
		
		//Increasing number of computer players after each instance
		computerPlayers ++;
	}

	/**
	 * Constructor used for creating a new player with specific values
	 * 
	 * @param initialPoints An Integer of the initial amount of points to give to the computer
	 */
	public ComputerPlayer(int initialPoints) {
		super(NO_NAME, NORMAL_SIDES, NORMAL_SIDES, initialPoints);
		
		//Giving computer player its computer values
		super.changeName(DEFAULT_NAME);
		super.changeComputer(true);
		
		//Increasing number of computer players after each instance
		computerPlayers ++;
	}
	
	/**
	 * This method renames a "computer player" to a "computer team" since there is dual usage
	 */
	public void changeToTeam()
	{
		super.changeName("Computer Team " + (computerPlayers - 1));
		
	}	
	
	/**
	 * This method makes a call on the computer behalf
	 */
	public void makeCall() {
		
		int call;
		int min = 1;
		int max = 2;
		
		int range = max - min + 1;
		
		//Generating random number within the range of low for 1 and high for 2
		call = (int)(Math.random() * range) + min;
		
		
		//Assigning call to global variable
		if(call == LOW) {
			setCurrentCall(Call.LOW);
		} else if(call == HIGH) {
			setCurrentCall(Call.HIGH);
		}
	}
	
	/**
	 * This method risks points from the computer
	 */
	public void riskPoints() {
		
		//If the computer has more than 100 points, make it risk its normal percentage
		if(getPoints() > 100) {
			setPointsToRisk((int) (getPoints() * RISK_PERCENTAGE));
		}
		
		//Otherwise, tell it to risk everything left
		else {
			setPointsToRisk(getPoints());
		}
	}

	
	/**
	 *	This method overrides method from superclass to state that the current instance is a computer
	 */
	public boolean isComputer() {
		return true;
	}
	
}
