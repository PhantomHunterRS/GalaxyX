package ru.phantomhunter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.base.BaseScreen;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.sprite.Background;
import ru.phantomhunter.sprite.Star1;

public class MenuScreen extends BaseScreen {
    private TextureAtlas atlas;
    private Texture imgBG;
    private Background background;
    private Star1 star1;


    @Override
    public void show() {
        super.show();
        imgBG = new Texture("textures/Space1920x1200.jpg");
        background = new Background(imgBG);
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        star1 = new Star1(atlas);
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
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    private void update(float delta) {
        star1.update(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        star1.resize(worldBounds);
    }
    private void draw(){
        Gdx.gl.glClearColor(1.f, 1.f, 1.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        star1.draw(batch);
        batch.end();
    }
}
