package com.senon.lib_common.net;

import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 网络统一拦截
 */
public class RequestInterceptor implements Interceptor {

    private final int DEVICE_TYPE = 1; // 设备类型

    private RequestInterceptor() {
    }

    private static RequestInterceptor instance;

    public static RequestInterceptor getInstance() {
        if (instance == null) {
            synchronized (RequestInterceptor.class) {
                if (instance == null) {
                    instance = new RequestInterceptor();
                }
            }
        }
        return instance;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("data", fetchHeaderInfo())
                .method(original.method(), original.body());
        return chain.proceed(requestBuilder.build());
    }

    private String fetchHeaderInfo() {
//        String deviceCode = MD5Utils.GetMD5Code(MobileInfo.phoneOnlyCode(context));


        Map<String, String> map = new HashMap<>();
//        map.put("DeviceType", String.valueOf(DEVICE_TYPE));
//        map.put("DeviceToken", deviceCode);
//        map.put("APPVersion", APP_VERSION_NAME);
//        map.put("RegistrationID", jpushID);
//        map.put("Authorization", token);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }
}
