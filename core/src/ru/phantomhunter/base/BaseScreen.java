package ru.phantomhunter.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.math.MatrixUtils;
import ru.phantomhunter.math.Rect;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    private Rect screenBounds;
    private Rect worldBounds;
    private Rect glBounds;

    private Matrix4 worldToGL;
    private Vector2 touch;
    private Matrix3 screenToWorld;

    @Override
    public void show() {
        System.out.println("show");
        batch = new SpriteBatch();
        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0,0,1f,1f);
        touch = new Vector2();
        worldToGL = new Matrix4();
        screenToWorld = new Matrix3();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        //System.out.println("resize " + width +"x" + height);
        screenBounds.setSize(width,height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);
        worldBounds.setHeight(1f);
        float aspect = width / (float) height;
        worldBounds.setWidth(1f*aspect);
        MatrixUtils.calcTransitionMatrix(worldToGL,worldBounds,glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld,screenBounds,worldBounds);
        batch.setProjectionMatrix(worldToGL);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {
        System.out.println("game coordinates (" + worldBounds.getWidth() +":" + worldBounds.getHeight()+")");

    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown - " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp - "+ keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped - " +character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown(" + screenX + ";" + screenY +")");
        touch.set(screenX,screenBounds.getHeight()-screenY).mul(screenToWorld);
        touchDown(touch,pointer,button);
        return false;
    }
    public boolean touchDown(Vector2 touch,int pointer, int button) {
        System.out.println("touchDown(" + touch.x + ";" + touch.y +")");
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp(" + screenX + ";" + screenY +")");
        touch.set(screenX,screenBounds.getHeight()-screenY).mul(screenToWorld);
        touchUp(touch,pointer,button);
        return false;
    }
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        System.out.println("touchUp(" + touch.x + ";" + touch.y +")");
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged(" + screenX + ";" + screenY +")");
        touch.set(screenX,screenBounds.getHeight()-screenY).mul(screenToWorld);
        touchDragged(touch,pointer);
        return false;
    }
    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("touchDragged(" + touch.x + ";" + touch.y +")");
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (amount<0) {
            System.out.println("scrolled up");
        }else {
            System.out.println("scrolled down");
        }
        return false;
    }
}
