package npc.com.flipcard.Activity;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import npc.com.flipcard.Adapter.MusicAdapter;
import npc.com.flipcard.Model.GamerModel;
import npc.com.flipcard.R;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.imgSound)
    ImageView imgSound;

    @BindView(R.id.imgMusic)
    ImageView imgMusic;

    @BindView(R.id.tvPlayContinue)
    TextView tvPlayContinue;

    @BindView(R.id.ivPlayContinue)
    ImageView ivPlayContinue;

    Button btnClose;
    ImageView ivClose;
    Dialog guideDialog;
    private Bundle keys;
    private MusicAdapter musicAdapter;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        guideDialog = new Dialog(this);
        mediaPlayer = musicAdapter.mediaPlayerSound;


        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        keys = getIntent().getExtras();

        if (keys != null) {
            if (!(keys.getString("state").equals(null))) {
                tvPlayContinue.setText(R.string.playContinue);
                ivPlayContinue.setImageResource(R.drawable.pause);
            }
        }

        if (musicAdapter.isPlayingSound)
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                musicAdapter.playSound(this);
            }

        if (!musicAdapter.isPlayingMusic)
            imgSound.setImageResource(R.drawable.music_mute);

        if (!musicAdapter.isPlayingSound)
            imgSound.setImageResource(R.drawable.sound_mute);
    }


    @Override
    protected void onResume() {
        super.onResume();
        keys = getIntent().getExtras();
    }

    @OnClick(R.id.playContinue)
    public void onPlay() {
        //check state subway
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = manager.getRunningTasks(5);
        Log.d("MAIN", "" + list.size());

        //state resume
        if (list.size() >= 2) {
            Intent resumeGame = new Intent(this, GameActivity.class);
            resumeGame.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(resumeGame);
            finish();

        } else {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("game", "pokemon");
            startActivity(intent);
        }
    }

    @OnClick(R.id.home)
    public void onHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.store)
    public void onStore() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(R.string.confirm);
        b.setMessage(R.string.doYouIntent);
        b.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("market://details?id=com.facebook.lite"));
                startActivity(viewIntent);
            }
        });
        b.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog al = b.create();
        al.show();
    }

    @OnClick(R.id.guide)
    public void onGuide() {
        ShowGuideDialog();
    }

    private void ShowGuideDialog() {
        guideDialog.setContentView(R.layout.guide_dialog);
        guideDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = guideDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.AlertDialogTheme;

        btnClose = guideDialog.findViewById(R.id.btnClose);
        ivClose = guideDialog.findViewById(R.id.ivClose);

        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                guideDialog.dismiss();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                guideDialog.dismiss();
            }
        });

        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        guideDialog.show();
    }

    @OnClick(R.id.sound)
    public void onSound() {
        if (!musicAdapter.isPlayingSound)
            this.playSound();
        else
            this.muteSound();
    }

    @OnClick(R.id.music)
    public void onMusic() {
        if (musicAdapter.isPlayingMusic)
            muteMusic();
        else
            this.playMusic();
    }

    private void muteSound() {
        musicAdapter.stopSound();
        imgSound.setImageResource(R.drawable.sound_mute);
    }

    private void muteMusic() {
        musicAdapter.stopMusic();
        imgMusic.setImageResource(R.drawable.music_mute);
    }

    private void playMusic() {
        musicAdapter.runMusic();
        imgMusic.setImageResource(R.drawable.music);
    }

    private void playSound() {
        musicAdapter.playSound(this);
        imgSound.setImageResource(R.drawable.sound);
    }

}
