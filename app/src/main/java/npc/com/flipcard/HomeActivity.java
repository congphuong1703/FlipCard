package npc.com.flipcard;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Intent.ACTION_GET_CONTENT;
import static android.content.Intent.ACTION_VIEW;

public class HomeActivity extends AppCompatActivity {


    private Bundle keys;
    MusicAdapter musicAdapter;
    private MediaPlayer mediaPlayer;

    @BindView(R.id.flag_play)
    CardView cardFlag;

    @BindView(R.id.pikachu_play)
    CardView cardPikachu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mediaPlayer = musicAdapter.mediaPlayerSound;

        if (musicAdapter.isPlayingSound)
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                musicAdapter.playSound(this);
            }
    }

    public void dialogExit() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(R.string.confirm);
        b.setMessage(R.string.doYouExit);
        b.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
                System.exit(0);
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

    @OnClick(R.id.flag_play)
    public void onFlag() {
        playGame("flag");

    }

    @OnClick(R.id.pikachu_play)
    public void onPikachu() {
        playGame("pokemon");
    }

    public void playGame(String typeGame) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.setAction(ACTION_VIEW);
        intent.putExtra("game", typeGame);
        startActivity(intent);
    }

    @OnClick(R.id.cardSettings)
    public void settings() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.setAction(ACTION_VIEW);
        startActivity(intent);
    }

    public void onBackPressed() {
        this.dialogExit();
    }

    @OnClick(R.id.cardStore)
    public void onShop(View view) {
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

    @OnClick(R.id.cardExit)
    public void onExit() {
        dialogExit();
    }

}
