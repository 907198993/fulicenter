package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.Good_DetailActivity;
import cn.ucai.fulicenter.activity.SplashActivity;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;

/**
 * Created by Administrator on 2016/6/15.
 */
public class NewGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    private ArrayList<NewGoodBean> newGoodsBeenList;

    //这是构造器用来降低耦合度
    public NewGoodsAdapter(Context mContext,ArrayList<NewGoodBean> goodsBeenList) {
        this.newGoodsBeenList = goodsBeenList;
        this.mContext = mContext;
    }

    //定义itemType的常量
    final int NEW_GOODS_TYPE=0;
    final int FOOTER_HINT_TYPE = 1;
    final int NULL_TYPE=2;
    //用来判断是否还有更多商品
    private boolean isMore=true;



    //取得ViewGroup用来给加在商品图片是设置parent；
    ViewGroup parent;



    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        LayoutInflater from = LayoutInflater.from(mContext);
        View layout;
        switch (viewType) {
            case FOOTER_HINT_TYPE:
                layout = from.inflate(R.layout.item_foot_table, parent, false);
                holder = new FootViewHolder(layout);
                break;
            case NEW_GOODS_TYPE:
                layout = from.inflate(R.layout.new_good, parent, false);
                holder = new NewGoodsViewHolder(layout);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //这里进行判断，如果是最后一个就代表是底部提醒信息
        if(position==newGoodsBeenList.size()){
          //  mGridaLayoutManager.setSpanCount(2);
            FootViewHolder footerViewHolder = (FootViewHolder) holder;
            //通过isMore变量来判断是否有更多数据加载
            if(isMore){
                footerViewHolder.foot.setText("下拉加载更多");
            }else {
                footerViewHolder.foot.setText("没有跟多数据可以加载");
            }
            return;
        }
        NewGoodBean newGoodsBean = newGoodsBeenList.get(position);
        NewGoodsViewHolder newGoodsViewHolder = (NewGoodsViewHolder) holder;
        newGoodsViewHolder.goodTvName.setText(newGoodsBean.getGoodsName());
        newGoodsViewHolder.tvPrice.setText(newGoodsBean.getShopPrice());
        newGoodsViewHolder.newGoodDetail.setTag(newGoodsBean.getGoodsId());
        //下载图片
        ImageLoader.build(I.SERVER_ROOT+I.REQUEST_DOWNLOAD_IMAGE)
                .addParam(I.Boutique.IMAGE_URL,newGoodsBean.getGoodsThumb())
                .imageView(newGoodsViewHolder.netivPhoto)
                .width(160)
                .height(240)
                .listener(parent)
                .showImage(mContext);
    }

    @Override
    public int getItemCount() {
        //如果这个list是null就返回0
        return newGoodsBeenList==null?0:newGoodsBeenList.size()+1;

    }

    //定义刷新加载时list数据改变的方法
    public  void initOrRefreshList(ArrayList<NewGoodBean> list){
        newGoodsBeenList.clear();
        this.newGoodsBeenList.addAll(list);
        L.i(newGoodsBeenList.toString());
        notifyDataSetChanged();
    }

    public void addList(ArrayList<NewGoodBean> list){
        this.newGoodsBeenList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==newGoodsBeenList.size()){
            //如果是最后一个就返回FOOTER_HINT
            return FOOTER_HINT_TYPE;
        }
        //如果不是最后就代表显示商品信息
        return NEW_GOODS_TYPE;
    }




     class FootViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.foot)
        TextView foot;

        FootViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

     class NewGoodsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.netiv_photo)
        ImageView netivPhoto;
        @Bind(R.id.good_tv_name)
        TextView goodTvName;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.new_good_detail)
        LinearLayout newGoodDetail;
        NewGoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

         @OnClick(R.id.new_good_detail)
         public void onGoodsItemClick() {
             int goodsId = (int) newGoodDetail.getTag();
             mContext.startActivity(new Intent(mContext, Good_DetailActivity.class)
                     .putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId));
         }

     }
}