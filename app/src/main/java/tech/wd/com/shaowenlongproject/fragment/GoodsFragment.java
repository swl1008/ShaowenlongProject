package tech.wd.com.shaowenlongproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.wd.com.shaowenlongproject.R;
import tech.wd.com.shaowenlongproject.bean.CommentBean;
import tech.wd.com.shaowenlongproject.bean.DetailBean;
import tech.wd.com.shaowenlongproject.bean.MessageBean;

public class GoodsFragment extends Fragment {
    @BindView(R.id.product_title)
    TextView title;
    @BindView(R.id.product_price)
    TextView price;
    @BindView(R.id.btn_todetail)
    Button btn_todetail;
    @BindView(R.id.btn_tocomment)
    Button btn_tocomment;
    private String title1;
    private String price1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods,container,false);
        ButterKnife.bind(this,view);
        title.setText(title1);
        price.setText(price1);

        btn_todetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailBean detailBean = new DetailBean(title1);
                EventBus.getDefault().postSticky(detailBean);
            }
        });
        btn_tocomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentBean commentBean = new CommentBean(price1);
                EventBus.getDefault().postSticky(commentBean);
            }
        });
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getMessage(MessageBean messageBean){
        title1 = messageBean.getTitle();
        price1 = messageBean.getMessage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
