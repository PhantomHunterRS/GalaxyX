package ru.phantomhunter.sprite;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import javax.swing.JButton;

import ru.phantomhunter.base.Ship;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.pool.BulletPool;
import ru.phantomhunter.pool.ExplosionPool;

public class EnemyShip extends Ship {
    private final Vector2 descentSpeed;

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool,Sound shootSound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = shootSound;
        this.worldBounds = worldBounds;
        this.speedShip = new Vector2();
        this.speedShipZero = new Vector2();
        this.bulletSpeed = new Vector2();
        this.bulletPosition = new Vector2();
        this.descentSpeed = new Vector2(0,-0.3f);
    }

    @Override
    public void update(float delta) {
        bulletPosition.set(pos.x, getBottom());
        super.update(delta);
        if (getTop()<worldBounds.getTop()){
        speedShip.set(speedShipZero);
        }else{
            this.reloadTimer = reloadInterval*0.85f;
        }
        if ((getTop()-getHeight()/1.5f) < worldBounds.getBottom()) {
            destroy();
        }
    }
    public void set(
            TextureRegion[] regions,
            Vector2 speedShipZero,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            float height,
            int healthPoint
    ){  this.regions = regions;
        this.speedShipZero.set(speedShipZero);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletSpeed.set(0,bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = 0f;
        setHeightProportion(height);
        this.healthPoint = healthPoint;
        speedShip.set(descentSpeed);
    }
    public boolean isBulletCollision (Rect bullet){
        return !(bullet.getRight() < getLeft() || bullet.getLeft() > getRight()
                || bullet.getBottom()>getTop() || bullet.getTop()< pos.y);
    }

}
