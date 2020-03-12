package ru.phantomhunter.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.base.Sprite;
import ru.phantomhunter.math.Rect;

public class Logo extends Sprite {
    private static final float V_LEN = 0.01f;
    private Vector2 touch;
    private Vector2 speed;
    private Vector2 buf;

    public Logo(Texture region) {
        super(new TextureRegion(region));
        touch = new Vector2();
        speed = new Vector2();
        buf = new Vector2();
    }


    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.5f);

    }
    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        speed.set(touch.sub(pos)).setLength(V_LEN);
    }

    @Override
    public void update(float delta) {
        buf.set(touch);
        if (buf.sub(pos).len()>V_LEN){
            pos.add(speed);
        }else {
            pos.set(touch);
        }
           }
}
