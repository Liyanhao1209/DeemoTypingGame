package com.deemo.typing.GameResources.Images.Impl;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deemo.typing.DeemoTypingGame;
import com.deemo.typing.GameResources.Asset;
import com.deemo.typing.GameResources.Images.SuperClass.Images;
import com.deemo.typing.GameScreens.Impl.GameScreen;
import com.deemo.typing.GameScreens.Impl.SelectGameScreen;
import com.deemo.typing.GameScreens.Impl.SettleAccountScreen;

public class SettleAccountScreenImages extends Images {
    public static Image next;
    public static Image retry;

    public static void load(DeemoTypingGame game){
        next = DrawAndSetBounds(Asset.title+"next.png",1920/3-805/5/2,100-190/5/2,805/5,190/5);
        AddNextListener(next,game);
        retry = DrawAndSetBounds(Asset.title+"retry.png",1920*2/3-829/5/2,100-228/5/2,829/5,228/5);
        AddRetryListener(retry,game);
    }

    private static void AddNextListener(Image img, DeemoTypingGame game){
        img.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(touchDown(event, x, y, pointer, button)){
                    game.setScreen(new SelectGameScreen(game));
                }
            }
        });
    }

    private static void AddRetryListener(Image img,DeemoTypingGame game){
        img.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(touchDown(event, x, y, pointer, button)){
                    game.setScreen(new GameScreen(game, SettleAccountScreen.getGameName()));
                }
            }
        });
    }

}
