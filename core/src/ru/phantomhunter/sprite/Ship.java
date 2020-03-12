package ru.phantomhunter.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.base.Sprite;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.math.Rnd;
import ru.phantomhunter.pool.BulletPool;

public class Ship extends Sprite {
   private static final int INVALID_POINTER = -1;

    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private final Vector2 bulletSpeed;
    private final Vector2 bulletPosition;
    private final Vector2 speedShip = new Vector2();
    private final Vector2 speedShipZero = new Vector2(0.5f,0);

    private boolean pressedLeft;
    private boolean pressedRight;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer= INVALID_POINTER;

    private float reloadInterval;
    private float reloadTimer;

    private Sound shootSound;



    public Ship(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletSpeed = new Vector2(0,0.7f);
        this.bulletPosition = new Vector2();
        this.reloadInterval = 0.2f;
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

        pos.mulAdd(speedShip,delta);
        reloadTimer += delta;
        if(reloadTimer>=reloadInterval){
            reloadTimer=0;
            shooter();
        }
        if ( getRight() > worldBounds.getRight()+0.05f){
            setRight(worldBounds.getRight()+0.05f);
            stop();
        }if (getLeft() <worldBounds.getLeft()-0.05f){
            setLeft(worldBounds.getLeft()-0.05f);
            stop();
        }

    }
    public void dispose(){
        shootSound.dispose();
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

    private void shooter(){
        shootSound.play();
        Bullet bullet = bulletPool.obtain();
        bulletPosition.set(pos.x,getTop());
        bullet.set(this,bulletRegion,bulletPosition,bulletSpeed,0.01f,worldBounds,1);
    }
}

