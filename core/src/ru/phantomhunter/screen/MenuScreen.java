package ru.phantomhunter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.base.BaseScreen;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.sprite.Background;
import ru.phantomhunter.sprite.Logo;

public class MenuScreen extends BaseScreen {
    private Texture imgLG,imgBG;
    private Vector2 firstPosition;
    private Vector2 speed;
    private Background background;
    private Logo logo;

    @Override
    public void show() {
        super.show();
        imgLG = new Texture("badlogic.jpg");
        imgBG = new Texture("textures/Space1920x1200.jpg");
        speed = new Vector2();
        background = new Background(imgBG);
        logo = new Logo(imgLG);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1.f, 1.f, 1.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        logo.update(delta);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        //firstPosition.add(speed);
        //batch.draw(img, firstPosition.x, firstPosition.y,0.5f,0.5f);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        imgBG.dispose();
        imgLG.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }
}
