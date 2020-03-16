package ru.phantomhunter.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.math.Rect;
import ru.phantomhunter.pool.BulletPool;
import ru.phantomhunter.pool.ExplosionPool;
import ru.phantomhunter.sprite.Bullet;
import ru.phantomhunter.sprite.Explosion;

public class Ship extends Sprite {

    protected Vector2 speedShip;
    protected Vector2 speedShipZero;

    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletSpeed;
    protected Vector2 bulletPosition;
    protected int damage;
    protected float bulletHeight;
    protected float reloadInterval;
    protected float reloadTimer;
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
    @Override
    public void destroy() {
        super.destroy();
        this.healthPoint = 0;
        boom();
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(speedShip, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shooter();
        }
    }



    protected void shooter(){
        shootSound.play();
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,bulletRegion,bulletPosition,bulletSpeed,bulletHeight,worldBounds,damage);
    }
    protected void boom (){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(),pos);
    }
}