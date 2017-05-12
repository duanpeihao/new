package au.com.brentoncrowley.bowlinggame.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FinalFrameTest
{

    @Test
    public void testIsRollValid() throws Exception
    {
        // ------------------------------------------

        // VALID TESTING 5,5

        FinalFrame frame = new FinalFrame(9);
        int roll = 5;

        assertTrue("Roll(" + roll + ") is NOT valid when it should be.",
                frame.isRollValid(roll));

        frame.addRoll(roll);
        roll = 5;

        assertTrue("Roll(" + roll + ") is NOT valid when it should be.",
                frame.isRollValid(roll));

        frame.addRoll(roll);

        roll = 5;

        assertTrue("Roll(" + roll + ") is NOT valid when it should be.",
                frame.isRollValid(roll));

        frame.addRoll(roll);

        // ------------------------------------------

        // INVALID TESTING 5,6

        frame = new FinalFrame(9);
        roll = 5;

        assertTrue("Roll(" + roll + ") is NOT valid when it should be.",
                frame.isRollValid(roll));

        frame.addRoll(roll);
        roll = 6;

        assertFalse("Roll(" + roll + ") is valid when it should NOT be. " +
                        "Valid roll is: " + frame.validNumPinsForNextRoll(),
                frame.isRollValid(roll)
        );

        // ------------------------------------------

        // INVALID TESTING 5,4,1

        frame = new FinalFrame(9);
        roll = 5;

        assertTrue("Roll(" + roll + ") is NOT valid when it should be.",
                frame.isRollValid(roll));

        frame.addRoll(roll);
        roll = 4;

        assertTrue("Roll(" + roll + ") is NOT valid when it should be.",
                frame.isRollValid(roll));

        frame.addRoll(roll);

        roll = 1;

        assertFalse("Roll(" + roll + ") is valid when it should NOT be. " +
                        "No more getRolls available.",
                frame.isRollValid(roll));

        // ------------------------------------------

    }

    @Test
    public void testGetAccumulativeScore() throws Exception
    {
        // ------------------------------------------

        FinalFrame frame = new FinalFrame(9);
        frame.addRoll(10); // 10

        int score = 10;

        assertTrue("Accumulative Score: " + frame.getAccumulativeScore() +
                        " should equal " + score + ", but it does not.",
                (frame.getAccumulativeScore() == score) );

        // ------------------------------------------

        frame = new FinalFrame(9);
        frame.addRoll(10); // 10
        frame.addRoll(10); // 10 + 10 : 20

        score = 20;

        assertTrue("Accumulative Score: " + frame.getAccumulativeScore() +
                        " should equal " + score + ", but it does not.",
                (frame.getAccumulativeScore() == score) );

        // ------------------------------------------

        frame = new FinalFrame(9);
        frame.addRoll(10); // 10 : 10
        frame.addRoll(10); // 10 + 10 : 20
        frame.addRoll(10); // 10 + 10 + 10 : 30

        score = 30;

        assertTrue("Accumulative Score: " + frame.getAccumulativeScore() +
                        " should equal " + score + ", but it does not.",
                (frame.getAccumulativeScore() == score) );

        // ------------------------------------------
    }

    @Test
    public void testValidNumPinsForNextRoll() throws Exception
    {
        // ------------------------------------------

        FinalFrame frame = new FinalFrame(9);
        frame.addRoll(0);

        int validNum = 10 - frame.getRolls().get(0);

        assertTrue("Valid pins should be: " + validNum + ", but the number" +
                        "returned is: " + frame.validNumPinsForNextRoll(),
                (frame.validNumPinsForNextRoll() == 10));

        // ------------------------------------------

        frame = new FinalFrame(9);
        frame.addRoll(5);

        validNum = 10 - frame.getRolls().get(0);

        assertTrue("Valid pins should be: " + validNum + ", but the number" +
                        "returned is: " + frame.validNumPinsForNextRoll(),
                (frame.validNumPinsForNextRoll() == 5));

        // ------------------------------------------

        frame = new FinalFrame(9);
        frame.addRoll(10);

        validNum = 10;

        assertTrue("Valid pins should be: " + validNum + ", but the number" +
                        "returned is: " + frame.validNumPinsForNextRoll(),
                (frame.validNumPinsForNextRoll() == 10));

        // ------------------------------------------

        frame = new FinalFrame(9);
        frame.addRoll(10);
        frame.addRoll(10);

        validNum = 10;

        assertTrue("Valid pins should be: " + validNum + ", but the number" +
                        " returned is: " + frame.validNumPinsForNextRoll(),
                (frame.validNumPinsForNextRoll() == 10));

        // ------------------------------------------

        frame = new FinalFrame(9);
        frame.addRoll(10);
        frame.addRoll(10);
        frame.addRoll(10);

        validNum = 0;

        assertTrue("Valid pins should be: " + validNum + ", but the number" +
                        " returned is: " + frame.validNumPinsForNextRoll(),
                (frame.validNumPinsForNextRoll() == 0)
        );

        // ------------------------------------------
    }

    @Test
    public void testIsFirstRollStrike() throws Exception
    {
        // ------------------------------------------

        FinalFrame frame = new FinalFrame(0);
        frame.addRoll(10);

        assertTrue("First roll in frame should be a strike, but is not.",
                frame.isFirstRollStrike());

        // ------------------------------------------

        frame = new FinalFrame(0);
        frame.addRoll(10);
        frame.addRoll(10);

        assertTrue("First roll in frame should be a strike, but is not.",
                frame.isFirstRollStrike());

        // ------------------------------------------

        frame = new FinalFrame(0);
        frame.addRoll(10);
        frame.addRoll(10);
        frame.addRoll(10);

        assertTrue("First roll in frame should be a strike, but is not.",
                frame.isFirstRollStrike());

        // ------------------------------------------

        frame = new FinalFrame(0);
        frame.addRoll(9);
        frame.addRoll(0);

        assertFalse("First roll in frame should NOT be a strike, but it is.",
                frame.isFirstRollStrike());

        // ------------------------------------------

        frame = new FinalFrame(0);

        assertFalse("First roll in frame should NOT be a strike, but it is.",
                frame.isFirstRollStrike());

        // ------------------------------------------
    }

    @Test
    public void testIsSecondRollStrike() throws Exception
    {
        // ------------------------------------------

        FinalFrame frame = new FinalFrame(0);
        frame.addRoll(10);
        frame.addRoll(10);

        assertTrue("Second roll in frame should be a strike, but is not.",
                frame.isSecondRollStrike());

        // ------------------------------------------

        frame = new FinalFrame(0);
        frame.addRoll(0);
        frame.addRoll(10);

        assertFalse("Second roll in frame should NOT be a strike, but it is.",
                frame.isSecondRollStrike());

        // ------------------------------------------

        frame = new FinalFrame(0);
        frame.addRoll(10);
        frame.addRoll(9);

        assertFalse("Second roll in frame should NOT be a strike, but it is.",
                frame.isSecondRollStrike());

        // ------------------------------------------
    }

    @Test
    public void testIsFinalFrameSpare() throws Exception
    {
        // ------------------------------------------

        FinalFrame frame = new FinalFrame(0);
        frame.addRoll(0);
        frame.addRoll(10);

        assertTrue("First two rolls should equate to a spare, but they do not",
                frame.isFinalFrameSpare());

        // ------------------------------------------

        frame = new FinalFrame(0);
        frame.addRoll(5);
        frame.addRoll(5);

        assertTrue("First two rolls should equate to a spare, but they do not",
                frame.isFinalFrameSpare());

        // ------------------------------------------

        frame = new FinalFrame(0);
        frame.addRoll(5);
        frame.addRoll(4);

        assertFalse("First two rolls should NOT equate to a spare, but they do",
                frame.isFinalFrameSpare());

        // ------------------------------------------

        frame = new FinalFrame(0);
        frame.addRoll(10);
        frame.addRoll(0);

        assertFalse("First two rolls should NOT equate to a spare, but they do",
                frame.isFinalFrameSpare());

        // ------------------------------------------
    }
}