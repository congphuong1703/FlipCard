package npc.com.flipcard.Adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.util.Random;

import npc.com.flipcard.R;


public class MusicAdapter {

    public static MediaPlayer mediaPlayerSound;
    public static MediaPlayer mediaPlayerMusic;
    public static boolean isPlayingMusic = true;
    public static boolean isPlayingSound = true;

    public static void playSound(Context context) {
        mediaPlayerSound = MediaPlayer.create(context, R.raw.sound);
        mediaPlayerSound.setVolume(50, 50);
        mediaPlayerSound.setLooping(true);
        mediaPlayerSound.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayerSound.start();
        isPlayingSound = true;
    }

    public static void stopSound() {
        if (mediaPlayerSound != null)
            mediaPlayerSound.stop();
        isPlayingSound = false;
    }

    public static void playMusic(Context context, int id) {
        mediaPlayerMusic = MediaPlayer.create(context, id);
        mediaPlayerMusic.setLooping(false);
        mediaPlayerMusic.setVolume(100, 100);
        mediaPlayerMusic.start();
        isPlayingMusic = true;
    }

    public static void stopMusic() {
        isPlayingMusic = false;
    }

    public static void runMusic() {
        isPlayingMusic = true;
    }

}
