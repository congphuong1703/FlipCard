package npc.com.flipcard;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.util.Random;


public class MusicAdapter {

    public static MediaPlayer mediaPlayerSound;
    public static MediaPlayer mediaPlayerMusic;
    public static boolean isPlayingMusic = true;
    public static boolean isPlayingSound = true;

    public static void playSound(Context context) {
        int[] arr = new int[]{R.raw.soundtrack1, R.raw.soundtrack2, R.raw.soundtrack3, R.raw.soundtrack4, R.raw.soundtrack5};
        mediaPlayerSound = MediaPlayer.create(context, arr[new Random().nextInt(4)]);
        mediaPlayerSound.setVolume(50, 50);
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
        mediaPlayerSound.setVolume(100, 100);
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
