package com.deemo.typing.GameResources.Textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deemo.typing.GameResources.Asset;

import static com.deemo.typing.GameResources.Asset.pic;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenHeight;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenWidth;

public class GameScreenTextures {
    public static Texture YellowKey;
    public static Texture WhiteKey;
    public static Texture gamePageBg;
    public static Texture Resume;
    public static Texture Retry;
    public static Texture Songs;
    public static Texture Home;

    public static  void load(){
        YellowKey = new Texture(pic+"YellowKey.png");
        WhiteKey = new Texture(pic+"WhiteKey.png");
        gamePageBg = new Texture(pic+"gameBg.png");
        Resume = new Texture(Asset.icon+"Resume.png");
        Retry = new Texture(Asset.icon+"Retry.png");
        Songs = new Texture(Asset.icon+"Songs.png");
        Home = new Texture(Asset.icon+"Home.png");
    }

    public static void draw(){

    }

    public static void showGamingConfigs(SpriteBatch batch){
        float offset = (ScreenWidth * 2 / 3 - 1036/6 * 4) / 3;
        float configsX = ScreenWidth/6;
        float configsY = ScreenHeight/2-1268/6/2;
        batch.draw(Resume,configsX,configsY,1036/6,1268/6);configsX+=1036/6+offset;
        batch.draw(Retry,configsX,configsY,1036/6,1268/6);configsX+=1036/6+offset;
        batch.draw(Songs,configsX,configsY,1036/6,1268/6);configsX+=1036/6+offset;
        batch.draw(Home,configsX,configsY,1036/6,1268/6);
    }
}
