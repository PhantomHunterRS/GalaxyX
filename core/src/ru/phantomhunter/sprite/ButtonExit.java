package ru.phantomhunter.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.phantomhunter.base.ScaledButton;
import ru.phantomhunter.math.Rect;

public class ButtonExit extends ScaledButton {

    private static final float PADDING = 0.07f;

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("stop"));
    }

    @Override
    public void resize(Rect worldBounds) {
       setHeightProportion(0.2f);
       setRight(worldBounds.getRight()- PADDING);
       setBottom(worldBounds.getBottom()+ PADDING);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
