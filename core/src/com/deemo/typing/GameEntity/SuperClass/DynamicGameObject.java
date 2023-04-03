package com.deemo.typing.GameEntity.SuperClass;

import com.badlogic.gdx.math.Vector2;

public class DynamicGameObject extends GameObject{
    protected Vector2 velocity;
    protected Vector2 accel;


    public DynamicGameObject(float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Vector2();
        accel = new Vector2();
    }
}
