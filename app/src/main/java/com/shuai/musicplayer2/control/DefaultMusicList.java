package com.shuai.musicplayer2.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shuai.musicplayer2.R;
import com.shuai.musicplayer2.adapter.MusicListAdapter;
import com.shuai.musicplayer2.domain.MusicListInfo;
import com.shuai.musicplayer2.utils.GetMenuList;
import com.shuai.musicplayer2.utils.LikeCRUD;
import com.shuai.musicplayer2.utils.MenuList;

public class DefaultMusicList extends AppCompatActivity {
    private RecyclerView mRvResult;
    private MusicListAdapter mAdapter;
    private TextView mInfo;
    private ProgressBar mProgressBar;
    public static Handler mHandler;
    private String mTag;
    private EditText mEditText;
    private String mKeyWord;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        InitView();
    }
    private void InitView() {
        mEditText = findViewById(R.id.keywords);
        mInfo = findViewById(R.id.list_info);
        mRvResult = findViewById(R.id.rv_list);
        mAdapter = new MusicListAdapter();
        mProgressBar = findViewById(R.id.pv_list);
        mProgressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        mTag = intent.getStringExtra("Tag");
        String info = mTag +intent.getStringExtra("keyword");
        mInfo.setText(info);
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what==100){
                    /**
                     * Result数据加载完毕，开始更新UI
                     */
                    mProgressBar.setVisibility(View.GONE);
                    //UI更新搜索结果
                    update();
                    initListener();
                }
                return true;
            }
        });
    }


    public void search(View view) {
        mKeyWord = mEditText.getText().toString();
        if(mKeyWord ==null|| mKeyWord.equals("")){
            Toast.makeText(this, "请输入搜索关键词", Toast.LENGTH_SHORT).show();
        }
        // TODO: 2020/11/11 实现加载更多后更改这里
        new GetMenuList(30).getMusicList(mKeyWord);
        Intent intent = new Intent(DefaultMusicList.this,Result.class);
        intent.putExtra("Tag","搜索：");
        intent.putExtra("keyword",mKeyWord);
        startActivity(intent);
    }

    //实现点击的接口
    public void initListener(){
        mAdapter.setOnMusicClickListener(new MusicListAdapter.OnMusicClickListener() {
            @Override
            public void onMusicClick(int position) {
                if (MenuList.sMusicListInfo.get(position).getUrl()!=null){
                    Intent intent = new Intent(DefaultMusicList.this,Player.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                }else {
                    Toast.makeText(DefaultMusicList.this, "付费歌曲，不支持试听", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAdapter.setOnLongClickListener(new MusicListAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DefaultMusicList.this);
                MusicListInfo info = MenuList.sMusicListInfo.get(position);
                builder.setMessage("音乐名："+info.getName()+"\r\n作者："+info.getArtistsName()+"\r\n专辑："+info.getAlbumName());
                builder.setCancelable(true);
                if (mTag.equals("我喜欢的音乐")){
                    builder.setTitle("确认删除："+info.getName());
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new LikeCRUD().likeDelete(DefaultMusicList.this,position);
                            MenuList.sMusicListInfo.remove(position);
                            finish();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    //更新UI
    private void update() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(DefaultMusicList.this);
        mAdapter.setData();
        mRvResult.setAdapter(mAdapter);
        mRvResult.setLayoutManager(layoutManager);
    }
}
