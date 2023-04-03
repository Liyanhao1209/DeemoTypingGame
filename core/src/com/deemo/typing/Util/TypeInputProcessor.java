package com.deemo.typing.Util;

import com.badlogic.gdx.InputProcessor;
import com.deemo.typing.StateMachine.Impl.World;

import static com.deemo.typing.StateMachine.Impl.World.inputWrong;

public class TypeInputProcessor implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //62是空格
        //向右22 向左21
        if(keycode==22||keycode==62){
            World.currentQueue+=1;
            if(World.currentQueue==5){
                World.currentQueue=1;
            }
            //切换键道则重置需要检查的字符位置
            World.currentCheckIndex=0;
        }
        else if(keycode==21){
            World.currentQueue-=1;
            if(World.currentQueue==0){
                World.currentQueue=4;
            }
            World.currentCheckIndex=0;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        //空格默认成切换键道的快捷键了，不要做判定
        if(character==' '){
            return false;
        }
        boolean flag = checkCurrentInputAndCurrentHeadWord(character);
        if(flag&&World.currentCheckIndex<World.currentHeadWord.length()){
            World.currentCheckIndex++;
            inputWrong=false;
        }
        //输入错误
        else if(!flag){
            inputWrong=true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    //检查当前输入的字符串是否和需要输入的单词一致
    private boolean checkCurrentInputAndCurrentHeadWord(char Character){
        if(World.currentHeadWord.equals("")){
            return false;
        }
        return World.currentHeadWord.charAt(World.currentCheckIndex)==Character;
    }
}
