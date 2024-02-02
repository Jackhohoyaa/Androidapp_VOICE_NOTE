package com.example.myapplication;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NoteActivity extends AppCompatActivity {


        String textToTranslate = "";
        String languageCode = "en"; // 源語言代碼（英文）
        String languageCode1 = "zh-TW";
        String targetLanguageCode = "fr-FR"; // 目標語言代碼（中文）
        String targetLanguageCode1 = "en";
        String targetLanguageCode2 = "es";
        String targetLanguageCode3 = "ja";
        String targetLanguageCode4 = "ko";
        ImageView speechButton, france, japan, usa, spain, korea, back, googletranslate,options,return_to_menu;
        EditText speechText;
        EditText note,title;
        TextView start,hint;
        Button save,tips;
        Animation options_animation;
        boolean isLongClick = false;//長按事件預設為false

        private NoteDbHelper dbHelper;
        private SQLiteDatabase db;
        private ContentValues values;
        private static final String tablename = "mymemo";

        boolean hints_visible = false;
        private static final int RECOGNIZER_RESULT = 10;
        private static final String PREF_KEY_NOTE_TEXT = "note_text";
        private static final String title_text = "title_text";

        @Override
        public void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);//進入程式
            setContentView(R.layout.voice_note);//設定頁面
            findView();
        }

            public void findView() {
                speechButton = findViewById(R.id.Microphone);
                speechText = findViewById(R.id.editText);
                note = findViewById(R.id.editTextTextMultiLine);
                france = findViewById(R.id.france);
                japan = findViewById(R.id.japan);
                usa = findViewById(R.id.usa);
                spain =  findViewById(R.id.spain);
                korea = findViewById(R.id.korea);
                back = findViewById(R.id.back);
                start =  findViewById(R.id.start);
                hint =  findViewById(R.id.hint);
                options =  findViewById(R.id.options);
                googletranslate =  findViewById(R.id.googletranslate);
                return_to_menu =  findViewById(R.id.return_menu);
                title =  findViewById(R.id.filename);
                save =  findViewById(R.id.save);
                tips = findViewById(R.id.tips);
                Button sky1 = findViewById(R.id.sky1);
                Button sky2 = findViewById(R.id.sky2);
                Button sky3 = findViewById(R.id.sky3);
                Button sky4 = findViewById(R.id.sky4);
                Button sky5 = findViewById(R.id.sky5);
                Button sky6 = findViewById(R.id.sky6);
                Button sky7 = findViewById(R.id.sky7);


                dbHelper = new NoteDbHelper(this);//初始化數據庫工具類實例




                save.setOnClickListener(new View.OnClickListener() {//設定點擊麥克風圖片開啟Google語音輸入
                    @Override
                    public void onClick(View view) {
                        NoteDbHelper myDB = new NoteDbHelper(NoteActivity.this);
                        myDB.AddMemo(title.getText().toString().trim(),//將標題及記事本內容儲存至SQLLITE
                                     note.getText().toString().trim());

                        Intent intent = new Intent(NoteActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });



                speechButton.setOnClickListener(new View.OnClickListener() {//設定點擊麥克風圖片開啟Google語音輸入
                @Override
                public void onClick(View view) {
                    Intent speachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode1);//根據設定的語系,更換輸入法(languageCode1為中文繁體輸入法)
                    speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to Text");//字串為API提示句
                    startActivityForResult(speachIntent, RECOGNIZER_RESULT);
                    start.setVisibility(View.INVISIBLE);
                }
            });


                speechButton.setOnLongClickListener(new View.OnLongClickListener() {//設定長按麥克風圖片開啟Google語音輸入
                    @Override
                    public boolean onLongClick(View view) {
                        TranslateAnimation animation7 = new TranslateAnimation(0, 300, 0, 130);
                        animation7.setDuration(500); // 動畫持續時間，單位是毫秒
                        animation7.setFillAfter(true); // 將 view 移動到動畫結束的位置
                        back.startAnimation(animation7);

                        TranslateAnimation animation1 = new TranslateAnimation(0, 400, 0, -200);
                        animation1.setDuration(300); // 動畫持續時間，單位是毫秒
                        animation1.setFillAfter(true); // 將 view 移動到動畫結束的位置
                        spain.startAnimation(animation1);

                        TranslateAnimation animation2 = new TranslateAnimation(0, 200, 0, -200);
                        animation2.setDuration(200); // 動畫持續時間，單位是毫秒
                        animation2.setFillAfter(true); // 將 view 移動到動畫結束的位置
                        usa.startAnimation(animation2);

                        TranslateAnimation animation3 = new TranslateAnimation(0, 0, 100, -200);
                        animation3.setDuration(100); // 動畫持續時間，單位是毫秒
                        animation3.setFillAfter(true); // 將 view 移動到動畫結束的位置
                        france.startAnimation(animation3);

                        TranslateAnimation animation4 = new TranslateAnimation(0, -200, 0, -200);
                        animation4.setDuration(200); // 動畫持續時間，單位是毫秒
                        animation4.setFillAfter(true); // 將 view 移動到動畫結束的位置
                        japan.startAnimation(animation4);

                        TranslateAnimation animation5 = new TranslateAnimation(0, -400, 0, -200);
                        animation5.setDuration(300); // 動畫持續時間，單位是毫秒
                        animation5.setFillAfter(true); // 將 view 移動到動畫結束的位置
                        korea.startAnimation(animation5);

                        TranslateAnimation animation6 = new TranslateAnimation(0, -300, 0, 200);
                        animation6.setDuration(500); // 動畫持續時間，單位是毫秒
                        animation6.setFillAfter(true); // 將 view 移動到動畫結束的位置
                        googletranslate.startAnimation(animation6);

                        if(sky1.getVisibility() == View.INVISIBLE){
                            sky1.setVisibility(View.VISIBLE);
                            sky2.setVisibility(View.VISIBLE);
                            sky3.setVisibility(View.VISIBLE);
                            sky4.setVisibility(View.VISIBLE);
                            sky5.setVisibility(View.VISIBLE);
                            sky6.setVisibility(View.VISIBLE);
                            sky7.setVisibility(View.VISIBLE);

                        }


                        isLongClick = true;//判定是否為長按事件,若為長按回傳true
                        return true;
                    }
                });

                if (!isLongClick) {//判定是否為長按,若不是長按,則長按事件為false
                    isLongClick = false;
                }


            start.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 檢測觸摸事件的動作
                    int action = event.getAction();

                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // 按下時執行的操作
                            start.setVisibility(View.INVISIBLE);
                            return true;
                        default:
                            return false;
                    }
                }
            });

                sky1.setOnClickListener(new View.OnClickListener() {//設定點擊麥克風圖片開啟Google翻譯連結
                    @Override
                    public void onClick(View view) {
                        Intent speachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, targetLanguageCode2);//根據設定的語系,更換輸入法
                        speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to Text");//字串為API提示句
                        startActivityForResult(speachIntent, RECOGNIZER_RESULT);

                    }
                });

                sky2.setOnClickListener(new View.OnClickListener() {//設定點擊麥克風圖片開啟Google翻譯連結
                    @Override
                    public void onClick(View view) {
                        Intent speachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, targetLanguageCode1);//根據設定的語系,更換輸入法
                        speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to Text");//字串為API提示句
                        startActivityForResult(speachIntent, RECOGNIZER_RESULT);
                    }
                });

                sky3.setOnClickListener(new View.OnClickListener() {//設定點擊麥克風圖片開啟Google翻譯連結
                    @Override
                    public void onClick(View view) {
                        Intent speachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, targetLanguageCode);//根據設定的語系,更換輸入法(languageCode1為中文繁體輸入法)
                        speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to Text");//字串為API提示句
                        startActivityForResult(speachIntent, RECOGNIZER_RESULT);
                    }
                });

                sky4.setOnClickListener(new View.OnClickListener() {//設定點擊麥克風圖片開啟Google翻譯連結
                    @Override
                    public void onClick(View view) {
                        Intent speachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, targetLanguageCode3);//根據設定的語系,更換輸入法
                        speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to Text");//字串為API提示句
                        startActivityForResult(speachIntent, RECOGNIZER_RESULT);
                    }
                });

                sky5.setOnClickListener(new View.OnClickListener() {//設定點擊麥克風圖片開啟Google翻譯連結
                    @Override
                    public void onClick(View view) {
                        Intent speachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, targetLanguageCode4);//根據設定的語系,更換輸入法
                        speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to Text");//字串為API提示句
                        startActivityForResult(speachIntent, RECOGNIZER_RESULT);
                    }
                });

                sky6.setOnClickListener(new View.OnClickListener() {//設定點擊麥克風圖片開啟Google翻譯連結
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://translate.google.com/#" + languageCode1 + "/" + languageCode + "/" + textToTranslate));
                        startActivity(intent);
                    }
                });

                sky7.setOnClickListener(new View.OnClickListener() {//設定點擊麥克風圖片開啟Google翻譯連結
                    @Override
                    public void onClick(View view) {
//                    spain.setVisibility(View.INVISIBLE);
                        TranslateAnimation animation13 = new TranslateAnimation(400, 0, -200, 0);
                        animation13.setDuration(200); // 動畫持續時間，單位是毫秒
                        animation13.setFillAfter(false); // 將 view 移動到動畫結束的位置
                        spain.startAnimation(animation13);

                        TranslateAnimation animation12 = new TranslateAnimation(200, 0, -200, 0);
                        animation12.setDuration(200); // 動畫持續時間，單位是毫秒
                        animation12.setFillAfter(false); // 將 view 移動到動畫結束的位置
                        usa.startAnimation(animation12);

                        TranslateAnimation animation11 = new TranslateAnimation(0, 0, -200, 0);
                        animation11.setDuration(200); // 動畫持續時間，單位是毫秒
                        animation11.setFillAfter(false); // 將 view 移動到動畫結束的位置
                        france.startAnimation(animation11);

                        TranslateAnimation animation14 = new TranslateAnimation(-200, 0, -200, 0);
                        animation14.setDuration(200); // 動畫持續時間，單位是毫秒
                        animation14.setFillAfter(false); // 將 view 移動到動畫結束的位置
                        japan.startAnimation(animation14);

                        TranslateAnimation animation15 = new TranslateAnimation(-400, 0, -200, 0);
                        animation15.setDuration(200); // 動畫持續時間，單位是毫秒
                        animation15.setFillAfter(false); // 將 view 移動到動畫結束的位置
                        korea.startAnimation(animation15);

                        TranslateAnimation animation16 = new TranslateAnimation(300, 0, 130, 0);
                        animation16.setDuration(200); // 動畫持續時間，單位是毫秒
                        animation16.setFillAfter(true); // 將 view 移動到動畫結束的位置
                        back.startAnimation(animation16);

                        TranslateAnimation animation17 = new TranslateAnimation(-300, 0, 200, 0);
                        animation17.setDuration(200); // 動畫持續時間，單位是毫秒
                        animation17.setFillAfter(false); // 將 view 移動到動畫結束的位置
                        googletranslate.startAnimation(animation17);

                        if(sky1.getVisibility() == View.VISIBLE){

                            sky1.setVisibility(View.INVISIBLE);
                            sky2.setVisibility(View.INVISIBLE);
                            sky3.setVisibility(View.INVISIBLE);
                            sky4.setVisibility(View.INVISIBLE);
                            sky5.setVisibility(View.INVISIBLE);
                            sky6.setVisibility(View.INVISIBLE);
                            sky7.setVisibility(View.INVISIBLE);
                        }


                    }
                });

            options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //檢查提示的狀態,若提示為不可見狀態則按下選項後會顯示提示,反之亦然
                    if(save.getVisibility() == View.INVISIBLE){
                        tips.setVisibility(View.VISIBLE);
                        save.setVisibility(View.VISIBLE);
                        options_animation = AnimationUtils.loadAnimation(NoteActivity.this, R.anim.note_options_ani);
                        save.setAnimation(options_animation);
                        tips.setAnimation(options_animation);
                    }
                    else{
                    tips.setVisibility(View.INVISIBLE);
                    save.setVisibility(View.INVISIBLE);
                        options_animation = AnimationUtils.loadAnimation(NoteActivity.this, R.anim.note_options_ani2);
                        save.setAnimation(options_animation);
                        tips.setAnimation(options_animation);
                    }
                }});

                tips.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { //檢查提示的狀態,若提示為不可見狀態則按下選項後會顯示提示,反之亦然
                        if(hint.getVisibility() == View.INVISIBLE){
                            hint.setVisibility(View.VISIBLE);
                            options_animation = AnimationUtils.loadAnimation(NoteActivity.this, R.anim.note_options_ani);
                            hint.setAnimation(options_animation);
                        }
                        else{
                            options_animation = AnimationUtils.loadAnimation(NoteActivity.this, R.anim.note_options_ani2);
                            hint.setAnimation(options_animation);
                            hint.setVisibility(View.INVISIBLE);
                        }
                    }});

            return_to_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent return_to_menu = new Intent();
                        return_to_menu.setClass(NoteActivity.this, MainActivity.class);
                        startActivity(return_to_menu);
                        NoteActivity.this.finish();
                    }
            });

        }


    @Override
        public void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data)
        {//回傳語音輸入的字串到TextField
            if (requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK) {
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String comma = "逗號";
                String input = matches.get(0);
                String regrex_text = replaceText(comma, input, "，")
                        .replace("逗點", "，")
                        .replace("句號", "。")
                        .replace("據點", "。")
                        .replace("句點", "。")
                        .replace("驚嘆號", "!")
                        .replace("頓號", "、")
                        .replace("頓好", "、")
                        .replace("燉號", "、")
                        .replace("燉好", "、")
                        .replace("盾號", "、")
                        .replace("問號", "?")
                        .replace("冒號", ":")
                        .replace("分號", ";")
                        .replace("波浪號", "~")
                        .replace("伯朗號", "~")
                        .replace("我掛號", "(")
                        .replace("我括號", "(")
                        .replace("走括號", "(")
                        .replace("走掛號", "(")
                        .replace("左括號", "(")
                        .replace("左掛號", "(")
                        .replace("右括號", ")")
                        .replace("又括號", ")")
                        .replace("又掛號", ")")
                        .replace("加", " + ")
                        .replace("減", " - ")
                        .replace("乘以", " * ")
                        .replace("除以", " / ")
                        .replace("等於", "=")
                        .replace("下一行", "\n")
                        .replace("空格", "  ");

                speechText.setText(regrex_text.toString());//提視窗顯示最後一次語音輸入的內容
                speechText.setTextColor(Color.WHITE);
                // note.append(speechText.getText().toString());//NOTE內新增最後一次語音輸入的內容
                //note.setTextColor(Color.BLACK);

                switch (input) {
                    case "刪除全部文字":
                        note.setText("");
                        break;
                    case "刪除所有文字":
                        note.setText("");
                        break;
                    case "清除所有文字":
                        note.setText("");
                        break;
                    case "清除全部文字":
                        note.setText("");
                        break;
                    case "儲存":
                        NoteDbHelper myDB = new NoteDbHelper(NoteActivity.this);
                        myDB.AddMemo(title.getText().toString().trim(),//將標題及記事本內容儲存至SQLLITE
                                note.getText().toString().trim());
                        break;
                    case"上一頁":{
                        Intent intent = new Intent();
                        intent.setClass(NoteActivity.this, MainActivity.class);
                        startActivity(intent);}
                        break;
                    default:
                        note.append(speechText.getText().toString());//NOTE內新增最後一次語音輸入的內容
                        note.setTextColor(Color.WHITE);
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

        public String replaceText (String target, String input, String replacement){//使用正則替換文字
            // 創建Pattern对象
            Pattern pattern = Pattern.compile(target);
            // 創建Matcher对象
            Matcher matcher = pattern.matcher(input);
            // 替换
            return matcher.replaceAll(replacement);
        }

}


