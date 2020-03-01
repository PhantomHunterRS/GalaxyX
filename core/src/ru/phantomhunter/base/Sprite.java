package ru.phantomhunter.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.math.Rect;

public abstract class Sprite extends Rect {
    protected float angel;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;

    public Sprite(TextureRegion region) {
        if (region == null ){
            throw new RuntimeException("Do NOT SET THE TEXTURE");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public void draw(SpriteBatch batch){
        batch.draw(
                regions[frame],
                getLeft(),
                getBottom(),
                halfWidth,
                halfHeight,
                getWidth(),
                getHeight(),
                scale,scale,angel
        );

    }

    public void setHeightProportion(float height){
        setHeight(height);
        float aspect = regions[frame].getRegionWidth()/(float)regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }
    public void resize(Rect worldBounds){};
    public void touchDown(Vector2 touch, int pointer, int button) {};
    public void touchUp(Vector2 touch, int pointer, int button){};
    public void touchDragged(Vector2 touch, int pointer){};
    public void update (float delta){};

    public float getAngel() {
        return angel;
    }

    public void setAngel(float angel) {
        this.angel = angel;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

}
