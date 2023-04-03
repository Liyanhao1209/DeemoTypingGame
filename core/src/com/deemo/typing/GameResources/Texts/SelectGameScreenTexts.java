package com.deemo.typing.GameResources.Texts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deemo.typing.GameResources.Asset;
import com.deemo.typing.GameResources.Textures.SelectGameScreenTextures;
import com.deemo.typing.GameScreens.Impl.SelectGameScreen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.deemo.typing.GameResources.Asset.assets;
import static com.deemo.typing.GameResources.Setting.file;
import static com.deemo.typing.GameResources.Textures.SelectGameScreenTextures.gamePortraitNumber;
import static com.deemo.typing.GameScreens.Impl.SelectGameScreen.gameIndexPointer;
import static com.deemo.typing.GameScreens.Impl.SelectGameScreen.games;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenHeight;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenWidth;

public class SelectGameScreenTexts {
    //为了把用户导入的文件放在最后，只能出此下策了
    private static String[] systemGames;
    static{
        systemGames = new String[gamePortraitNumber];
        systemGames[0]="Anima";systemGames[1]="Evolution Era";systemGames[2]="Mirror Night";systemGames[3]="Ripples";
    }
    /**
     * 加载选择关卡界面的所有关卡名
     * 但这个写的有问题，我们必须让所有用户自己导入的文件放到数组最后
     */
//    public static void load(){
//        FileHandle text = Gdx.files.internal(assets+ Asset.text);
//        games = new String[text.list().length];
//        int index = 0;
//        for (FileHandle fileHandle : text.list()) {
//            String change = cancelSuffix(fileHandle.name());
//            games[index++] = change;
//        }
//    }
    public static void load(){
        loadTexts();
        loadScores();
    }

    private static void loadTexts(){
        //如果是jar包则以jar包的目录为起点
        Path pathJar = Paths.get("..\\resources\\main\\text").toAbsolutePath();
        String path = "assets\\text";
        List<Path> fileNames = null;
        try (Stream<Path> paths = Files.walk(Paths.get(path))){
            fileNames = paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileNames==null){
            try (Stream<Path> paths = Files.walk(pathJar)){
                fileNames = paths
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        games = new String[fileNames.size()];
//        games = new String[5];
//        systemGames = new String[gamePortraitNumber];
//        systemGames[0]="Anima";systemGames[1]="Evolution Era";systemGames[2]="Mirror Night";systemGames[3]="Ripples";
        for(int i=0;i<systemGames.length;i++){
            games[i]=systemGames[i];
        }
        int index = systemGames.length;
        for (Path fileName : fileNames) {
            String change = cancelSuffix(String.valueOf(fileName.getFileName()));
            if(!Arrays.asList(systemGames).contains(change)){
                games[index++] = change;
            }
        }
    }

    private static void loadScores() {
        FileHandle scoreFile = Gdx.files.external(file);
        String path = scoreFile.file().getAbsolutePath();
        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
//        System.out.println(path);
        String[] scores = scoreFile.readString().split("\n");
        SelectGameScreen.scores = new String[games.length];
        for(int i=0;i<games.length;i++){
            SelectGameScreen.scores[i] = "0.00";
            if(i<scores.length&&!scores[i].equals("")){
                SelectGameScreen.scores[i] = scores[i];
            }
        }
    }


    //去掉后缀
    private static String cancelSuffix(String origin){
        return origin.substring(0, origin.length() - 4);
    }

    public static void draw(){
        BitmapFont font = new BitmapFont();
        SpriteBatch batch = new SpriteBatch();
        font.getData().scale(2.0f);
        font.getData().markupEnabled = true;
        batch.begin();
        //渲染关卡名到中间位置
        String game = games[gameIndexPointer];
        String score = "[#000000]Score: "+SelectGameScreen.scores[gameIndexPointer]+"[]";
        //其实我是很讨厌卡常数硬凑位置的，但这里我不知道图片主要区域宽度多大（不是670-170=500，要把渐变拖尾去掉），也不知道一个字大概多大
        //但是这个参数是行之有效的
        font.draw(batch,game==null?"game":game,ScreenWidth/4+(670-170-100)/2-(670/2-170)-240/2,ScreenHeight/2+20,240,1,false);
        //把分数渲染上去
        font.getData().setScale(1.5f);
        font.draw(batch,score,ScreenWidth/4+(670-170-100)/2-(670/2-170)-240/2,ScreenHeight/2-100,120,1,false);
        batch.end();
    }
}
