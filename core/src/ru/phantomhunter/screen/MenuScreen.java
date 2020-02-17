package ru.phantomhunter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img,img1;
    private Vector2 touch;
    private Vector2 speed;
    private Vector2 position;


    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        img1 = new Texture("StarsSky2048x1152.jpg");
        touch = new Vector2();
        speed = new Vector2();
        position = new Vector2(0,0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1.f, 1.f, 1.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (((position.y + img.getHeight())< Gdx.graphics.getHeight()) || (position.y ==-1) || (position.x ==-1) ||
                ((position.x + img.getWidth())< Gdx.graphics.getWidth())){
            position.add(speed);
        }
        batch.begin();
        batch.draw(img1, 0,0 , 640, 480);
        batch.draw(img, position.x, position.y);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        touch.set(screenX,Gdx.graphics.getHeight()- screenY);
        System.out.println("touch ("+ touch.x  +";" + touch.y);
        orientationFly(touch.x,touch.y);
        return false;
    }
    public void orientationFly (float x, float y){
        speed.set((position.x + x)*0.05f,(position.y + y)*0.05f);
    }
}
