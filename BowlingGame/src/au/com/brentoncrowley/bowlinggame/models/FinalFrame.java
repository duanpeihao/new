package au.com.brentoncrowley.bowlinggame.models;

/**
 * Created by brentoncrowley on 27/04/2014.
 */
public class FinalFrame extends Frame
{

    public FinalFrame(int index)
    {
        super(index);
    }

    @Override
    public boolean isStrike()
    {
        return false;
    }

    @Override
    public boolean isSpare()
    {
        return false;
    }

    @Override
    public int getAccumulativeScore()
    {
        int score = frameScore;

        if (previousFrame != null)
            score += previousFrame.getAccumulativeScore();

        return score;
    }

    @Override
    public int validNumPinsForNextRoll()
    {

        int validNumPins = MAX_PIN_VALUE;

        switch (rolls.size())
        {
            case 1:

                if (!isFirstRollStrike())
                    validNumPins = MAX_PIN_VALUE - rolls.get(0);

                break;
            case 2:

                //XX :10
                //-/ : 10
                if ((isFirstRollStrike() && isSecondRollStrike()) || isFinalFrameSpare())
                    break;

                //X- : difference
                if (isFirstRollStrike() && !isSecondRollStrike())
                {
                    validNumPins = MAX_PIN_VALUE - rolls.get(1);
                    break;
                }

                //-- : 0

            case 3:

                validNumPins = 0;

                break;
        }

        return validNumPins;
    }

    /*
    *
    * If the first roll in the final frame was a strike, then this will return
    * true. Otherwise false.
    *
    * @return boolean
    *
    * */
    public boolean isFirstRollStrike()
    {
        if (rolls.size() > 0 && rolls.get(0) == MAX_PIN_VALUE)
            return true;

        return false;
    }

    /*
    *
    * If the second roll in the final frame was a strike, then this will return
    * true. Otherwise false.
    *
    * @return boolean
    *
    * */
    public boolean isSecondRollStrike()
    {
        if (rolls.size() > 1 &&
            isFirstRollStrike() &&
            rolls.get(1) == MAX_PIN_VALUE)
        {
            return true;
        }

        return false;
    }


    /*
    *
    * If the sum of the first two rolls is equal to two without the first roll
    * being a strike, then this will return true. Otherwise false.
    *
    * @return boolean
    *
    * */
    public boolean isFinalFrameSpare()
    {
        if (rolls.size() < 2 || isFirstRollStrike())
            return false;

        int firstRoll = rolls.get(0).intValue();
        int secondRoll = rolls.get(1).intValue();

        if ((firstRoll + secondRoll) != MAX_PIN_VALUE)
            return false;

        return true;
    }

}
