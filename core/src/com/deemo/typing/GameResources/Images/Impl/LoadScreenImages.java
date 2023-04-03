package com.deemo.typing.GameResources.Images.Impl;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deemo.typing.DeemoTypingGame;
import com.deemo.typing.GameResources.Asset;
import com.deemo.typing.GameResources.Images.SuperClass.Images;
import com.deemo.typing.GameScreens.Impl.SelectGameScreen;

import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenHeight;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenWidth;

/**
 * 专门用来获取各Screen中Stage所需的Image（一般来说都是按钮）
 */
public class LoadScreenImages extends Images {
    public static Image badLogicTest;

    public static void load(DeemoTypingGame game) {
        badLogicTest = DrawAndSetBounds(Asset.title+"TOUCHTOSTART.png",ScreenWidth/2-1766/4/2,ScreenHeight/4-184/4/2,1766/4,184/4);
        AddNextScreenListener(badLogicTest,game);
    }


    private static void AddNextScreenListener(Image img, DeemoTypingGame game){
        img.addListener(new InputListener(){
            /**
             *  这个x,y是相对于整个图片左下角的
             */
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

}
