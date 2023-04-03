package com.deemo.typing.GameEntity.SuperClass;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
    protected Vector2 position;
    protected Rectangle bounds;

    public GameObject(float x,float y,float width,float height) {
        this.position = new Vector2(x,y);
        this.bounds = new Rectangle(x-width/2,y-height/2,width,height);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
