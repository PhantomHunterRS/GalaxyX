package ru.phantomhunter;

import com.badlogic.gdx.Game;

import ru.phantomhunter.screen.MenuScreen;


public class MyGameGalaxyX extends Game {


	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
