package com.deemo.typing.Util;

import com.deemo.typing.GameEntity.Impl.DynamicGameEntity.Key;

/**
 * 可以把黄/白键的信息封装到这个类中
 * 舍弃WhiteKey和YellowKey，一律写为Key
 */
public class WordKey {
    //默认0白1黄,一经确定不可修改
    private final int KEY_TYPE;
    private Key key;
    private String word;

    public WordKey(int KEY_TYPE, Key key, String word) {
        this.KEY_TYPE = KEY_TYPE;
        this.key = key;
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public int getKEY_TYPE() {
        return KEY_TYPE;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
