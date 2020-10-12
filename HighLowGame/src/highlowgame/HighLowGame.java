package assessment;

import simpleIO.Console;

public class HighLowGame {

    public static void main(String[] args) {

        final int QUIT = -1;
        final int LOW = HLPlayer.LOW;
        final int HIGH = HLPlayer.HIGH;
        HLPlayer player = new HLPlayer();
        int pointsToRisk, call;
        
        /* play High or Low game */
        Console.print("You have " + player.showPoints() + " points.");

        pointsToRisk = Console.readInt("How many points do you want to risk? (" + QUIT + " to quit) ");

        while (pointsToRisk != QUIT) {

            player.riskPoints(pointsToRisk);

            do {
                call = Console.readInt("Make a call (" + LOW + " for low, " + HIGH + " for high): ");
            } while (call != LOW && call != HIGH);

            player.makeCall(call);

            player.rollDice();

            Console.print("You rolled: " + player.showRoll());
            Console.print("You now have " + player.showPoints() + " points.");

            pointsToRisk = Console.readInt("How many points do you want to risk? (" + QUIT + " to quit) ");
        }
    }
}
