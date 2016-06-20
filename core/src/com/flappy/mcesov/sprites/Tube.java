package com.flappy.mcesov.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Mark on 14.06.2016.
 */
public class Tube {
    private static final int FLUCTUATION = 180;
    private static final int GAP = 90;
    private static final int LOWEST_OPENING = 100;
    private static final int TUBE_WIDTH = 72;
    private Texture top;
    private Texture bot;
    private Texture tube0, tube1, tube2, tube3, tube4;
    private Vector2 posTopTube;
    private Vector2 posBotTube;
    private Rectangle boundsTop, boundsBot;
    private Random rndFluctuation, rndTube;

    public Tube(float x){
        initiateTubes();
        rndFluctuation = new Random();
        rndTube = new Random();
        colorTubes();

        posTopTube = new Vector2(x, rndFluctuation.nextInt(FLUCTUATION)+ GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - GAP - bot.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y+10, top.getWidth(), top.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bot.getWidth(), bot.getHeight()-10);
    }

    public void reposition(float x){
        colorTubes();
        posTopTube = new Vector2(x, rndFluctuation.nextInt(FLUCTUATION)+ GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - GAP - bot.getHeight());

        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    public boolean collides(Rectangle bird){
        return bird.overlaps(boundsBot) || bird.overlaps(boundsTop);
    }

    public Texture getTop() {
        return top;
    }

    public Texture getBot() {
        return bot;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public void dispose(){
        top.dispose();
        bot.dispose();
        tube0.dispose();
        tube1.dispose();
        tube2.dispose();
        tube3.dispose();
        tube4.dispose();
    }

    public static int getTubeWidth() {
        return TUBE_WIDTH;
    }

    private void initiateTubes(){
        tube0 = new Texture("tubes/tube0.png");
        tube1 = new Texture("tubes/tube1.png");
        tube2 = new Texture("tubes/tube2.png");
        tube3 = new Texture("tubes/tube3.png");
        tube4 = new Texture("tubes/tube4.png");
    }

    private void colorTubes(){
        int x = rndTube.nextInt(5);
        switch (x){
            case 0:
                top = tube0;
                bot = tube0;
                break;
            case 1:
                top = tube1;
                bot = tube1;
                break;
            case 2:
                top = tube2;
                bot = tube2;
                break;
            case 3:
                top = tube3;
                bot = tube3;
                break;
            case 4:
                top = tube4;
                bot = tube4;
                break;
            default:
                top = tube0;
                bot = tube0;
                break;
        }
    }
}
