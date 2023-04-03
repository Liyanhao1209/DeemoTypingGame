package com.deemo.typing.GameScreens.Impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deemo.typing.DeemoTypingGame;
import com.deemo.typing.GameResources.Images.Impl.LoadScreenImages;
import com.deemo.typing.GameResources.Textures.LoadScreenTextures;
import com.deemo.typing.GameScreens.SuperClass.GeneralScreen;

public class LoadScreen extends GeneralScreen {

    public LoadScreen(DeemoTypingGame game){
        this.game = game;
        guiCam = new OrthographicCamera(ScreenWidth,ScreenHeight);
        guiCam.position.set(ScreenWidth/2,ScreenHeight/2,0);
//        touchPoint = new Vector3();

        stage = new Stage();
        LoadScreenImages.load(game);
        stage.addActor(LoadScreenImages.badLogicTest);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void draw() {
        clearScreen();
        LoadScreenTextures.draw(game.batch);
        stage.draw();
        /**
         * 相机聚焦中心后，矩形实体是按照中心坐标系放置的。
         * 但是点阵是按照整个窗口左下角的坐标系画的。
         */
    }

    @Override
    public void update() {
        stage.act();
    }

    @Override
    public void render(float delta){
        update();
        draw();
    }



}
