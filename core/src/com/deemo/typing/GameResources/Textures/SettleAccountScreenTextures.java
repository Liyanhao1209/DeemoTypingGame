package com.deemo.typing.GameResources.Textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deemo.typing.GameResources.Asset;
import com.deemo.typing.GameScreens.Impl.SelectGameScreen;
import com.deemo.typing.GameScreens.Impl.SettleAccountScreen;
import com.deemo.typing.StateMachine.Impl.World;

import java.text.DecimalFormat;

import static com.deemo.typing.GameResources.Textures.SelectGameScreenTextures.gamePortrait;
import static com.deemo.typing.GameScreens.Impl.SelectGameScreen.gameIndexPointer;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenHeight;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenWidth;

public class SettleAccountScreenTextures {

    public static Texture SettleAccountScreenBackground;
    public static Texture Charming;
    public static Texture maxCombo;
    public static Texture NewRecord;
    public static Texture pointRound;
    public static Texture easyBar;
    public static Texture normalBar;
    public static Texture hardBar;
    public static Texture bottomShadow;

    public static void load(){
        //这个时候选择游戏的背景图肯定已经加载好了，直接用
        SettleAccountScreenBackground = SelectGameScreenTextures.SelectGameScreenBackGround;
        Charming = new Texture(Asset.title+"charming.png");
        maxCombo = new Texture(Asset.title+"maxCombo.png");
        NewRecord = new Texture(Asset.title+"NewRecord.png");
        pointRound = new Texture(Asset.icon+"pointRound.png");
        easyBar = new Texture(Asset.pic+"easyBar.png");
        normalBar = new Texture(Asset.pic+"normalBar.png");
        hardBar = new Texture(Asset.pic+"hardBar.png");
        bottomShadow = new Texture(Asset.pic+"bottomShadow.png");
    }

    public static void draw(SpriteBatch batch, BitmapFont font){
        font.getData().markupEnabled=true;
        batch.begin();
        batch.draw(SettleAccountScreenBackground,0,0,ScreenWidth,ScreenHeight);
        if(gameIndexPointer>=gamePortrait.length){
            batch.draw(gamePortrait[0],ScreenWidth/2-1360/8,ScreenHeight/2-1080/2,1360,1080);
        }
        else{
            batch.draw(gamePortrait[gameIndexPointer],ScreenWidth/2-1360/8,ScreenHeight/2-1080/2,1360,1080);
        }
        batch.draw(bottomShadow,0,0,ScreenWidth,215);//7155 793
        //这个参数好,-125正好卡在标题中间了
        batch.draw(pointRound,ScreenWidth/4-1254/4/2-125,ScreenHeight/2-1263/4/2,1254/4,1263/4);
        //写入charming数和totalKey数量
        font.getData().setScale(2.85f);
        font.draw(batch,"[#000000]"+World.charmingNumber+"[]",ScreenWidth/4-1254/4/2-100,ScreenHeight/2+30,1200/4/2,1,false);
        font.draw(batch,"[#F8F8FF]"+World.keyTotalNumbers+"[]",ScreenWidth/4-1254/4/2,ScreenHeight/2,1200/4/2,1,false);
        batch.draw(Charming,ScreenWidth/4-824/4/2-125,ScreenHeight/2+1263/4/2,824/4,157/4);
        float barX = 0f;
        float barY = ScreenHeight/2+1263/4/2+157/4+100;
        float barWidth = 2750/4;
        float barHeight = 405/4;
        switch (SelectGameScreen.Current_Level){
            case SelectGameScreen.Easy_Level:
                batch.draw(easyBar,barX,barY,barWidth,barHeight);
                break;
            case SelectGameScreen.Normal_Level:
                batch.draw(normalBar,barX,barY,barWidth,barHeight);
                break;
            case SelectGameScreen.Hard_Level:
                batch.draw(hardBar,barX,barY,barWidth,barHeight);
                break;
        }
        font.draw(batch,"[#000000]Check Wrong Words[]",ScreenWidth/4-1254/4/2-100,barY+200,240,1,false);
        font.getData().setScale(3.5f);
        font.draw(batch,"[#F8F8FF]"+SettleAccountScreen.getGameName()+"[]",barX+100,barY+85,240,1,false);
        batch.draw(maxCombo,ScreenWidth/4-1100/4-125,ScreenHeight/2-1263/4/2-162/4,1100/4,162/4);
        //写入maxCombo数
        font.draw(batch,"[#000000]"+Math.max(World.currentComboNumber,World.maxComboNumber)+"[]",ScreenWidth/4-100,ScreenHeight/2-1263/4/2,240,1,false);
        font.getData().setScale(3.5f);
        font.draw(batch,KeepTwoDecimalPlaces(World.score),ScreenWidth/4-240/2-125,ScreenHeight/2-1263/4/2-162/4-30,240,1,false);
        batch.end();
    }

    private static String KeepTwoDecimalPlaces(float score){
        DecimalFormat formatter = new DecimalFormat("##0.00");
        return "[#000000]"+formatter.format(score)+"[]";//颜色变黑
    }
}
