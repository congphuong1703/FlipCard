package npc.com.flipcard.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.appcompat.app.AppCompatActivity;


import com.nex3z.notificationbadge.NotificationBadge;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import npc.com.flipcard.Adapter.ImageAdapter;
import npc.com.flipcard.Adapter.MusicAdapter;
import npc.com.flipcard.Database.DatabaseHelper;
import npc.com.flipcard.Model.GamerModel;
import npc.com.flipcard.R;


public class GameActivity extends AppCompatActivity {


    @BindView(R.id.game_layout)
    GridView gridView;

    @BindView(R.id.point_counter)
    TextView points;

    @BindView(R.id.shuffle)
    ImageView shuffle;

    @BindView(R.id.timeBar)
    ProgressBar bar;

    @BindView(R.id.badgeShuffle)
    NotificationBadge badgeShuffle;

    @BindView(R.id.timlat)
    Button timlat;

    private ObjectAnimator progressAnimation;
    private MusicAdapter musicAdapter;
    private MediaPlayer mediaPlayer;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    GamerModel user;

    ArrayList<ImageView> activeCards;
    ArrayList<Integer> checkMarkIndexes;
    ArrayList<Integer> gameArrayList;

    int pointCounter;
    int countShuffle;
    private boolean won = false;
    Integer[] gameArray;
    ImageAdapter adapter;
    int[] indexes;
    Bundle keys;
    Dialog dialog;
    Button btnClose;
    Button btnNewGame;
    Button btnNextGame;
    TextView score_lose;
    TextView score_won;
    ImageView ivClose;

    int solanlat = 0;
    int solantimlat = 0;

    int vitri;
    View view;
    ArrayList<Integer> timlatlist;
    int image;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        indexes = new int[2];

        mediaPlayer = musicAdapter.mediaPlayerSound;

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        user = databaseHelper.getByName(sharedPreferences.getString("username", "Unknown"));

        if (!user.isActive()) {
            user.setScoreCurrent(0);
            user.setShuffle(10);
        } else {

        }
        countShuffle = user.getShuffle();
        pointCounter = 0;

        points.setText(Integer.toString(pointCounter));
        badgeShuffle.setNumber(countShuffle);

        keys = getIntent().getExtras();

        if (keys != null) {

            if ((keys.getString("game").equals("pokemon"))) {

                adapter = new ImageAdapter(this, true, "pokemon");
            } else if ((keys.getString("game").equals("flag"))) {

                adapter = new ImageAdapter(this, true, "flag");
            }
        }

        gameArray = adapter.getArray();

        gridView.setAdapter(adapter);
        won = false;
        activeCards = new ArrayList<>();
        checkMarkIndexes = new ArrayList<>();
        initTime();
        progressAnimation.start();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        if (musicAdapter.isPlayingSound)
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                musicAdapter.playSound(this);
            }

        for (int i = 0; i < gridView.getAdapter().getCount(); i++) {
            gridView.getAdapter().getView(i, null, gridView).setTag(R.drawable.card);
            ((ImageView) gridView.getAdapter().getView(i, null, gridView)).setImageResource(R.drawable.card);
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
        progressAnimation.pause();

        Intent pause = new Intent(this, SettingsActivity.class);
        pause.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        pause.putExtra("state", "true");

        startActivity(pause);
    }

    @OnClick(R.id.timlat)
    public void timlat() {

        Integer[] newArray = new Integer[20];

        int index = 0;

        gameArrayList = new ArrayList<>(Arrays.asList(gameArray));

        //add array not solve to new array
        for (Integer j : gameArrayList) {
            if (j.intValue() != -1) {
                newArray[index] = j;
                index++;
            }
        }

        for (int i = 0; i < gameArray.length; i++) {
            View view = ((ImageView) gridView.getAdapter().getView(i, null, gridView));
            if (gameArray[i] == image && vitri != i) {
                ObjectAnimator flip = ObjectAnimator.ofFloat(view, "rotationY", 0f, 180f);
                flip.setDuration(150);
                flip.start();
                ((ImageView) view).setImageResource(gameArray[i]);

                Toast.makeText(this, "Hình bạn muốn tìm : " + i, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void checkGame(final View card, final int position) {

        final int[] music = new int[1];
        if ((int) (((ImageView) card).getTag()) != R.drawable.checkmark && activeCards.size() < 1) {
            view = card;
            vitri = position;
            image = gameArray[position];
            activeCards.add((ImageView) card);
            indexes[0] = position;
            ObjectAnimator flip = ObjectAnimator.ofFloat(card, "rotationY", 0f, 180f);
            flip.setDuration(150);
            flip.start();

            solanlat++;
            Toast.makeText(this, "Số lần vừa lật : " + solanlat, Toast.LENGTH_SHORT).show();

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
            view = card;
            ObjectAnimator flip = ObjectAnimator.ofFloat(card, "rotationY", 0f, 180f);
            flip.setDuration(250);
            flip.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((ImageView) card).setImageResource(gameArray[position]);
                    ((ImageView) card).setTag(gameArray[position]);
                    music[0] = gameArray[position];
                }
            }, 250);
        }

        if (activeCards.size() > 1) {
            gridView.setEnabled(false);
            solanlat++;
            Toast.makeText(this, "Số lần vừa lật : " + solanlat, Toast.LENGTH_SHORT).show();
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

                        if (musicAdapter.isPlayingMusic) {

                            musicAdapter.playMusic(GameActivity.this, musicOfFlag(music[0]));
                        }

                    }
                    gridView.setEnabled(true);

                    if (isWon()) {
                        showWonDialog();
                    }

                }

            }, 500);

        }


    }

    private void showLoseDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.lose_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.AlertDialogAnimation;

        user.setScoreCurrent(pointCounter + user.getScoreCurrent());
        user.setScoreHighest();
        user.setActive(false);

        databaseHelper.update(user);

        btnClose = dialog.findViewById(R.id.btnClose);
        ivClose = dialog.findViewById(R.id.ivClose);
        btnNewGame = dialog.findViewById(R.id.btnNewGame);
        score_lose = dialog.findViewById(R.id.score_lose);

        score_lose.setText(Integer.toString(user.getScoreCurrent()));
        btnNewGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onHome();

            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onHome();

            }
        });

        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void showWonDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.won_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.AlertDialogAnimation;

        user.setScoreCurrent(pointCounter + user.getScoreCurrent());
        user.setScoreHighest();
        user.setActive(true);

        btnClose = dialog.findViewById(R.id.btnClose);
        ivClose = dialog.findViewById(R.id.ivClose);
        btnNextGame = dialog.findViewById(R.id.btnNextGame);
        score_won = dialog.findViewById(R.id.score_won);

        score_won.setText(Integer.toString(user.getScoreCurrent()));

        databaseHelper.update(user);

        progressAnimation.pause();

        btnNextGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onHome();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onHome();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onHome();
            }
        });

        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public void onHome() {
        finish();
        startActivity(new Intent(this, HomeActivity.class));
    }

    @OnClick(R.id.shuffle)
    public void shuffleGame() {

        if (countShuffle > 0) {
            int size = checkMarkIndexes.size();
            badgeShuffle.setNumber(--countShuffle);

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
        } else {
            Toast.makeText(this, R.string.outOfShuffle, Toast.LENGTH_SHORT).show();
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
        if (pointCounter > 9) {
            won = true;
            countShuffle++;
        }
        return won;
    }

    public int musicOfFlag(int image) {
        switch (image) {
            case R.drawable.vietnam:
                return R.raw.vietnam;
            case R.drawable.thailand:
                return R.raw.thailand;
            case R.drawable.italy:
                return R.raw.italy;
            case R.drawable.america:
                return R.raw.america;
            case R.drawable.england:
                return R.raw.england;
            case R.drawable.germany:
                return R.raw.germany;
            case R.drawable.spain:
                return R.raw.spain;
            case R.drawable.china:
                return R.raw.china;
            case R.drawable.korea:
                return R.raw.korea;
            case R.drawable.egypt:
                return R.raw.egypt;
            default:
                return R.raw.ting;
        }

    }
}
