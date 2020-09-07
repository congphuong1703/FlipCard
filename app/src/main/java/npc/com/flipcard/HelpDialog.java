package npc.com.flipcard;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpDialog extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.guideOk)
    Button ok;
    @BindView(R.id.guideExit)
    Button exit;
    @BindView(R.id.guideContent)
    TextView content;


    static HelpDialog newInstance() {
        return new HelpDialog();
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, getTheme());
    }

    @Override
    public void onClick(View view) {
        this.dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.guide_dialog, container, false);
        ButterKnife.bind(this, v);
        ok.setOnClickListener(this);
        content.setOnClickListener(this);
        exit.setOnClickListener(this);
        return v;
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
    }
}
