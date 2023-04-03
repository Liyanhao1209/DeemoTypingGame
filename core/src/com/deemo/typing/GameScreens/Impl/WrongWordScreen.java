package com.deemo.typing.GameScreens.Impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deemo.typing.DeemoTypingGame;
import com.deemo.typing.GameResources.Images.Impl.WrongWordScreenImages;
import com.deemo.typing.GameResources.Textures.SettleAccountScreenTextures;
import com.deemo.typing.GameScreens.SuperClass.GeneralScreen;
import com.deemo.typing.StateMachine.Impl.World;

import static com.deemo.typing.GameScreens.Impl.SettleAccountScreen.maxWrongWordPage;

public class WrongWordScreen extends GeneralScreen {
    public static int pageIndex;

    public WrongWordScreen(DeemoTypingGame game,int pageIndex){
        this.game = game;
        guiCam = new OrthographicCamera(ScreenWidth,ScreenHeight);
        guiCam.position.set(ScreenWidth/2,ScreenHeight/2,0);
        touchPoint = new Vector3();

        //页数
        WrongWordScreen.pageIndex =pageIndex;

        stage = new Stage();
        WrongWordScreenImages.load(game);
        //最后一页没有下一页的按钮
        if(pageIndex<maxWrongWordPage){
            stage.addActor(WrongWordScreenImages.nextPage);
        }
        stage.addActor(WrongWordScreenImages.previousPage);
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void draw() {
        clearScreen();
        game.batch.begin();
        game.batch.draw(SettleAccountScreenTextures.SettleAccountScreenBackground,0,0,ScreenWidth,ScreenHeight);
        int offsetY = (ScreenHeight-96*2)/5;
        int currentPageMaxWordNum;
        if(pageIndex!= maxWrongWordPage){
            currentPageMaxWordNum=5;
        }
        else{
            currentPageMaxWordNum=World.wrongWords.size()-pageIndex*5;
        }
        for(int i=0;i<currentPageMaxWordNum;i++){
            int index = pageIndex*5+i;
            game.font.draw(game.batch,"[#000000]"+(index+1)+". "+ World.wrongWords.get(index)+"[]",ScreenWidth/2-240/2,ScreenHeight-offsetY*(i+1),240,1,false);
        }
        game.batch.end();
        stage.draw();
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
