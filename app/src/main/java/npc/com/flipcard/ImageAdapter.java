package npc.com.flipcard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Random;

public class ImageAdapter extends BaseAdapter {

    Context context;
    boolean check = false;
    Integer[] androidPhotos;
    int sizeOfSolved;

    @Override
    public int getCount() {
        return androidPhotos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public ImageAdapter(Context context, boolean shuffle, String typeGame) {
        this.context = context;
        if (check == false) {
            if (typeGame.equals("flag")) {
                androidPhotos = new Integer[]{
                        R.drawable.vietnam,
                        R.drawable.thailand,
                        R.drawable.italy,
                        R.drawable.america,
                        R.drawable.england,
                        R.drawable.germany,
                        R.drawable.spain,
                        R.drawable.china,
                        R.drawable.korea,
                        R.drawable.egypt,
                        R.drawable.vietnam,
                        R.drawable.thailand,
                        R.drawable.italy,
                        R.drawable.america,
                        R.drawable.england,
                        R.drawable.germany,
                        R.drawable.spain,
                        R.drawable.china,
                        R.drawable.korea,
                        R.drawable.egypt,
                };
            } else if (typeGame.equals("pokemon"))
                androidPhotos = new Integer[]{
                        R.drawable.pikachu,
                        R.drawable.pikachu1,
                        R.drawable.pikachu2,
                        R.drawable.pikachu3,
                        R.drawable.pikachu4,
                        R.drawable.pikachu20,
                        R.drawable.pikachu6,
                        R.drawable.pikachu9,
                        R.drawable.pikachu8,
                        R.drawable.pikachu10,
                        R.drawable.pikachu,
                        R.drawable.pikachu1,
                        R.drawable.pikachu2,
                        R.drawable.pikachu3,
                        R.drawable.pikachu4,
                        R.drawable.pikachu20,
                        R.drawable.pikachu6,
                        R.drawable.pikachu9,
                        R.drawable.pikachu8,
                        R.drawable.pikachu10,
                };
        }

        if (shuffle) {
            shuffleArray();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView card;
        if (convertView == null) {
            card = new ImageView(context);
            card.setLayoutParams(new GridView.LayoutParams(85, 85));
            card.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            card = (ImageView) convertView;
        }

        if (check) {
            if (position < sizeOfSolved) {
                card.setImageResource(androidPhotos[position]);
                card.setTag(R.drawable.checkmark);
            } else {
                card.setImageResource(R.drawable.card);
                card.setTag(R.drawable.card);
            }
        } else {
            card.setImageResource(R.drawable.card);
            card.setTag(R.drawable.card);
        }

        ViewGroup.LayoutParams imageLayout = card.getLayoutParams();
        imageLayout.width = 200;
        imageLayout.height = 200;

        return card;
    }

    public void shuffleArray() {
        Random random = new Random();
        for (int i = androidPhotos.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            int a = androidPhotos[index];
            androidPhotos[index] = androidPhotos[i];
            androidPhotos[i] = a;
        }
    }

    public Integer[] getArray() {
        return androidPhotos;
    }

    public void setArray(Integer[] androidPhotos) {
        this.androidPhotos = androidPhotos;
    }

    public void updateAdapter(Integer[] newArray, int newSize) {
        androidPhotos = null;
        androidPhotos = newArray;
        check = true;
        sizeOfSolved = newSize;
        notifyDataSetChanged();
    }
}
