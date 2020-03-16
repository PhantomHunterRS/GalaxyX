package ru.phantomhunter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.phantomhunter.base.BaseScreen;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.math.Rnd;
import ru.phantomhunter.pool.BulletPool;
import ru.phantomhunter.pool.EnemyPool;
import ru.phantomhunter.pool.ExplosionPool;
import ru.phantomhunter.sprite.Background;
import ru.phantomhunter.sprite.EnemyShip;
import ru.phantomhunter.sprite.MyGameShip;
import ru.phantomhunter.sprite.Star;
import ru.phantomhunter.utils.EnemiesEmitter;


public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 100;
    private TextureAtlas atlas;
    private TextureAtlas atlasStars;
    private Star[] stars;
    private int x;
    private Texture imgBG2;
    private Background background;
    private MyGameShip myGameShip;
    private Music melodyGame;
    private Sound soundShootEnemy;
    private Sound soundBoom;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private EnemiesEmitter enemiesEmitter;

    @Override
    public void show() {
        super.show();
        imgBG2 = new Texture("textures/StarsSky2048x1152.jpg");
        background = new Background(imgBG2);


        atlasStars = new TextureAtlas(Gdx.files.internal("textures/menuGameAtlas.atlas"));
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        soundShootEnemy = Gdx.audio.newSound(Gdx.files.internal("music/OneShoot.mp3"));
        soundBoom = Gdx.audio.newSound(Gdx.files.internal("music/boom.mp3"));
        stars = new Star[STAR_COUNT];
        for (int i=0;i<STAR_COUNT;i++) {
            stars[i] = new Star(atlasStars, "star2");
            stars[i].getSpeedStar().set(Rnd.nextFloat(-0.001f, 0.001f),Rnd.nextFloat(-2f, -0.3f));
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas,soundBoom);
        enemyPool = new EnemyPool(bulletPool,explosionPool,soundShootEnemy,worldBounds);
        enemiesEmitter = new EnemiesEmitter(atlas,enemyPool,worldBounds);
        myGameShip = new MyGameShip(atlas,bulletPool,explosionPool);
        melodyGame = Gdx.audio.newMusic(Gdx.files.internal("music/cosmos.mp3"));
        melodyGame.setLooping(true);
        melodyGame.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star:stars) {
            star.resize(worldBounds);
        }
        myGameShip.resize(worldBounds);
    }

    @Override
    public void pause() {
       melodyGame.pause();
    }

    @Override
    public void resume() {
        melodyGame.play();
    }

    @Override
    public void dispose() {
        imgBG2.dispose();
        atlas.dispose();
        atlasStars.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        melodyGame.dispose();
        soundShootEnemy.dispose();
        soundBoom.dispose();
        myGameShip.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        myGameShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        myGameShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        myGameShip.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        myGameShip.touchUp(touch,pointer,button);
        return false;
    }
    private void update(float delta) {
        for (Star star:stars) {
            star.update(delta);
        }
        myGameShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemiesEmitter.generateEnemyShips(delta);
    }
    private void checkCollisions(){
        List<EnemyShip> enemyShipsList = enemyPool.getActiveObjects();
        for (EnemyShip one:enemyShipsList) {
            float minDist = myGameShip.getHalfWidth()+ one.getHalfWidth();
            if (myGameShip.pos.dst(one.pos) <= minDist){
                one.destroy();
            }
            
        }
    }


    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
    }

    private void draw(){
        Gdx.gl.glClearColor(1.f, 1.f, 1.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star:stars) {
            star.draw(batch);
        }
        myGameShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }
}
