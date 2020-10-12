package assessment;
import java.lang.Math;

import javafx.scene.image.ImageView;


public class Die {

	//Variables for use in program
	private int roll;
	private int sides;
	private static final int NORMAL_SIDES = 6;
	
	
	/**
	 * Constructor for initializing a new die with default number of sides
	 */
	public Die () {
		
		sides = NORMAL_SIDES;
	}
	
	/**
	 * Constructor for initializing a new die with a given number of sides
	 * 
	 * @param sides An Integer of the amount of sides to put on die
	 */
	public Die(int sides) {
		
		this.sides = sides;
		
	}
	
	/**
	 * This method rolls the dice in the range from the 1 to the x number of sides
	 */
	public void rollDice(){
		
		
		//Generating random number between range of 1 and the number of sides
		int min = 1;
		int max = sides;
		
		int range = max - min + 1;
		
		//Generating random number within the range of the number of sides
		roll = (int)(Math.random() * range) + min;
		
		
	}
	
	/**
	 * This method determines the current roll face of a default die
	 * 
	 * @return An ImageView of the roll face of the current rolled number on a default (6-sided die)
	 */
	public ImageView getRollFace() {
		
		//Initializng ImageViews for each side
		ImageView imgDiceOne = new ImageView(getClass().getResource("/images/dice/diceOne.png").toString());
		ImageView imgDiceTwo = new ImageView(getClass().getResource("/images/dice/diceTwo.png").toString());
		ImageView imgDiceThree = new ImageView(getClass().getResource("/images/dice/diceThree.png").toString());
		ImageView imgDiceFour = new ImageView(getClass().getResource("/images/dice/diceFour.png").toString());
		ImageView imgDiceFive = new ImageView(getClass().getResource("/images/dice/diceFive.png").toString());
		ImageView imgDiceSix = new ImageView(getClass().getResource("/images/dice/diceSix.png").toString());
		
		//ImageView for blank side
		ImageView imgDiceBlank = new ImageView(getClass().getResource("/images/dice/blankDice.png").toString());
		
		//ImagView for the roll face that will be returned
		ImageView currentDice;
		
		//Switch statement for assigning the sides of die
		switch (roll) {
		case 1:
			currentDice = imgDiceOne;
			break;
		case 2:
			currentDice = imgDiceTwo;
			break;
		case 3:
			currentDice = imgDiceThree;
			break;
		case 4:
			currentDice = imgDiceFour;
			break;
		case 5:
			currentDice = imgDiceFive;
			break;
		case 6:
			currentDice = imgDiceSix;
			break;
			
		default:
			currentDice = imgDiceBlank;
			break;
		}
		
		//Returning ImageView with given number of sides
		return currentDice;
	}
	
	
	/**
	 * This method gets the current roll of the die
	 * 
	 * @return An Integer of the roll of the die
	 */
	public int getRoll() {
		return roll;
	}

	/**
	 * This method gets the sides of the die
	 * 
	 * @return An Integer of the number of sides on the die
	 */
	public int getSides() {
		return sides;
	}
	
}
