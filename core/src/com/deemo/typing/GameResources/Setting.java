package com.deemo.typing.GameResources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.text.DecimalFormat;

import static com.deemo.typing.GameScreens.Impl.SelectGameScreen.gameIndexPointer;
import static com.deemo.typing.GameScreens.Impl.SelectGameScreen.scores;

public class Setting {
    	public final static String file = ".score";
    public static void saveScores(float score){
        DecimalFormat formatter = new DecimalFormat("##0.00");
        scores[gameIndexPointer]=formatter.format(score);
        FileHandle scoreFile = Gdx.files.external(file);
        String scoreString="";
        for(int i=0;i<scores.length;i++) {
            scoreString += scores[i] + "\n";
        }
        scoreFile.writeString(scoreString, false);
    }
}
