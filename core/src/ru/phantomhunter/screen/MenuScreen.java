package ru.phantomhunter.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.phantomhunter.base.BaseScreen;
import ru.phantomhunter.math.Rect;
import ru.phantomhunter.sprite.Background;
import ru.phantomhunter.sprite.ButtonExit;
import ru.phantomhunter.sprite.ButtonPlay;
import ru.phantomhunter.sprite.Star;
import ru.phantomhunter.utils.Font;

public class MenuScreen extends BaseScreen {
    private static final int STAR_COUNT = 300;
    private static final float SIZE_FONT = 0.02f;
    private static final float SIZE_FONT_NAME_GAME = 0.07f;
    private final String DEVELOPER = "by PhantomHunter";
    private final String GAMENAME = "GALAXY X";
    private final Game game;
    private TextureAtlas atlas;
    private Texture imgBG;
    private Background background;
    private Star[] stars;
    private int x;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;
    private Font fontsMenu;
    private Font fontsGameName;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        imgBG = new Texture("textures/Space1920x1200.jpg");
        background = new Background(imgBG);
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuGameAtlas.atlas"));
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT ; i++) {
            //stars[i] = new Star(atlas);
            x = i % 2;
          if (i%50 == 0){
              stars[i] = new Star(atlas, "star3");
          }else {
              switch (x) {
                  case (0):
                      stars[i] = new Star(atlas, "star2");
                      break;
                  case (1):
                      stars[i] = new Star(atlas, "star1");
                      break;
              }
          }
        }
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas,game) ;
        fontsMenu = new Font("fonts/gothic.fnt","fonts/gothic.png");
        fontsMenu.setSize(SIZE_FONT);
        fontsGameName = new Font("fonts/PoorRichard.fnt","fonts/PoorRichard.png");
        fontsGameName.setSize(SIZE_FONT_NAME_GAME);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }
    @Override
    public void dispose() {
        imgBG.dispose();
        atlas.dispose();
        fontsMenu.dispose();
        fontsGameName.dispose();
        super.dispose();
    }
    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star:stars) {
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch,pointer,button);
        buttonPlay.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch,pointer,button);
        buttonPlay.touchUp(touch,pointer,button);
        return false;
    }

    private void update(float delta) {
        for (Star star:stars) {
            star.update(delta);
        }
    }


    private void draw(){
        Gdx.gl.glClearColor(1.f, 1.f, 1.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star:stars) {
            star.draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        fontsMenu.draw(batch,DEVELOPER,worldBounds.getRight()-0.25f,worldBounds.getBottom()+0.05f);
        fontsGameName.draw(batch,GAMENAME,worldBounds.pos.x-0.25f,worldBounds.pos.y+0.1f);
        batch.end();
    }
}
