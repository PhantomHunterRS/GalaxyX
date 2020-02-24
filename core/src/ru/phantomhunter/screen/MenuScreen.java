package ru.phantomhunter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.base.BaseScreen;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.sprite.Background;

public class MenuScreen extends BaseScreen {
    private Texture img,imgBG;
    private Vector2 firstPosition;

    private Background background;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        imgBG = new Texture("textures/Space1920x1200.jpg");
        firstPosition = new Vector2();
        background = new Background(imgBG);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1.f, 1.f, 1.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        batch.draw(img, firstPosition.x, firstPosition.y,0.5f,0.5f);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        imgBG.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
    }
}
