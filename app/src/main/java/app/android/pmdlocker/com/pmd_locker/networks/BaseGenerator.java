package app.android.pmdlocker.com.pmd_locker.networks;

import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import app.android.pmdlocker.com.pmd_locker.BuildConfig;
import app.android.pmdlocker.com.pmd_locker.utils.ConstantGlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.EndpointUtil;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by tuanhoang on 3/23/17.
 */

public class BaseGenerator {
    private static OkHttpClient.Builder httpClient;
    private static String API_BASE_URL = BaseEndpoint.HOST;
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());
    private static String TAG = "--BaseGenerator--";

    public static <S> S createService(Class<S> serviceClass, final String authToken, final String language, final Map<String, String> options) {
        if(!options.containsKey(ConstantGlobalVariable.USER_DEVICE_TYPE))
        {
            options.put(ConstantGlobalVariable.USER_DEVICE_TYPE,ConstantGlobalVariable.DEVICE_ANDROID);
        }
        if (authToken != null) {
            httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(BuildConfig.READ_TIME_OUT, TimeUnit.SECONDS);
            httpClient.connectTimeout(BuildConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS);
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();
                    HttpUrl.Builder builder = originalHttpUrl.newBuilder();
                    for (Map.Entry<String, String> entry : options.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        builder.addQueryParameter(key, value);
                    }
                    HttpUrl url = builder.build();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "laidrub_ZpN6HjrO6oWl4FRMV1le93UJKZmZ2P4Tp0zmayDlgJIQxQ")
//                            .header("Usertoken", authToken)
                            .header("Customer-Token", authToken)
                            .header("access_token", authToken)
                            .header("Accept-Language", language)
                            .header("Accept","application/json")

                            .url(url);
                    // Request customization: add request headers
                    Request request = requestBuilder.build();
                    Log.e(TAG, "intercept: " + authToken);
                    Log.e(TAG, request.toString());
                    return chain.proceed(request);
                }
            });
        }
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(EndpointUtil.getLogging()).build();
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
