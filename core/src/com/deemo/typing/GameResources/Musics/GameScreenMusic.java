package com.deemo.typing.GameResources.Musics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.GdxRuntimeException;

import static com.deemo.typing.GameResources.Asset.music;
import static com.deemo.typing.GameScreens.Impl.SelectGameScreen.gameIndexPointer;
import static com.deemo.typing.GameScreens.Impl.SelectGameScreen.games;

public class GameScreenMusic {
    public static final int gameMusicNumber;
    static{
        gameMusicNumber=4;
    }
    public static Music[] gameMusic;

    public static void load(){
        gameMusic = new Music[gameMusicNumber];
        int index=0;
        while(index<gameMusicNumber){
            String filepathOgg = music+games[index]+".ogg";
            String filepathMp3 = music+games[index]+".mp3";

            try{
                Music musicOgg = Gdx.audio.newMusic(Gdx.files.internal((filepathOgg)));
                gameMusic[index++] = musicOgg;
            }catch (GdxRuntimeException grtE){
                gameMusic[index++] = Gdx.audio.newMusic(Gdx.files.internal(filepathMp3));
            }

        }
    }

    public static void play(){
        if(gameIndexPointer>=gameMusic.length){
            gameMusic[0].play();
        }
        else{
            gameMusic[gameIndexPointer].play();
        }

    }

    public static void stop(){
        if(gameIndexPointer>=gameMusic.length){
            gameMusic[0].stop();
        }
        else{
            gameMusic[gameIndexPointer].stop();
        }

    }

    public static Music getCurrentMusic(){
        if(gameIndexPointer>=GameScreenMusic.gameMusic.length){
            return gameMusic[0];
        }
        return gameMusic[gameIndexPointer];
    }

    public static void pause(){
        if(gameIndexPointer>=GameScreenMusic.gameMusic.length){
            gameMusic[0].pause();
        }
        else{
            gameMusic[gameIndexPointer].pause();
        }
    }
}
