package au.com.brentoncrowley.bowlinggame;

import au.com.brentoncrowley.bowlinggame.controllers.BowlingGame;

/**
 * Created by brentoncrowley on 24/04/2014.
 */
public class Main
{
    public static void main(String[] args)
    {
        BowlingGame bowlingGame = new BowlingGame();

        boolean roll = true;

        while (roll)
        {
            try
            {
                int numPins = (int) Math.floor(Math.random() * 11);
                roll = bowlingGame.roll(numPins); // random game
//                roll = bowlingGame.roll(10); // Cheat a perfect game...
//                roll = bowlingGame.roll(5); // Cheat a spare game...
            }
            catch (Exception error){} // catches incorrect pin exception
        }

        System.out.println(bowlingGame.toString());

        System.out.println("Final Score: " + bowlingGame.score());

    }

}
