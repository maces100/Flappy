package com.flappy.mcesov.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mark on 14.06.2016.
 */
public class MenuState extends State{
    private Texture background;
    private Texture buttonStart, buttonEnde;
    private BitmapFont fontFlappy;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, com.flappy.mcesov.Flappy.WIDTH/2, com.flappy.mcesov.Flappy.HEIGHT/2);
        background = new Texture("bg2.png");
        fontFlappy = new BitmapFont(Gdx.files.internal("font.fnt"));
        fontFlappy.setColor(Color.GREEN);
        buttonStart = new Texture("buttons/b_start.png");
        buttonEnde = new Texture("buttons/b_quit.png");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {

        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background,
                cam.position.x - cam.viewportWidth/2,
                0,
                cam.viewportWidth, cam.viewportHeight);
        sb.draw(buttonEnde,
                (cam.viewportWidth/9),
                cam.viewportHeight/7,
                cam.viewportWidth/3, cam.viewportHeight/10);
        sb.draw(buttonStart,
                (cam.viewportWidth/9*5),
                cam.viewportHeight/7,
                cam.viewportWidth/3, cam.viewportHeight/10);
        fontFlappy.draw(sb, "Flappy Doge", cam.viewportWidth/2 - 95, cam.viewportHeight / 2 + 100);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        fontFlappy.dispose();
        buttonStart.dispose();
        buttonEnde.dispose();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = (int)((float)cam.viewportWidth / (float)Gdx.graphics.getWidth() * (float)screenX);
        screenY = (int)((float)cam.viewportHeight / (float)Gdx.graphics.getHeight() * (float)screenY);
        if(screenX >= cam.viewportWidth/9 &&
                screenX <= (cam.viewportWidth/9 + cam.viewportWidth/3) &&
                screenY <= cam.viewportHeight - cam.viewportHeight/7 &&
                screenY >= cam.viewportHeight - cam.viewportHeight/7 - cam.viewportHeight/10){
            Gdx.app.exit();

        }
        if(screenX >= cam.viewportWidth/9*5 &&
                screenX <= (cam.viewportWidth/9*5 + cam.viewportWidth/3) &&
                screenY <= cam.viewportHeight - cam.viewportHeight/7 &&
                screenY >= cam.viewportHeight - cam.viewportHeight/7 - cam.viewportHeight/10) {
            Gdx.input.vibrate(20);
            Gdx.input.setInputProcessor(null);
            gsm.set(new PlayState(gsm));
        }



        return super.touchUp(screenX, screenY, pointer, button);
    }
}
