package ru.phantomhunter.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.math.Rect;
import ru.phantomhunter.pool.BulletPool;
import ru.phantomhunter.sprite.Bullet;

public class Ship extends Sprite {

    protected Vector2 speedShip;
    protected Vector2 speedShipZero;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletSpeed;
    protected Vector2 bulletPosition;
    protected float bulletHeight;
    protected float reloadInterval;
    protected float reloadTimer;
    protected int damage;
    protected int healthPoint;

    protected Sound shootSound;

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }
    public void dispose(){
        shootSound.dispose();
    }


    protected void shooter(){
        shootSound.play();
        Bullet bullet = bulletPool.obtain();
        bulletPosition.set(pos.x,getTop());
        bullet.set(this,bulletRegion,bulletPosition,bulletSpeed,bulletHeight,worldBounds,damage);
    }
}