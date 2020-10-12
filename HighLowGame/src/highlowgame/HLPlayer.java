package highlowgame;

import java.util.ArrayList;


public class HLPlayer {

	//Declaring static final variables for constant use throughout program 
	public static final int NORMAL_SIDES = 6;
	public static final int INITIAL_POINTS = 1000;
	public static final String NO_NAME = "";

	public static final int HIGH = 2;
	public static final int LOW = 1;

	//Declaring variables for points to risk, current points, checking if user has won, and checking if the current player is a computer, respectively.
	private int pointsToRisk = 0;
	private int points = 0;
	private static boolean hasWon;
	private boolean isComputer = false;

	//Counting total number of players
	private static int players = 0;

	//The index will remain the same as the number of players existing in that instance
	private int playerIndex = players + 1;
	
	//Variables used for finding if the middle number was rolled on both dice (e.g. rolling 7 on 6-sided die)
	private boolean isMiddleNum = false;

	//Initializing a name for this instance of the player
	private final String DEFAULT_NAME = "Player " + playerIndex;

	//Initializing other variables for name, the dice of the user, and the current call, respectively.
	private String name;
	private ArrayList<Die> diceRolls = new ArrayList<Die>();
	private Call currentCall;

	
	/**
	 * This constructor is used for creating a player but giving no specific player values
	 */
	public HLPlayer() {
		
		//Default values if no values are given (calls other constructor for efficiency)
		this(NO_NAME,NORMAL_SIDES,NORMAL_SIDES, INITIAL_POINTS);
	}

	
	/**
	 * This constructor creates the player with the required player information
	 * 
	 * @param name	A String of the name of the player
	 * @param sidesOne	An Integer of the number of sides on die one
	 * @param sidesTwo 	An Integer of the number of sides on die two
	 * @param initialPoints An Integer of the amount of initial points
	 */
	public HLPlayer(String name, int sidesOne, int sidesTwo, int initialPoints) {

		//Players is static so it will keep increasing after each instance
		players += 1;

		/*If there is no name, give the player a default name 
		(not allowed to use global variable in "this" statement for constructor so made it an empty string and converted it here) */
		if(name.equals(NO_NAME)) {
			name = DEFAULT_NAME;
		}

		//Assigning global variables to their according values
		this.name = name;
		points = initialPoints;

		//Adding two initial dices for the user with the given number of sides
		Die dieOne = new Die(sidesOne);
		Die dieTwo = new Die(sidesTwo);
		getDiceRolls().add(dieOne);
		getDiceRolls().add(dieTwo);

	}

	/**
	 * This method sets the amount of points to risk during a bet
	 * 
	 * @param points An Integer of the amount of points to risk
	 */
	public void riskPoints(int points) {
		
		//Assigning value to global variable
		setPointsToRisk(points);
	}

	/**
	 * This method determines the amount of points after the roll
	 * 
	 * @return An Integer of the amount of points after the roll
	 */
	public int showPoints() {

		//Calculates the amount of points the user now has after the roll
		calculatePoints();

		return points;
	}

	/**
	 * This method calculates the amount of points the user has after the roll
	 */
	public void calculatePoints() {

		//If the call matches the predicted high or low roll, they won
		if(isLowRoll() && getCurrentCall() == Call.LOW || !isLowRoll() && getCurrentCall() == Call.HIGH && !isMiddleNum) {

			setHasWon(true);

			//Give them a reward of twice the points they risked
			addPoints(getPointsToRisk() * 2);
		}
		
		//Otherwise, they lost (this includes if their number was the middle number - e.g. 7 for two 6-sided dice)
		else{

			setHasWon(false);

			//They lost so make them lose their bet points that they risked
			removePoints(getPointsToRisk());
		}

		
		//Reset variable to false for next roll in the future (in case they rolled the middle number).
		if(isMiddleNum) {

			isMiddleNum = false;

		}
	}


	/**
	 * This method determines the total roll of the player
	 * 
	 * @return An Integer of the total roll of the player
	 */
	public int showRoll() {

		//Loop for each dice and add up their total amount of rolls for the final roll (e.g. 6 + 5 = 11 as total roll for two dice)
		int totalRoll = 0;
		for (int i = 0; i < getDiceRolls().size(); i++) {

			//Getting role of each dice and adding it to the total roll
			totalRoll += getDiceRolls().get(i).getRoll();

		}
		return totalRoll;
	}


	
	/**
	 * This method lets the user make a call
	 * 
	 * @param call An Integer for the call that the user made. 1 for LOW and 2 for HIGH 
	 */
	public void makeCall(int call) {

		//Make the call and assign to global variable
		if(call == LOW) {
			setCurrentCall(Call.LOW);
		} else if(call == HIGH) {
			setCurrentCall(Call.HIGH);
		}
	}

	/**
	 * This method lets the user make a call
	 * 
	 * @param userCall A Call type for the call that the user made. Call can be Call.HIGH or Call.LOW.
	 */
	public void makeCall(Call userCall) {
		
		//Assigning to global variable
		setCurrentCall(userCall);
	}


	/**
	 * This method rolls all the dice
	 */
	public void rollDice() {

		//Rolling each dice in the loop
		for (int i = 0; i < getDiceRolls().size(); i++) {
			getDiceRolls().get(i).rollDice();
		}
	}

	
	/**
	 * This method adds a dice to the list of dice the player has
	 * 
	 * @param sides An Integer of the amount of sides on the added dice
	 */
	public void addDice(int sides) {

		Die newDie = new Die(sides);
		getDiceRolls().add(newDie);
	}

	/**
	 * This method adds a dice with 6 sides to the list of dice the player has
	 */
	public void addDice() {
		
		//Adding dice with default number of sides
		addDice(NORMAL_SIDES);
	}

	/**
	 * This method determines if the user got a low roll
	 * 
	 * @return A Boolean that determines if the user got a low roll
	 */
	private boolean isLowRoll() {

		//Finds the highest roll possible on all the dice combined (e.g. 12 for two 6-sided dice)
		int maxRoll = determineMaxRoll();
		
		//Finds lowest roll possible (every dice has lowest roll of one)
		int lowestRoll = getDiceRolls().size();
		
		//Variable for finding the middle number in the list (if there is one)
		double middleNum = 0;
		
		//Boolean for determining if the roll was low
		boolean lowRoll = false;

		//If there is an odd number for the max roll on a combined number of dice (e.g. 13, 15, 17, etc...)
		if(maxRoll % 2 == 1) {
			//Find middle number since the number is odd there will be two middle numbers
			//For example: Imagine you have one six sided die and one seven sided die. The possible rolls are listed below
			//Possible rolls: 2,3,4,5,6,7,8.9,10,11,12,13
			//Since the number line starts at two for the minimum, there are actually an even number of selections so there's two middle numbers
			//Which are 7 and 8 in this case
			
			//Finding the roll
			int totalRoll = showRoll();
			
			//Here, we're calculating the middle number as a decimal (e.g. situation above would be 7.5)
			middleNum = (maxRoll + lowestRoll) / 2;
			
			//If the roll is greater than the middle numbers (7 or 8 in the case above), it is a high number 
			//(adding 0.5 gives the higher number (i.e. 8 in case above) since that fits both cases and 8 is greater than 7)
			if(totalRoll > middleNum + 0.5) {
				lowRoll = false;
			}
			//If it is equal to the middle numbers (7 or 8 in case above), it is the middle number
			else if(totalRoll == middleNum + 0.5 || totalRoll == middleNum - 0.5){
				isMiddleNum = true;
			}
			//Otherwise, it is a low roll
			else {
				lowRoll = true;
			}
			
		}
		//Since, it's even, it's much easier with one middle number
		else {
			//Finding the roll
			int totalRoll = showRoll();
			
			//Calculating the middle number
			middleNum = (maxRoll + lowestRoll) / 2;
			
			//If it's above the middle number, it is high.  
			if(totalRoll > middleNum) {
				lowRoll = false;
			}
			//If it is equal to the middle number, it is the middle.
			else if(totalRoll == middleNum){
				isMiddleNum = true;
			}
			//Otherwise, it is below the middle number and is a low roll
			else {
				lowRoll = true;
			}
		}
		return lowRoll;
	}



	/**
	 * This method determines the maximum roll possible for all the dice combined
	 * 
	 * @return An Integer of the maximum possible roll for all the player's dice
	 */
	private int determineMaxRoll() {

		int total = 0;

		//Loop through each dice and calculate the maximum of all of them together
		for (int i = 0; i < getDiceRolls().size(); i++) {

			total += getDiceRolls().get(i).getSides();

		}
		return total;
	}

	/**
	 * This method adds points to the player
	 * 
	 * @param newPoints An Integer of the amount of points to add
	 */
	private void addPoints(int newPoints) {
		points += newPoints;
	}

	
	/**
	 * This method removes points from the player
	 * 
	 * @param newPoints An Integer of the amount of points to remove
	 */
	private void removePoints(int newPoints) {
		points -= newPoints;
	}

	/**
	 * This method determines the index of the current player
	 * 
	 * @return An Integer of the index of the player
	 */
	public int getPlayerIndex() {

		return playerIndex;
	}

	/**
	 * This method returns the current player name
	 * 
	 * @return A String of the name of the player
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * This enum is used for listing a Call of HIGH or LOW roll
	 *
	 */
	public enum Call {
		LOW,
		HIGH,
	}

	/**
	 * This method changes the name
	 * 
	 * @param name A String of the new name to change to
	 */
	public void changeName(String name) {
		this.name = name;
	}

	/**
	 * This method determines if the current instance is a computer (since this class is extended)
	 * 
	 * @return A Boolean that states if the current instance is a computer
	 */
	public boolean isComputer() {
		return isComputer;
	}

	/**
	 * This method determines if the current instance is a team (since this class is extended)
	 * 
	 * @return A Boolean that states if the current instance is a team
	 */
	public boolean isTeam() {
		return false;
	}

	/**
	 * This method changes the value of the instance being stated as a computer or not
	 * 
	 * @param isComputer A Boolean for if the value that the instance should be stated as a computer
	 */
	public void changeComputer(boolean isComputer) {

		this.isComputer = isComputer;
	}
	
	

	/**
	 * This method determines if the player has a normal amount of dice (two 6-sided dice ONLY)
	 * 
	 * @return A Boolean stating whether the player has normal dice
	 */
	public boolean hasNormalDice() {

		boolean hasNormalDice = false;

		//Checks if there are two dice
		if(getDiceRolls().size() == 2) {
			
			//Checks if all the dice have two sides
			for (int i = 0; i < getDiceRolls().size(); i++) {

				if(getDiceRolls().get(i).getSides() == 6) {

					hasNormalDice = true;
				}
				else {
					hasNormalDice = false;
				}

			}
		}

		return hasNormalDice;
	}
	
	/**
	 * This method gets the points that a user has
	 * 
	 * @return An Integer of the amount of points the user has
	 */
	public int getPoints() {
		
		return points;
	}

	/**
	 * This method gets the list of the user's dice
	 * 
	 * @return An ArrayList with the list of dice
	 */
	public ArrayList<Die> getDiceRolls() {
		return diceRolls;
	}


	/**
	 * This method gets the current call
	 * 
	 * @return A Call type of the current call
	 */
	public Call getCurrentCall() {
		return currentCall;
	}

	/**
	 * This method sets the current call
	 * 
	 * @param currentCall A Call type of the current call
	 */
	public void setCurrentCall(Call currentCall) {
		this.currentCall = currentCall;
	}

	/**
	 * This method gets the current points to risk
	 * 
	 * @return An Integer of the amount of points to risk
	 */
	public int getPointsToRisk() {
		return pointsToRisk;
	}


	/**
	 * This method sets the amount of points to risk
	 * 
	 * @param pointsToRisk An Integer with the amount of points to risk
	 */
	public void setPointsToRisk(int pointsToRisk) {
		this.pointsToRisk = pointsToRisk;
	}

	
	/**
	 * This method gets the value of the roll being a win
	 * 
	 * @return A Boolean of the value of the roll being a win
	 */
	public static boolean isHasWon() {
		return hasWon;
	}


	/**
	 * This method sets the value of the roll being a win
	 * 
	 * @param hasWon A Boolean of the value of the roll being a win
	 */
	private void setHasWon(boolean hasWon) {
		HLPlayer.hasWon = hasWon;
	}
	
	
}
