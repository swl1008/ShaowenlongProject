package tech.wd.com.shaowenlongproject.retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager {
    private final String BASE_URL = "http://www.xieast.com";
    private static RetrofitManager retrofitManager;
    public static synchronized RetrofitManager getInstance() {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager();
        }
        return retrofitManager;
    }

    private BaseApis baseApis;

    public RetrofitManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);
        builder.retryOnConnectionFailure(true);

        OkHttpClient httpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient)
                .build();

        baseApis = retrofit.create(BaseApis.class);
    }

    public RetrofitManager get(String url) {

        baseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        return retrofitManager;
    }

    private Observer observer = new Observer<ResponseBody>() {
        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String data = responseBody.string();
                if (httpListener != null) {
                    httpListener.onSuccess(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (httpListener != null) {
                    httpListener.onFail(e.getMessage());
                }
            }
        }

        @Override
        public void onError(Throwable e) {
            if (httpListener != null) {
                httpListener.onFail(e.getMessage());
            }
        }

        @Override
        public void onCompleted() {

        }
    };

    private HttpListener httpListener;

    public void result(HttpListener httpListener) {
        this.httpListener = httpListener;
    }

    public interface HttpListener {
        void onSuccess(String data);

        void onFail(String error);
    }

}
