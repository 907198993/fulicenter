package cn.ucai.fulicenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;

/**
 * Created by Administrator on 2016/6/15.
 */
public class BoutiqueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    MainActivity context;
    private ArrayList<BoutiqueBean> boutiqueBeanArrayList;

    //这是构造器用来降低耦合度
    public BoutiqueAdapter(MainActivity context, ArrayList<BoutiqueBean> boutiqueBeanArrayList) {
        this.boutiqueBeanArrayList = boutiqueBeanArrayList;
        this.context = context;
    }

    //定义itemType的常量
    final int NEW_GOODS_TYPE = 0;
    final int FOOTER_HINT_TYPE = 1;
    final int NULL_TYPE = 2;
    //用来判断是否还有更多商品
    private boolean isMore = true;


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
        LayoutInflater from = LayoutInflater.from(context);
        View layout;
        switch (viewType) {
            case FOOTER_HINT_TYPE:
                layout = from.inflate(R.layout.item_foot_table, parent, false);
                holder = new FootViewHolder(layout);
                break;
            case NEW_GOODS_TYPE:
                layout = from.inflate(R.layout.boutique, parent, false);
                holder = new BoutiqueViewHolder(layout);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //这里进行判断，如果是最后一个就代表是底部提醒信息
        if (position == boutiqueBeanArrayList.size()) {
            //  mGridaLayoutManager.setSpanCount(2);
            FootViewHolder footerViewHolder = (FootViewHolder) holder;
            //通过isMore变量来判断是否有更多数据加载
            if (isMore) {
                footerViewHolder.foot.setText("下拉加载更多");
            } else {
                footerViewHolder.foot.setText("没有跟多数据可以加载");
            }
            return;
        }
        BoutiqueBean boutiqueBean = boutiqueBeanArrayList.get(position);
        BoutiqueViewHolder boutiqueViewHolder = (BoutiqueViewHolder) holder;
        boutiqueViewHolder.titleBoutique.setText(boutiqueBean.getTitle());
        boutiqueViewHolder.nameBoutique.setText(boutiqueBean.getName());
       // boutiqueViewHolder.desBoutique.setText(boutiqueBean.setDescription());
        //下载图片
        ImageLoader.build(I.SERVER_ROOT+I.REQUEST_DOWNLOAD_IMAGE)
                .addParam(I.Boutique.IMAGE_URL,boutiqueBean.getImageurl())
                .imageView(boutiqueViewHolder.botiquePhoto)
                .width(160)
                .height(240)
                .listener(parent)
                .showImage(context);
    }

    @Override
    public int getItemCount() {
        //如果这个list是null就返回0
        return boutiqueBeanArrayList == null ? 0 : boutiqueBeanArrayList.size() + 1;

    }

    //定义刷新加载时list数据改变的方法
    public void initOrRefreshList(ArrayList<BoutiqueBean> list) {
        boutiqueBeanArrayList.clear();
        this.boutiqueBeanArrayList.addAll(list);
        L.i(boutiqueBeanArrayList.toString());
        notifyDataSetChanged();
    }

    public void addList(ArrayList<BoutiqueBean> list) {
        this.boutiqueBeanArrayList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == boutiqueBeanArrayList.size()) {
            //如果是最后一个就返回FOOTER_HINT
            return FOOTER_HINT_TYPE;
        }
        //如果不是最后就代表显示商品信息
        return NEW_GOODS_TYPE;
    }


    static class FootViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.foot)
        TextView foot;

        FootViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class BoutiqueViewHolder  extends RecyclerView.ViewHolder{
        @Bind(R.id.botique_photo)
        ImageView botiquePhoto;
        @Bind(R.id.title_boutique)
        TextView titleBoutique;
        @Bind(R.id.name_boutique)
        TextView nameBoutique;
        @Bind(R.id.des_boutique)
        TextView desBoutique;


        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}









