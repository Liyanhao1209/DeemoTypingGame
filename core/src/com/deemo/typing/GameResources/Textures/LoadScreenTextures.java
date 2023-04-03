package com.deemo.typing.GameResources.Textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.deemo.typing.GameResources.Asset.pic;

public class LoadScreenTextures {
    public static Texture LoadScreenBackground;
    public static void load(){
        LoadScreenBackground = new Texture(pic+"LoadScreen.png");
    }

    public static void draw(SpriteBatch batch){
        batch.begin();
        batch.enableBlending();
        batch.draw(LoadScreenBackground,0,0,1920,1080);
        batch.end();
    }


}
