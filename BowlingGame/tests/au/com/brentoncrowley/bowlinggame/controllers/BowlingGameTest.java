package au.com.brentoncrowley.bowlinggame.controllers;

import au.com.brentoncrowley.bowlinggame.models.FinalFrame;
import au.com.brentoncrowley.bowlinggame.models.Frame;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BowlingGameTest
{
    @Test
    public void testScore() throws Exception
    {
        // Start with a basic test of simple sums

        BowlingGame bowlingGame = new BowlingGame();

        System.out.println("Score - Simple Sum");
        System.out.println("Score: " + bowlingGame.score());

        bowlingGame.roll(9);
        bowlingGame.roll(1);

        bowlingGame.roll(9);

        System.out.println("Score: " + bowlingGame.score());

        int score = 28;

        assertTrue("Score: " + bowlingGame.score() +
                        " should equal " + score +", but it does not.",
                (bowlingGame.score() == score)
        );

        // Testing for perfect game score: 300

        bowlingGame = new BowlingGame();

        bowlingGame.roll(10); //1
        bowlingGame.roll(10); //2
        bowlingGame.roll(10); //3
        bowlingGame.roll(10); //4

        bowlingGame.roll(10); //5
        bowlingGame.roll(10); //6
        bowlingGame.roll(10); //7
        bowlingGame.roll(10); //8

        bowlingGame.roll(10); //9
        bowlingGame.roll(10); //10.1
        bowlingGame.roll(10); //10.2 - 11
        bowlingGame.roll(10); // 10.3 - 12

        score = 300;

        assertTrue("Score: " + bowlingGame.score() +
                        " should equal " + score +", but it does not.",
                (bowlingGame.score() == score)
        );
    }

}