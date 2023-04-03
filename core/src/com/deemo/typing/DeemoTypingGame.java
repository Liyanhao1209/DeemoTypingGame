package com.deemo.typing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.deemo.typing.GameResources.Asset;
import com.deemo.typing.GameScreens.Impl.LoadHintScreen;
import com.deemo.typing.GameScreens.Impl.LoadScreen;

public class DeemoTypingGame extends Game {
	public SpriteBatch batch;

	public BitmapFont font;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		/**
		 * 这个地方预先加载可以防止卡顿
		 */
		Asset.load();
		setScreen(new LoadScreen(this));
	}

	private synchronized void  preLoad(){
		batch.begin();
		batch.draw(new Texture("badlogic.jpg"),0,0,256,256);
		batch.end();
	}

	@Override
	public void render(){
		super.render();
	}
}
