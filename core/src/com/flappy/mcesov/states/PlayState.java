package com.flappy.mcesov.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.flappy.mcesov.Flappy;
import com.flappy.mcesov.sprites.Bird;


/**
 * Created by Mark on 14.06.2016.
 */
public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;

    private Array<com.flappy.mcesov.sprites.Tube> tubes;
    private Bird bird;
    private Texture background;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private static final int GROUND_OFFSET = -70;
    private Sound crash;
    private BitmapFont font;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 200);
        background = new Texture("bg2.png");
        cam.setToOrtho(false, Flappy.WIDTH/2, Flappy.HEIGHT/2);

        tubes = new Array<com.flappy.mcesov.sprites.Tube>();
        for (int i = 2; i <= TUBE_COUNT+1; i++){
            tubes.add(new com.flappy.mcesov.sprites.Tube(i * (TUBE_SPACING + com.flappy.mcesov.sprites.Tube.getTubeWidth())));
        }

        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth /2, GROUND_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth /2) + ground.getWidth(), GROUND_OFFSET);

        crash = Gdx.audio.newSound(Gdx.files.internal("crash.ogg"));
        font = new BitmapFont();

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
        if(bird.getPosition().y < ground.getHeight() + GROUND_OFFSET ||
                bird.getPosition().y > cam.viewportHeight- bird.getBirdHeight()){
            System.out.println("cam.viewportwidth: " + cam.viewportHeight + " " + " bird.getBirdHeight: " + bird.getBirdHeight() + " birdposition: " + bird.getPosition());
            crash.play();
            Gdx.input.vibrate(45);
            gsm.set(new EndState(gsm, countPoints()));

        }

        cam.position.x = bird.getPosition().x + 80;
        for(com.flappy.mcesov.sprites.Tube tube : tubes){
            if(cam.position.x -(cam.viewportWidth / 2) > tube.getPosTopTube().x + com.flappy.mcesov.sprites.Tube.getTubeWidth()){
                tube.reposition(tube.getPosTopTube().x
                + ((com.flappy.mcesov.sprites.Tube.getTubeWidth() + TUBE_SPACING))*TUBE_COUNT);
            }
            if (tube.collides(bird.getBounds())){
                crash.play();
                Gdx.input.vibrate(45);
                gsm.set(new EndState(gsm, countPoints()));
                break;
            }
        }
        if((cam.position.x - (cam.viewportWidth / 2)) > groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth()*2,0);
        }
        if((cam.position.x - (cam.viewportWidth / 2)) > groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth()*2,0);
        }
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background,
                cam.position.x - cam.viewportWidth/2,
                0,
                cam.viewportWidth, cam.viewportHeight);
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        for(com.flappy.mcesov.sprites.Tube tube : tubes){
            sb.draw(tube.getTop(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBot(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        font.draw(sb, "Score: " + countPoints(),
                cam.position.x -100, cam.position.y + cam.viewportHeight/2 -10);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        crash.dispose();
        font.dispose();
        for (com.flappy.mcesov.sprites.Tube tube : tubes){
            tube.dispose();
        }
    }

    private int countPoints(){
        if ((int)(bird.getPosition().x / (TUBE_SPACING+ com.flappy.mcesov.sprites.Tube.getTubeWidth()))<1) return 0;
        return (int)(bird.getPosition().x / (TUBE_SPACING+ com.flappy.mcesov.sprites.Tube.getTubeWidth()))-1;
    }
}
