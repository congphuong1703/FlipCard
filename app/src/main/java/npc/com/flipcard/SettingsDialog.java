package npc.com.flipcard;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

public class SettingsDialog extends DialogFragment implements View.OnClickListener {


    @Override
    public void onClick(View view) {

    }
    
    public static SettingsDialog newInstance() {
        return new SettingsDialog();
    }
}
