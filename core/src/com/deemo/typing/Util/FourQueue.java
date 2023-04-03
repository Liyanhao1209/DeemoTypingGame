package com.deemo.typing.Util;

import com.deemo.typing.GameEntity.Impl.DynamicGameEntity.Key;

import com.badlogic.gdx.utils.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class FourQueue {
    private final Queue<WordKey>[] keyQueues;

    public Queue<WordKey>[] getKeyQueues() {
        return keyQueues;
    }

    public FourQueue(){
        this.keyQueues = new Queue[5];
        //初始化四个键道
        for(int i = 1;i<=4;i++){
            keyQueues[i] = new com.badlogic.gdx.utils.Queue<WordKey>();
        }
    }

    /**
     * 生成首批键，这个是同步的，要保证每个单词都轮一遍
     * @param index 键道索引
     * @param word 附带的单词
     * @param keyType 键的类型
     */
    public void addToQueue(int index,String word,int keyType,Key key){
        if(index<1||index>4){
            return;
        }
            //向指定键道添加键
            this.keyQueues[index].addLast(new WordKey(keyType, key, word));
    }


}
