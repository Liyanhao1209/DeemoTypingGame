package com.deemo.typing.GameScreens.SuperClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deemo.typing.DeemoTypingGame;

public abstract class GeneralScreen extends ScreenAdapter {
    protected DeemoTypingGame game;
    protected Vector3 touchPoint;
    protected OrthographicCamera guiCam;

    protected Stage stage;

    public static final int ScreenWidth = 1920;
    public static final int ScreenHeight = 1080;
    public static final int topLineWidth = ScreenWidth/4;

    public abstract void draw();

    public abstract  void update();

    /**
     * glClearColor设置颜色
     * glClear清除缓冲
     */
    public void clearScreen(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }



}
