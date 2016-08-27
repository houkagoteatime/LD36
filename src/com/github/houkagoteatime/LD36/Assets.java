package com.github.houkagoteatime.LD36;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

public class Assets {
	
	public static AssetManager manager = new AssetManager();
	public static void load() {
		manager.load("assets/music/Ho-kago Tea Time - U&I.mp3", Music.class);
	}
	
	

}
