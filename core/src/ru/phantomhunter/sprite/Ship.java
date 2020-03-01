package ru.phantomhunter.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.base.Sprite;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.math.Rnd;

public class Ship extends Sprite {
    private Rect worldBounds;

    public Ship(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        super.touchDown(touch, pointer, button);
    }

    @Override
    public void touchUp(Vector2 touch, int pointer, int button) {
        super.touchUp(touch, pointer, button);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom()+0.05f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

    }
}
