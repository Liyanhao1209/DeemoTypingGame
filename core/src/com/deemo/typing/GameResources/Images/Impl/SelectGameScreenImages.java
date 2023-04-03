package com.deemo.typing.GameResources.Images.Impl;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deemo.typing.DeemoTypingGame;
import com.deemo.typing.GameResources.Asset;
import com.deemo.typing.GameResources.Images.SuperClass.Images;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.deemo.typing.GameResources.Asset.icon;
import static com.deemo.typing.GameScreens.Impl.SelectGameScreen.gameIndexPointer;
import static com.deemo.typing.GameScreens.Impl.SelectGameScreen.games;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenHeight;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenWidth;

public class SelectGameScreenImages extends Images {
    public static Image selectUp;
    public static Image selectDown;
    public static Image txtIn;


    public static void load(DeemoTypingGame game){
        //选择曲目的按钮
        selectUp = DrawAndSetBounds(icon+"UpArrow.png",ScreenWidth/4-54/2,ScreenHeight-ScreenHeight/4-58/2,54,58);
        AddSelectUpListener(selectUp);
        selectDown = DrawAndSetBounds(icon+"DownArrow.png",ScreenWidth/4-54/2,ScreenHeight/4-58/2,54,58);
        AddSelectDownListener(selectDown);
        //导入文件的按钮
        txtIn = DrawAndSetBounds(icon+"txtIn.png",96+96+96/2,0+96/2,96,96);
        AddTXTInListener(txtIn);
    }

    private static void AddSelectUpListener(Image img){
        img.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(touchDown(event, x, y, pointer, button)){
                    if(gameIndexPointer==0){
                        gameIndexPointer = games.length-1;
                    }
                    else{
                        gameIndexPointer-=1;
                    }
                }
            }
        });
    }

    private static void AddSelectDownListener(Image img){
        img.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(touchDown(event, x, y, pointer, button)){
                    if(gameIndexPointer==games.length-1){
                        gameIndexPointer=0;
                    }
                    else{
                        gameIndexPointer+=1;
                    }
                }
            }
        });
    }


    private static void AddTXTInListener(Image img) {
        img.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(touchDown(event, x, y, pointer, button)){
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(null);
                    //点击了“打开按钮”
                    if(result == JFileChooser.APPROVE_OPTION){
                        File selectedFile = fileChooser.getSelectedFile();
                        //获取文本内容
                        String s = new FileHandle(selectedFile).readString();
                        //获取写入路径
                        String pathname = Asset.RelativePathFromSelectGameScreenImagesAddTxtInListenerToProjectAssetText + selectedFile.getName();
                        //获取写入文件
                        File file = new File(pathname);
                        FileWriter fileWriter = null;
                        try {
                            //创建写入文件
                            if(!file.exists()){
                                try{
                                    file.createNewFile();
                                    fileWriter = new FileWriter(pathname);
                                }catch (Exception e){
                                    pathname = "..\\resources\\main\\text\\"+selectedFile.getName();
                                    file = new File(pathname);
                                    file.createNewFile();
                                    fileWriter = new FileWriter(pathname);
                                }
                            }
                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                            bufferedWriter.write(s);
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }



}
