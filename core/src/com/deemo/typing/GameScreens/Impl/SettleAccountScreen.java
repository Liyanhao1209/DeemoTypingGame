package com.deemo.typing.GameScreens.Impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deemo.typing.DeemoTypingGame;
import com.deemo.typing.GameResources.Images.Impl.SettleAccountScreenImages;
import com.deemo.typing.GameResources.Textures.SettleAccountScreenTextures;
import com.deemo.typing.GameScreens.SuperClass.GeneralScreen;
import com.deemo.typing.StateMachine.Impl.World;

public class SettleAccountScreen extends GeneralScreen {
    private static  String gameName;
    private Rectangle checkWrongWords;
    //一页五个错误的单词，算出来总页数
    public static int maxWrongWordPage;
    static{
        maxWrongWordPage = Math.round((float) World.wrongWords.size()/5)-1;
    }

    public SettleAccountScreen(DeemoTypingGame game,String gameName){
        this.game = game;
        guiCam = new OrthographicCamera(ScreenWidth,ScreenHeight);
        guiCam.position.set(ScreenWidth/2,ScreenHeight/2,0);
        touchPoint = new Vector3();

        SettleAccountScreen.gameName =gameName;

        checkWrongWords = new Rectangle(ScreenWidth/4-1254/4/2-100,ScreenHeight/2+1263/4/2+157/4+100+200-30,240,30);

        stage = new Stage();
        SettleAccountScreenImages.load(game);
        stage.addActor(SettleAccountScreenImages.next);
        stage.addActor(SettleAccountScreenImages.retry);
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void draw() {
        clearScreen();
        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);
        SettleAccountScreenTextures.draw(game.batch,game.font);
        stage.draw();
    }

    @Override
    public void update() {
        stage.act();
        if(Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if(checkWrongWords.contains(touchPoint.x,touchPoint.y)){
                game.setScreen(new WrongWordScreen(game,0));
            }
        }
    }

    @Override
    public void render (float delta) {
        update();
        draw();
    }

    public static  String getGameName() {
        return gameName;
    }
}
