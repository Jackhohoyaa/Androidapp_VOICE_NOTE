package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

public class NoteDbHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Note.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_memo";
    private static final String COLUMN_ID = "note_id";
    private static final String COLUMN_TITLE = "memo_title";
    private static final String COLUMN_CONTENT = "memo_content";

     NoteDbHelper(Context context) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//在SQLLITE內部建立TABLE
     String query = "CREATE TABLE " + TABLE_NAME +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_CONTENT + " TEXT);";
     db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void AddMemo(String title,String content){//添加記事本到SQLLITE
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_CONTENT,content);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "儲存失敗", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "儲存成功", Toast.LENGTH_SHORT).show();
        }
    }

      Cursor ReadAllData(){//讀取SQL內部資料
      String query = "SELECT * FROM " + TABLE_NAME;
      SQLiteDatabase db = this.getReadableDatabase();

      Cursor cursor = null;
      if(db != null){
          cursor = db.rawQuery(query,null);
      }
      return cursor;
    }

    public boolean UpdateData(String _id,String title,String content){//更新記事本
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv= new ContentValues();
         cv.put(COLUMN_TITLE,title);
         cv.put(COLUMN_CONTENT,content);

         long result = db.update(TABLE_NAME,cv,"note_id=?",new String[]{_id});
        Log.d("UpdateData", "After update, result: " + result);
        Log.d("UpdateData", "Updated data for note_id: " + _id);
        Log.d("UpdateData", "After update, new data: " + Arrays.toString(getDataForId(_id)));
        return result > 0;
     }
    public String[] getDataForId(String _id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(_id)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        String[] data = null;

        if (cursor != null && cursor.moveToFirst()) {
            data = new String[]{
                    cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT))
            };
            cursor.close();
        }

        return data;
    }
    void deleteOneRow(String _id){//刪除指定的記事本
         SQLiteDatabase db = this.getWritableDatabase();
         long result = db.delete(TABLE_NAME,"note_id=?",new String[]{_id});
         if(result == -1){
             Toast.makeText(context,"刪除失敗",Toast.LENGTH_SHORT).show();
         }else{
             Toast.makeText(context,"刪除成功",Toast.LENGTH_SHORT).show();
         }
     }

    void deleteAllData(){//刪除所有記事本
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
