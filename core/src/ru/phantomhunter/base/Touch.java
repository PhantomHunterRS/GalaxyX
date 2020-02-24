package ru.phantomhunter.base;

import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.math.Rect;

public interface Touch {
    public void resize(Rect worldBounds);
    public void touchDown(Vector2 touch, int pointer, int button);
    public void touchUp(Vector2 touch, int pointer, int button);
    public void touchDragged(Vector2 touch, int pointer);
    public void update (float delta);
}
