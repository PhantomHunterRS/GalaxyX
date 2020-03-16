package ru.phantomhunter.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.base.Ship;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.pool.BulletPool;
import ru.phantomhunter.pool.ExplosionPool;

public class MyGameShip extends Ship {
    private static final int INVALID_POINTER = -1;

    private boolean pressedLeft;
    private boolean pressedRight;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer= INVALID_POINTER;

    public MyGameShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.speedShip = new Vector2();
        this.speedShipZero = new Vector2(0.5f,0);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletSpeed = new Vector2(0,0.6f);
        this.bulletPosition = new Vector2();
        this.bulletHeight =0.01f;
        this.damage = 1;
        this.reloadInterval = 0.2f;
        this.healthPoint = 100;
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("music/laserShoot.mp3"));
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

    }

    @Override
    public void update(float delta) {
        bulletPosition.set(pos.x, getTop());
        super.update(delta);
        if ( getRight() > worldBounds.getRight()+0.05f){
            setRight(worldBounds.getRight()+0.05f);
            stop();
        }if (getLeft() <worldBounds.getLeft()-0.05f){
            setLeft(worldBounds.getLeft()-0.05f);
            stop();
        }
    }
    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x){
            if (leftPointer != INVALID_POINTER){
                return;
            }
            leftPointer = pointer;
            moveLeft();
        }else {
            if ( rightPointer != INVALID_POINTER){
                return;
            }
            rightPointer = pointer;
            moveRight();
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer, int button) {
        if (pointer  == leftPointer){
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER){
                moveRight();
            }else{
                stop();
            }
        }else if ( pointer == rightPointer){
            rightPointer = INVALID_POINTER;
            if ( leftPointer !=INVALID_POINTER){
                moveLeft();
            }else {
                stop();
            }
        }
    }

    public void keyDown( int keycode){
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shooter();
        }
    }
    public void keyUp( int keycode){
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight){
                    moveRight();
                }else{
                    stop();
                }
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft){
                    moveLeft();
                }else{
                    stop();
                }
                break;
        }
    }
    private void moveRight (){
      speedShip.set(speedShipZero);
    }
    private void moveLeft (){
        speedShip.set(speedShipZero).rotate(180);
    }
    private void stop(){
        speedShip.setZero();
    }


}

