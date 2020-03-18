package ru.phantomhunter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import ru.phantomhunter.base.ScaledButton;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.screen.GameScreen;

public class NewGameStart extends ScaledButton {
    private GameScreen gameScreen;

    public NewGameStart(TextureAtlas atlas,GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.07f);
        setTop(0.0f);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
