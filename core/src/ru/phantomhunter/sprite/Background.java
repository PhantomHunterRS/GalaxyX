package ru.phantomhunter.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.phantomhunter.base.Sprite;
import ru.phantomhunter.math.Rect;

public class Background extends Sprite {

    public Background(Texture region) {
        super(new TextureRegion(region));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
    }
}
