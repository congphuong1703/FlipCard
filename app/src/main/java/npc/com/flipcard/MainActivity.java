package npc.com.flipcard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar bar;

    @BindView(R.id.btnStart)
    Button startBtn;

    private ObjectAnimator progressAnimation;
    private MusicAdapter musicAdapter;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mediaPlayer = musicAdapter.mediaPlayerMusic;
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            if(musicAdapter.isPlayingSound)
                musicAdapter.playSound(this);
        }

        init();

        //30 seconds
        progressAnimation.setDuration(60 * 30);

        progressAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationStart(animation);

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @OnClick(R.id.btnStart)
    public void goHome(View view) {
        progressAnimation.start();
        Toast.makeText(getBaseContext(), R.string.loading, Toast.LENGTH_SHORT).show();
    }

    private void init() {
        progressAnimation = ObjectAnimator.ofInt(bar, "progress", 0, 100);
    }

}