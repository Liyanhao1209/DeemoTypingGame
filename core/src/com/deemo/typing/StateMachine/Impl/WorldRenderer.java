package com.deemo.typing.StateMachine.Impl;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deemo.typing.GameEntity.Impl.DynamicGameEntity.Key;
import com.deemo.typing.GameResources.Textures.GameScreenTextures;
import com.deemo.typing.StateMachine.Interface.WorldEventRenderer;
import com.deemo.typing.Util.FourQueue;
import com.deemo.typing.Util.WordKey;

import java.text.DecimalFormat;

import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.*;

public class WorldRenderer implements WorldEventRenderer {
    private World world;
    private SpriteBatch batch;
    private BitmapFont font;


    public WorldRenderer(World world, SpriteBatch batch){
        this.world = world;
        this.batch = batch;
        this.font = new BitmapFont();
        font.getData().markupEnabled = true;
    }

    @Override
    public void render() {
        batch.enableBlending();
        batch.begin();
        renderBackGround();
        renderLineSymbols();
        renderKeys();
        renderScore();
        renderNumber();
        batch.end();
    }

    private void renderLineSymbols() {
        int queueStart = (ScreenWidth - topLineWidth) / 2;
        //上方的标记
        batch.draw(GameScreenTextures.TopLineSymbol,queueStart+(World.currentQueue-1)*topLineWidth/4,945,topLineWidth/4,20);
        //下方的标记
        batch.draw(GameScreenTextures.BottomLineSymbol,(World.currentQueue-1)*ScreenWidth/4,185,ScreenWidth/4,30);
    }

    private void renderBackGround(){
        batch.draw(GameScreenTextures.gamePageBg,0,0,1920,1080);
    }

    /**
     * 渲染时，需要将当前选择键道的输入用不同颜色标记出来，提示用户已经正确的输入
     * 思路为：判断当前需要渲染的键是否为当前选择键道的头部元素
     * 如果是，则用颜色标记字符串将已经输入的（用World的公共类字段currentCheckIndex确定）字符串标红
     * 然后拼接上后续的字符串
     * 这里要注意，当切换键道时，需要取消之前已经标红的字符（这个功能被自动实现了）
     */
    private void renderKeys(){
        FourQueue runningQueue = world.getRunningQueue();
        for(int i=1;i<=4;i++){
            for (WordKey wordKey : runningQueue.getKeyQueues()[i]) {
                String word = wordKey.getWord();
                if(wordKey.equals(runningQueue.getKeyQueues()[i].first())&&i==World.currentQueue){
                    String wordAlreadyInput = word.substring(0, Math.min(World.currentCheckIndex, word.length()));
                    wordAlreadyInput = "[#FFA500]" + wordAlreadyInput +"[]";
                    String wordNotInput = "[#67C23A]"+word.substring(World.currentCheckIndex)+"[]";
                    word = wordAlreadyInput+wordNotInput;
                }
                Key key = wordKey.getKey();
                float x = key.getPosition().x;
                float y = key.getPosition().y;
                float width = key.getBounds().width;
                float height = key.getBounds().height;
                if(wordKey.getKEY_TYPE()==1){
                    batch.draw(GameScreenTextures.YellowKey,x-width/2,
                            y-height/2,width,height);
                }
                else if(wordKey.getKEY_TYPE()==0){
                    batch.draw(GameScreenTextures.WhiteKey,x-width/2,
                            y-height/2,width,height);
                }
                /**
                 * 一开始字体大小是1倍，最后是1.5倍
                 * 这个过程从y=1080持续到y=0
                 * fontSize = 1+2*(1080-y)/1080
                 * 这个是卡了几个参数感觉效果不错弄的
                 */
                font.getData().setScale(1+2*(1080-y)/1080);
                font.draw(batch,word,x-240/2,y+height,240,1,false);
            }
        }
    }

    //向右上角渲染分数
    private void renderScore(){
        font.getData().setScale(2.5f);
        DecimalFormat formatter = new DecimalFormat("##0.00");
        font.draw(batch,formatter.format(World.score),ScreenWidth-240-20,ScreenHeight-65,240,1,false);
    }

    //向底线下方中间渲染正确数或者是错误数
    private void renderNumber(){
        font.getData().setScale(3.0f);
        String renderString="";
        if(World.CorrectSignal){
            renderString="[#67C23A]Correct:"+World.CorrectNumber+"[]";
        }
        else if(World.WrongSignal){
            renderString = "[#F56C6C]Wrong:"+World.WrongNumber+"[]";
        }
        font.draw(batch,renderString,ScreenWidth/2-240/2,100+50,240,1,false);
    }

}
