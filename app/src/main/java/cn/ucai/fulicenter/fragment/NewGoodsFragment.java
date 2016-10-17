package cn.ucai.fulicenter.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.adapter.NewGoodsAdapter;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/10/14.
 */
public class NewGoodsFragment extends Fragment {
    View view;
    @Bind(R.id.tv_hint)
    TextView tvHint;
    @Bind(R.id.recyclerview_newgoods)
    RecyclerView recyclerviewNewgoods;
    @Bind(R.id.swipe_Refresh)
    SwipeRefreshLayout swipeRefresh;

    GridLayoutManager mGridaLayoutManager;
    StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    NewGoodsAdapter mNewGoodsAdapter;
    ArrayList<NewGoodBean> list;
    MainActivity mContext;

    int page_id = 1;

    final int PULL_UP_ACTION = 0;
    final int PULL_DOWN_ACTION = 1;
    final int BENGIE_ACTION = 2;

    int mNewState;

    public NewGoodsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = (MainActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_new_good, container, false);
        ButterKnife.bind(this, view);
        initView();
        initDate();
        setListener();
        setManagerSpan();
        return view;

    }

    private void setManagerSpan() {
        mGridaLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == mNewGoodsAdapter.getItemCount() - 1 ? 2 : 1;
            }
        });
    }

    //设置监听事件
    private void setListener() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setEnabled(true);
                swipeRefresh.setRefreshing(true);
                tvHint.setVisibility(View.VISIBLE);
                page_id = 1;
                downData(page_id, PULL_DOWN_ACTION);
            }
        });
        recyclerviewNewgoods.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mNewState = newState;
                int lastPosition = mGridaLayoutManager.findLastVisibleItemPosition();

                if (lastPosition >= mNewGoodsAdapter.getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && mNewGoodsAdapter.isMore()) {
                    page_id++;
                    downData(page_id, PULL_UP_ACTION);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void initView() {
        list = new ArrayList<>();
        mGridaLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mNewGoodsAdapter = new NewGoodsAdapter(mContext, list);
        recyclerviewNewgoods.setLayoutManager(mGridaLayoutManager);
        recyclerviewNewgoods.setAdapter(mNewGoodsAdapter);
    }

    private void downData(int page_id, final int action) {
        final OkHttpUtils<NewGoodBean[]> utils = new OkHttpUtils<>(getContext());

        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.GoodsDetails.KEY_CAT_ID, 0 + "")
                .addParam(I.PAGE_ID, page_id + "")
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT) + "")
                .targetClass(NewGoodBean[].class)
                .execute(new OkHttpUtils.OnCompleteListener<NewGoodBean[]>() {
                    @Override
                    public void onSuccess(NewGoodBean[] result) {

                        if (result != null && result.length != 0) {
                            list = utils.array2List(result);
                            switch (action) {
                                case BENGIE_ACTION:
                                    mNewGoodsAdapter.initOrRefreshList(list);
                                    break;
                                case PULL_DOWN_ACTION:
                                    swipeRefresh.setRefreshing(false);
                                    tvHint.setVisibility(View.GONE);
                                    mNewGoodsAdapter.setMore(true);
                                    mNewGoodsAdapter.initOrRefreshList(list);
                                    ImageLoader.release();
                                    break;
                                case PULL_UP_ACTION:
                                    mNewGoodsAdapter.addList(list);
                                    break;
                            }

                        } else {
                            mNewGoodsAdapter.setMore(false);
                            mNewGoodsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        L.e("下载失败了");
                    }
                });

    }

    //初始化数据
    private void initDate() {
        downData(1, BENGIE_ACTION);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
