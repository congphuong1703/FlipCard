package npc.com.flipcard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String GAMER_TABLE = "gamer_table";
    public static final String COLUMN_GAMER_NAME = "gamer_name";
    public static final String COLUMN_SCORE_HIGHEST = "score_highest";
    public static final String COLUMN_SCORE_CURRENT = "score_current";
    public static final String COLUMN_IS_ACTIVE = "isActive";
    public static final String COLUMN_ID = "id";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "gamer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "create table " + GAMER_TABLE + " (" + COLUMN_ID + " integer primary key autoincrement," + COLUMN_GAMER_NAME + " text," + COLUMN_SCORE_HIGHEST + " int, " + COLUMN_SCORE_CURRENT + " int, " + COLUMN_IS_ACTIVE + " bool)";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean save(GamerModel gamerModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_GAMER_NAME, gamerModel.getName());
        contentValues.put(COLUMN_SCORE_HIGHEST, gamerModel.getScoreHighest());
        contentValues.put(COLUMN_SCORE_CURRENT, gamerModel.getScoreCurrent());
        contentValues.put(COLUMN_IS_ACTIVE, gamerModel.isActive());

        long insert = sqLiteDatabase.insert(GAMER_TABLE, null, contentValues);
        if (insert == -1)
            return false;
        return true;
    }

    public boolean update(GamerModel gamerModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_GAMER_NAME, gamerModel.getName());
        contentValues.put(COLUMN_SCORE_HIGHEST, gamerModel.getScoreHighest());
        contentValues.put(COLUMN_SCORE_CURRENT, gamerModel.getScoreCurrent());
        contentValues.put(COLUMN_IS_ACTIVE, gamerModel.isActive());
        long update = sqLiteDatabase.update(GAMER_TABLE, contentValues, COLUMN_ID + "= ? ", new String[]{Integer.toString(gamerModel.getId())});
        if (update == -1)
            return false;
        return true;
    }

    public Cursor getById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + GAMER_TABLE + " where " + COLUMN_ID + "=" + id + "", null);
        return res;
    }

    public Cursor getByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + GAMER_TABLE + " where " + COLUMN_GAMER_NAME + "=" + name + "", null);
        return res;
    }

    public boolean delete(GamerModel gamerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryDelete = "delete from " + GAMER_TABLE + " where " + COLUMN_ID + " = " + gamerModel.getId();
        Cursor cursor = db.rawQuery(queryDelete, null);

        if (cursor.moveToFirst())
            return true;
        return false;
    }

    public List<GamerModel> getAll() {
        List<GamerModel> gamerModels = new ArrayList<>();

        String query = "select * from " + GAMER_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int gamerId = cursor.getInt(0);
                String gamerName = cursor.getString(1);
                int gamerScoreHighest = cursor.getInt(2);
                int gamerScoreCurrent = cursor.getInt(3);
                boolean isActive = cursor.getInt(3) == 1 ? true : false;

                GamerModel gamerModel = new GamerModel(gamerId, gamerName, gamerScoreHighest, gamerScoreCurrent, isActive);
                gamerModels.add(gamerModel);
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        sqLiteDatabase.close();
        return gamerModels;
    }

}
