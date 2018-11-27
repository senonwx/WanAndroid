package com.senon.lib_common.api;

import com.senon.lib_common.login.Login;
import com.senon.lib_common.net.bean.BaseResponse;
import java.util.Map;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 网络请求接口
 */
public interface BaseApi {


    @POST("login/login")
    @FormUrlEncoded
    Observable<BaseResponse<Login>> login(@FieldMap Map<String, String> map);
    @POST("login/is-registered")
    @FormUrlEncoded
    Observable<BaseResponse<Login>> verifyPhoneIsRsg(@FieldMap Map<String, String> map);

}
