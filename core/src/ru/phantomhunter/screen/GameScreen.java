package ru.phantomhunter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.sun.corba.se.impl.ior.ObjectAdapterIdNumber;

import ru.phantomhunter.base.BaseScreen;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.math.Rnd;
import ru.phantomhunter.pool.BulletPool;
import ru.phantomhunter.sprite.Background;
import ru.phantomhunter.sprite.Bullet;
import ru.phantomhunter.sprite.Ship;
import ru.phantomhunter.sprite.Star;


public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 100;
    private TextureAtlas atlas;
    private TextureAtlas atlasStars;
    private Star[] stars;
    private int x;
    private Texture imgBG2;
    private Background background;
    private Ship ship;
    private Music melodyGame;
    private Sound shootSound;

    private BulletPool bulletPool;

    @Override
    public void show() {
        super.show();
        imgBG2 = new Texture("textures/StarsSky2048x1152.jpg");
        background = new Background(imgBG2);
        melodyGame = Gdx.audio.newMusic(Gdx.files.internal("music/cosmos.mp3"));
        //shootSound = Gdx.audio.newSound(Gdx.files.internal("data/mysound.mp3"));
        atlasStars = new TextureAtlas(Gdx.files.internal("textures/menuGameAtlas.atlas"));
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        bulletPool = new BulletPool();
        ship = new Ship(atlas,bulletPool);
        stars = new Star[STAR_COUNT];
        for (int i=0;i<STAR_COUNT;i++) {
            stars[i] = new Star(atlasStars, "star2");
            stars[i].getSpeedStar().set(Rnd.nextFloat(-0.001f, 0.001f),Rnd.nextFloat(-2f, -0.3f));
        }
        melodyGame.play();
        melodyGame.setLooping(true);
    }

    @Override
    public void render(float delta) {
        update(delta);
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
        ship.resize(worldBounds);
    }

    @Override
    public void dispose() {
        imgBG2.dispose();
        atlas.dispose();
        bulletPool.dispose();
        atlasStars.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        ship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        ship.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        ship.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        ship.touchUp(touch,pointer,button);
        return false;
    }
    private void update(float delta) {
        for (Star star:stars) {
            star.update(delta);
        }
        ship.update(delta);
        bulletPool.updateActiveSprites(delta);
    }
    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
    }

    private void draw(){
        Gdx.gl.glClearColor(1.f, 1.f, 1.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star:stars) {
            star.draw(batch);
        }
        ship.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }
}
