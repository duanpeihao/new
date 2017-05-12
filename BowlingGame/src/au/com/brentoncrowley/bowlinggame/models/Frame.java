package au.com.brentoncrowley.bowlinggame.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by brentoncrowley on 24/04/2014.
 */
public class Frame
{
    public static final int MAX_PIN_VALUE = 10;
    public static final int MIN_PIN_VALUE = 0;

    protected int index;
    protected List<Integer> rolls;
    protected Frame nextFrame;
    protected Frame previousFrame;
    protected int frameScore;

    public Frame(int index)
    {
        this.index = index;

        init();
    }

    private void init()
    {
        this.rolls = new ArrayList<Integer>();
        this.frameScore = 0;
    }

    /*
    * Add the number of pins knocked over to the frame. The frameScore is then
    * incremented as a result.
    *
    * */
    public void addRoll(int numOfPins)
    {
        rolls.add(numOfPins);
        frameScore += numOfPins;
    }

    /*
    * Return a boolean as to whether or not the frame is a spare. A frame is a
    * spare when the sum of the two rolls equal the maximum pin value, which
    * is 10.
    *
    * e.g. [5,5], [4,6], [0, 10]
    *
    * @return boolean
    * */
    public boolean isSpare()
    {
        if (rolls.size() != 2)
            return false;

        int firstRoll = rolls.get(0).intValue();
        int secondRoll = rolls.get(1).intValue();

        if ((firstRoll + secondRoll) != MAX_PIN_VALUE)
            return false;

        return true;
    }
    /*
    * Return a boolean as to whether or not the frame is a strike. A frame is a
    * strike when the first roll is the value of the maximum pin value, which
    * is 10.
    *
    * e.g. [10]
    *
    * @return boolean
    * */
    public boolean isStrike()
    {
        if (rolls.size() != 1)
            return false;

        int firstRoll = rolls.get(0).intValue();

        if (firstRoll != MAX_PIN_VALUE)
            return false;

        return true;
    }

    /*
    * Returns the sum total of each frame up until this frame with the added
    * bonus points for strikes and spares.
    *
    * The initial value is the frame score, or in other words, the number
    * of pins knocked down in the frame. The bonus is calculated if future
    * frames exist. This total is then added to the previous frame's
    * accumulative total.
    *
    * @return int accumulativeScore
    * */
    public int getAccumulativeScore()
    {
        int score = frameScore;

        // If nextFrame exists the bonus needs to be obtained
        if ((isSpare() || isStrike()) &&
             nextFrame != null)
        {
            //strike bonus calculation
            if (isStrike())
            {
                score += nextFrame.getStrikeBonus();
                // get the next two values from nextFrame
//                if (nextFrame.rolls.size() > 1)
//                {
//                    score += nextFrame.rolls.get(0);
//                    score += nextFrame.rolls.get(1);
//                }
//                else if (nextFrame.rolls.size() == 1) // only one value in nextFrame
//                {
//                    score += nextFrame.rolls.get(0);
//
//                    // check ahead for another frame, as the next one
//                    // was probably a strike
//                    Frame twothFrame = nextFrame.getNextFrame();
//                    if (twothFrame != null && twothFrame.rolls.size() > 0)
//                        score += twothFrame.rolls.get(0);
//                }
            }
            else if (isSpare())
                score += nextFrame.getSpareBonus();
        }

        if (previousFrame != null)
            score += previousFrame.getAccumulativeScore();

        return score;
    }

    /*
    * Return a boolean as to whether or not the supplied number of pins is valid
    * for this frame. A roll is valid if it is between 0-10, does not exceed
    * the remainder of pins left in a frame and the valid number of pins
    * for the next roll is not 0.
    *
    * @return boolean
    * */
    public boolean isRollValid(int numOfPins)
    {
        // Any kind of roll must yield a value between 0-10
        if (numOfPins < MIN_PIN_VALUE || numOfPins > MAX_PIN_VALUE)
            return false;

        if (validNumPinsForNextRoll() == 0)
            return false;

        if (numOfPins > validNumPinsForNextRoll())
            return false;

        return true;
    }

    /*
    *
    * Determines the numbers of pins that any current roll can have.
    * For a standard frame this always begins with 10. After the first roll, the
    * value can change.
    *
    * The valid number of pins for a roll must not exceed the remainder of pins
    * left by the result of any previous roll.
    *
    * A standard frame can have no more than two rolls, so if two rolls exists,
    * there is no valid number of pins for the next roll of this frame.
    *
    * This method is crucial in determining the logic for generating new frames.
    *
    * @return int the number of valid pins for the next roll
    * */
    public int validNumPinsForNextRoll()
    {
        int validNumPins = MAX_PIN_VALUE;

        switch (rolls.size())
        {
            case 1:
                validNumPins -= rolls.get(0);
                break;
            case 2:
                validNumPins = 0;
                break;
        }

        return validNumPins;
    }

    public int getStrikeBonus()
    {
        int strikeBonus = 0;

        switch (rolls.size())
        {
            case 2:
            case 1:
                strikeBonus += frameScore;

                if (isStrike() &&
                        nextFrame != null &&
                        nextFrame.getRolls().size() > 0)
                {
                    strikeBonus += nextFrame.getRolls().get(0).intValue();
                }
        }

        return strikeBonus;
    }

    public int getSpareBonus()
    {
        int spareBonus = 0;

        if (rolls.size() > 0)
            spareBonus+= rolls.get(0).intValue();

        return spareBonus;
    }

    public List<Integer> getRolls()
    {
        return rolls;
    }

    public void setNextFrame(Frame nextFrame)
    {
        this.nextFrame = nextFrame;
    }

    public void setPreviousFrame(Frame previousFrame)
    {
        this.previousFrame = previousFrame;
    }

    public Frame getNextFrame()
    {
        return nextFrame;
    }

    @Override
    public String toString()
    {
        return "F" + (index + 1) + "\t " + Arrays.deepToString(rolls.toArray());
    }
}
