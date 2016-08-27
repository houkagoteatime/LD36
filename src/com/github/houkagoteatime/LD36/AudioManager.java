package com.github.houkagoteatime.LD36;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class AudioManager implements Disposable{

	private AssetManager musicAssets;
	private float volume;
	private boolean mute;
	private String current;
	
	public AudioManager() {
		musicAssets = new AssetManager();
		volume = 0.5f;
		mute = false;
	}
	
	public void playMusic(String musicName) {
		if(mute || current != null && current.equals(musicName))
			return;
		stop();
		Music currentMusic = Assets.manager.get(musicName, Music.class);
		currentMusic.setVolume(volume);
		currentMusic.setLooping(true);
		currentMusic.play();
		current = musicName;
	}
	
	public void stop() {
		if(current != null)
			Assets.manager.get(current, Music.class).stop();
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
		if(current != null) {
			Assets.manager.get(current, Music.class).setVolume(volume);
		}
	}

	@Override
	public void dispose() {
		stop();
	}

}
