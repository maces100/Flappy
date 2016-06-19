package com.flappy.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Mark on 14.06.2016.
 */
public class Bird {
    private static final int GRAVITY = -20;
    private static final int MOVEMENT = 180;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Texture birdTexture;
    private Sound flap;
    private int birdHeight;

    public Bird(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        birdTexture = new Texture("dogeanim.png");
        birdAnimation = new Animation(birdTexture, 2, 0.5f);
        birdHeight = birdTexture.getHeight();
        bounds = new Rectangle(x,y, birdTexture.getWidth()/2, birdTexture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("wings_small.ogg"));
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void update(float dt){
        birdAnimation.update(dt);
        velocity.add(0, GRAVITY, 0);
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);

        velocity.scl(1/dt);
        if(position.y<0) position.y = 0;
        bounds.setPosition(position.x, position.y);


    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    public int getBirdHeight() {
        return birdHeight;
    }

    public void jump(){
        Gdx.input.vibrate(17);

        flap.play(1f);
        velocity.y = 350;
    }

    public void dispose() {
        birdTexture.dispose();
        flap.dispose();
    }
}
