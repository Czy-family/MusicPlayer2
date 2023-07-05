package com.shuai.musicplayer2.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.shuai.musicplayer2.R;

import com.shuai.musicplayer2.utils.GetMenuList;
import com.shuai.musicplayer2.utils.LikeCRUD;


import java.io.File;


public class Main extends AppCompatActivity {

    private EditText mEditText;
    private static final String TAG = "Main";
    public static Handler mHandler;
    private String mKeyWord;
    private RecyclerView mTopList;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mEditText = findViewById(R.id.keywords);
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == 200) {

                    //mProgressBar.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    public void like(View view){
        if(new LikeCRUD().likeSelete(getApplicationContext(),10)){
            Intent intent = new Intent(Main.this,Result.class);
            intent.putExtra("Tag","音乐列表");
            intent.putExtra("keyword","");
            startActivity(intent);
        }else {
            Toast.makeText(this, "音乐列表为空", Toast.LENGTH_SHORT).show();
        }
    }

    public void dlList(View view){
        startActivity(new Intent(Main.this,Download.class));
    }

    public void search(View view) {
        mKeyWord = mEditText.getText().toString();
        if(mKeyWord ==null|| mKeyWord.equals("")){
            Toast.makeText(this, "请输入搜索关键词", Toast.LENGTH_SHORT).show();
        }
        // TODO: 2020/11/11 实现加载更多后更改这里
        new GetMenuList(30).getMusicList(mKeyWord);
        Intent intent = new Intent(Main.this,Result.class);
        intent.putExtra("Tag","搜索：");
        intent.putExtra("keyword",mKeyWord);
        startActivity(intent);
    }


}
