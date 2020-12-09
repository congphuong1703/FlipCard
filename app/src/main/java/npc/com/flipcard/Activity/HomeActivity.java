package npc.com.flipcard.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import npc.com.flipcard.Adapter.MusicAdapter;
import npc.com.flipcard.Database.DatabaseHelper;
import npc.com.flipcard.Model.GamerModel;
import npc.com.flipcard.R;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.point_highest)
    TextView pointHighest;

    @BindView(R.id.point_current)
    TextView pointCurrent;

    @BindView(R.id.full_name)
    TextView fullName;

    @BindView(R.id.rank)
    TextView rank;

    private Bundle keys;
    MusicAdapter musicAdapter;
    private MediaPlayer mediaPlayer;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    final DatabaseHelper databaseHelper = new DatabaseHelper(this);
    List<GamerModel> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        GamerModel gamerModel = databaseHelper.getByName(sharedPreferences.getString("username", "Unknown"));
        fullName.setText(gamerModel.getName());
        gamerModel.setScoreHighest();
        pointCurrent.setText(Integer.toString(gamerModel.getScoreCurrent()));
        pointHighest.setText(Integer.toString(gamerModel.getScoreHighest()));

        users = databaseHelper.getAll();
        rank.setText("Top " + this.rankOrder(users, gamerModel.getName()));
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
        finish();
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("game", typeGame);
        startActivity(intent);
    }

    @OnClick(R.id.cardSettings)
    public void settings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        finish();
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

    public int rankOrder(List<GamerModel> list, String name) {

        for (int i = 0; i < list.size(); i++) {
            if (name.equals(list.get(i).getName())) {
                return i + 1;
            }
        }

        return 0;
    }

    @OnClick(R.id.full_name)
    public void rename() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @OnClick(R.id.rank)
    public void onTop() {
        startActivity(new Intent(this, TopActivity.class));
    }
}
