package com.github.houkagoteatime.LD36;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

/**
 * Manager for music assets
 */
public class AudioManager implements Disposable{

	private float volume;
	private boolean mute;
	private String current;
	
	/**
	 * Initialize volume and mute
	 */
	public AudioManager() {
		volume = 0.5f;
		mute = false;
	}
	
	/**
	 * Play a song
	 * @param musicName the name of the music to play
	 */
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
	
	/**
	 * Stop music
	 */
	public void stop() {
		if(current != null)
			Assets.manager.get(current, Music.class).stop();
	}
	
	/**
	 * Set the sound level
	 * @param volume sound level
	 */
	public void setVolume(float volume) {
		this.volume = volume;
		if(current != null) {
			Assets.manager.get(current, Music.class).setVolume(volume);
		}
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.utils.Disposable#dispose()
	 */
	@Override
	public void dispose() {
		stop();
	}

}
