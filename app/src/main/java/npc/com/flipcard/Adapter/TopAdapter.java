package npc.com.flipcard.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import npc.com.flipcard.Model.GamerModel;
import npc.com.flipcard.R;

public class TopAdapter extends ArrayAdapter<GamerModel> {

    private Context context;
    int resource;
    TextView tvTop;
    TextView tvUsername;
    TextView tvPointHighest;
    LayoutInflater inflater;

    public TopAdapter(Context context, int resource, List<GamerModel> user) {
        super(context, resource, user);
        this.context = context;
        this.resource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        tvTop = convertView.findViewById(R.id.tvTop);
        tvUsername = convertView.findViewById(R.id.tvUsername);
        tvPointHighest = convertView.findViewById(R.id.tvPointHighest);

        tvTop.setText(Integer.toString(position + 1));
        tvUsername.setText(getItem(position).getName());
        tvPointHighest.setText(Integer.toString(getItem(position).getScoreHighest()));

        return convertView;
    }
}
