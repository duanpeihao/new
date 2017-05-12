package au.com.brentoncrowley.bowlinggame.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FrameTest
{

    @Test
    public void testIsRollValid() throws Exception
    {
        // ------------------------------------------

        // VALID TESTING 5,5

        Frame frame = new Frame(0);
        int roll = 5;

        assertTrue("Roll(" + roll + ") is NOT valid when it should be.",
                frame.isRollValid(roll));

        frame.addRoll(roll);
        roll = 5;

        assertTrue("Roll(" + roll + ") is NOT valid when it should be.",
                frame.isRollValid(roll));

        frame.addRoll(roll);

        // ------------------------------------------

        // INVALID TESTING 5,6

        frame = new Frame(0);
        roll = 5;

        assertTrue("Roll(" + roll + ") is NOT valid when it should be.",
                frame.isRollValid(roll));

        frame.addRoll(roll);
        roll = 6;

        assertFalse("Roll(" + roll + ") is valid when it should NOT be. " +
                        "Valid roll is: " + frame.validNumPinsForNextRoll(),
                frame.isRollValid(roll));

        frame.addRoll(roll);

        // ------------------------------------------

        // VALID TESTING 5,5,1

        frame = new Frame(0);
        roll = 5;

        assertTrue("Roll(" + roll + ") is NOT valid when it should be.",
                frame.isRollValid(roll));

        frame.addRoll(roll);
        roll = 5;

        assertTrue("Roll(" + roll + ") is NOT valid when it should be.",
                frame.isRollValid(roll));

        frame.addRoll(roll);
        roll = 1;

        assertFalse("Roll(" + roll + ") is valid when it should NOT be. " +
                        "Valid roll is: " + frame.validNumPinsForNextRoll(),
                frame.isRollValid(roll));

        // ------------------------------------------

    }

    @Test
    public void testFrameIsSpare() throws Exception
    {
        // The bowler knocks all pins in two tries
        // Eg: 0/10, 1/9, 8/2, 5/5 etc.

        // ------------------------------------------

        Frame frame = new Frame(0);
        frame.addRoll(0);
        frame.addRoll(10);

        assertTrue("The following frame: " + frame.toString() + " is NOT a " +
                        "spare. It should be!",
                frame.isSpare());

        // ------------------------------------------

        frame = new Frame(1);
        frame.addRoll(0);
        frame.addRoll(9);

        assertFalse("The following frame: " + frame.toString() + " is a spare, " +
                        "but it isn\'t",
                frame.isSpare());

        // ------------------------------------------

        frame = new Frame(2);
        frame.addRoll(0);
        frame.addRoll(9);
        frame.addRoll(1);

        assertFalse("The following frame: " + frame.toString() + " is a spare, " +
                        "but it isn\'t",
                frame.isSpare()
        );

        // ------------------------------------------

    }

    @Test
    public void testFrameIsStrike() throws Exception
    {
        // The bowler knocks all pins in one try
        // Eg: 10, 10, 10. NOT 0/10, 0/9/1 etc.

        // ------------------------------------------

        Frame frame = new Frame(0);
        frame.addRoll(10);

        assertTrue("The following frame: " + frame.toString() + " is NOT a " +
                        "strike. It should be!",
                frame.isStrike()
        );

        // ------------------------------------------

        frame = new Frame(1);
        frame.addRoll(0);
        frame.addRoll(10);

        assertFalse("The following frame: " + frame.toString() + " is a " +
                        "strike, but it isn\'t",
                frame.isStrike()
        );

        // ------------------------------------------

        frame = new Frame(2);
        frame.addRoll(5);
        frame.addRoll(5);

        assertFalse("The following frame: " + frame.toString() + " is a " +
                        "strike, but it isn\'t",
                frame.isStrike());

        // ------------------------------------------

        frame = new Frame(0);
        frame.addRoll(10);
        frame.addRoll(0);

        assertFalse("The following frame: " + frame.toString() + " is a " +
                        "strike, but it isn\'t",
                frame.isStrike());

        // ------------------------------------------
    }

    @Test
    public void testValidNumPinsForNextRoll() throws Exception
    {
        // ------------------------------------------

        Frame frame = new Frame(0);
        frame.addRoll(0);

        int validNum = 10 - frame.getRolls().get(0);

        assertTrue("Valid pins should be: " + validNum + ", but the number" +
                        "returned is: " + frame.validNumPinsForNextRoll(),
                (frame.validNumPinsForNextRoll() == 10));

        // ------------------------------------------

        frame = new Frame(1);
        frame.addRoll(0);
        frame.addRoll(10);

        validNum = 0;

        assertTrue("Valid pins should be: " + validNum + ", but the number" +
                        "returned is: " + frame.validNumPinsForNextRoll(),
                (frame.validNumPinsForNextRoll() == 0));

        // ------------------------------------------

        frame = new Frame(2);
        frame.addRoll(10);

        validNum = 0;

        assertTrue("Valid pins should be: " + validNum + ", but the number" +
                        "returned is: " + frame.validNumPinsForNextRoll(),
                (frame.validNumPinsForNextRoll() == 0));

        // ------------------------------------------

        frame = new Frame(3);
        frame.addRoll(7);
        frame.addRoll(3);

        validNum = 0;

        assertTrue("Valid pins should be: " + validNum + ", but the number" +
                        "returned is: " + frame.validNumPinsForNextRoll(),
                (frame.validNumPinsForNextRoll() == 0));

        // ------------------------------------------

    }

    @Test
    public void testGetAccumulativeScore() throws Exception
    {

        // ------------------------------------------

        Frame frame = new Frame(0);

        frame.addRoll(9); // 9
        frame.addRoll(1); // 10 (9+1)
        Frame prevFrame = frame;

        frame = new Frame(1);
        frame.setPreviousFrame(prevFrame);
        prevFrame.setNextFrame(frame);
        frame.addRoll(8); // 26 (10 + 8 + 8)
        frame.addRoll(1); // 27 (27 + 1)

        int score = 27;

        assertTrue("Accumulative Score: " + frame.getAccumulativeScore() +
                        " should equal " + score + ", but it does not.",
                (frame.getAccumulativeScore() == score) );

        // ------------------------------------------

        // Perfect Game 9 Frames

        prevFrame = null;

        for (int i = 0; i < 9; i++)
        {
            frame = new Frame(i);

            if (prevFrame != null)
            {
                frame.setPreviousFrame(prevFrame);
                prevFrame.setNextFrame(frame);
            }

            frame.addRoll(10);

            prevFrame = frame;

        }

        score = 240;

        assertTrue("Accumulative Score: " + frame.getAccumulativeScore() +
                        " should equal " + score + ", but it does not.",
                (frame.getAccumulativeScore() == score) );

        // ------------------------------------------

        // Perfect Game 9 Frames

        prevFrame = null;

        for (int i = 0; i < 10; i++)
        {
            frame = new Frame(i);

            if (prevFrame != null)
            {
                frame.setPreviousFrame(prevFrame);
                prevFrame.setNextFrame(frame);
            }

            frame.addRoll(10);

            prevFrame = frame;

        }

        frame = new FinalFrame(9);
        frame.setPreviousFrame(prevFrame);

        frame.addRoll(10);
        frame.addRoll(10);
        frame.addRoll(10);

        score = 300;

        assertTrue("Accumulative Score: " + frame.getAccumulativeScore() +
                        " should equal " + score + ", but it does not.",
                (frame.getAccumulativeScore() == score) );

        // ------------------------------------------
    }
}