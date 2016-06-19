package com.flappy.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappy.game.Flappy;

/**
 * Created by Mark on 17.06.2016.
 */
public class EndState extends State {
    private Texture background;
    private Texture buttonMenu, buttonAgain;
    private int score, highscore;
    private BitmapFont fontOver, fontScore;
    private float timeSinceEndScreen;
    private boolean allowClicking;

    public EndState(GameStateManager gsm, int score) {
        super(gsm);
        this.score = score;

        highscore = -1;
        highScoreUpdate();
        fontOver = new BitmapFont(Gdx.files.internal("fontover.fnt"));
        fontOver.setColor(Color.RED);
        fontScore = new BitmapFont(Gdx.files.internal("font.fnt"));
        fontScore.setColor(Color.LIGHT_GRAY);
        cam.setToOrtho(false, Flappy.WIDTH/2, Flappy.HEIGHT/2);
        background = new Texture("bg_end_contrast.png");
        buttonMenu = new Texture("buttons/b_menu.png");
        buttonAgain = new Texture("buttons/b_restart.png");
        timeSinceEndScreen = 0f;
        allowClicking = false;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void handleInput() {
        if(!allowClicking) {
            if (timeSinceEndScreen > 0.5f) allowClicking = true;
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(allowClicking){
            screenX = (int)((float)cam.viewportWidth / (float)Gdx.graphics.getWidth() * (float)screenX);
            screenY = (int)((float)cam.viewportHeight / (float)Gdx.graphics.getHeight() * (float)screenY);
            if(screenX >= cam.viewportWidth/9 &&
                    screenX <= (cam.viewportWidth/9 + cam.viewportWidth/3) &&
                    screenY <= cam.viewportHeight - cam.viewportHeight/7 &&
                    screenY >= cam.viewportHeight - cam.viewportHeight/7 - cam.viewportHeight/10){
                gsm.set(new MenuState(gsm));
            }
            if(screenX >= cam.viewportWidth/9*5 &&
                    screenX <= (cam.viewportWidth/9*5 + cam.viewportWidth/3) &&
                    screenY <= cam.viewportHeight - cam.viewportHeight/7 &&
                    screenY >= cam.viewportHeight - cam.viewportHeight/7 - cam.viewportHeight/10) {
                Gdx.input.vibrate(20);
                Gdx.input.setInputProcessor(null);
                gsm.set(new PlayState(gsm));
            }
        }



        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public void update(float dt) {
        timeSinceEndScreen += dt;
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
        sb.draw(buttonMenu,
                (cam.viewportWidth/9),
                cam.viewportHeight/7,
                cam.viewportWidth/3, cam.viewportHeight/10);
        sb.draw(buttonAgain,
                (cam.viewportWidth/9*5),
                cam.viewportHeight/7,
                cam.viewportWidth/3, cam.viewportHeight/10);
        fontOver.draw(sb, "Doge Over", cam.viewportWidth/2 - 95, cam.viewportHeight / 2 + 100);
        fontScore.draw(sb, "Highscore: " + highscore,
                cam.viewportWidth/2 - 95, cam.viewportHeight / 2 + 40);
        fontScore.draw(sb, "Score: " + score,
                cam.viewportWidth/2 - 95, cam.viewportHeight / 2);
        sb.end();
    }
    @Override
    public void dispose() {
        background.dispose();
        buttonAgain.dispose();
        buttonMenu.dispose();
        fontScore.dispose();
        fontOver.dispose();
    }

    private void highScoreUpdate() {
        FileHandle file = Gdx.files.external("flappy/highscore.txt");
        try {
            highscore = Integer.parseInt(file.readString());
            if(score>highscore){
                highscore = score;
                file.writeString(score+"", false);
            }

        } catch (Exception e) {
            System.out.println("Fehler beim Lesen des Highscore in highScoreUpdate von EndState.");
        }
    }
}
