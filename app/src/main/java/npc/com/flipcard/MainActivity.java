package npc.com.flipcard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar bar;

    @BindView(R.id.btnStart)
    Button button;

    private ObjectAnimator progressAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        init();

        progressAnimation.setDuration(7000);

        progressAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationStart(animation);

                Intent intent =new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @OnClick(R.id.btnStart)
    public void goHome(){
        progressAnimation.start();

        Toast.makeText(getBaseContext(),"Đang tải...",Toast.LENGTH_SHORT).show();

    }

    private void init(){
        progressAnimation = ObjectAnimator.ofInt(bar,"progress",0,100);
    }

}