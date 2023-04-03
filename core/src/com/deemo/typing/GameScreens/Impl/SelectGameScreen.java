package com.deemo.typing.GameScreens.Impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deemo.typing.DeemoTypingGame;
import com.deemo.typing.GameResources.Asset;
import com.deemo.typing.GameResources.Images.Impl.SelectGameScreenImages;
import com.deemo.typing.GameResources.Texts.SelectGameScreenTexts;
import com.deemo.typing.GameResources.Textures.SelectGameScreenTextures;
import com.deemo.typing.GameScreens.SuperClass.GeneralScreen;

import static com.deemo.typing.GameResources.Asset.assets;

public class SelectGameScreen extends GeneralScreen {
    public static  int gameNumber;
    static{
        FileHandle text = Gdx.files.internal(assets+ Asset.text);
        gameNumber = text.list().length;
        if(gameNumber==0){
            gameNumber=Gdx.files.internal("..\\resources\\main\\text").list().length;
        }
    }
    public static final int Easy_Level = 0;
    public static final int Normal_Level = 1;
    public static final int Hard_Level =2;
    public static boolean configOn;

    public static int Current_Level=0;
    public static int Current_Key_Speed=5;
    public static String[] games;
    public static String[] scores;
    public static int gameIndexPointer=0;

    private Rectangle changeLevelBound;

    private Rectangle gameConfigOnBound;
    private Rectangle gameConfigCancelBound;

    private Rectangle gameStartBound;
    private Rectangle keySpeedUpBound;
    private Rectangle keySpeedDownBound;

    public SelectGameScreen(DeemoTypingGame game) {
        this.game = game;
        guiCam = new OrthographicCamera(ScreenWidth,ScreenHeight);
        guiCam.position.set(ScreenWidth/2,ScreenHeight/2,0);
        touchPoint = new Vector3();
        configOn = false;

        changeLevelBound = new Rectangle(-(ScreenWidth/4+670/2),-170/2,170,170);
        gameConfigOnBound = new Rectangle(-(ScreenWidth/4+670/2-170),-170/2,500,170);
        gameConfigCancelBound = new Rectangle(-(ScreenWidth/2-96/2),-(ScreenHeight/2-96/2),96,96);
        gameStartBound = new Rectangle(-256/2,-((ScreenHeight/4.5f)*(4.5f/2-1)+40/2),256,40);
        keySpeedDownBound = new Rectangle(-(ScreenWidth/2-380),-(ScreenHeight/2-310),90,90);
        keySpeedUpBound = new Rectangle(ScreenWidth/2-450,-(ScreenHeight/2-280),90,90);

        stage = new Stage();
        SelectGameScreenImages.load(game);
        stage.addActor(SelectGameScreenImages.selectUp);
        stage.addActor(SelectGameScreenImages.selectDown);
        stage.addActor(SelectGameScreenImages.txtIn);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void draw() {
        clearScreen();
        SelectGameScreenTextures.draw(game.batch,Current_Level,game.font);
        stage.draw();
        SelectGameScreenTexts.draw();
        if(configOn){
            SelectGameScreenTextures.showConfig(game.batch);
            SelectGameScreenTextures.showKeySpeed(game.font,game.batch);
            SelectGameScreenTextures.showConfigCancel(game.batch);
            SelectGameScreenTextures.showGameStart(game.batch);
        }

    }

    @Override
    public void update() {
        stage.act();
        if(Gdx.input.justTouched()){
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            //切换难度等级
            if(changeLevelBound.contains(touchPoint.x,touchPoint.y)){
                Current_Level=(Current_Level+1)%3;
            }
            //打开设置页面
            else if(gameConfigOnBound.contains(touchPoint.x,touchPoint.y)){
                configOn=true;
            }
            //如果有设置页面，说明是取消设置页面
            else if(gameConfigCancelBound.contains(touchPoint.x,touchPoint.y)&&configOn){
                configOn=false;
            }
            //如果没有设置界面，说明是返回初始页
            else if(gameConfigCancelBound.contains(touchPoint.x, touchPoint.y)&&!configOn){
                game.setScreen(new LoadScreen(game));
            }
            //开启了设置界面，并且点击了开始键
            else if(gameStartBound.contains(touchPoint.x, touchPoint.y)&&configOn){
                game.setScreen(new GameScreen(game,games[gameIndexPointer]));
            }
            else if(keySpeedUpBound.contains(touchPoint.x,touchPoint.y)&&configOn){
                if(Current_Key_Speed<5){
                    Current_Key_Speed++;
                }
            }
            else if(keySpeedDownBound.contains(touchPoint.x, touchPoint.y)&&configOn){
                if(Current_Key_Speed>1){
                    Current_Key_Speed--;
                }
            }
        }
        if(Gdx.files.internal(assets+ Asset.text).list().length!=0){
            //检测是否有新文件导入
            if(Gdx.files.internal(assets+ Asset.text).list().length!=gameNumber){
                //重新加载资源（因为只导入了文本，加载文本即可）
                Asset.loadTexts();
                //游戏关卡数目加一（一次导入一个）
                gameNumber+=1;
            }
        }
        else{
            if(Gdx.files.internal("..\\resources\\main\\text").list().length!=gameNumber){
                //重新加载资源（因为只导入了文本，加载文本即可）
                Asset.loadTexts();
                //游戏关卡数目加一（一次导入一个）
                gameNumber+=1;
            }
        }
    }

    @Override
    public void render(float delta){
        update();
        draw();
    }
}
