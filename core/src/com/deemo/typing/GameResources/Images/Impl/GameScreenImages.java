package com.deemo.typing.GameResources.Images.Impl;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deemo.typing.GameResources.Asset;
import com.deemo.typing.GameResources.Images.SuperClass.Images;
import com.deemo.typing.GameResources.Musics.GameScreenMusic;
import com.deemo.typing.GameScreens.Impl.GameScreen;

import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenHeight;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenWidth;

public class GameScreenImages extends Images {

    public static Image gamingConfigIcon;

    public static void load(){
        gamingConfigIcon = DrawAndSetBounds(Asset.icon+"GamingConfigIcon.png",ScreenWidth-50,ScreenHeight-150-50,45,45);
        AddShowGamingConfigListener(gamingConfigIcon);
    }

    private static void AddShowGamingConfigListener(Image img){
        img.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(touchDown(event, x, y, pointer, button)){
                    GameScreenMusic.pause();
                    GameScreen.setState(GameScreen.GAME_PAUSED);
                }
            }
        });
    }

}
