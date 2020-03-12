package ru.phantomhunter.pool;

import ru.phantomhunter.base.SpritesPool;
import ru.phantomhunter.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
