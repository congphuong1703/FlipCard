package npc.com.flipcard;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {


    private Bundle keys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    public void diaglog() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Xác nhận");
        b.setMessage("Bạn có đồng ý thoát chương trình không?");
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DialogFragment settingsDialog = SettingsDialog.newInstance();
    }


//    @OnClick(R.id.btnRank)
//    public void rank() {
//        startActivity(new Intent(this, Rank.class);
//    }

    @OnClick(R.id.btnStore)
    public void store() {
        Uri uri = Uri.parse("http://www.facebook.com/congphuong1703");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

//    @OnClick(R.id.btnSupport)
//    public void support() {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        DialogFragment supportDialog = SupportDialog.newInstance();
//    }
}
