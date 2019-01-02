package tech.wd.com.shaowenlongproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import tech.wd.com.shaowenlongproject.R;
import tech.wd.com.shaowenlongproject.activity.DetailsActivity;
import tech.wd.com.shaowenlongproject.bean.NewsBean;
import tech.wd.com.shaowenlongproject.fragment.GoodsFragment;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NewsBean.DataBean> list;
    private Context context;
    private boolean boo=true;

    public NewsAdapter(Context context, boolean boo) {
        this.context = context;
        this.boo = boo;
        list = new ArrayList<>();
    }
    public void setData(List<NewsBean.DataBean> datas){
        list.clear();
        if(datas!=null){
            list.addAll(datas);
        }
        notifyDataSetChanged();
    }
    public void addData(List<NewsBean.DataBean> datas){
        if(datas!=null){
            list.addAll(datas);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (boo){
            View view = LayoutInflater.from(context).inflate(R.layout.goods_linear,viewGroup,false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.goods_grid,viewGroup,false);
            ViewHolder2 vh2 = new ViewHolder2(view);
            return vh2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (boo){
            ViewHolder holder = (ViewHolder) viewHolder;
            final int position = i;
            holder.title.setText(list.get(i).getCategory());
            holder.message.setText(list.get(i).getTitle());
            Uri uri = Uri.parse(list.get(position).getThumbnail_pic_s());
            holder.imageView.setImageURI(uri);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickCallBack!=null){
                        clickCallBack.setOnClick(list.get(position).getCategory(),list.get(position).getTitle());
                    }
                }
            });
        }else {
            ViewHolder2 holder2 = (ViewHolder2) viewHolder;
            final int position = i;
            holder2.title.setText(list.get(i).getCategory());
            holder2.message.setText( list.get(i).getTitle());
            Uri uri = Uri.parse(list.get(position).getThumbnail_pic_s());
            holder2.imageView.setImageURI(uri);
            holder2.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   context.startActivity(new Intent(context,DetailsActivity.class));
//                    if (clickCallBack!=null){
//                        clickCallBack.setOnClick(list.get(position).getCategory(),list.get(position).getTitle());
//                    }
                 // EventBus.getDefault().postSticky(new );
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imageView;
        TextView title;
        TextView message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.linear_image);
            title = itemView.findViewById(R.id.linear_title);
            message = itemView.findViewById(R.id.linear_price);
        }
    }
    public class ViewHolder2 extends RecyclerView.ViewHolder {
        SimpleDraweeView imageView;
        TextView title;
        TextView message;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.grid_image);
            title = itemView.findViewById(R.id.grid_title);
            message = itemView.findViewById(R.id.grid_price);
        }
    }
    private onClickCallBack clickCallBack;
    public interface onClickCallBack{
        void setOnClick(String title,String price);
    }
    public void setOnClickListener(onClickCallBack clickCallBack){
        this.clickCallBack = clickCallBack;
    }
}
