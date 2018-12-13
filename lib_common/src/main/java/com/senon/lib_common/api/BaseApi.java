package com.senon.lib_common.api;

import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.bean.Login;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.ProjectArticle;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 网络请求接口
 */
public interface BaseApi {


    //登录
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<Login>> login(@FieldMap Map<String, String> map);

    //登出
    @GET("user/logout/json")
    Observable<BaseResponse> logout();

    //注册
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<Login>> register(@FieldMap Map<String, String> map);


    //首页banner
    @GET("banner/json")
    Observable<BaseResponse<List<Banner>>> banner();

    //首页文章列表
    @GET("article/list/{page}/json")
    Observable<BaseResponse<HomeArticle>> getHomeArticle(@Path("page") int page);
    //项目列表数据
    @GET("project/list/{page}/json")
    Observable<BaseResponse<ProjectArticle>> getProjectList(@Path("page") int page, @Query("cid") int cid);


}
