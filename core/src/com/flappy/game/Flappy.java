package com.flappy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappy.game.states.GameStateManager;
import com.flappy.game.states.MenuState;

import java.io.InputStream;
import java.io.OutputStream;

public class Flappy extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Flappy Bird";

	private Music music;
	private GameStateManager gsm;
	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		music = Gdx.audio.newMusic(Gdx.files.internal("cabal.MP3"));
		music.setLooping(true);
		music.setVolume(0.3f);
		music.play();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		gsm.push(new MenuState(gsm));


		if(!Gdx.files.external("flappy/highscore.txt").exists()){
			FileHandle file = Gdx.files.external("flappy/highscore.txt");
			try {
				file.writeString("0", false);

			} catch (Exception e) {}
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
	}

}
