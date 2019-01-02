package tech.wd.com.shaowenlongproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import tech.wd.com.shaowenlongproject.R;
import tech.wd.com.shaowenlongproject.adapter.NewsAdapter;
import tech.wd.com.shaowenlongproject.apis.Apis;
import tech.wd.com.shaowenlongproject.bean.MessageBean;
import tech.wd.com.shaowenlongproject.bean.NewsBean;
import tech.wd.com.shaowenlongproject.presenter.PresenterImpl;
import tech.wd.com.shaowenlongproject.view.Iview;

public class MainActivity extends AppCompatActivity implements Iview {

    private PresenterImpl presenter;
    private NewsAdapter newsAdapter;
    private XRecyclerView xRecyclerView;
    private boolean bool = true;
    private int page;
    private ImageView image_change;
    private NewsBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page=1;
        setContentView(R.layout.activity_main);
        presenter = new PresenterImpl(this);
        xRecyclerView = findViewById(R.id.xrecycle);
        image_change = findViewById(R.id.image_change);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);

        newsAdapter = new NewsAdapter(MainActivity.this,bool);

        xRecyclerView.setAdapter(newsAdapter);
        xRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
        xRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.HORIZONTAL));
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
        initData();
        newsAdapter.setOnClickListener(new NewsAdapter.onClickCallBack() {
            @Override
            public void setOnClick(String title, String price) {
                MessageBean messageBean = new MessageBean(title,price);
                EventBus.getDefault().postSticky(messageBean);
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                startActivity(intent);
            }
        });
        image_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bool){
                    newsAdapter = new NewsAdapter(MainActivity.this,bool);
                    xRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
                    newsAdapter.setData(bean.getData());
                    xRecyclerView.setAdapter(newsAdapter);
                    bool=false;
                }else {
                    newsAdapter = new NewsAdapter(MainActivity.this,bool);
                    xRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    newsAdapter.setData(bean.getData());
                    xRecyclerView.setAdapter(newsAdapter);
                    bool = true;
                }
            }
        });

    }

    @Override
    public void showData(Object data) {
        if(data instanceof NewsBean){
            bean = (NewsBean) data;
            if (bean == null){
                Toast.makeText(MainActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
            }else{
                if(page == 1){
                    newsAdapter.setData(bean.getData());
                }else{
                    newsAdapter.addData(bean.getData());
                }
                xRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
                page++;
                xRecyclerView.refreshComplete();
                xRecyclerView.loadMoreComplete();
            }

        }
    }

    public void initData(){
        presenter.startRequest(Apis.URL+page,NewsBean.class);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
