package au.com.brentoncrowley.bowlinggame.controllers;

import au.com.brentoncrowley.bowlinggame.models.FinalFrame;
import au.com.brentoncrowley.bowlinggame.models.Frame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brentoncrowley on 24/04/2014.
 *
 * DiUS Bowling Exercise
 *
 */
public class BowlingGame {

    private static final int TOTAL_FRAMES = 10;

    private List<Frame> frames;
    private Frame currentFrame;

    public BowlingGame()
    {
        frames = new ArrayList<Frame>();
        addFrame();
    }

    /*
    *
    * Add a number of pins to the bowling game.
    * This method should handle invalid entries. Invalid entries include
    * pin numbers that exceed the frame sum, or out-of-bounds pin range.
    *
    * This method also takes care of frame creation when needed.
    *
    * @return boolean will return false if the game is over otherwise true.
    * */
    public boolean roll(int noOfPins) throws Exception
    {
        if (isGameOver())
            return false;

        if (!currentFrame.isRollValid(noOfPins))
            throw new Exception("The number of pins added (" + noOfPins +
                    ") are invalid. " +
                    " Valid pins: " + currentFrame.validNumPinsForNextRoll());

        currentFrame.addRoll(noOfPins);

        if (currentFrame.validNumPinsForNextRoll() == 0 && !isGameOver())
            addFrame();

        return true;
    }

    /*
    *
    * Returns an up to date score for the bowling game.
    *
    * @return int that is the most recent score
    *         obtained from currentFrame.getAccumulativeScore()
    *
    * */
    public int score()
    {
        if (currentFrame != null)
            return currentFrame.getAccumulativeScore();

        return 0;
    }

    /*
    *
    * Adds a new frame to the bowling game. It will set the next and previous
    * frames where appropriate whilst reassigning currentFrame.
    *
    * */
    private void addFrame()
    {
        boolean isFinalFrame = (frames.size() == TOTAL_FRAMES-1);
        int index = frames.size();

        Frame frame = isFinalFrame ? new FinalFrame(index) : new Frame(index);
        frames.add(frame);

        if (currentFrame != null)
        {
            frame.setPreviousFrame(currentFrame);
            currentFrame.setNextFrame(frame);
        }

        currentFrame = frame;
    }

    /*
    *
    * The end of the game is when the maximum number of frames have been reached,
    * and a minimum of two and maximum of three rolls have occurred in the final
    * frame.
    *
    * @return boolean signalling the end of the game.
    *
    * */
    private boolean isGameOver()
    {
        if (frames.size() == TOTAL_FRAMES &&
            (currentFrame.validNumPinsForNextRoll() == 0))
        {
            return true;
        }

        return false;
    }

    @Override
    public String toString()
    {
        String gameOutput = "";

        for (int i = 0; i < frames.size(); i++)
        {
            Frame frame = frames.get(i);
            gameOutput += frame.toString() + "\t- Score: " +
                    frame.getAccumulativeScore()+ "\n";
        }
        
        return gameOutput;
    }
}