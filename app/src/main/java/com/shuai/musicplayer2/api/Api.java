package com.shuai.musicplayer2.api;



import com.shuai.musicplayer2.domain.MusicInfo;
import com.shuai.musicplayer2.domain.MusicList;
import com.shuai.musicplayer2.domain.MusicUrl;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    // 使用@GET指定HTTP请求的路径为"/search"
// Call<MusicList>表示调用该接口会返回一个Call对象，该对象会返回一个MusicList类型的结果
// @Query("keywords")用于指定查询参数名为"keywords"，并将传入的String变量keywords作为值
    @GET("/search")
    Call<MusicList> getMusicList(@Query("keywords") String keywords);

    // 使用@GET指定HTTP请求的路径为"/song/detail"
// Call<MusicInfo>表示调用该接口会返回一个Call对象，该对象会返回一个MusicInfo类型的结果
// @Query("ids")用于指定查询参数名为"ids"，并将传入的String变量ids作为值
    @GET("/song/detail")
    Call<MusicInfo> getMusicInfo(@Query("ids") String ids);

    // 使用@GET指定HTTP请求的路径为"/song/url"
// Call<MusicUrl>表示调用该接口会返回一个Call对象，该对象会返回一个MusicUrl类型的结果
// @Query("id")用于指定查询参数名为"id"，并将传入的String变量id作为值
    @GET("/song/url")
    Call<MusicUrl> getMusicUrl(@Query("id") String id);

// 使用@GET指定HTTP请求的路径为"/toplist"
// Call<TopList>表示调用该接口会返回一个Call对象，该对象会返回一个TopList类型的结果
// 无参数传入

    //@GET("/toplist")
    //Call<TopList> getTopList();

    // 使用@GET指定HTTP请求的路径为"/playlist/detail"
// Call<TopMusicList>表示调用该接口会返回一个Call对象，该对象会返回一个TopMusicList类型的结果
// @Query("id")用于指定查询参数名为"id"，并将传入的String变量id作为值
    //@GET("/playlist/detail")
    //Call<TopMusicList> getTopMusicList(@Query("id") String id);

    // 使用@GET指定HTTP请求的路径为"/comment/music"
// Call<CommentList>表示调用该接口会返回一个Call对象，该对象会返回一个CommentList类型的结果
// @Query("id")用于指定查询参数名为"id"，并将传入的String变量id作为值
    //@GET("/comment/music")
    //Call<CommentList> getComment(@Query("id") String id);
}
