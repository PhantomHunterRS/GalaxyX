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
import ru.phantomhunter.sprite.NewGameStart;
import ru.phantomhunter.sprite.Star;
import ru.phantomhunter.utils.EnemiesEmitter;
import ru.phantomhunter.utils.Font;
import sun.print.PSPrinterJob;


public class GameScreen extends BaseScreen {
    private enum State {
        PLAYING,PAUSE,GAME_OVER
    }
    private static final int STAR_COUNT = 100;
    private static final float SIZE_FONT = 0.03f;
    private final int HEATH_POINT = 10;
    private static final String ENEMY_SHIP_KILLED = "Frags:";
    private static final String HEATHPOINT = "HP:";
    private static final String LEVEL_GAME = "Level:";

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
    private NewGameStart newGameStart;
    private Font fonts;
    private int enemyShipKilled;
    private StringBuilder stringBuilderEnemyKilled;
    private int heathPoint;
    private StringBuilder stringBuilderHeathPoint;
    private int levelGame;
    private StringBuilder stringBuilderLevel;

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
        newGameStart = new NewGameStart(atlas,this);
        state = State.PLAYING;
        fonts = new Font("fonts/TimesNewRoman.fnt","fonts/TimesNewRoman.png");
        fonts.setSize(SIZE_FONT);
        enemyShipKilled = 0;
        heathPoint = HEATH_POINT;
        levelGame = 1;
        stringBuilderEnemyKilled = new StringBuilder();
        stringBuilderHeathPoint = new StringBuilder();
        stringBuilderLevel = new StringBuilder();
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
        newGameStart.resize(worldBounds);
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
        fonts.dispose();
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
    }else if(state == State.GAME_OVER)
            newGameStart.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING){
        myGameShip.touchUp(touch,pointer,button);
        }
        else if(state == State.GAME_OVER)
            newGameStart.touchUp(touch, pointer, button);
        return false;
    }
    public void startNewGame(){
        state = State.PLAYING;
        enemyShipKilled = 0;
        levelGame = 1;
        heathPoint = HEATH_POINT;
        myGameShip.startNewGame();
        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }



    private void update(float delta) {
        for (Star star:stars) {
            if (state != State.GAME_OVER) {
                star.update(delta);
            }else {
                star.update(delta*0.2f);
            }
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING ) {
            myGameShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemiesEmitter.generateEnemyShips(delta,enemyShipKilled);
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
                enemyShipKilled++;
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
                    if (oneEnemyShip.isDestroyed()){
                      enemyShipKilled++;
                    }
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
                printInto();
            }else if(state == State.GAME_OVER) {
                gameOver.draw(batch);
                newGameStart.draw(batch);
            }

        batch.end();
    }
    private void printInto(){
        stringBuilderEnemyKilled.setLength(0);
        stringBuilderHeathPoint.setLength(0);
        stringBuilderLevel.setLength(0);
        fonts.draw(batch,stringBuilderEnemyKilled.append(ENEMY_SHIP_KILLED).append(enemyShipKilled),worldBounds.getLeft()+0.01f,worldBounds.getTop()-0.01f);
        fonts.draw(batch,stringBuilderLevel.append(LEVEL_GAME).append(enemiesEmitter.getLevel()),worldBounds.pos.x-0.07f,worldBounds.getTop() - 0.01f);
        fonts.draw(batch,stringBuilderHeathPoint.append(HEATHPOINT).append(myGameShip.getHealthPoint()),worldBounds.getRight()-0.12f,worldBounds.getTop()-0.01f);

    }
}
