package npc.com.flipcard.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import npc.com.flipcard.Adapter.MusicAdapter;
import npc.com.flipcard.Database.DatabaseHelper;
import npc.com.flipcard.Model.GamerModel;
import npc.com.flipcard.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.input_name)
    EditText editName;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private MusicAdapter musicAdapter;
    final DatabaseHelper databaseHelper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editName.setText(sharedPreferences.getString("username", ""));

        musicAdapter.playSound(this);
    }

    @OnClick(R.id.btnContinue)
    public void goHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);

        try {
            String name = editName.getText().toString().trim();
            GamerModel gamerModel = databaseHelper.getByName(name);

            if (gamerModel == null) {
                GamerModel user = new GamerModel(name);
                databaseHelper.save(user);
            }

            editor.putString("username", name);
            editor.apply();
            startActivity(intent);
        } catch (Exception e) {
            editName.requestFocus();
            editName.setError("Đã xảy ra lỗi!");
        }
    }

}