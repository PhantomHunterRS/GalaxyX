package ru.phantomhunter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class TrackingStar extends Star{

    private final Vector2 trackingSpeed;
    private final Vector2 summSpeed = new Vector2();


    public TrackingStar(TextureAtlas atlas,String name,Vector2 trackingSpeed) {
        super(atlas, name);
        this.trackingSpeed = trackingSpeed;
    }

    @Override
    public void update(float delta) {
        summSpeed.setZero().mulAdd(trackingSpeed,0.2f).rotate(180).add(speedStar);
        pos.mulAdd(summSpeed,delta);
        checkAndHandleBounds();
    }
}
