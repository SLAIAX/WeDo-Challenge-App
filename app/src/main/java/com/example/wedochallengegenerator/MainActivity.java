package com.example.wedochallengegenerator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean looped = false;
    private TextView challenge1;
    private TextView challenge2;
    private TextView challenge3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setActionBar(findViewById(R.id.toolbar));

        challenge1 = findViewById(R.id.challenge1);
        challenge2 = findViewById(R.id.challenge2);
        challenge3 = findViewById(R.id.challenge3);
        Button generate = findViewById(R.id.generate);
        generate.setOnClickListener(view -> generateLevelOneChallenges());

    }

    /*
     * Function to randomly choose an action block out of all possible action blocks
     * @return Block - the chosen block object
     */
    private Block chooseBlock()
    {
        Block obj;                                                    //Utilizes polymorphism
        int num = Block.randomNumber(1, (Block.get_totalBlocks() + 1));     //Generates a random number between 1 and the maximum number of action Blocks
        BlockNames test = BlockNames.getBlock(num);
        switch (test)
        {
            case SpinMotor:
                obj = new SpinMotorBlock();
                break;
            case LightSmartHub:
                obj = new LightSmartHubBlock();
                break;
            case PowerMotor:
                obj = new PowerMotorBlock();
                break;
            case SoundsBlock:
                obj = new SoundBlock();
                break;
            case DisplayBlock:
                obj = new DisplayBlock();
                break;
            default:
                obj = new SpinMotorBlock();
                break;
        }
        return obj;
    }

    private String generateChallenge(){
        String challenge = "Challenge 1: ";
        Block obj = new StartBlock();
        challenge += obj.generateSegment();
        return (challenge + generateEasyChallenge());
    }

    /*
     * Function to generate an easy challenge which will comprise of 1 blocks action
     * and the chance of repeating the whole thing
     * @return string - the generated challenge
     */
    private String generateEasyChallenge()
    {
        looped = false;
        String challenge;
        Block obj = chooseBlock();
        challenge = obj.generateSegment();
        looped = obj.iszLooped();

        //challenge += "." + repeat();
        return challenge;
    }

    /*
     * Function to generate a medium challenge which will comprise of 2 block segments
     * which will be of different block types and has the chance of repeating the whole thing
     * @return string - the generated challenge
     */
    private String generateMediumChallenge()
    {
        looped = false;
        String challenge;
        Block obj = chooseBlock();
        challenge = obj.generateSegment();
        looped = obj.zLooped;

        Block objNew;
        do
        {
            objNew = chooseBlock();
        } while (obj.zName == objNew.zName);

        challenge += " and then " + objNew.generateSegment();

        if (!looped)
        {
            looped = objNew.zLooped;
        }


        challenge += "." + repeat();

        return challenge;
    }

    /*
     * Function to generate a medium challenge which will comprise of 3 block segments
     * which will be of at least 2 different block types with the chance of repeating the whole thing
     * @return string - the generated challenge
     */
    private String generateHardChallenge()
    {
        looped = false;
        String challenge;
        Block obj = chooseBlock();
        challenge = obj.generateSegment();
        looped = obj.zLooped;

        Block objNew;
        do
        {
            objNew = chooseBlock();
        } while (obj.zName == objNew.zName);

        challenge += " and then " + objNew.generateSegment();

        if(!looped)
        {
            looped = objNew.zLooped;
        }

        do
        {
            obj = chooseBlock();
        } while (obj.zName == objNew.zName);

        challenge += " and finally " + obj.generateSegment();
        if (!looped)
        {
            looped = obj.zLooped;
        }

        challenge += "." + repeat();

        return challenge;
    }

    /*
     * Function to decide whether the challenge should be looped
     * 1 in 5 chance of it being looped
     * @return the generated string if any
     */
    private String repeat()
    {
        String toReturn = "";
        if (looped == false)
        {
            int num = Block.randomNumber(1, 6);                             //Generate a number between 0 and 6
            if (num == 5)                                                   //If it's a 5, repeat the code segment
            {
                num = Block.randomNumber(2, 6);                             //Generate a new random number to specify how many times to repeat it
                toReturn = " Repeat this ";
                if (num == 5)                                               //If it's a 5 generated, it will be repeated an infinite amount of times
                {
                    toReturn += "an infinite amount of times.";
                }
                else                                                        //Else it will repeat the number of times according to the generated number
                {
                    toReturn += num + " times using the loop block.";
                }
            }
        }
        return toReturn;
    }

    /*
     * Function to generate Sensor specific challenges
     */
    public void generateLevelTwoChallenges()
    {
        int randomNumber = Block.randomNumber(1, 4);
        SensorNames _sensor = SensorNames.getBlock(randomNumber);
        Block obj;
        int num;
        switch (_sensor)
        {
            case Motion:
                challenge1.setText("Challenge 1: When the model is tilted forward, " + generateEasyChallenge());

                obj = chooseBlock();
                challenge2.setText("Challenge 2: When the model is tilted backward, " + generateMediumChallenge());

                num = Block.randomNumber(1, 3);
                obj = chooseBlock();
                if (num == 1)
                {
                    challenge3.setText("Challenge 3: When the model is tilted left, " + generateHardChallenge());
                }
                else
                {
                    challenge3.setText("Challenge 3: When the model is tilted right, " + generateHardChallenge());
                }
                break;
            case Distance:
                int dist1, dist2, dist3;
                dist1 = Block.randomNumber(1, 16);                              //Generate a random distance between 1 and 15 (the maximum detectable distance)
                challenge1.setText("Challenge 1: When the distance is " + dist1 + " centimenters away, " + generateEasyChallenge());

                do
                {
                    dist2 = Block.randomNumber(1, 16);
                } while (dist2 == dist1);
                challenge2.setText("Challenge 2: When the distance is " + dist2 + " centimenters away, " + generateMediumChallenge());

                do
                {
                    dist3 = Block.randomNumber(1, 16);
                } while (dist3 == dist1 || dist3 == dist2);
                challenge3.setText("Challenge 3: When the distance is " + dist3 + " centimenters away, " + generateHardChallenge());

                break;
            case Sound:
                int vol1, vol2, vol3;
                vol1 = Block.randomNumber(1, 16);                              //Generate a random distance between 1 and 15 (the maximum detectable distance)
                challenge1.setText("Challenge 1: When the volume is " + vol1 + ", " + generateEasyChallenge());

                do
                {
                    vol2 = Block.randomNumber(1, 16);
                } while (vol2 == vol1);
                challenge2.setText("Challenge 2: When the volume is " + vol2 + ", " + generateMediumChallenge());

                do
                {
                    vol3 = Block.randomNumber(1, 16);
                } while (vol3 == vol1 || vol3 == vol2);
                challenge3.setText("Challenge 3: When the volume is " + vol3 + ", " + generateHardChallenge());
                break;
        }

    }

    /*
     * Function to generate Level one (broad) challenges
     */
    public void generateLevelOneChallenges()
    {
        try {
            String challenge = "Challenge 1: ";
            Block obj = new StartBlock();
            challenge += obj.generateSegment();
            challenge1.setText(challenge + generateEasyChallenge());

            obj = new StartBlock();
            challenge = "Challenge 2: " + obj.generateSegment();
            challenge2.setText(challenge + generateMediumChallenge());

            obj = new StartBlock();
            challenge = "Challenge 3: " + obj.generateSegment();
            challenge3.setText(challenge + generateHardChallenge());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
