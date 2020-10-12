package highlowgame;

import java.util.ArrayList;

public class Team extends HLPlayer {

	
	//Default name for team
	private final String DEFAULT_NAME = "Team " + getPlayerIndex();

	//Variable for the number of the team member
	private int playerMemberNumber = 1;
	
	//Default Team Member name
	private final String DEFAULT_MEMBER_NAME = "Team Member " + playerMemberNumber;
	
	//List of team members
	public ArrayList<String> teamMembersList = new ArrayList<String>();
	
	//Variable for determining which team member's turn it is (only used for when there are members)
	private int selectedMember = 0;
	
	/**
	 * Constructor for creating default team
	 */
	public Team() {
		super();
		super.changeName(DEFAULT_NAME);
	}

	
	/**
	 * Constructor for creating team with specific details
	 * 
	 * @param name String of the name of the team
	 * @param sidesOne Integer of the number of sides on die one
	 * @param sidesTwo Integer of the number of sides on die one
	 * @param initialPoints Integer of the amount of initial points
	 */
	public Team(String name, int sidesOne, int sidesTwo, int initialPoints) {
		super(name, sidesOne, sidesTwo, initialPoints);
		
		//Renaming team to default team name if no name is given
		if(name == NO_NAME){
			super.changeName(DEFAULT_NAME);
		}
	}
	
	/**
	 * This method adds a members to a team
	 * 
	 * @param memberName String of the name of the member
	 */
	public void addMember(String memberName) {
		playerMemberNumber ++;
		teamMembersList.add(memberName);
	}
	
	
	/**
	 * This method adds a member to a team with a default name
	 */
	public void addMember() {
		playerMemberNumber ++;
		
		teamMembersList.add(DEFAULT_MEMBER_NAME);
	}
	
	/**
	 * Determines if the team even has members
	 * 
	 * @return	A Boolean stating whether the team has members
	 */
	public boolean hasMembers() {
		boolean hasMembers = true;
		
		if(teamMembersList.size() >= 1) {
			hasMembers = true;
		}
		else {
			hasMembers = false;
		}
		
		return hasMembers;
	}
	
	/**
	 * This member gets the name of the team member
	 * 
	 * @return A String of the name of the team members whose turn it is
	 */
	public String getCurrentTurnMemberName() {
		
		String name = teamMembersList.get(selectedMember);
		nextMemberTurn();
		return name;
	}
	
	/**
	 * This method moves on to the turn of the next member
	 */
	private void nextMemberTurn() {
		
		//Add one to move on to the next team member in the list
		selectedMember += 1;
		
		if(selectedMember >= teamMembersList.size()) {
			
			//If you've surpassed the last member, go back to the first member
			selectedMember = 0;
		}
		
	}
	
	/**
	 * This method overrides method from superclass to state that the current instance is a team
	 */
	public boolean isTeam() {
		
		return true;
	}
	
	
	

}
