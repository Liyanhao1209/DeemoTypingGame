package com.deemo.typing.GameResources.Images.SuperClass;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deemo.typing.DeemoTypingGame;

public abstract class Images {
    /**
     * 这是一个引用副本的问题
     * img为badLogicTest的一个引用副本
     * img一开始指向null
     * 但new Image()让它改指一个Image类的对象
     * 但副本指向对象并没有让原本指向对象
     * 所以我们需要将这个引用返回，让原本指向这个对象
     */
    protected static Image DrawAndSetBounds(String imgPath, float x, float y, float width, float height){
        Texture texture = new Texture(imgPath);
        Image img = new Image(texture){
            public void draw (Batch batch, float parentAlpha) {
                batch.draw(texture,x,y,width,height);
            }
        };
        img.setBounds(x,y,width,height);
        return img;
    }

}
