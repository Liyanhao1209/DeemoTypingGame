package com.deemo.typing.GameScreens.Impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deemo.typing.DeemoTypingGame;
import com.deemo.typing.GameResources.Images.Impl.GameScreenImages;
import com.deemo.typing.GameResources.Musics.GameScreenMusic;
import com.deemo.typing.GameResources.Setting;
import com.deemo.typing.GameResources.Textures.GameScreenTextures;
import com.deemo.typing.GameScreens.SuperClass.GeneralScreen;
import com.deemo.typing.StateMachine.Impl.World;
import com.deemo.typing.StateMachine.Impl.WorldListener;
import com.deemo.typing.StateMachine.Impl.WorldRenderer;


public class GameScreen extends GeneralScreen {
    public static final int GAME_RUNNING = 0;

    public static final int GAME_PAUSED = 1;

    public static final int GAME_OVER = 2;

    private static int state;

    private static World world;

    private static WorldRenderer renderer;

    private static WorldListener listener;

    private static InputMultiplexer inputMultiplexer;

    private static float deltaTime;

    private final String gameName;

    private Rectangle returnToSelectGameScreenBound;
    private Rectangle goToSettleAccountScreenBound;
    private Rectangle resumeBound;
    private Rectangle retryBound;
    private Rectangle songsBound;
    private Rectangle homeBound;

    public GameScreen(DeemoTypingGame game,String GameName){
        this.game = game;
        this.gameName = GameName;

        guiCam = new OrthographicCamera(ScreenWidth,ScreenHeight);
        guiCam.position.set(ScreenWidth/2,ScreenHeight/2,0);
        touchPoint = new Vector3();

        listener = new WorldListener();
        inputMultiplexer = new InputMultiplexer();
        world = new World(listener,gameName);
        renderer = new WorldRenderer(world,game.batch);

        returnToSelectGameScreenBound = new Rectangle(0,0,64,64);
        goToSettleAccountScreenBound = new Rectangle(1920-64,0,64,64);

        float offset = (ScreenWidth * 2 / 3 - 1036/6 * 4) / 3;
        float configsX = ScreenWidth/6;
        float configsY = ScreenHeight/2-1268/6/2;
        resumeBound = new Rectangle(configsX,configsY,1036/6,1268/6);configsX+=1036/6+offset;
        retryBound = new Rectangle(configsX,configsY,1036/6,1268/6);configsX+=1036/6+offset;
        songsBound = new Rectangle(configsX,configsY,1036/6,1268/6);configsX+=1036/6+offset;
        homeBound = new Rectangle(configsX,configsY,1036/6,1268/6);

        state = GAME_RUNNING;

        stage = new Stage();
        GameScreenImages.load();
        stage.addActor(GameScreenImages.gamingConfigIcon);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        GameScreenMusic.play();

    }

    /**
     * present系列方法是展示在对应state下的静态的资源内容
     * 所有动态需要修改的内容一律放到了WorldRenderer中
     */
    @Override
    public void draw() {
        clearScreen();

        renderer.render();
        stage.draw();

        //这两行可以把坐标改了，我也不知道为什么（坐标改成原点为左下角）
        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);
        game.batch.enableBlending();
        game.batch.begin();
        switch (state){
            case GAME_RUNNING:
                presentRunning();
                break;
            case GAME_PAUSED:
                presentPaused();
                break;
            case GAME_OVER:
                presentGameOver();
                break;
        }
        game.batch.end();
    }

    private void presentGameOver() {

    }

    private void presentPaused() {
        GameScreenTextures.showGamingConfigs(game.batch);
    }


    private void presentRunning() {
    }

    @Override
    public void update() {
        stage.act();
        if(Gdx.input.justTouched()){
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if(returnToSelectGameScreenBound.contains(touchPoint.x,touchPoint.y)){
                GameScreenMusic.stop();
                game.setScreen(new SettleAccountScreen(game,gameName));
            }
            if(goToSettleAccountScreenBound.contains(touchPoint.x, touchPoint.y)){
                state = GAME_OVER;
            }
        }
        switch (state){
            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }

    private void updateGameOver() {
        GameScreenMusic.stop();
        Setting.saveScores(World.score);
        game.setScreen(new SettleAccountScreen(game,gameName));
    }

    private void updatePaused() {
        if(Gdx.input.justTouched()){
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if(resumeBound.contains(touchPoint.x, touchPoint.y)){
                GameScreenMusic.play();
                state = GAME_RUNNING;
            }
            //重新载入这个页面
            else if(retryBound.contains(touchPoint.x, touchPoint.y)){
                GameScreenMusic.stop();
                game.setScreen(new GameScreen(game,gameName));
            }
            else if(songsBound.contains(touchPoint.x, touchPoint.y)){
                GameScreenMusic.stop();
                game.setScreen(new SelectGameScreen(game));
            }
            else if(homeBound.contains(touchPoint.x, touchPoint.y)){
                GameScreenMusic.stop();
                game.setScreen(new LoadScreen(game));
            }
        }
    }

    private void updateRunning(float deltaTime) {
        //更新实体
        world.update(deltaTime);
        //检查音乐是否停止，停止的话切换游戏状态
        if(!GameScreenMusic.getCurrentMusic().isPlaying()&&state!=GAME_PAUSED){
            state = GAME_OVER;
        }
    }

    @Override
    public void render(float deltaTime){
        GameScreen.deltaTime =deltaTime;//此为昏招
        update();
        draw();
    }

    @Override
    public void pause(){
        //强制退出挂起
        if(state == GAME_RUNNING){
            state = GAME_PAUSED;
        }
    }

    public static int getState() {
        return state;
    }

    public static void setState(int state) {
        GameScreen.state = state;
    }

    public static InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public static void setInputMultiplexer(InputMultiplexer inputMultiplexer) {
        GameScreen.inputMultiplexer = inputMultiplexer;
    }
}
