package ru.phantomhunter.sprite;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.base.Ship;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.pool.BulletPool;

public class EnemyShip extends Ship {
    public EnemyShip(BulletPool bulletPool,Sound shootSound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
        this.worldBounds = worldBounds;
        this.speedShip = new Vector2();
        this.speedShipZero = new Vector2();
        this.bulletSpeed = new Vector2();
        this.bulletPosition = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
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
        setHeightProportion(height);
        speedShip.set(speedShipZero);

    }
}
