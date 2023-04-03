package com.deemo.typing.GameScreens.Impl;

import com.badlogic.gdx.graphics.Texture;
import com.deemo.typing.DeemoTypingGame;
import com.deemo.typing.GameResources.Asset;
import com.deemo.typing.GameScreens.SuperClass.GeneralScreen;

/**
 * 加载资源时提示一个加载页面
 * TODO:用new 资源对象()的方式来加载资源会阻塞其他线程,也就是加载页背景画不上去,改用assetManager异步加载资源
 */
public class LoadHintScreen extends GeneralScreen {

    private static boolean hasBeenLoad;

    public LoadHintScreen(DeemoTypingGame game){
        this.game = game;
        hasBeenLoad = false;
    }

    @Override
    public void draw() {
        clearScreen();
        game.batch.begin();
        game.batch.draw(new Texture("badlogic.jpg"),0,0,256,256);
        game.batch.end();
    }

    @Override
    public void update() {
        if(!hasBeenLoad){
            hasBeenLoad= true;
            Asset.load();
            game.setScreen(new LoadScreen(game));
        }

    }

    @Override
    public void render (float delta) {
        draw();
        update();
    }
}
