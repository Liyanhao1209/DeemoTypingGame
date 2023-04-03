package com.deemo.typing.GameResources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.deemo.typing.GameResources.Musics.GameScreenMusic;
import com.deemo.typing.GameResources.Texts.SelectGameScreenTexts;
import com.deemo.typing.GameResources.Textures.GameScreenTextures;
import com.deemo.typing.GameResources.Textures.LoadScreenTextures;
import com.deemo.typing.GameResources.Textures.SelectGameScreenTextures;
import com.deemo.typing.GameResources.Textures.SettleAccountScreenTextures;
import com.deemo.typing.Util.WordCollector;


public class Asset {
    public static final String assets = "assets/";
    public static final String main = "main/";
    public static final String icon = "icon/";
    public static final String pic = "picture/";
    public static final String gamePortrait = "gamePortrait/";
    public static final String text = "text/";
    public static final String title = "title/";
    public static final String music = "Music/";
    public static final String RelativePathFromSelectGameScreenImagesAddTxtInListenerToProjectAssetText;
    static{
        StringBuilder s= new StringBuilder();
        s.append("assets\\text\\");
        RelativePathFromSelectGameScreenImagesAddTxtInListenerToProjectAssetText = s.toString();
    }

    public static String[] loadText(String file){
        FileHandle internal = Gdx.files.internal(text + file);
        return WordCollector.collectWord(internal.readString());
    }

    //加载图片、文本、音乐资源
    public static void load(){
        loadTexts();
        loadTextures();
        loadMusic();
    }

    public static void loadTexts(){
        SelectGameScreenTexts.load();
    }

    public static void loadTextures(){
        LoadScreenTextures.load();
        SelectGameScreenTextures.load();
        GameScreenTextures.load();
        SettleAccountScreenTextures.load();
    }

    public static void loadMusic(){
        GameScreenMusic.load();
    }

}

