package ru.phantomhunter.pool;

import com.badlogic.gdx.audio.Sound;

import ru.phantomhunter.base.SpritesPool;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.sprite.EnemyShip;

public class EnemyPool  extends SpritesPool<EnemyShip> {
    private BulletPool bulletPool;
    private  ExplosionPool explosionPool;
    private Sound soundShoot;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool,ExplosionPool explosionPool,Sound soundShoot,Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.soundShoot = soundShoot;
        this.worldBounds = worldBounds;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool,explosionPool,soundShoot,worldBounds);
    }
}
