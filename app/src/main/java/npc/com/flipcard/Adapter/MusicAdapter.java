package npc.com.flipcard.Adapter;

import android.content.Context;
import android.media.AudioAttributes;
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
        mediaPlayerSound.setAudioAttributes(
                new AudioAttributes
                        .Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build());
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
        mediaPlayerSound.setAudioAttributes(
                new AudioAttributes
                        .Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build());
        mediaPlayerMusic.setVolume(50, 50);
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
