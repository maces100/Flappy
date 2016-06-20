package com.flappy.mcesov;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Mark on 17.06.2016.
 */
public class FileIO implements ApplicationListener{
    @Override
    public void create() {
        FileHandle file = Gdx.files.internal("data/test.txt");
        System.out.println(file.readString());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
