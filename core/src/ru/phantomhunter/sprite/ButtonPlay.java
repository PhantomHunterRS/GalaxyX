package ru.phantomhunter.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.phantomhunter.base.ScaledButton;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.screen.GameScreen;

public class ButtonPlay extends ScaledButton {
    private static final float PADDING = 0.06f;
    private final Game game;

    public ButtonPlay(TextureAtlas atlas,Game game) {
        super(atlas.findRegion("start"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.21f);
        setLeft(worldBounds.getLeft()+ PADDING);
        setBottom(worldBounds.getBottom()+ PADDING);
    }

    @Override
    public void action() {
    game.setScreen(new GameScreen());
    }

}
