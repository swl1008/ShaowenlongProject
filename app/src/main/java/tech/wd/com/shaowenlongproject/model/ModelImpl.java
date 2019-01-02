package tech.wd.com.shaowenlongproject.model;

import com.google.gson.Gson;

import tech.wd.com.shaowenlongproject.bean.NewsBean;
import tech.wd.com.shaowenlongproject.callback.MyCallBack;
import tech.wd.com.shaowenlongproject.retrofit.RetrofitManager;

public class ModelImpl implements Imodel {
    @Override
    public void requestData(String url, Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().get(url).result(new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                NewsBean newsBean = new Gson().fromJson(data, NewsBean.class);
                myCallBack.onSuccess(newsBean);
            }

            @Override
            public void onFail(String error) {

            }
        });
    }
}
