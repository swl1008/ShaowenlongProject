package tech.wd.com.shaowenlongproject.model;

import tech.wd.com.shaowenlongproject.callback.MyCallBack;

public interface Imodel {
    void requestData(String url, Class clazz, MyCallBack myCallBack);
}
