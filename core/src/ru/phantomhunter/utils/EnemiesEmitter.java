package ru.phantomhunter.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.math.Rect;
import ru.phantomhunter.pool.EnemyPool;
import ru.phantomhunter.sprite.EnemyShip;

public class EnemiesEmitter {
    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOADINTERVAL = 3f;
    private static final int ENEMY_SMALL_HEALTHPOINT = 1;

    private float generateIntervalEnemyShips = 4f;
    private float generateTimer;

    private final TextureRegion [] enemySmallRegions;

    private final Vector2 enemySmallSpeed = new Vector2(0f,-0.2f);
    private TextureRegion bulletRegion;
    private final EnemyPool enemyPool;

    public EnemiesEmitter(TextureAtlas atlas, EnemyPool enemyPool) {
        this.enemyPool = enemyPool;
        this.enemySmallRegions = Regions.split(atlas.findRegion("enemy0"),1,2,2);
        this. bulletRegion = atlas.findRegion("bulletEnemy");
    }
    public void generateEnemyShips(float delta){
        generateTimer +=delta;
        if (generateTimer >= generateIntervalEnemyShips){
            generateTimer = 0f;
            EnemyShip enemyShip = enemyPool.obtain();
            enemyShip.set(enemySmallRegions,
                    enemySmallSpeed,
                    bulletRegion,
                    ENEMY_SMALL_BULLET_HEIGHT,
                    ENEMY_SMALL_BULLET_VY,
                    ENEMY_SMALL_DAMAGE,
                    ENEMY_SMALL_RELOADINTERVAL,
                    ENEMY_SMALL_HEIGHT,
                    ENEMY_SMALL_HEALTHPOINT
                    );
        }
    }
}
