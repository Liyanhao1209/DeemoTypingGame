package com.deemo.typing.StateMachine.Impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.deemo.typing.GameEntity.Impl.DynamicGameEntity.Key;
import com.deemo.typing.GameEntity.Impl.StaticGameEntity.BaseLine;
import com.deemo.typing.GameResources.Asset;
import com.deemo.typing.GameScreens.Impl.GameScreen;
import com.deemo.typing.StateMachine.Interface.WorldEvent;
import com.deemo.typing.StateMachine.Interface.WorldEventListener;
import com.deemo.typing.Util.FourQueue;
import com.deemo.typing.Util.LinearFunctionCoefficient;
import com.deemo.typing.Util.TypeInputProcessor;
import com.deemo.typing.Util.WordKey;

import java.util.*;

import static com.deemo.typing.GameScreens.Impl.SelectGameScreen.*;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenHeight;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.ScreenWidth;

public class World implements WorldEvent {
    public static LinearFunctionCoefficient[] queueCoefficient = new LinearFunctionCoefficient[5];

    static {
        //计算k,b数组
        //第一条键道开始的位置
        int queueStart = (ScreenWidth - topLineWidth) / 2;
        int bottomWidth = ScreenWidth / 8;
        int topWidth = topLineWidth / 8;
        for (int i = 1; i <= 4; i++) {
            int index = 2 * i - 1;
            LinearFunctionCoefficient linearFunctionCoefficient = new LinearFunctionCoefficient();
            linearFunctionCoefficient.computeKAndB(index * bottomWidth, 0, queueStart + topWidth * index, ScreenHeight);
            queueCoefficient[i] = linearFunctionCoefficient;
        }
    }

    private List<WordKey> wordKeys;
    private BaseLine baseLine;

    private FourQueue queues;

    //已经进入屏幕的键的队列
    private FourQueue runningQueue;

    private final Random random;

    private final WorldEventListener listener;

    private final String gameName;

    public static float score;
    public static int CorrectNumber;
    public static int WrongNumber;
    public static boolean CorrectSignal;
    public static boolean WrongSignal;
    //当前选择的键道
    public static int currentQueue;
    //当前键道最头部的单词
    public static String currentHeadWord;
    //当前需要检查的位置
    public static int currentCheckIndex;
    private String[] words;
    //集合存储打错的单词
    public static ArrayList<String> wrongWords;
    //记录最大连击数
    public static int maxComboNumber;
    //记录当前连击数
    public static int currentComboNumber;
    //记录charming数
    public static int charmingNumber;
    //记录生成的键数
    public static int keyTotalNumbers;
    public static boolean inputWrong;

    public World(WorldEventListener listener, String GameName) {
        this.gameName = GameName;
        this.listener = listener;
        this.wordKeys = new ArrayList<>();
        this.baseLine = new BaseLine(ScreenWidth / 2, 170, 1920, 32);
        this.random = new Random();
        this.queues = new FourQueue();
        this.runningQueue = new FourQueue();
        //初始化分数
        score = 0.0f;
        //初始化正确数和错误数
        CorrectNumber = 0;
        WrongNumber = 0;
        //初始化正确渲染标记
        CorrectSignal = false;
        //初始化错误渲染标记
        WrongSignal = false;
        //初始化键数
        keyTotalNumbers = 0;
        //监听键盘
        GameScreen.getInputMultiplexer().addProcessor(new TypeInputProcessor());
//        Gdx.input.setInputProcessor(new TypeInputProcessor());
        //加载文本
        words = Asset.loadText(gameName + ".txt");
        //生成第一批键
        generateNewKeys(words);
        //默认初始键道为第一道
        currentQueue = 1;
        //默认当前单词为空串
        currentHeadWord = "";
        //默认待检查字符位置为0
        currentCheckIndex = 0;
        //重置错误单词集合
        wrongWords = new ArrayList<>();
        //重置最大连击数
        maxComboNumber = 0;
        //重置当前连击数
        currentComboNumber = 0;
        //重置charming数
        charmingNumber = 0;
        //输入错误标记
        inputWrong = false;
    }

    @Override
    public synchronized void update(float deltaTime) {
        updateKeys(deltaTime);
        Queue<WordKey> keyQueue = runningQueue.getKeyQueues()[currentQueue];
        if (!keyQueue.isEmpty()) {
            currentHeadWord = keyQueue.first().getWord();
        } else {
            currentHeadWord = "";
        }
        check();
    }


    private void updateKeys(float deltaTime) {
        updateKeysPosition(deltaTime);//更新已进入屏幕的键的位置
        updateKeysToRunningQueues(deltaTime);//向屏幕四键道不断添加新的键
        if (checkIfThereStillHaveKeys()) {
            generateNewKeys(words);
        }
    }

    //音乐不停，键的生成也不停。需要不断检查是否四个键道都没键了，如果是，生成一批新的。
    private boolean checkIfThereStillHaveKeys() {
        for (int i = 1; i < queues.getKeyQueues().length; i++) {
            if (!queues.getKeyQueues()[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void updateKeysPosition(float deltaTime) {
        //用最笨的方法，暴力试一下
        Queue<WordKey>[] keyQueues = runningQueue.getKeyQueues();
        for (int i = 1; i <= 4; i++) {
            for (WordKey wordKey : keyQueues[i]) {
                Key key = wordKey.getKey();
                key.update(deltaTime, i, wordKey.getKEY_TYPE());
                if (key.getPosition().y < baseLine.getPosition().y + baseLine.getBounds().height / 2) {
                    //和底线碰了就是过期的键，准备让renderer渲染过期动画
                    key.setState(Key.Key_OutDate);
                    WordKey outDateKey = keyQueues[i].removeFirst();
                    if (!wrongWords.contains(outDateKey.getWord())) {
                        wrongWords.add(outDateKey.getWord());//没来得及打的单词也算错误
                    }

                }
            }
        }
    }

    private void updateKeysToRunningQueues(float deltaTime) {
        boolean flag = randomDetermineWhetherToAddKeys();
        if (flag) {
            int queueChoice = randomQueueChoice();//随机选一个键道
            Queue<WordKey> keyQueue = queues.getKeyQueues()[queueChoice];
            if (keyQueue.isEmpty()) {
                return;
            }
            WordKey poll = keyQueue.removeFirst();//获取该键道头部键
            /**
             * 不能让同一条键道上的两个键挨得太近
             */
            if (!runningQueue.getKeyQueues()[queueChoice].isEmpty()) {
                WordKey runningLast = runningQueue.getKeyQueues()[queueChoice].last();
                Key pollKey = poll.getKey();
                Key runningLastKey = runningLast.getKey();
                if (pollKey.getPosition().y - 64 < runningLastKey.getPosition().y) {
                    pollKey.setPosition(new Vector2(pollKey.getPosition().x, runningLastKey.getPosition().y + 64));
                }
            }
            //随机决定某一键道中的非头部元素是否滞后出现
            if (determineWhetherToIncreaseTheDistance() && !runningQueue.getKeyQueues()[queueChoice].isEmpty()) {
                Key key = poll.getKey();
                key.setPosition(new Vector2(key.getPosition().x, key.getPosition().y + 256));
            }
            runningQueue.addToQueue(queueChoice, poll.getWord(), poll.getKEY_TYPE(), poll.getKey());//加入屏幕内的键道
        }
    }

    @Override
    public void check() {
        checkCurrentKey();
    }

    private void checkCurrentKey() {

        if (inputWrong) {
            //TODO:加上动画 现在只是简单的移除了这个键
            Queue<WordKey> keyQueue = runningQueue.getKeyQueues()[currentQueue];
            if (!keyQueue.isEmpty()) {
                WordKey wordKey = keyQueue.removeFirst();
                //加入错误单词集合
                if (!wrongWords.contains(wordKey.getWord())) {
                    wrongWords.add(wordKey.getWord());
                }
            }
            //重置待检查的字符位置
            currentCheckIndex = 0;
            //重置该键道首个单词
            currentHeadWord = "";
            //重置maxCombo
            maxComboNumber = Math.max(maxComboNumber, currentComboNumber);
            //重置currentCombo
            currentComboNumber = 0;
            //增加错误键数
            WrongNumber += 1;
            //标记错误渲染
            WrongSignal = true;
            CorrectSignal = false;
            //输入错误置位
            inputWrong = false;
        } else if (currentCheckIndex == currentHeadWord.length() && currentHeadWord.length() != 0) {
            Queue<WordKey> keyQueue = runningQueue.getKeyQueues()[currentQueue];
            keyQueue.removeFirst();
            score += 0.16f;
//            System.out.println(score);
            //重置待检查的字符位置
            currentCheckIndex = 0;
            //重置该键道首个单词
            currentHeadWord = "";
            //增加currentCombo
            currentComboNumber += 1;
            //增加charming数
            charmingNumber += 1;
            //增加正确键数
            CorrectNumber += 1;
            //标记正确渲染
            CorrectSignal = true;
            WrongSignal = false;
        }
    }


    //生成首批单词
    private void generateNewKeys(String[] words) {
        int wordsIndex = 0;
        int queueChoice = 0;//选择放到哪个键道
        while (wordsIndex < words.length) {
            //生成一个随机数
            queueChoice = randomQueueChoice();
            int keyType = 0;
            if (words[wordsIndex].length() <= 2) {
                keyType = 1;
            }
            float[] position = randomKeyCoordinate(queueChoice, keyType);
            this.queues.addToQueue(queueChoice, words[wordsIndex], keyType, new Key(position[0], position[1], position[2], position[3]));
            keyTotalNumbers += 1;
            wordsIndex += 1;
        }
    }


    private int randomQueueChoice() {
        int queueChoice = 0;
        float v = random.nextFloat();
        if (v <= 0.25f) {
            queueChoice = 1;
        } else if (v <= 0.5f) {
            queueChoice = 2;
        } else if (v <= 0.75f) {
            queueChoice = 3;
        } else {
            queueChoice = 4;
        }
        return queueChoice;
    }

    /**
     * 这个策略不太好，过于生硬
     * TODO:调整一个较好的进入屏幕的策略
     */
    private boolean randomDetermineWhetherToAddKeys() {

        float v = random.nextFloat();
        if (Current_Level == Easy_Level) {
            return v > 0.96f;
        } else if (Current_Level == Normal_Level) {
            return v > 0.96f;
        }
        /**
         * Hard_level
         */
        else {
            return v > 0.95f;
        }
    }

    //根据键道的选择确定键生成的位置
    private float[] randomKeyCoordinate(int queueChoice, int KeyType) {
        float[] position = new float[4];
        position[1] = ScreenHeight - 32 / 2;
        //初始宽度，白键为键道最上方的一半，黄键为1/4
        position[2] = KeyType == 0 ? ScreenWidth / 4 / 3.5f : ScreenWidth / 4 / 6.5f;
        position[3] = 32;
        //第一条键道开始的位置
        int queueStart = (ScreenWidth - topLineWidth) / 2;
        switch (queueChoice) {
            case 1:
                position[0] = queueStart + topLineWidth / 4 / 2;
                break;
            case 2:
                position[0] = queueStart + 3 * topLineWidth / 4 / 2;
                break;
            case 3:
                position[0] = queueStart + 5 * topLineWidth / 4 / 2;
                break;
            case 4:
                position[0] = queueStart + 7 * topLineWidth / 4 / 2;
                break;
        }
        return position;
    }

    private boolean determineWhetherToIncreaseTheDistance() {
        float v = random.nextFloat();
        switch (Current_Level) {
            case Easy_Level:
                return v > 0.1f;
            case Normal_Level:
                return v > 0.3f;
            case Hard_Level:
                return v > 0.5f;
        }
        return false;
    }


    public List<WordKey> getWordKeys() {
        return wordKeys;
    }

    public void setWordKeys(List<WordKey> wordKeys) {
        this.wordKeys = wordKeys;
    }

    public BaseLine getBaseLine() {
        return baseLine;
    }

    public void setBaseLine(BaseLine baseLine) {
        this.baseLine = baseLine;
    }

    public FourQueue getQueues() {
        return queues;
    }

    public void setQueues(FourQueue queues) {
        this.queues = queues;
    }

    public FourQueue getRunningQueue() {
        return runningQueue;
    }

    public void setRunningQueue(FourQueue runningQueue) {
        this.runningQueue = runningQueue;
    }

    public Random getRandom() {
        return random;
    }

    public WorldEventListener getListener() {
        return listener;
    }

    public String getGameName() {
        return gameName;
    }
}