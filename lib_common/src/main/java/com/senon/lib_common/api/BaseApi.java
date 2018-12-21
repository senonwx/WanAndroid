package com.senon.lib_common.api;

import com.senon.lib_common.bean.Banner;
import com.senon.lib_common.bean.CollectionArticle;
import com.senon.lib_common.bean.HomeArticle;
import com.senon.lib_common.bean.KnowledgeSysArticle;
import com.senon.lib_common.bean.KnowledgeSystem;
import com.senon.lib_common.bean.Login;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.bean.ProjectArticle;
import com.senon.lib_common.bean.WXarticle;
import com.senon.lib_common.bean.WXchapters;
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
    //首页最新项目
    @GET("article/listproject/{page}/json")
    Observable<BaseResponse<ProjectArticle>> getHomeProject(@Path("page") int page);

    //项目列表数据
    @GET("project/list/{page}/json")
    Observable<BaseResponse<ProjectArticle>> getProjectList(@Path("page") int page, @Query("cid") int cid);

    //体系数据
    @GET("tree/json")
    Observable<BaseResponse<List<KnowledgeSystem>>> getKnowledgeList();
    //知识体系下的文章
    @GET("article/list/{page}/json")
    Observable<BaseResponse<KnowledgeSysArticle>> getKnowledgeArticle(@Path("page") int page, @Query("cid") int cid);

    //获取公众号列表
    @GET("wxarticle/chapters/json")
    Observable<BaseResponse<List<WXchapters>>> getWXarticleChapters();
    //查看某个公众号历史数据
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<BaseResponse<WXarticle>> getWXarticleList(@Path("id") int id, @Path("page") int page);

    //收藏站内文章
    @POST("lg/collect/{id}/json")
    Observable<BaseResponse> getCollect(@Path("id") int id);
    //取消收藏-->文章列表
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResponse> getUncollectOriginId(@Path("id") int id);
    //取消收藏-->我的收藏页面
    @POST("lg/uncollect/{id}/json")
    Observable<BaseResponse> getUncollect(@Path("id") int id,@Query("originId") int originId);

    //收藏文章列表
    @GET("lg/collect/list/{page}/json")
    Observable<BaseResponse<CollectionArticle>> getCollectList(@Path("page") int page);
}
