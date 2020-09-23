package npc.com.flipcard.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import npc.com.flipcard.Adapter.TopAdapter;
import npc.com.flipcard.Database.DatabaseHelper;
import npc.com.flipcard.Model.GamerModel;
import npc.com.flipcard.R;

public class TopActivity extends AppCompatActivity {

    @BindView(R.id.listTop)
    ListView listView;

    @BindView(R.id.scoreCurrent)
    TextView scoreCurrent;

    @BindView(R.id.topCurrent)
    TextView topCurrent;

    @BindView(R.id.usernameCurrent)
    TextView usernameCurrent;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    final DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapter_top);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        GamerModel gamerModel = databaseHelper.getByName(sharedPreferences.getString("username", "Unknown"));
        List<GamerModel> gamerModelList = databaseHelper.getAll();

        topCurrent.setText(Integer.toString(rankOrder(gamerModelList, gamerModel.getName())));
        usernameCurrent.setText(gamerModel.getName());
        scoreCurrent.setText(Integer.toString(gamerModel.getScoreCurrent()));

        TopAdapter adapter = new TopAdapter(this, R.layout.activity_top, gamerModelList);
        listView.setAdapter(adapter);
    }

    public int rankOrder(List<GamerModel> list, String name) {

        for (int i = 0; i < list.size(); i++) {
            if (name.equals(list.get(i).getName())) {
                return i + 1;
            }
        }
        return 0;
    }

    @OnClick(R.id.cardReturn)
    public void onMain() {
        finish();
        startActivity(new Intent(this, HomeActivity.class));
    }


}
