package ru.phantomhunter.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.base.Sprite;
import ru.phantomhunter.math.Rect;

public class Logo extends Sprite {
    Rect worldBounds = new Rect();

    public Logo(Texture region) {
        super(new TextureRegion(region));
    }
    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        super.resize(worldBounds);
        setHeightProportion(0.5f);
        this.pos.set(worldBounds.pos);
    }
    public Vector2 touchDown(Vector2 touch, int pointer, int button) {
    Vector2 speed = new Vector2();
    speed.set(touch.sub(worldBounds.pos));
    return speed;
    };
}
