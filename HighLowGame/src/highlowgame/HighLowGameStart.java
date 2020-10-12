package assessment;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import assessment.HLPlayer.Call;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 * @author Ishan Baliyan
 * 2020-02-24
 * ICS4U
 * Program that plays a High Low game of chance for winning and losing points
 */

public class HighLowChanceGame extends Application{

	
	//Declaring javafx framework objects for use later in the program
	private ImageView imgSpinWheel, imgWelcome, imgLow, imgHigh, imgFirstDie, imgSecondDie;
	private TextField txtCall;
	private Button btnRules, btnHigh, btnLow, btnSettings;
	private Label lblTitle, lblGameInfo, lblTeamGameInfo, lblWinner, lblResultsTrack, lblResults;
	private ListView<String> lstGameStats;
	private VBox root, welcomeRoot, rulesRoot, statisticsRoot, playerGameSettingsRoot, teamGameSettingsRoot, endRoot;
	private Scene scene, welcomeScene, rulesScene, statisticsScene, startSettingScene, startTeamstatisticsScene, endScene;
	private Stage myStage;
	private PieChart pieChart;

	//Global static constants for use throughout the program
	private static final int SMALL_GAP = 15;
	private static final int SMALL_MEDIUM_GAP = 30;
	private static final int MEDIUM_GAP = 45;
	private static final int MEDIUM_LARGE_GAP = 60;
	private static final int LARGE_GAP = 75;
	private static final int VERY_LARGE_GAP = 150;
	private static final int LARGE_FONT = 40;
	private static final int MEDIUM_FONT = 25;
	private static final int SMALL_FONT = 15;

	//Private variables for tracking game progress and results
	private int round = 1;
	private int currentIndex = 0;
	private String oldResults = "";

	//List of players/teams in the game
	private ArrayList<HLPlayer> playerList = new ArrayList<HLPlayer>();

	//Bounds for screen
	Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	
	/**
	 * Launches entire application
	 * 
	 * @param args Array of Strings that stores arguments passed
	 */
	public static void main(String[] args) {

		//Launching program
		launch(args);

	}

	/**
	 *	Starts application with initial setup for entire program
	 */
	@Override
	public void start(Stage myStage) {

		//Use @Override since that double checks

		//Assigning stage
		this.myStage = myStage;
 
		//Assigning initial root
		root = new VBox(SMALL_GAP);

		//Initiating Root padding
		root.setPadding(new Insets(SMALL_GAP, SMALL_GAP, SMALL_GAP, SMALL_GAP));

		//Assigning vbox objects for roots for different pages
		welcomeRoot = new VBox(SMALL_GAP);

		rulesRoot = new VBox(SMALL_GAP);

		statisticsRoot = new VBox(SMALL_GAP);

		playerGameSettingsRoot = new VBox(SMALL_GAP);

		teamGameSettingsRoot = new VBox(SMALL_GAP);

		endRoot = new VBox(SMALL_GAP);

		//Setting proper bounds for stage so that it fits better
		myStage.setX(primaryScreenBounds.getMinX());
		myStage.setY(primaryScreenBounds.getMinY());
		myStage.setWidth(primaryScreenBounds.getWidth());
		myStage.setHeight(primaryScreenBounds.getHeight());


		//Calling methods for setting up all the pages in the program
		setMainBackgrounds();
		rulesPage();
		setWelcomeScene();
		startSettingPage();
		startTeamSettingPage();
		setEndScene();
		gameStatisticsPage();

		//Assigning scene with vbox root
		scene = new Scene(root);

		//Initiating root padding
		root.setPadding(new Insets(SMALL_GAP, SMALL_GAP, SMALL_GAP, SMALL_GAP));

		//Setting stage and displaying program to the user (starting with welcome page)
		myStage.setTitle("High or Low Game");
		myStage.setScene(welcomeScene);
		myStage.show();


	}

	/**
	 * This method sets up the welcome scene for the user
	 */
	private void setWelcomeScene() {

		//Setting root alignment to the center
		welcomeRoot.setAlignment(Pos.CENTER);

		//Initializing label for introduction 
		Label lblResponse = new Label("Welcome to the High Low Game!");
		lblResponse.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, LARGE_FONT * 2));
		lblResponse.setTextFill(Color.WHITE);
		welcomeRoot.getChildren().add(lblResponse);

		//Initializing button for starting a player game
		Button btnStartPlayerGame = new Button("Start Player Game");
		btnStartPlayerGame.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, LARGE_FONT));
		btnStartPlayerGame.setStyle("-fx-background-color: #FFFFFF; "); //Change colour to white
		btnStartPlayerGame.setTextFill(Color.BLACK);
		btnStartPlayerGame.setOnAction(event -> myStage.setScene(startSettingScene));

		
		//Initializing button for starting a team game
		Button btnStartTeamGame = new Button("Start Team Game");
		btnStartTeamGame.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, LARGE_FONT));
		btnStartTeamGame.setStyle("-fx-background-color: #000000; "); //Change colour to white
		btnStartTeamGame.setTextFill(Color.WHITE);
		btnStartTeamGame.setOnAction(event -> myStage.setScene(startTeamstatisticsScene));

		//Placing both buttons in HBox for easier alignment
		HBox startButtonList = new HBox(MEDIUM_GAP, btnStartPlayerGame, btnStartTeamGame);

		//Aligning button list to center to provide more visual appearance
		startButtonList.setAlignment(Pos.CENTER);
		
		//Adding button list to display root
		welcomeRoot.getChildren().add(startButtonList);

		//Setting background of welcome page and assigning a new scene with the welcome root
		welcomeRoot.setBackground(changeBackground("/images/sky.jpeg"));
		welcomeScene = new Scene(welcomeRoot);

	}

	
	/**
	 * This method displays the end page when the game is over and a player/team has won
	 */
	private void setEndScene() {

		//Align the root to center
		endRoot.setAlignment(Pos.CENTER);
		
		//Assigning new label for declaring the winner of the game
		lblWinner = new Label();
		lblWinner.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, LARGE_FONT * 2));
		lblWinner.setTextFill(Color.WHITE);
		
		//Adding label to root
		endRoot.getChildren().add(lblWinner);

		//Changing background of end page and assigning a new scene with the end root
		endRoot.setBackground(changeBackground("/images/sky.jpeg"));
		endScene = new Scene(endRoot);

	}
	
	
	/**
	 * This method is allows the user to control the initial game settings for setting up a game with players
	 */
	private void startSettingPage(){

		//Changing background of page
		playerGameSettingsRoot.setBackground(changeBackground("/images/redCards.jpg"));

		//Initializing VBox for displaying items on left side of page
		VBox leftSide = new VBox(SMALL_GAP);

		//Initializing label for title
		Label lblGameTitle = new Label("Game Setup Settings" + "\n" );
		lblGameTitle.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, LARGE_FONT * 1.5));
		lblGameTitle.setTextFill(Color.WHITESMOKE);
		playerGameSettingsRoot.getChildren().add(lblGameTitle);

		//Initializing label for subtitle
		Label lblGameSubtitle = new Label("Note that leaving any value empty will leave it to the following defaults:" + "\n"
				+ "Name: Player X, Initial Points: 1000, Die 1 and Die 2 sides: 6");
		lblGameSubtitle.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, SMALL_FONT));
		lblGameSubtitle.setTextFill(Color.WHITESMOKE);
		playerGameSettingsRoot.getChildren().add(lblGameSubtitle);

		//Initializing label for adding human player
		Label lblInfoHumanPlayer = new Label("Adding Human Player:");
		lblInfoHumanPlayer.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, LARGE_FONT));
		lblInfoHumanPlayer.setTextFill(Color.WHITE);
		leftSide.getChildren().add(lblInfoHumanPlayer);

		//Initializing label and textfield for adding player name
		Label lblName = new Label("New Player Name:");
		lblName.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, LARGE_FONT));
		lblName.setTextFill(Color.WHITE);

		TextField txtName = new TextField();

		HBox layoutNameList = new HBox(SMALL_GAP,lblName, txtName);
		layoutNameList.setAlignment(Pos.CENTER);
		leftSide.getChildren().add(layoutNameList);

		//next line
		
		//Initializing label and textfield for player initial points
		Label lblInitialPoints = new Label("Initial Amount of points for player:");
		lblInitialPoints.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, LARGE_FONT));
		lblInitialPoints.setTextFill(Color.WHITE);

		TextField txtInitialPoints = new TextField();

		HBox layoutInitialPointsList = new HBox(SMALL_GAP,lblInitialPoints, txtInitialPoints);
		layoutInitialPointsList.setAlignment(Pos.CENTER);
		leftSide.getChildren().add(layoutInitialPointsList);

		//next line

		//Initializing label and textfield for number of sides on dice one
		Label lblDieOneSides = new Label("Number of sides on Die 1:");
		lblDieOneSides.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, LARGE_FONT));
		lblDieOneSides.setTextFill(Color.WHITE);

		TextField txtDieOneSides = new TextField();

		HBox layoutDieOneSidesList = new HBox(SMALL_GAP,lblDieOneSides, txtDieOneSides);
		layoutDieOneSidesList.setAlignment(Pos.CENTER);
		leftSide.getChildren().add(layoutDieOneSidesList);

		//next line

		//Initializing label and textfield for number of sides on dice two
		Label lblDieTwoSides = new Label("Number of sides on Die 2:");
		lblDieTwoSides.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, LARGE_FONT));
		lblDieTwoSides.setTextFill(Color.WHITE);

		TextField txtDieTwoSides = new TextField();

		HBox layoutDieTwoSidesList = new HBox(SMALL_GAP,lblDieTwoSides, txtDieTwoSides);
		layoutDieTwoSidesList.setAlignment(Pos.CENTER);
		leftSide.getChildren().add(layoutDieTwoSidesList);

		//next line

		//Initializing button to add the player to the game
		
		Button btnAddPlayer = new Button("Add Player");
		btnAddPlayer.setFont(Font.font(MEDIUM_FONT));
		btnAddPlayer.setStyle("-fx-background-color: #000000; "); 
		btnAddPlayer.setTextFill(Color.WHITE);

		leftSide.getChildren().add(btnAddPlayer);

		
		//Initializing label for error message if input is incorrectly formatted
		Label lblMessage = new Label();
		lblMessage.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, MEDIUM_FONT));
		lblMessage.setTextFill(Color.WHITE);

		//Calling method when Button for Adding a player is pressed
		btnAddPlayer.setOnAction(event -> addPlayer(txtName, txtInitialPoints, txtDieOneSides, txtDieTwoSides, lblMessage));

		//next line - adding dice

		//Initializing label for adding dice to player
		Label lblInfoAddDice = new Label("Continue here for adding dice to the added player:");
		lblInfoAddDice.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, MEDIUM_FONT));
		lblInfoAddDice.setTextFill(Color.WHITE);

		leftSide.getChildren().add(lblInfoAddDice);

		//next line
		
		//Initializing label and textfield for adding dice to the player
		Label lblAddDice = new Label("Number of sides on dice:");
		lblAddDice.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, MEDIUM_FONT));
		lblAddDice.setTextFill(Color.WHITE);

		TextField txtDiceSides = new TextField();

		HBox layoutDiceSides = new HBox(SMALL_GAP,lblAddDice, txtDiceSides);
		layoutDiceSides.setAlignment(Pos.CENTER);
		leftSide.getChildren().add(layoutDiceSides);

		// next line
		
		//Initializing button for adding dice
		Button btnAddDice = new Button("Add Dice");
		btnAddDice.setFont(Font.font(SMALL_FONT));
		btnAddDice.setStyle("-fx-background-color: #000000; ");
		btnAddDice.setTextFill(Color.WHITE);

		leftSide.getChildren().add(btnAddDice);

		//Calling method if button to add dice is pressed
		btnAddDice.setOnAction(event -> addDice(txtDiceSides, lblMessage));

		//Aligning the objects in the left side VBox to the center of their environment (i.e. the center of the left side)
		leftSide.setAlignment(Pos.TOP_CENTER);

		//Initializing VBoc for items on the right side
		VBox rightSide = new VBox(SMALL_GAP);
		//next line

		//Initializing subtitle label for adding a computer player
		Label lblInfoComputerPlayer = new Label("Adding Computer Player:");
		lblInfoComputerPlayer.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, LARGE_FONT));
		lblInfoComputerPlayer.setTextFill(Color.WHITE);
		rightSide.getChildren().add(lblInfoComputerPlayer);

		//next line
		
		//Initializing label and textfield for determining initial amount of points
		Label lblInitialComputerPoints = new Label("Initial Amount of points:");
		lblInitialComputerPoints.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, LARGE_FONT));
		lblInitialComputerPoints.setTextFill(Color.WHITE);

		TextField txtInitialComputerPoints = new TextField();

		HBox layoutInitialComputerPointsList = new HBox(SMALL_GAP,lblInitialComputerPoints, txtInitialComputerPoints);
		layoutInitialComputerPointsList.setAlignment(Pos.CENTER_RIGHT);
		rightSide.getChildren().add(layoutInitialComputerPointsList);

		//next line

		//Initializing button for adding computer player
		Button btnAddComputerPlayer = new Button("Add Computer Player");
		btnAddComputerPlayer.setFont(Font.font(MEDIUM_FONT));
		btnAddComputerPlayer.setStyle("-fx-background-color: #000000; ");
		btnAddComputerPlayer.setTextFill(Color.WHITE);
		rightSide.getChildren().add(btnAddComputerPlayer);
		rightSide.getChildren().add(lblMessage);

		//Calling method if the add computer button is pressed
		btnAddComputerPlayer.setOnAction(event -> addComputerPlayer(txtInitialComputerPoints, false, lblMessage));

		//Aligning all object in the right VBox to the center of their environment (i.e. the center of the right side)
		rightSide.setAlignment(Pos.TOP_CENTER);

		//Combining the left and right Vbox items togather and adding to main page root 
		HBox layoutPage = new HBox(MEDIUM_LARGE_GAP, leftSide, rightSide);
		layoutPage.setAlignment(Pos.CENTER);
		playerGameSettingsRoot.getChildren().add(layoutPage);

		//Displaying the current number of player added to the game
		lblGameInfo = new Label("\n" + "The current number of players is: " + playerList.size() + "\n");
		lblGameInfo.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, MEDIUM_FONT));
		lblGameInfo.setTextFill(Color.WHITE);
		playerGameSettingsRoot.getChildren().add(lblGameInfo);


		//Initializing button for starting the game
		Button btnStart = new Button("Start Game");
		btnStart.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, LARGE_FONT));
		btnStart.setStyle("-fx-background-color: #FFFFFF; ");
		btnStart.setTextFill(Color.BLACK);
		
		btnStart.setOnAction(event -> startMainGame(lblMessage, false));
		
		playerGameSettingsRoot.getChildren().add(btnStart);
		
		//Aligning the items on the page to the center
		playerGameSettingsRoot.setAlignment(Pos.TOP_CENTER);

		//Initializing a new scene and adding page items from main root to the scene
		startSettingScene = new Scene(playerGameSettingsRoot);

	}

	/**
	 * This method determines to start the game if there is at least one player/team created
	 * 
	 * @param lblMessage The Label to provide error message if there is not at least one team/player
	 * @param isTeam A Boolean to specify if the game is for teams or for players
	 */
	private void startMainGame(Label lblMessage, boolean isTeam) {
		
		//If there's at least one player in the game, start the game
		if(playerList.size() > 0) {
		
			myStage.setScene(scene); mainGameLogic();
			
		}
		//Otherwise, provide error message (message changes slightly for teams game or player game)
		else {
			
			if(isTeam) {
				lblMessage.setText("Must have at least one Team to start the game");
			}else {
				
				lblMessage.setText("Must have at least one Player to start the game");
			}
			
		}
	}

	/**
	 * This method is allows the user to control the initial game settings for setting up a game with teams
	 */
	private void startTeamSettingPage(){

		
		//Changing background of page
		teamGameSettingsRoot.setBackground(changeBackground("/images/greyBack.jpg"));

		//Initializing VBox for displaying items on left side of page
		VBox leftSide = new VBox(SMALL_GAP);

		//Initializing label for title
		Label lblGameTitle = new Label("Team Game Setup Settings" + "\n" );
		lblGameTitle.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, LARGE_FONT * 1.5));
		lblGameTitle.setTextFill(Color.WHITESMOKE);
		teamGameSettingsRoot.getChildren().add(lblGameTitle);

		//Initializing label for subtitle
		Label lblGameSubtitle = new Label("Note that leaving any value empty will leave it to the following defaults:" + "\n"
				+ "Name: Team X, Initial Points: 1000, Die 1 and Die 2 sides: 6, Team Member Names: None (will play as Team)");
		lblGameSubtitle.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, SMALL_FONT));
		lblGameSubtitle.setTextFill(Color.WHITESMOKE);
		teamGameSettingsRoot.getChildren().add(lblGameSubtitle);

		//Initializing label for adding human team
		Label lblInfoHumanPlayer = new Label("Adding Human Team:");
		lblInfoHumanPlayer.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, MEDIUM_FONT));
		lblInfoHumanPlayer.setTextFill(Color.WHITE);
		leftSide.getChildren().add(lblInfoHumanPlayer);

		//Initializing label and textfield for adding team name
		Label lblName = new Label("Team Name:");
		lblName.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, MEDIUM_FONT));
		lblName.setTextFill(Color.WHITE);

		TextField txtName = new TextField();
		HBox layoutNameList = new HBox(SMALL_GAP,lblName, txtName);
		layoutNameList.setAlignment(Pos.CENTER);
		leftSide.getChildren().add(layoutNameList);

		//next line
		
		//Initializing label and textfield for team initial points
		Label lblInitialPoints = new Label("Initial Amount of points for Team:");
		lblInitialPoints.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, MEDIUM_FONT));
		lblInitialPoints.setTextFill(Color.WHITE);

		TextField txtInitialPoints = new TextField();

		HBox layoutInitialPointsList = new HBox(SMALL_GAP,lblInitialPoints, txtInitialPoints);
		layoutInitialPointsList.setAlignment(Pos.CENTER);
		leftSide.getChildren().add(layoutInitialPointsList);

		//next line

		//Initializing label and textfield for number of sides on dice one
		
		Label lblDieOneSides = new Label("Number of sides on Die 1:");
		lblDieOneSides.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, MEDIUM_FONT));
		lblDieOneSides.setTextFill(Color.WHITE);

		TextField txtDieOneSides = new TextField();

		HBox layoutDieOneSidesList = new HBox(SMALL_GAP,lblDieOneSides, txtDieOneSides);
		layoutDieOneSidesList.setAlignment(Pos.CENTER);
		leftSide.getChildren().add(layoutDieOneSidesList);

		//next line
		
		//Initializing label and textfield for number of sides on dice two

		Label lblDieTwoSides = new Label("Number of sides on Die 2:");
		lblDieTwoSides.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, MEDIUM_FONT));
		lblDieTwoSides.setTextFill(Color.WHITE);

		TextField txtDieTwoSides = new TextField();

		HBox layoutDieTwoSidesList = new HBox(SMALL_GAP,lblDieTwoSides, txtDieTwoSides);
		layoutDieTwoSidesList.setAlignment(Pos.CENTER);
		leftSide.getChildren().add(layoutDieTwoSidesList);

		//next line

		//Initializing button to add the player to the game 
		
		Button btnAddTeam = new Button("Add Team");
		btnAddTeam.setFont(Font.font(MEDIUM_FONT));
		btnAddTeam.setStyle("-fx-background-color: #000000; "); 
		btnAddTeam.setTextFill(Color.WHITE);

		leftSide.getChildren().add(btnAddTeam);

		//Initializing label for subtitle to inform where to add team member names
		Label lblTeamInfo = new Label("Continue here for adding Team member names to the added team:");
		lblTeamInfo.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, MEDIUM_FONT));
		lblTeamInfo.setTextFill(Color.WHITE);

		//Initializing label for error message if input is incorrectly formatted 
		Label lblMessage = new Label();
		lblMessage.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, MEDIUM_FONT * 0.90));
		lblMessage.setTextFill(Color.WHITE);

		//Calling method when button for adding a team is pressed
		btnAddTeam.setOnAction(event -> addTeam(txtName, txtInitialPoints, txtDieOneSides, txtDieTwoSides, lblTeamInfo, lblMessage));

		//next line - adding dice
		
		//Initializing label for subtitle to inform where to add dice
		Label lblInfoAddDice = new Label("Continue here for adding dice to the added team:");
		lblInfoAddDice.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, MEDIUM_FONT));
		lblInfoAddDice.setTextFill(Color.WHITE);
		leftSide.getChildren().add(lblInfoAddDice);

		//next line
		
		//Initializing label and textfield for adding dice to the team
		Label lblAddDice = new Label("Number of sides on dice:");
		lblAddDice.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, MEDIUM_FONT));
		lblAddDice.setTextFill(Color.WHITE);

		TextField txtDiceSides = new TextField();

		HBox layoutDiceSides = new HBox(SMALL_GAP,lblAddDice, txtDiceSides);
		layoutDiceSides.setAlignment(Pos.CENTER);
		leftSide.getChildren().add(layoutDiceSides);

		// next line
		
		//Initializing button for adding dice 
		Button btnAddDice = new Button("Add Dice");
		btnAddDice.setFont(Font.font(SMALL_FONT));
		btnAddDice.setStyle("-fx-background-color: #000000; "); 
		btnAddDice.setTextFill(Color.WHITE);
		leftSide.getChildren().add(btnAddDice);

		//Call method when adding dice button is pressed
		btnAddDice.setOnAction(event -> addDice(txtDiceSides, lblMessage));


		//next line - adding TEAM MEMBER

		//Adding team info to root
		leftSide.getChildren().add(lblTeamInfo);
		
		//Initializing label and textfield for adding name of team member in the team
		Label lblAddTeamMember = new Label("Name of Team Member:");
		lblAddTeamMember.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, MEDIUM_FONT));
		lblAddTeamMember.setTextFill(Color.WHITE);

		TextField txtTeamMember = new TextField();

		HBox layoutAddMember = new HBox(SMALL_GAP,lblAddTeamMember, txtTeamMember);
		layoutAddMember.setAlignment(Pos.CENTER);
		leftSide.getChildren().add(layoutAddMember);

		// next line
		
		//Initializing button for adding team member names to the team
		Button btnAddMember = new Button("Add Team Member to Team");
		btnAddMember.setFont(Font.font(SMALL_FONT));
		btnAddMember.setStyle("-fx-background-color: #000000; "); 
		btnAddMember.setTextFill(Color.WHITE);
		leftSide.getChildren().add(btnAddMember);

		//Calling method when button to add team member is pressed
		btnAddMember.setOnAction(event -> addTeamMember(txtTeamMember));

		//Aligning the objects in the left side VBox to the center of their environment (i.e. the center of the left side) 
		leftSide.setAlignment(Pos.TOP_CENTER);

		//Initializing VBox for items on the right side
		VBox rightSide = new VBox(SMALL_GAP);
		
		//next line
		//Initializing label subtitle for adding a computer team
		Label lblInfoComputerPlayer = new Label("Adding Computer Team:");
		lblInfoComputerPlayer.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, MEDIUM_FONT));
		lblInfoComputerPlayer.setTextFill(Color.WHITE);
		rightSide.getChildren().add(lblInfoComputerPlayer);

		//next line
		
		//Initializing label and textfield for initial amount of points for pl
		Label lblInitialComputerPoints = new Label("Initial Amount of points:");
		lblInitialComputerPoints.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, MEDIUM_FONT));
		lblInitialComputerPoints.setTextFill(Color.WHITE);

		TextField txtInitialComputerPoints = new TextField();

		HBox layoutInitialComputerPointsList = new HBox(SMALL_GAP,lblInitialComputerPoints, txtInitialComputerPoints);
		layoutInitialComputerPointsList.setAlignment(Pos.CENTER_RIGHT);
		rightSide.getChildren().add(layoutInitialComputerPointsList);

		//next line

		//Initializing button for adding a computer team
		Button btnAddComputerTeam = new Button("Add Computer Team");
		btnAddComputerTeam.setFont(Font.font(MEDIUM_FONT));
		btnAddComputerTeam.setStyle("-fx-background-color: #000000; ");
		btnAddComputerTeam.setTextFill(Color.WHITE);
		rightSide.getChildren().add(btnAddComputerTeam);
		
		//Displaying any user format error messages that may have occured
		rightSide.getChildren().add(lblMessage);

		//Calling method when method for adding computer player is pressed
		btnAddComputerTeam.setOnAction(event -> addComputerPlayer(txtInitialComputerPoints, true, lblMessage));

		//Aligning the objects in the right side VBox to the center of their environment (i.e. the center of the right side) 
		rightSide.setAlignment(Pos.TOP_CENTER);

		//Combining the left and right side elements for one display
		HBox layoutPage = new HBox(MEDIUM_LARGE_GAP, leftSide, rightSide);
		layoutPage.setAlignment(Pos.CENTER);
		teamGameSettingsRoot.getChildren().add(layoutPage);

		//Displaying the current number of team added to the game 
		lblTeamGameInfo = new Label("\n" + "The current number of teams is: " + playerList.size() + "\n");
		lblTeamGameInfo.setFont(Font.font("Times", FontWeight.NORMAL, FontPosture.ITALIC, MEDIUM_FONT));
		lblTeamGameInfo.setTextFill(Color.WHITE);
		teamGameSettingsRoot.getChildren().add(lblTeamGameInfo);
		
		//Initializing button for starting the game 
		Button btnStart = new Button("Start Game");
		btnStart.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, MEDIUM_FONT));
		btnStart.setStyle("-fx-background-color: #FFFFFF; ");
		btnStart.setOnAction(event -> startMainGame(lblMessage, true));
		btnStart.setTextFill(Color.BLACK);
		teamGameSettingsRoot.getChildren().add(btnStart);

		//Aligning the items on the page to the center 
		teamGameSettingsRoot.setAlignment(Pos.TOP_CENTER);

		//Initializing a new scene and adding page items from main root to the scene
		startTeamstatisticsScene = new Scene(teamGameSettingsRoot);
	}

	/**
	 * This method creates a team and adds it the list of teams in the game
	 * 
	 * @param txtName	TextField with information about the Team Name
	 * @param txtInitialPoints 	TextField with information about the initial amount of points the team has
	 * @param txtDieOneSides	TextField with information about the number of sides on dice one
	 * @param txtDieTwoSides	TextField with information about the number of sides on dice two
	 * @param lblTeamInfo	Label that gives information about the number of teams in the game
	 * @param lblMessage	Label that is used to provide error message if there is a format error
	 */
	private void addTeam(TextField txtName, TextField txtInitialPoints, TextField txtDieOneSides,
			TextField txtDieTwoSides, Label lblTeamInfo, Label lblMessage) {

		//Initializing variables for checking which TextFields are empty
		boolean hasName = !txtName.getText().isEmpty();
		boolean hasPoints = !txtInitialPoints.getText().isEmpty();
		boolean hasDiceOne = !txtDieOneSides.getText().isEmpty();
		boolean hasDiceTwo = !txtDieTwoSides.getText().isEmpty();

		//Assigning default team values if none are selected
		String name = Team.NO_NAME;
		int dieOneSides = Team.NORMAL_SIDES;
		int dieTwoSides = Team.NORMAL_SIDES;
		int initialPoints = Team.INITIAL_POINTS;

		//Initializing variable for format error in the TextFields
		boolean errorFormat = false;

		//If team has a name, change the name from the default name
		if(hasName) {
			name = txtName.getText();
		}

		//If the team has a different number of initial points (that is greater than 0), change the amount of initial points
		if (hasPoints && isNumber(txtInitialPoints.getText()) && Integer.parseInt(txtInitialPoints.getText()) > 0) {

			initialPoints = Integer.parseInt(txtInitialPoints.getText());
		} 
		//If the user has entered a value but it does not follow the criteria, there is an error in the format
		else if(hasPoints) {

			errorFormat = true;

		}

		//If the team has a different number of sides on die one (that is greater than 4), change the amount of sides on the die
		if (hasDiceOne && isNumber(txtDieOneSides.getText()) && Integer.parseInt(txtDieOneSides.getText()) > 4) {

			dieOneSides = Integer.parseInt(txtDieOneSides.getText());
		} 
		//If the user has entered a value but it does not follow the criteria, there is an error in the format
		else if(hasDiceOne) {

			errorFormat = true;

		}

		//If the team has a different number of sides on die two (that is greater than 4), change the amount of sides on the die
		if (hasDiceTwo && isNumber(txtDieTwoSides.getText()) && Integer.parseInt(txtDieTwoSides.getText()) > 4) {

			dieTwoSides = Integer.parseInt(txtDieTwoSides.getText());
		} 
		//If the user has entered a value but it does not follow the criteria, there is an error in the format
		else if(hasDiceTwo) {

			errorFormat = true;

		}

		//If everything is empty, create a full default team
		if(!hasName && !hasPoints && !hasDiceOne && !hasDiceTwo){
			Team team = new Team();
			playerList.add(team);
			
			//Clearing error message 
			lblMessage.setText("");
		}
		//If there is a format error, advise the user
		else if(errorFormat) {

			lblMessage.setText("Incorrect format for one or multiple textboxes." + "\n" + "Please ensure that input is a number when required and try again." 
			+ "\n" + "Note that initial points must be above 0, and the dice must have at least 5 sides");

		}
		//If there is no error in format and not all the TextFields are empty, create a new team with the user's details
		else {

			Team team = new Team(name, dieOneSides, dieTwoSides, initialPoints);
			playerList.add(team);
			
			//Clearing error message 
			lblMessage.setText("");
		}

		//If there is at least one team in the game, give message to user for adding team member names to that team
		if(playerList.size() >= 1) {

			lblTeamInfo.setText("Continue here for adding Team member names to " + playerList.get(playerList.size() - 1).getName() + " :");

		}
		
		//Display the current number of teams in the game
		lblTeamGameInfo.setText("\n" + "The current number of Teams is: " + playerList.size() + "\n");
		
		//Clear all the TextFields
		txtName.clear();
		txtInitialPoints.clear();
		txtDieOneSides.clear();
		txtDieTwoSides.clear();
	}

	/**
	 * Method for adding a computer player or computer team to the game
	 * 
	 * @param txtInitialComputerPoints	The TextField for the initial points for the computer player
	 * @param isTeam	The Boolean for confirming to add a team or a player (true for team)
	 * @param lblMessage	Label for displaying any format error messages to the user
	 */
	private void addComputerPlayer(TextField txtInitialComputerPoints, boolean isTeam, Label lblMessage) {

		//If there are no human players or teams in the game yet, advise the user to add them before adding a computer (or else it would be comp vs comp)
		if(playerList.size() == 0) {
			
			lblMessage.setText("Must have at least one human user");
		}
		
		//If the user entered a number that was greater than 0 for initial points, create a computer with those requirements
		else if(!txtInitialComputerPoints.getText().isEmpty() && isNumber(txtInitialComputerPoints.getText()) && Integer.parseInt(txtInitialComputerPoints.getText()) > 0){
			ComputerPlayer player = new ComputerPlayer(Integer.parseInt(txtInitialComputerPoints.getText()));
			playerList.add(player);
			
			//Clearing error message 
			lblMessage.setText("");
		}
		//If the user has entered a value but it does not follow the criteria, there is an error in the format so advise the user
		else if (!txtInitialComputerPoints.getText().isEmpty()) {

			lblMessage.setText("Incorrect format for intial computer player points." + "\n" + "Please ensure that input is a proper number (greater than 0) and try again.");

		}
		//If the user left the TextField for the initial points blank, create a default computer with a default amount of points
		else {

			ComputerPlayer player = new ComputerPlayer();
			playerList.add(player);
			//Clearing error message 
			lblMessage.setText("");
		}

		//If the computer is a team, rename the computer to a team and display team message
		if(isTeam && playerList.size() >= 1) {

			((ComputerPlayer) playerList.get(playerList.size() - 1)).changeToTeam();
			lblTeamGameInfo.setText("\n" + "The current number of Teams is: " + playerList.size() + "\n");
		} 
		//Otherwise, the computer is obviously a player so provide normal computer player feedback
		else if (!isTeam && playerList.size() >= 1){
			lblGameInfo.setText("\n" + "The current number of players is: " + playerList.size() + "\n");
		}
		
		//Clear the textbox
		txtInitialComputerPoints.clear();
	}

	/**
	 * Method to add dice to the user
	 * 
	 * @param txtDiceSides TextField for the amount of sides on the dice
	 * @param lblMessage	Label for displaying format error messages to the user if needed
	 */
	private void addDice(TextField txtDiceSides, Label lblMessage) {
		
		//Check if there are any players created yet
		if(playerList.size() >= 1) {

			//Check if the text field is filled and has a number (greater than 4)
			if(!txtDiceSides.getText().isEmpty() && isNumber(txtDiceSides.getText()) && Integer.parseInt(txtDiceSides.getText()) > 4) {
				playerList.get(playerList.size() - 1).addDice(Integer.parseInt(txtDiceSides.getText()));
				
				//Clearing error message 
				lblMessage.setText("");
				
			}
			//If the user has entered a value but it does not follow the criteria, there is an error in the format so advise user
			else if(!txtDiceSides.getText().isEmpty()) {

				lblMessage.setText("Incorrect format for number of dice sides." + "\n" + "Please ensure that input is a proper number and try again." 
				+ "\n" + "Note that the die must have at least 5 sides");

			}
			//Text field is empty so use default number of sides
			else {
				playerList.get(playerList.size() - 1).addDice();
				
				//Clearing error message 
				lblMessage.setText("");
			}
		}

		//Clear TextField
		txtDiceSides.clear();
	}

	/**
	 * Method to add team member names to teams
	 * 
	 * @param txtTeamMember TextField with name for new team member
	 */
	private void addTeamMember(TextField txtTeamMember) {
		
		//Check if there are any teams created yet
		if(playerList.size() >= 1) {

			//Check if the text field is filled
			if(!txtTeamMember.getText().isEmpty()) {
				((Team) playerList.get(playerList.size() - 1)).addMember(txtTeamMember.getText());
			}
			//Text field is empty so use default number of sides
			else {
				((Team) playerList.get(playerList.size() - 1)).addMember();
			}
		}

		//Empty TextField
		txtTeamMember.clear();
	}

	
	/**
	 * This method creates a player and adds it the list of players in the game
	 * 
	 * @param txtName	TextField with information about the Player Name
	 * @param txtInitialPoints 	TextField with information about the initial amount of points the player has
	 * @param txtDieOneSides	TextField with information about the number of sides on dice one
	 * @param txtDieTwoSides	TextField with information about the number of sides on dice two
	 * @param lblTeamInfo	Label that gives information about the number of players in the game
	 * @param lblMessage	Label that is used to provide error message if there is a format error
	 */
	private void addPlayer(TextField txtName, TextField txtInitialPoints, TextField txtDieOneSides,
			TextField txtDieTwoSides, Label lblMessage) {
		
		//Initializing variables for checking which TextFields are empty 
		boolean hasName = !txtName.getText().isEmpty();
		boolean hasPoints = !txtInitialPoints.getText().isEmpty();
		boolean hasDiceOne = !txtDieOneSides.getText().isEmpty();
		boolean hasDiceTwo = !txtDieTwoSides.getText().isEmpty();

		//Assigning default player values if none are selected 
		String name = HLPlayer.NO_NAME;
		int dieOneSides = HLPlayer.NORMAL_SIDES;
		int dieTwoSides = HLPlayer.NORMAL_SIDES;
		int initialPoints = HLPlayer.INITIAL_POINTS;

		//Initializing variable for format error in the TextFields
		boolean errorFormat = false;

		//If player has a name, change the name from the default name
		if(hasName) {
			name = txtName.getText();

		}

		//If the player has a different number of initial points (that is greater than 0), change the amount of initial points 
		if (hasPoints && isNumber(txtInitialPoints.getText()) && Integer.parseInt(txtInitialPoints.getText()) > 0) {

			initialPoints = Integer.parseInt(txtInitialPoints.getText());
		}
		//If the user has entered a value but it does not follow the criteria, there is an error in the format
		else if(hasPoints) {

			errorFormat = true;
		}

		//If the player has a different number of sides on die one (that is greater than 4), change the amount of sides on the die
		if (hasDiceOne && isNumber(txtDieOneSides.getText()) && Integer.parseInt(txtDieOneSides.getText()) > 4) {

			dieOneSides = Integer.parseInt(txtDieOneSides.getText());
		}
		
		//If the user has entered a value but it does not follow the criteria, there is an error in the format
		else if(hasDiceOne) {

			errorFormat = true;
		}

		//If the player has a different number of sides on die two (that is greater than 4), change the amount of sides on the die
		if (hasDiceTwo && isNumber(txtDieTwoSides.getText()) && Integer.parseInt(txtDieTwoSides.getText()) > 4) {

			dieTwoSides = Integer.parseInt(txtDieTwoSides.getText());
		} 
		
		//If the user has entered a value but it does not follow the criteria, there is an error in the format
		else if(hasDiceTwo) {

			errorFormat = true;
		}

		//If everything is empty, create a full default player 
		if(!hasName && !hasPoints && !hasDiceOne && !hasDiceTwo){
			HLPlayer player = new HLPlayer();
			playerList.add(player);
			
			//Clearing error message 
			lblMessage.setText("");
		}
		
		//If there is a format error, advise the user 
		else if (errorFormat) {

			lblMessage.setText("Incorrect format for one or multiple textboxes." + "\n" + "Please ensure that input is a number when required and try again." 
					+ "\n" + "Note that initial points must be above 0, and the dice must have at least 5 sides");
		}
		//If there is no error in format and not all the TextFields are empty, create a new player with the user's details 
		else {

			HLPlayer player = new HLPlayer(name, dieOneSides, dieTwoSides, initialPoints);
			playerList.add(player);
			
			//Clearing error message 
			lblMessage.setText("");
		}


		//Display the current number of teams in the game

		lblGameInfo.setText("\n" + "The current number of players is: " + playerList.size() + "\n");

		//Clear TextFields
		txtName.clear();
		txtInitialPoints.clear();
		txtDieOneSides.clear();
		txtDieTwoSides.clear();
	}


	/**
	 * This method displays the rules of the game
	 */
	private void rulesPage() {

		//Padding root
		rulesRoot.setPadding(new Insets(SMALL_GAP, SMALL_GAP, SMALL_GAP, SMALL_GAP));

		//Displaying rules for game
		Label lblRulesTitle = new Label("Rules of the High Low Game");
		lblRulesTitle.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, LARGE_FONT));
		lblRulesTitle.setTextFill(Color.WHITESMOKE);
		rulesRoot.getChildren().add(lblRulesTitle);
		rulesRoot.setAlignment(Pos.TOP_CENTER);

		//Listing rules for game

		String rules = "In the High or Low game, with 1-x players, the player begins with a score of y (default 1000)." + "\r\n" + 
				"The player is prompted for the number of points to risk and a second prompt asks the player" +
				"\r\n"+ " to choose either high or low.\r\n" + 
				"\r\n" + 
				"The player rolls the dice and the outcome is compared to the player's choice of high or low.\r\n" + 
				"\r\n" + 
				"If the dice total is between the lowest and the closest integer below the middle number" + "\n" + 
				" (e.g. 2-6 for two 6 sided dice) inclusive, then it is considered \"low\".\r\n" + 
				"A total between the closest integer after middle number and highest integer (e.g. 8-12) inclusive is"+"\n" + "\"high\". " + 
				"A total of the middle number (e.g. 7 for two 6 sided dice) is neither high nor low, and the player" + "\n" +"loses the points at risk." + 
				"If the player calls correctly, the points at risk are doubled and added" + "\n" + "to the total points. " + 
				"For a wrong call, the player loses the points at risk.\r\n";

		
		//Adding rules to a label
		Label lblResponse = new Label(rules);
		lblResponse.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, SMALL_FONT * 1.5));
		lblResponse.setTextFill(Color.WHITE);
		rulesRoot.getChildren().add(lblResponse);
		
		//Resizing image on rules page
		imgWelcome.setFitWidth(350);
		imgWelcome.setFitHeight(400);
		
		//Aligning root to center and changing background
		rulesRoot.setAlignment(Pos.TOP_RIGHT);
		rulesRoot.setBackground(changeBackground("/images/roulette-wheel.jpg"));

		//Initializing button for going back to main page
		Button btnBack = new Button("Back");
		btnBack.setFont(Font.font(MEDIUM_FONT));
		btnBack.setStyle("-fx-background-color: #FFFFFF; ");

		//Button takes user back to main game scene
		btnBack.setOnAction(event -> myStage.setScene(scene));

		//Adding images and back button to page
		HBox sideImage = new HBox(VERY_LARGE_GAP,imgWelcome, btnBack);

		//Aligning image and button to right and displaying to screen
		sideImage.setAlignment(Pos.BASELINE_RIGHT);
		rulesRoot.getChildren().add(sideImage);

		//Assigning new scene for rules page
		rulesScene = new Scene(rulesRoot);

	}


	/**
	 * This method lists the game statistics
	 */
	private void gameStatisticsPage(){

		//Initializing Label for title
		Label lblGameStatsTitle = new Label("Current Game Stats:");
		lblGameStatsTitle.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, LARGE_FONT));
		lblGameStatsTitle.setTextFill(Color.WHITESMOKE);
		statisticsRoot.getChildren().add(lblGameStatsTitle);

		//Assigning new pie chart and adding to root
		pieChart = new PieChart();
		statisticsRoot.getChildren().add(pieChart);	

		//Initializing Label for subtitle of game stats
		Label lblGameStats = new Label("Full Game will be updated here after each round:");
		lblGameStats.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, MEDIUM_FONT));
		lblGameStats.setTextFill(Color.WHITESMOKE);
		statisticsRoot.getChildren().add(lblGameStats);

		//Initializing listview for listing the game stats after every round
		lstGameStats = new ListView<String>();
		statisticsRoot.getChildren().add(lstGameStats);

		//Changing background of the page
		statisticsRoot.setBackground(changeBackground("/images/casinoChipsBackground.jpg"));

		//Initializing button for going back to main page
		Button btnBack = new Button("Back");
		btnBack.setFont(Font.font(MEDIUM_FONT));
		btnBack.setStyle("-fx-background-color: #FFFFFF; ");
		btnBack.setTextFill(Color.BLACK);
		btnBack.setOnAction(event -> myStage.setScene(scene));

		//Aligning the page to the center
		statisticsRoot.setAlignment(Pos.CENTER);
		
		//Laying out the back button at the bottom right
		HBox layoutButton = new HBox(LARGE_GAP,btnBack);
		layoutButton.setAlignment(Pos.BASELINE_RIGHT);
		statisticsRoot.getChildren().add(layoutButton);
		
		//Assigning new scene and adding root
		statisticsScene = new Scene(statisticsRoot);
	}

	/**
	 * This method displays and runs the main parts of the game
	 */
	private void mainGameLogic() {

		//Declaring String for message
		String message = "";

		//If the game is not over and there is more than one person in the game left
		if(playerList.size() >= 1) {

			//If the current player/team playing is a team, give a team message
			if(playerList.get(currentIndex).isTeam()) {

				//If the team has members, display the member's name
				if(((Team)playerList.get(currentIndex)).teamMembersList.size() > 1) {
					message = "Welcome! It is currently " + playerList.get(currentIndex).getName() + "'s turn with Member: " + 
							((Team) playerList.get(currentIndex)).getCurrentTurnMemberName() + ". \n"+ "The team currently has: " 
							+ playerList.get(currentIndex).getPoints() + " points.";
				}
				
				//Otherwise, give the whole team's name
				else {
					message = "Welcome! It is currently " + playerList.get(currentIndex).getName() + "'s turn" + 
							"\n"+ playerList.get(currentIndex).getName() + " currently has: " + playerList.get(currentIndex).getPoints() + " points.";	
				}

			}
			
			//Give a player message since it's not a team
			else {

				message = "Welcome! It is currently " + playerList.get(currentIndex).getName() + "'s turn with " + playerList.get(currentIndex).getPoints() + " points.";

			}

			//Display message
			lblTitle.setText(message);

			//If the current player/team playing is a computer, tell it to make a call and bet points
			if(playerList.get(currentIndex).isComputer()) {
				//Casting to computer player make call method
				((ComputerPlayer) playerList.get(currentIndex)).makeCall();
				betPoints(playerList.get(currentIndex).getCurrentCall());

			}
			
			//Call method for betting points with the call that the user makes
			btnLow.setOnAction(event -> betPoints(Call.LOW));
			btnHigh.setOnAction(event -> betPoints(Call.HIGH));

		}
	}

	/**
	 * This method is used for betting points from the user's call
	 * 
	 * @param userCall The Call the player/team made
	 */
	private void betPoints(Call userCall) {
		
		//If the current player/team is a computer or the human player/team made a valid call
		if(playerList.get(currentIndex).isComputer() || (txtCall.getLength() >= 1 && isNumber(txtCall.getText()) 
				&& Integer.parseInt(txtCall.getText()) <= playerList.get(currentIndex).getPoints() && playerList.get(currentIndex).getPoints() > 0
				&& Integer.parseInt(txtCall.getText()) > 0)) {

			//If the current player/team is not a computer, let it make its call and risk its points
			if(!playerList.get(currentIndex).isComputer()) {
				playerList.get(currentIndex).makeCall(userCall);
				playerList.get(currentIndex).riskPoints(Integer.parseInt(txtCall.getText()));

			}
			//Otherwise it is a computer, so tell it to make a call and risk points
			else {

				((ComputerPlayer) playerList.get(currentIndex)).riskPoints();

			}
			
			
			//Roll the dice
			playerList.get(currentIndex).rollDice();

			//Calculate the current amount of points
			int currentPoints = playerList.get(currentIndex).showPoints();

			//Determine result message if the user won or lost the roll
			String rollResult = "";
			if(HLPlayer.isHasWon()) {
				rollResult = "won";
			} else {

				rollResult = "lost";
			}

			//Display the result of the roll to the user
			String results = playerList.get(currentIndex).getName() + " rolled: " + playerList.get(currentIndex).showRoll() + " (called: " +
					playerList.get(currentIndex).getCurrentCall() + " and " + rollResult + " the roll)";
			results += "\r\n" + playerList.get(currentIndex).getName() + " now has: " + currentPoints + " points.";

			//If the player has 2 normal 6-sided dice, show the dice in images and make a dice sound
			if(playerList.get(currentIndex).hasNormalDice()) {
				imgFirstDie.setImage(playerList.get(currentIndex).getDiceRolls().get(0).getRollFace().getImage());
				imgSecondDie.setImage(playerList.get(currentIndex).getDiceRolls().get(1).getRollFace().getImage());
				
				imgFirstDie.setVisible(true);
				imgSecondDie.setVisible(true);
				
				String diceFile = "/audio/diceRoll.mp3";
		        Media sound = new Media(getClass().getResource(diceFile).toString());  
				
				MediaPlayer mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.play();
				
			} 
			
			//Otherwise, keep the dice invisible if the current player/team does not have the normal dice scenario
			else {
				imgFirstDie.setVisible(false);
				imgSecondDie.setVisible(false);
			}
			
			//If the player/team has 0 points, make them leave the list of player/teams
			if(playerList.get(currentIndex).getPoints() == 0) {

				results += "\r\n" + playerList.get(currentIndex).getName() + " has left the game (no points to play).";
				playerList.remove(currentIndex);

				if(playerList.size() == 1) {

					results += "\r\n" + "\n" + playerList.get(0).getName() + " has won the game!";
					lblWinner.setText(playerList.get(0).getName() + " has won the game!");
					
					//Display winner page for the end of the game
					myStage.setScene(endScene);
				}

			}

			//Display message for current events in game
			lblTitle.setText(results);

			//Add info with old and new results
			lblResultsTrack.setText(oldResults + "\n" + results);

			//Clear TextField
			txtCall.clear();

			//If you've hit the end of the list of the players/teams, restart from the beginning
			if(currentIndex >= playerList.size() - 1) {
				currentIndex = 0;

				//Also, update information from new round and add it to the pie chart
				ArrayList<PieChart.Data> pieDataList = new ArrayList<PieChart.Data>();

				//Making slices for each player/team
				for (int i = 0; i <= playerList.size() - 1; i++) {

					PieChart.Data slice1 = new PieChart.Data(playerList.get(i).getName(), playerList.get(i).getPoints());
					pieDataList.add(slice1);
				}

				//Emptying data from pie chart from last round
				pieChart.getData().clear();
				
				//Adding new slices and info from data of each player/team to the new pie chart
				for (int i = 0; i <= playerList.size() - 1; i++) {

					pieChart.getData().add(pieDataList.get(i));

				}

				//Add information about the round to the list view
				lstGameStats.getItems().add(statPoints());
			}
			else{
				//Since this person has finished their turn, move on to the next person
				currentIndex++;
			}

			//These results are now old since we'll have new results from the next player
			oldResults = results;
		}

		//If there is an error in formating for the points to risk, let the user know
		else {

			lblTitle.setText("Please enter the number of points you want to risk (in proper number format)" + "\r\n" + "Make sure value is less than the amount of points you have."
					+ "\r\n" + playerList.get(currentIndex).getName() + " has " + playerList.get(currentIndex).getPoints() + " points.");
		}

		//Declaring String for message to the user
		String message = "";

		//If the current player/team is a team, and the game is not over (more than one person in game)
		if(playerList.size() > 1 && playerList.get(currentIndex).isTeam()) {

			//If the team has members, give member info
			if(((Team)playerList.get(currentIndex)).teamMembersList.size() >= 1) {

				message = "It is currently " + playerList.get(currentIndex).getName() + "'s turn with Member: " + 
						((Team) playerList.get(currentIndex)).getCurrentTurnMemberName() + "\n" +
						playerList.get(currentIndex).getName() + " currently has: "+ playerList.get(currentIndex).getPoints() + " points.";
			}
			//If the team does not have members, just give the team name with the info
			else {

				message = "It is currently " + playerList.get(currentIndex).getName() + "'s turn. " + "\n" +
						playerList.get(currentIndex).getName() + " currently has: " + playerList.get(currentIndex).getPoints() + " points.";

			}
		}
		//Otherwise, it's obviously just a player, so give the player info
		else if(playerList.size() > 1) {

			message = "It is currently " + playerList.get(currentIndex).getName() + "'s turn with " + playerList.get(currentIndex).getPoints() + " points.";

		}
		
		
		//Display the results
		lblResults.setText(message);

		//Since it's the next user's turn, if the next player/team is a computer, tell the computer to make a call and bet points
		if(playerList.size() > 1 && playerList.get(currentIndex).isComputer()) {

			//Casting to computer player make call method
			((ComputerPlayer) playerList.get(currentIndex)).makeCall();
			betPoints(playerList.get(currentIndex).getCurrentCall());

		}
	}

	/**
	 * This method sets up the design for the main page of the game
	 */
	private void setMainBackgrounds(){

		
		//ImagesViews are initialized
		imgSpinWheel = new ImageView(getClass().getResource("/images/wheelSpin.gif").toString());
		imgWelcome = new ImageView(getClass().getResource("/images/coverSpin.gif").toString());

		imgHigh = new ImageView(getClass().getResource("/images/upArrow.gif").toString());
		imgLow = new ImageView(getClass().getResource("/images/downArrow.gif").toString());

		//Resizing the images to better fit the screen
		imgHigh.setFitHeight(75);
		imgHigh.setFitWidth(55);

		//Resizing the images to better fit the screen
		imgLow.setFitHeight(75);
		imgLow.setFitWidth(55);

		//Resizing the images to better fit the screen
		imgSpinWheel.setFitHeight(300);
		imgSpinWheel.setFitWidth(500);

		//Initializing button for calling HIGH
		btnHigh = new Button("HIGH",imgHigh);
		btnHigh.setFont(Font.font(MEDIUM_FONT));
		btnHigh.setTextFill(Color.WHITE);
		btnHigh.setStyle("-fx-background-color: #000000; ");

		//Initializing button for calling LOW
		btnLow = new Button("LOW", imgLow);
		btnLow.setFont(Font.font(MEDIUM_FONT));
		btnLow.setTextFill(Color.WHITE);
		btnLow.setStyle("-fx-background-color: #000000; ");

		//Initializing button for going to rules page
		btnRules = new Button("Rules");
		btnRules.setFont(Font.font(MEDIUM_FONT));
		btnRules.setTextFill(Color.WHITE);
		btnRules.setStyle("-fx-background-color: #000000; ");

		//If the rules button is pressed, tak the user to the rules page
		btnRules.setOnAction(event -> myStage.setScene(rulesScene));

		//Initializing button for going to stats page
		btnSettings = new Button("View Game Statistics");
		btnSettings.setFont(Font.font(MEDIUM_FONT));
		btnSettings.setTextFill(Color.WHITE);
		btnSettings.setStyle("-fx-background-color: #000000; ");
		btnSettings.setOnAction(event -> myStage.setScene(statisticsScene));


		//Displaying title and welcoming users to the game
		lblTitle = new Label("Welcome to the High Low Chance program!");
		lblTitle.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, LARGE_FONT));
		lblTitle.setTextFill(Color.WHITE);
		root.getChildren().add(lblTitle);

		//Initializing label for results of game that will later occur
		lblResults = new Label("");
		lblResults.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, LARGE_FONT));
		lblResults.setTextFill(Color.WHITE);
		root.getChildren().add(lblResults);
		
		//Initializing TextField for the points to risk
		txtCall = new TextField();

		//Adding images of the two 6-sided dice and setting them to not be visible
		imgFirstDie = new ImageView(getClass().getResource("/images/dice/blankDice.png").toString());
		imgSecondDie = new ImageView(getClass().getResource("/images/dice/blankDice.png").toString());
		
		imgFirstDie.setVisible(false);
		imgSecondDie.setVisible(false);
		
		//Resizing images
		imgFirstDie.setFitWidth(200);
		imgFirstDie.setFitHeight(200);
		
		imgSecondDie.setFitWidth(200);
		imgSecondDie.setFitHeight(200);
		
		//Displaying images next to each other
		HBox displayList = new HBox(imgSpinWheel, imgFirstDie, imgSecondDie);
		root.getChildren().add(displayList);

		//Align root elements to the top left
		root.setAlignment(Pos.TOP_LEFT);

		//Adding list of main game control buttons
		HBox buttonList = new HBox(SMALL_MEDIUM_GAP,btnHigh,btnLow, txtCall);
		root.getChildren().add(buttonList);

		//Adding list of other page navigation buttons
		HBox buttonPage = new HBox(SMALL_MEDIUM_GAP, btnRules, btnSettings);
		buttonPage.setAlignment(Pos.TOP_RIGHT);
		root.getChildren().add(buttonPage);

		//Initializing a label for tracking the results of the past two turns
		lblResultsTrack = new Label();
		lblResultsTrack.setFont(Font.font("Times", FontWeight.BOLD, FontPosture.ITALIC, MEDIUM_FONT));
		lblResultsTrack.setTextFill(Color.WHITE);
		lblResultsTrack.setBackground(changeBackground("/images/casinoChipsBackground.jpg"));
		root.getChildren().add(lblResultsTrack);

		//Changing background
		root.setBackground(changeBackground("/images/casinoChipsBackground.jpg"));

	}

	/**
	 * This method formats the statistics for each round 
	 * 
	 * @return A String with the point statistics for that round for each player
	 */
	private String statPoints() {

		/* Different Structures:
		  
		 	Round is defined as every time everyone has finished rolling dice once

							Round 1	 	Round 2 	Rounds...
		Player 1 points

		Player 2 points

		Player x points...

		Another way to display the round information is by putting rounds in rows like this:


					Player 1 points,    	Player 2 points,		 Player x points...		
		Round 1

		Round 2

		Rounds...

		So, we will do it that way

		Decided it the best way to do is the following format: 

		Round 1: | Player 1: x points | Player 2: x points | Player 3: x points |

		Round 2: | Player 1: x points | Player 2: x points | Player 3: x points |

		Round 3: | Player 1: x points | Player 2: x points | Player 3: x points |

		Round x: | Player 1: x points | Player 2: x points | Player 3: x points |


		 */

		//Declaring variable for round statistics
		String roundStats = "";

		//If there's at least one person playing the game
		if(playerList.size() >= 1) {
			
			//Current round
			roundStats += "Round " + round + ": | ";

			//Looping for the amount of players in selected round
			for (int i = 0; i <= playerList.size() - 1; i++) {

				//Adding cast for hlplayer and getting player name and number of points
				roundStats += playerList.get(i).getName() + ": " + playerList.get(i).getPoints() + " points | ";

			}

			roundStats += "\n";

			round ++;
		}


		//Returning variable with the round stats back
		return roundStats;

	}

	/**
	 * This method changes the background to an image
	 * 
	 * @param source The source for the image
	 * @return A Background with that image as the background
	 */
	private Background changeBackground(String source) {
		
		//Since I kept changing the background images for many pages, I decided to put it all into one method and reuse it
		
		//Initializing image with given image source
		Image imgSpace = new Image(getClass().getResource(source).toString());

		//Creating background with a background image and returning back to user
		BackgroundSize backgroundSize = new BackgroundSize(primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight(), false, false, false, false);
		BackgroundImage backImage = new BackgroundImage(imgSpace, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		Background imageBackground = new Background(backImage);

		return imageBackground;
	}

	
	/**
	 * This method determines if a given String is a number
	 * 
	 * @param strNum The String to check whether its value is an integer
	 * @return A Boolean that states whether the String is an integer
	 */
	private static boolean isNumber(String strNum) {
		//If empty, it's not a number
		if (strNum == null) {
			return false;
		}
		//Try parsing to integer, if it doesn't work, then it must not be an integer. Otherwise it is an integer.
		try {
			int integer = Integer.parseInt(strNum);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}


}
