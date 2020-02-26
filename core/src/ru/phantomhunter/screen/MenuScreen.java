package ru.phantomhunter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.base.BaseScreen;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.sprite.Background;
import ru.phantomhunter.sprite.Star;

public class MenuScreen extends BaseScreen {
    private static final int STAR_COUNT = 256;
    private TextureAtlas atlas;
    private Texture imgBG;
    private Background background;
    private Star[] stars;

    @Override
    public void show() {
        super.show();
        imgBG = new Texture("textures/Space1920x1200.jpg");
        background = new Background(imgBG);
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuGameAtlas.pack"));
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < 256 ; i++) {
            stars[i] = new Star(atlas);

//            switch (i/4){
//                case (0): stars[i] = new Star(atlas,"star1");
//                    break;
//                case (1): stars[i] = new Star(atlas,"star2");
//                    break;
//                case (2): stars[i] = new Star(atlas,"star3");
//                    break;
//                case (3): stars[i] = new Star(atlas,"star4");
//                    break;
//            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }
    @Override
    public void dispose() {
        imgBG.dispose();
        atlas.dispose();
        super.dispose();
    }
    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star:stars) {
            star.resize(worldBounds);
        }
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    private void update(float delta) {
        for (Star star:stars) {
            star.update(delta);
        }
    }


    private void draw(){
        Gdx.gl.glClearColor(1.f, 1.f, 1.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star:stars) {
            star.draw(batch);
        }
        batch.end();
    }
}
