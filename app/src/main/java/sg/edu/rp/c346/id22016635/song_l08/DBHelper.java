package sg.edu.rp.c346.id22016635.song_l08;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "song.db";

    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGERS = "singers";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String createTableSql = "CREATE TABLE " + TABLE_SONG + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TITLE + " TEXT," + COLUMN_SINGERS + " TEXT, " + COLUMN_YEAR + " INTEGER," + COLUMN_STARS + " INTEGER)" ;
        db.execSQL(createTableSql);
        Log.i("info","created tables");
     }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
    }
    public void insertSong(String title, String singers, int year , int stars){
        SQLiteDatabase db = this.getWritableDatabase();
        // We use ContentValues object to store the values for
        //  the db operation
        ContentValues values = new ContentValues();
        // Store the column name as key and the description as value
        values.put(COLUMN_TITLE, title);
        // Store the column name as key and the date as value
        values.put(COLUMN_SINGERS, singers);

        values.put(COLUMN_YEAR,year);

        values.put(COLUMN_STARS,stars);
        // Insert the row into the TABLE_TASK
        db.insert(TABLE_SONG, null, values);
        // Close the database connection
        db.close();

    }
    public ArrayList<String> getSongContent() {
        // Create an ArrayList that holds String objects
        ArrayList<String> song = new ArrayList<String>();
        // Get the instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS,COLUMN_YEAR,COLUMN_STARS};
        // Run the query and get back the Cursor object
        Cursor cursor = db.query(TABLE_SONG, columns, null, null, null, null, null, null);

        // moveToFirst() moves to first row, null if no records
        if (cursor.moveToFirst()) {
            // Loop while moveToNext() points to next row
            //  and returns true; moveToNext() returns false
            //  when no more next row to move to
            do {
                // Add the task content to the ArrayList object
                //  getString(0) retrieves first column data
                //  getString(1) return second column data
                //  getInt(0) if data is an integer value
                song.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();

        return song;
    }
    public ArrayList<Song> getSong() {
        ArrayList<Song> songs = new ArrayList<Song>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS,COLUMN_YEAR,COLUMN_STARS};
        Cursor cursor = db.query(TABLE_SONG, columns, null, null, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song obj = new Song(_id,title,singers,year,stars);
                songs.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }
    public ArrayList<Song> filterSongs(String filterWord){
        ArrayList<Song> Song = new ArrayList<Song>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID,COLUMN_TITLE,COLUMN_SINGERS,COLUMN_YEAR,COLUMN_STARS};
        String filter = COLUMN_STARS + " Like ?";
        String[] args = {"%" + filterWord + "%"};
        Cursor cursor = db.query(TABLE_SONG,columns, filter,args,null,null,null,null);

        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song obj = new Song(_id,title,singers,year,stars);
                Song.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Song;
    }
    //UPDATE SONG
    public void updateSong(Song title, Song singers, Song year, Song star){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put(COLUMN_TITLE,title.getTitle());
        value.put(COLUMN_SINGERS,singers.getSingers());
        value.put(COLUMN_YEAR,year.getYear());
        value.put(COLUMN_STARS,star.getStars());
        String conditions = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(title.getId())};
        db.update(TABLE_SONG,value,conditions,args);
        db.close();

    }
    public int deleteSong(int id){
            SQLiteDatabase db = this.getWritableDatabase();
            String condition = COLUMN_ID + "= ?";
            String[] args = {String.valueOf(id)};
            int result = db.delete(TABLE_SONG, condition, args);
            db.close();
            return result;
        }






}
