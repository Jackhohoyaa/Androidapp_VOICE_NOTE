package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView noNote;
    ImageView plus,trashcan,empty;
    NoteDbHelper myDB;
    ArrayList<String> memo_id,memo_title,note_content;
    MyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        plus = (ImageView) findViewById(R.id.plus);
        trashcan = (ImageView) findViewById(R.id.trashcan);
        empty = (ImageView) findViewById(R.id.empty);
        noNote = (TextView) findViewById(R.id.noNote2);
        ImageView imageView = findViewById(R.id.empty);                //改圖片顏色
        imageView.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);  //改圖片顏色

        myDB = new NoteDbHelper(MainActivity.this);
        memo_id = new ArrayList<>();
        memo_title = new ArrayList<>();
        note_content = new ArrayList<>();

        StoreData();

        adapter = new MyAdapter(MainActivity.this,this,memo_id,memo_title,note_content);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent noteIntent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(noteIntent);
                MainActivity.this.finish();
            }
        });

        trashcan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = myDB.ReadAllData();
                if(cursor.getCount() == 0){
                    Toast.makeText(MainActivity.this,"沒有記事本",Toast.LENGTH_SHORT).show();
                } else{confirmDialog();}
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void StoreData(){
        Cursor cursor = myDB.ReadAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"沒有記事本",Toast.LENGTH_SHORT).show();}
        else {while(cursor.moveToNext()){
              memo_id.add(cursor.getString(0));
              memo_title.add(cursor.getString(1));
              note_content.add(cursor.getString(2));

              if (cursor.getCount() > 0) {
                empty.setVisibility(View.INVISIBLE);
                noNote.setVisibility(View.INVISIBLE);
              }
            }
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("刪除所有記事本");
        builder.setMessage("\n確定要刪除 ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NoteDbHelper myDB = new NoteDbHelper(MainActivity.this);
                myDB.deleteAllData();
                Intent intent = new Intent(MainActivity.this, NullMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}






