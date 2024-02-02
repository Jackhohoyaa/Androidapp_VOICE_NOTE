package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView id,titlename,notecontent;
    Button button;
    RelativeLayout mainLayout;
    Animation menu_animation;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.id);
        titlename = itemView.findViewById(R.id.titlename);
        notecontent = itemView.findViewById(R.id.notecontent);
        button = itemView.findViewById((R.id.button));
        mainLayout = itemView.findViewById(R.id.mainLayout);
        menu_animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.meun_animation);
        mainLayout.setAnimation(menu_animation);
    }
}
