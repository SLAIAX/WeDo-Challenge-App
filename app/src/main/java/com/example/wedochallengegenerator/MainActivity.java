package com.example.wedochallengegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean looped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setActionBar(findViewById(R.id.toolbar));
        TextView challenge1 = findViewById(R.id.challenge1);
        Button generate = findViewById(R.id.generate);
        generate.setOnClickListener(view -> challenge1.setText(generateChallenge()));
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




}
