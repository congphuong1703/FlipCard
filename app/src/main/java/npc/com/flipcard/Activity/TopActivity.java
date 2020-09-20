package npc.com.flipcard.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import npc.com.flipcard.Adapter.TopAdapter;
import npc.com.flipcard.Database.DatabaseHelper;
import npc.com.flipcard.Model.GamerModel;
import npc.com.flipcard.R;

public class TopActivity extends AppCompatActivity {

    @BindView(R.id.listTop)
    ListView listView;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView usernameCurrent;
    TextView topCurrent;
    TextView scoreCurrent;

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

        bindData(R.layout.activity_top);

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

    public void bindData(int resource) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(resource, null);
        topCurrent = view.findViewById(R.id.topCurrent);
        usernameCurrent = view.findViewById(R.id.usernameCurrent);
        scoreCurrent = view.findViewById(R.id.scoreCurrent);
    }

}
