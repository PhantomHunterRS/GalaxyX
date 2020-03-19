package ru.phantomhunter.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Font extends BitmapFont {
    public Font(String fontFilePath, String imageFilePath) {
        super(Gdx.files.internal(fontFilePath),Gdx.files.internal(imageFilePath),false,false );
        getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
    public void setSize (float sizeFont){
        getData().setScale(sizeFont/getCapHeight());
    }
}
