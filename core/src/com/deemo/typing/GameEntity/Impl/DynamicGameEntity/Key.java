package com.deemo.typing.GameEntity.Impl.DynamicGameEntity;

import com.deemo.typing.GameEntity.SuperClass.DynamicGameObject;

import static com.deemo.typing.GameScreens.Impl.SelectGameScreen.Current_Key_Speed;
import static com.deemo.typing.GameScreens.SuperClass.GeneralScreen.*;
import static com.deemo.typing.StateMachine.Impl.World.queueCoefficient;

public class Key extends DynamicGameObject {
    public static final int Key_Running = 0;
    public static final int Key_Correct = 1;
    public static final int Key_Error = 2;
    public static final int Key_OutDate = 3;
    public static final float timeToAccessMax = 6.4f;
    private int state;
    public Key(float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity.y=-30;
        if(position.y<ScreenHeight+128){
            velocity.y=-100;
        }
        velocity.x=0;
        //计算加速度
        accel.y=-Current_Key_Speed*30/timeToAccessMax;
        state=0;
    }

    public void update(float deltaTime,int queueIndex,int keyType){
        updateSpeed(deltaTime);
        updatePosition(deltaTime,queueIndex);
        updateBound(keyType);
    }

    private void updateSpeed(float deltaTime){
        int maxSpeed = -Current_Key_Speed * 30;
        //因为是负的，所以不到的话是取大于
        if(velocity.y>maxSpeed){
            velocity.y+=accel.y*deltaTime;
        }
        else if(velocity.y<maxSpeed){
            velocity.y = maxSpeed;
        }

    }

    private void updatePosition(float deltaTime,int queueIndex){
        //更新y轴坐标
        position.add(0,deltaTime*velocity.y);
        //更新x轴坐标
        float k = queueCoefficient[queueIndex].getK();
        float b = queueCoefficient[queueIndex].getB();
        position.x = 1/k*(position.y-b);
    }

    private void updateBound(int keyType){
        //更新碰撞箱大小
        int topWidth = topLineWidth / 4/2;
        if(keyType==0){
            if(bounds.width<ScreenWidth/4/3){
                bounds.width = (1-position.y/ScreenHeight)*(ScreenWidth/4/3f-ScreenWidth/4/3.5f)+ScreenWidth/4/3.5f;
            }
        }
        else if(keyType==1){
            if(bounds.width<ScreenWidth/4/5){
                bounds.width = (1-position.y/ScreenHeight)*(ScreenWidth/4/5f-ScreenWidth/4/6.5f)+ScreenWidth/4/6.5f;
            }
        }
        //更新碰撞箱坐标
        bounds.y = position.y - bounds.height/2;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
