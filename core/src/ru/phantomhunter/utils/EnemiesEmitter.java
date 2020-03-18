package ru.phantomhunter.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.math.Rect;
import ru.phantomhunter.math.Rnd;
import ru.phantomhunter.pool.EnemyPool;
import ru.phantomhunter.sprite.EnemyShip;

public class EnemiesEmitter {
    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.015f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOADINTERVAL = 2f;
    private static final int ENEMY_SMALL_HEALTHPOINT = 1;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.025f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.3f;
    private static final int ENEMY_MEDIUM_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOADINTERVAL = 2f;
    private static final int ENEMY_MEDIUM_HEALTHPOINT = 5;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.035f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOADINTERVAL = 1.5f;
    private static final int ENEMY_BIG_HEALTHPOINT = 10;
    private Rect worldBounds;

    private float generateIntervalEnemyShips = 4f;
    private float generateTimer;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMediumRegions;
    private final TextureRegion[] enemyBigRegions;

    private final Vector2 enemySmallSpeed = new Vector2(0f,-0.2f);
    private final Vector2 enemyMediumSpeed = new Vector2(0f, -0.03f);
    private final Vector2 enemyBigSpeed = new Vector2(0f, -0.005f);

    private TextureRegion bulletRegion;
    private final EnemyPool enemyPool;

    public EnemiesEmitter(TextureAtlas atlas, EnemyPool enemyPool,Rect worldBounds) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
        this.enemySmallRegions = Regions.split(atlas.findRegion("enemy0"),1,2,2);
        this.enemyMediumRegions = Regions.split(atlas.findRegion("enemy1"),1,2,2);
        this.enemyBigRegions = Regions.split(atlas.findRegion("enemy2"),1,2,2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
    }
    public void generateEnemyShips(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateIntervalEnemyShips) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
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
            }else if (type <0.8f){
                enemyShip.set(enemyMediumRegions,
                        enemyMediumSpeed,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY,
                        ENEMY_MEDIUM_DAMAGE,
                        ENEMY_MEDIUM_RELOADINTERVAL,
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_HEALTHPOINT
                );
            }else {
                enemyShip.set(enemyBigRegions,
                        enemyBigSpeed,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_DAMAGE,
                        ENEMY_BIG_RELOADINTERVAL,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_HEALTHPOINT
                );
            }
                System.out.println("Enemy Starts");
                enemyShip.setBottom(worldBounds.getTop());
                enemyShip.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemyShip.getHalfWidth(), worldBounds.getRight() - enemyShip.getHalfWidth());

        }
    }
}
