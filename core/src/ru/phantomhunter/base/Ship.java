package ru.phantomhunter.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.math.Rect;
import ru.phantomhunter.pool.BulletPool;

public class Ship extends Sprite {

    protected Vector2 speedShip);
    protected Vector2 speedShipZero;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletSpeed;
    protected Vector2 bulletPosition;

    private float reloadInterval;
    private float reloadTimer;

    private Sound shootSound;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }
}
