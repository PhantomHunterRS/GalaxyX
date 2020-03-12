package ru.phantomhunter.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.phantomhunter.base.SpritesPool;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.sprite.EnemyShip;

public class EnemyPool  extends SpritesPool<EnemyShip> {
    private BulletPool bulletPool;
    private Sound soundShoot;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool,Sound soundShoot,Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.soundShoot = soundShoot;
        this.worldBounds = worldBounds;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool,soundShoot,worldBounds);
    }
}
