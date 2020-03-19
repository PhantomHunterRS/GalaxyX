package ru.phantomhunter.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.phantomhunter.base.Sprite;
import ru.phantomhunter.math.Rect;

public class GameOver extends Sprite{
    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.1f);
        setBottom(0.2f);
    }
}
