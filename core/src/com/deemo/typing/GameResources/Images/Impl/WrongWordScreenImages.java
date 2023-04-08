package com.deemo.typing.GameResources.Images.Impl;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deemo.typing.DeemoTypingGame;
import com.deemo.typing.GameResources.Asset;
import com.deemo.typing.GameResources.Images.SuperClass.Images;
import com.deemo.typing.GameScreens.Impl.SettleAccountScreen;
import com.deemo.typing.GameScreens.Impl.WrongWordScreen;

import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenWidth;

public class WrongWordScreenImages extends Images {
    public static Image nextPage;
    public static Image previousPage;

    public static void load(DeemoTypingGame game){
        previousPage = DrawAndSetBounds(Asset.icon+"return.png",96,96,96,96);
        AddPreviousPageListener(previousPage,game);
        //最后一页没有去下一页的按钮
        nextPage = DrawAndSetBounds(Asset.icon+"goAhead.png",ScreenWidth-96-96,96,96,96);
        AddNextPageListener(nextPage,game);

    }

    private static void AddPreviousPageListener(Image img,DeemoTypingGame game){
        img.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(WrongWordScreen.pageIndex==0){
                    game.setScreen(new SettleAccountScreen(game,SettleAccountScreen.getGameName()));
                }
                else{
                    game.setScreen(new WrongWordScreen(game,--WrongWordScreen.pageIndex));
                }
            }
        });
    }

    private static void AddNextPageListener(Image img,DeemoTypingGame game){
        img.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new WrongWordScreen(game,++WrongWordScreen.pageIndex));
            }
        });
    }
}
