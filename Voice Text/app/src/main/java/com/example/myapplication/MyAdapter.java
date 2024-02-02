package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    ArrayList memo_id;
    ArrayList memo_title;
    ArrayList note_content;
    Activity activity;

    public MyAdapter(Activity activity,Context context, ArrayList memo_id,ArrayList memo_title,ArrayList note_content) {
        this.activity = activity;
        this.context = context;
        this.memo_id = memo_id;
        this.memo_title = memo_title;
        this.note_content = note_content;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        holder.id.setText(String.valueOf(memo_id.get(position)));
        holder.titlename.setText(String.valueOf(memo_title.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (memo_id != null && memo_title != null && note_content != null
                        && position >= 0 && position < memo_id.size() && position < note_content.size()) {
                    Intent intent = new Intent(context, UpdateNoteActivity.class);
                    intent.putExtra("id",String.valueOf(memo_id.get(position)));
                    intent.putExtra("title", String.valueOf(memo_title.get(position)));
                    intent.putExtra("content", String.valueOf(note_content.get(position)));
                    activity.startActivityForResult(intent,1);
                } else {
                    Log.e("MyAdapter", "ArrayLists or position is null or out of bounds");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return memo_id.size();
    }

}
