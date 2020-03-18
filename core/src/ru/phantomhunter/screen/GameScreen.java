package ru.phantomhunter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.List;

import ru.phantomhunter.base.BaseScreen;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.math.Rnd;
import ru.phantomhunter.pool.BulletPool;
import ru.phantomhunter.pool.EnemyPool;
import ru.phantomhunter.pool.ExplosionPool;
import ru.phantomhunter.sprite.Background;
import ru.phantomhunter.sprite.Bullet;
import ru.phantomhunter.sprite.EnemyShip;
import ru.phantomhunter.sprite.GameOver;
import ru.phantomhunter.sprite.MyGameShip;
import ru.phantomhunter.sprite.Star;
import ru.phantomhunter.utils.EnemiesEmitter;
import sun.print.PSPrinterJob;


public class GameScreen extends BaseScreen {
    private enum State {
        PLAYING,PAUSE,GAME_OVER
    }
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
    private State state;
    private State pervState;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private EnemiesEmitter enemiesEmitter;
    private GameOver gameOver;

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
        gameOver = new GameOver(atlas);
        state = State.PLAYING;
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
        gameOver.resize(worldBounds);
    }

    @Override
    public void pause() {
       melodyGame.pause();
       pervState = state;
       state = State.PAUSE;
    }

    @Override
    public void resume() {
        melodyGame.play();
        state = pervState;
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
        if (state == State.PLAYING) {
            myGameShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
        myGameShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING){
            myGameShip.touchDown(touch, pointer, button);
    }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING){
        myGameShip.touchUp(touch,pointer,button);
        }
        return false;
    }
    private void update(float delta) {
        for (Star star:stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING ) {
            myGameShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemiesEmitter.generateEnemyShips(delta);
        }
    }
    private void checkCollisions(){
        if (state != State.PLAYING) {
            return;
        }
        List<EnemyShip> enemyShipsList = enemyPool.getActiveObjects();
        for (EnemyShip one:enemyShipsList) {
            float minDist = myGameShip.getHalfWidth()+ one.getHalfWidth();
            if (myGameShip.pos.dst(one.pos) <= minDist){
                one.destroy();
                myGameShip.damage(one.getDamage());
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet oneShoot:bulletList) {
            if (oneShoot.getOwner() != myGameShip){
                if (myGameShip.isBulletCollision(oneShoot)){
                    myGameShip.damage(oneShoot.getDamage());
                    oneShoot.destroy();
                }
                continue;
            }
            for (EnemyShip oneEnemyShip:enemyShipsList ) {
                if (oneEnemyShip.isBulletCollision(oneShoot)){
                    oneEnemyShip.damage(oneShoot.getDamage());
                    oneShoot.destroy();
                }
            }
        }
        if (myGameShip.isDestroyed()){
            state = State.GAME_OVER;
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
        explosionPool.drawActiveSprites(batch);
            if (state == State.PLAYING) {
                myGameShip.draw(batch);
                bulletPool.drawActiveSprites(batch);
                enemyPool.drawActiveSprites(batch);
            }else if(state == State.GAME_OVER) {
                gameOver.draw(batch);
            }
        batch.end();
    }
}
