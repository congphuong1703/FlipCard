package npc.com.flipcard;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {


    private Bundle keys;
    MusicAdapter musicAdapter;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mediaPlayer = musicAdapter.mediaPlayerMusic;
        if (musicAdapter.isPlayingSound)
            musicAdapter.playSound(this);
    }

    public void dialogExit() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(R.string.confirm);
        b.setMessage(R.string.doYouExit);
        b.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
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

    @Override
    protected void onResume() {
        super.onResume();
        keys = getIntent().getExtras();
    }

    @OnClick(R.id.btnGo)
    public void playGame() {
        startActivity(new Intent(this, GameActivity.class));
    }

    @OnClick(R.id.btnSettings)
    public void settings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void onBackPressed() {
        this.dialogExit();
    }

//    @OnClick(R.id.btnRank)
//    public void rank() {
//        startActivity(new Intent(this, Rank.class);
//    }

    @OnClick(R.id.btnShop)
    public void goShop(View view) {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse("market://details?id=com.facebook.lite"));
        startActivity(viewIntent);
    }

//    @OnClick(R.id.btnSupport)
//    public void support() {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        DialogFragment supportDialog = SupportDialog.newInstance();
//    }
}
