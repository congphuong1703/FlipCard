package npc.com.flipcard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class GameActivity extends AppCompatActivity {


    @BindView(R.id.game_layout)
    GridView gridView;

    @BindView(R.id.point_counter)
    TextView points;

    @BindView(R.id.shuffle)
    ImageView shuffle;

    @BindView(R.id.timeBar)
    ProgressBar bar;

    private ObjectAnimator progressAnimation;
    private MusicAdapter musicAdapter;

    ArrayList<ImageView> activeCards;
    ArrayList<Integer> checkMarkIndexes;
    ArrayList<Integer> gameArrayList;
    private MediaPlayer mediaPlayer;


    int pointCounter;
    int countShuffle;
    private boolean won = false;
    Integer[] gameArray;
    ImageAdapter adapter;
    int[] indexes;
    Bundle keys;
    Dialog winDialog;
    Button btnClose;
    ImageView ivClose;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        adapter = new ImageAdapter(this, true);
        gameArray = adapter.getArray();
        ButterKnife.bind(this);
        gridView.setAdapter(adapter);
        won = false;
        activeCards = new ArrayList<>();
        checkMarkIndexes = new ArrayList<>();
        initTime();
        progressAnimation.start();

        keys = getIntent().getExtras();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        countShuffle = 5;
        pointCounter = 0;
        points.setText(Integer.toString(pointCounter));
        indexes = new int[2];

        for (int i = 0; i < gridView.getAdapter().getCount(); i++) {
            gridView.getAdapter().getView(i, null, gridView).setTag(R.drawable.card);
            ((ImageView) gridView.getAdapter().getView(i, null, gridView)).setImageResource(R.drawable.card);
        }

        mediaPlayer = musicAdapter.mediaPlayerMusic;
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            if(musicAdapter.isPlayingSound)
                musicAdapter.playSound(this);
        }
    }

    @OnItemClick(R.id.game_layout)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        checkGame(view, position);
    }

    @Override
    protected void onResume() {
        progressAnimation.resume();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        this.pauseGame();
    }

    @OnClick(R.id.pause)
    public void pause() {
        this.pauseGame();
    }

    public void pauseGame() {
        Intent pause = new Intent(this, SettingsActivity.class);
        pause.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        pause.putExtra("state", "true");
        startActivity(pause);
        progressAnimation.pause();
    }

    public void checkGame(final View card, final int position) {

        if ((int) (((ImageView) card).getTag()) != R.drawable.checkmark && activeCards.size() < 1) {
            activeCards.add((ImageView) card);
            indexes[0] = position;
            ObjectAnimator flip = ObjectAnimator.ofFloat(card, "rotationY", 0f, 180f);
            flip.setDuration(150);
            flip.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((ImageView) card).setImageResource(gameArray[position]);
                    ((ImageView) card).setTag(gameArray[position]);
                }
            }, 250);

        }
        //So user doesn't press same image twice
        else if ((int) (((ImageView) card).getTag()) != R.drawable.checkmark && !((ImageView) card).equals(activeCards.get(0))) {
            activeCards.add((ImageView) card);
            indexes[1] = position;
            ObjectAnimator flip = ObjectAnimator.ofFloat(card, "rotationY", 0f, 180f);
            flip.setDuration(250);
            flip.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((ImageView) card).setImageResource(gameArray[position]);
                    ((ImageView) card).setTag(gameArray[position]);
                }
            }, 250);
        }

        if (activeCards.size() > 1) {
            gridView.setEnabled(false);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int size = activeCards.size();

                    //player doesn't choose correct
                    ObjectAnimator flip;
                    if (!(activeCards.get(0).getTag().equals(activeCards.get(1).getTag()))) {
                        for (int i = 0; i < size; i++) {

                            flip = ObjectAnimator.ofFloat(activeCards.get(0), "rotationY", 0f, 180f);
                            flip.setDuration(250);
                            flip.start();

                            activeCards.get(0).setImageResource(R.drawable.card);
                            activeCards.get(0).setTag(R.drawable.card);
                            activeCards.remove(0);

                        }
                    }
                    //Player choose correct two cards

                    else if ((int) activeCards.get(0).getTag() != R.drawable.checkmark && (int) activeCards.get(1).getTag() != R.drawable.checkmark) {
                        pointCounter++;
                        points.setText(Integer.toString(pointCounter));

                        for (int i = 0; i < activeCards.size(); i++) {

                            gridView.getAdapter().getView(indexes[i], null, gridView).setTag(R.drawable.checkmark);
                            checkMarkIndexes.add(indexes[i]);
                            activeCards.get(i).setTag(R.drawable.checkmark);
                        }
                        //clear when 2 card equal and done handle
                        activeCards.clear();

                        if(musicAdapter.isPlayingMusic){
                            musicAdapter.playMusic(GameActivity.this,R.raw.ting);
                        }

                    }
                    gridView.setEnabled(true);

                }
            }, 500);
        }

        if (isWon()) {
            showWonDialog();
        }
    }

    private void showLoseDialog() {
        winDialog.setContentView(R.layout.guide_dialog);
        winDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = winDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.AlertDialogTheme;

        btnClose = winDialog.findViewById(R.id.btnClose);
        ivClose = winDialog.findViewById(R.id.ivClose);

        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                winDialog.dismiss();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                winDialog.dismiss();
            }
        });

        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        winDialog.show();
    }

    private void showWonDialog() {
        winDialog.setContentView(R.layout.guide_dialog);
        winDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = winDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.AlertDialogTheme;

        btnClose = winDialog.findViewById(R.id.btnClose);
        ivClose = winDialog.findViewById(R.id.ivClose);

        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                winDialog.dismiss();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                winDialog.dismiss();
            }
        });

        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        winDialog.show();
    }

    @OnClick(R.id.shuffle)
    public void shuffleGame() {

        int size = checkMarkIndexes.size();
        if (pointCounter > 0) {
            Integer[] newArray = new Integer[20];

            int index = 0;

            gameArrayList = new ArrayList<>(Arrays.asList(gameArray));

            //add array solved to new array
            for (Integer checkMarkIndex : checkMarkIndexes) {
                newArray[index] = gameArray[checkMarkIndex];
                gameArrayList.set(checkMarkIndex.intValue(), -1);
                index++;
            }

            //shuffle array not solve
            this.shuffleArray();

            //add array not solve to new array
            for (Integer j : gameArrayList) {
                if (j.intValue() != -1) {
                    newArray[index] = j;
                    index++;
                }
            }

            gameArray = newArray;

            //update new array to grid view
            ((ImageAdapter) gridView.getAdapter()).updateAdapter(newArray, size);

            for (int i = 0; i < (pointCounter * 2); i++) {
                checkMarkIndexes.set(i, i);
            }

            Toast.makeText(this, R.string.shuffle, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.notShuffle, Toast.LENGTH_SHORT).show();
        }
    }


    public void shuffleArray() {
        Random random = new Random();
        for (int i = gameArrayList.size() - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            int a = gameArrayList.get(index);
            gameArrayList.set(index, gameArrayList.get(i));
            gameArrayList.set(i, a);
        }
    }


    public void initTime() {
        progressAnimation = ObjectAnimator.ofInt(bar, "progress", 0, 100);

        //2 minutes
        progressAnimation.setDuration(1000 * 60 * 2);

        progressAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationStart(animation);

                showLoseDialog();
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public boolean isWon() {
        if (pointCounter > 9)
            won = true;
        return won;
    }
}
