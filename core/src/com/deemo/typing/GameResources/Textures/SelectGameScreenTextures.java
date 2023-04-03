package com.deemo.typing.GameResources.Textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deemo.typing.GameResources.Asset;
import com.deemo.typing.GameScreens.Impl.SelectGameScreen;

import static com.deemo.typing.GameResources.Asset.*;
import static com.deemo.typing.GameScreens.Impl.SelectGameScreen.*;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenHeight;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenWidth;

public class SelectGameScreenTextures {
    public static final int gamePortraitNumber;
    static{
        gamePortraitNumber=4;
    }
    public static Texture EasyMusic;
    public static Texture NormalMusic;
    public static Texture HardMusic;
    public static Texture SelectGameScreenBackGround;
    public static Texture[] gamePortrait;
    public static Texture GoBack;
    public static Texture Config;
    public static Texture ConfigCancel;
    public static Texture GameStart;

    public static void load(){
        EasyMusic = new Texture(pic+"EasyMusic.png");//670*170
        NormalMusic = new Texture(pic+"NormalMusic.png");
        HardMusic = new Texture(pic+"HardMusic.png");
        GoBack = new Texture(icon+"return.png");
        SelectGameScreenBackGround = new Texture(pic+"SelectGameScreenBackGround.png");
        Config = new Texture(pic+"configPage.png");
        ConfigCancel = new Texture(icon+"return.png");
        GameStart = new Texture(title+"START.png");
        loadPortrait();
    }

    private static void loadPortrait(){
        gamePortrait = new Texture[gamePortraitNumber];
        int index=0;
        while(index<gamePortraitNumber){
            String filePath = pic+ Asset.gamePortrait + games[index] + ".png";

            if(new Texture(filePath)==null){
                continue;
            }
            gamePortrait[index++] = new Texture(filePath);
        }
    }

    public static void draw(SpriteBatch batch, int Current_Level, BitmapFont font){
        batch.begin();
        batch.disableBlending();
        //让背景先画，不然层叠掉了
        batch.draw(SelectGameScreenBackGround,0,0);
        batch.enableBlending();
        switch (Current_Level){
            case Easy_Level:
                batch.draw(EasyMusic,ScreenWidth/4-670/2,ScreenHeight/2-170/2,670,170);
                break;
            case Normal_Level:
                batch.draw(NormalMusic,ScreenWidth/4-670/2,ScreenHeight/2-170/2,670,170);
                break;
            case Hard_Level:
                batch.draw(HardMusic,ScreenWidth/4-670/2,ScreenHeight/2-170/2,670,170);
                break;
        }
        //如果是用户导入的文件，立绘用第一张Evolution Era的
        if(gameIndexPointer>=gamePortrait.length){
            batch.draw(gamePortrait[0],ScreenWidth/2-1360/8,ScreenHeight/2-1080/2,1360,1080);
        }
        else{
            batch.draw(gamePortrait[gameIndexPointer],ScreenWidth/2-1360/8,ScreenHeight/2-1080/2,1360,1080);
        }
        batch.draw(GoBack,0+96/2,0+96/2,96,96);
        font.getData().markupEnabled=true;
        font.getData().setScale(2.15f);
        font.draw(batch,"[#000000]Only .txt and English accepted![]",96*4.5f,100,240,1,false);
        batch.end();
    }

    public static void showConfig(SpriteBatch batch){
        batch.begin();
        batch.enableBlending();
        batch.draw(Config,0,0,1920,1080);
        batch.end();
    }

    public static void showKeySpeed(BitmapFont font,SpriteBatch batch){
        batch.begin();
        font.getData().setScale(3.0f);
        font.draw(batch,Current_Key_Speed+"",ScreenWidth/2-240/2,ScreenHeight/2+125,240,1,false);
        batch.end();
    }

    public static void showConfigCancel(SpriteBatch batch){
        batch.begin();
        batch.enableBlending();
        batch.draw(ConfigCancel,0+96/2,0+96/2,96,96);
        batch.end();
    }

    public static void showGameStart(SpriteBatch batch){
        batch.begin();
        batch.enableBlending();
        batch.draw(GameStart,ScreenWidth/2-256/2,ScreenHeight/4.5f-40/2,256,40);
        batch.end();
    }

}
