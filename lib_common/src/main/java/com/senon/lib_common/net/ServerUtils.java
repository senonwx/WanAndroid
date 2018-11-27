package com.senon.lib_common.net;

import com.senon.lib_common.AppConfig;
import com.senon.lib_common.api.BaseApi;
import com.senon.lib_common.utils.ConstantUtils;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 网络请求 工具
 */
public class ServerUtils {
    private static final int TIME_OUT = 5 * 1000;//链接超时时间
    private static BaseApi mBaseApi;
//    private static final String baseUrl = "http://www.kuaidi100.com/";


    public static BaseApi getCommonApi() {
        try {
            if (mBaseApi == null) {
                synchronized (ServerUtils.class) {
                    if (mBaseApi == null) {
                        mBaseApi = createService(BaseApi.class, AppConfig.BASE_URL);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBaseApi;
    }

    private static <S> S createService(Class<S> serviceClass, String url) throws Exception {
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(url)
                        .client(httpClient.build())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder()
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
//                    .addInterceptor(RequestInterceptor.getInstance())//网络请求 统一拦截
                    .addInterceptor(getLogInterceptor())
                    .sslSocketFactory(getSSLSocketFactory())
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);


    private static HttpLoggingInterceptor getLogInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (ConstantUtils.isAppDebug()) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return loggingInterceptor;
    }

    /**
     * 不验证证书
     *
     * @return
     * @throws Exception
     */
    private static SSLSocketFactory getSSLSocketFactory() {
        //创建一个不验证证书链的证书信任管理器。
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        final SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            return sslContext
                    .getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
