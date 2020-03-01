package ru.phantomhunter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.base.Sprite;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.math.Rnd;

public class Star extends Sprite {
    private static final float STAR_HEIGHT = 0.006f;

    public Vector2 getSpeedStar() {
        return speedStar;
    }
    private final Vector2 speedStar;

    private Rect worldBounds;
    private float animateTimer;
    private float animateInterval = 0.5f;

    public Star(TextureAtlas atlas,String name) {
        super(atlas.findRegion(name));
        speedStar = new Vector2();
        speedStar.set(Rnd.nextFloat(-0.005f, 0.005f),Rnd.nextFloat(-0.2f, -0.01f));
        animateTimer = Rnd.nextFloat(0,1f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(speedStar,delta);
        if (getRight()< worldBounds.getLeft()){
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()){
            setRight(worldBounds.getLeft());
        }
        if (getTop()< worldBounds.getBottom()){
            setBottom(worldBounds.getTop());
        }
        if (getBottom()> worldBounds.getTop()){
            setTop(worldBounds.getBottom());
        }
        animateTimer +=delta;
        if (animateTimer >= animateInterval){
            animateTimer=0;
            setHeightProportion(STAR_HEIGHT);
        }else {
            setHeightProportion(getHeight()+ 0.0002f);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
            setHeightProportion(STAR_HEIGHT);
        float positionStarX = Rnd.nextFloat(worldBounds.getLeft(),worldBounds.getRight());
        float positionStarY = Rnd.nextFloat(worldBounds.getBottom(),worldBounds.getTop());
        pos.set(positionStarX,positionStarY);
        this.worldBounds = worldBounds;
    }


}
