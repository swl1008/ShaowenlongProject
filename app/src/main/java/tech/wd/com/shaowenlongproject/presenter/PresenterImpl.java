package tech.wd.com.shaowenlongproject.presenter;

import tech.wd.com.shaowenlongproject.bean.NewsBean;
import tech.wd.com.shaowenlongproject.callback.MyCallBack;
import tech.wd.com.shaowenlongproject.model.ModelImpl;
import tech.wd.com.shaowenlongproject.view.Iview;

public class PresenterImpl implements Ipresenter {
    private Iview iview;
    private ModelImpl model;

    public PresenterImpl(Iview iview) {
        this.iview = iview;
        model = new ModelImpl();
    }

    @Override
    public void startRequest(String url, Class clazz) {
        model.requestData(url, NewsBean.class, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.showData(data);
            }
        });
    }

    public void onDetach(){
        if (model!=null){
            model=null;
        }
        if (iview != null){
            iview=null;
        }
    }
}
