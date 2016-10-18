//package cn.ucai.fulicenter.fragment;
//
//import android.app.Fragment;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import cn.ucai.fulicenter.R;
//import cn.ucai.fulicenter.activity.MainActivity;
//import cn.ucai.fulicenter.adapter.NewGoodsAdapter;
//
///**
// * Created by Administrator on 2016/10/18.
// */
//public class BoutiqueFragment extends Fragment {
//    MainActivity context;
//    @Bind(R.id.tvRefreshHint_boutique)
//    TextView tvRefreshHintBoutique;
//    @Bind(R.id.recyclerview_boutique)
//    RecyclerView recyclerviewBoutique;
//    @Bind(R.id.swiprefresh_boutique)
//    SwipeRefreshLayout swiprefreshBoutique;
//    GridLayoutManager   mGridaLayoutManager;
//
//
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        context = (MainActivity) getActivity();
//        View view = inflater.inflate(R.layout.fragment_boutique, container, false);
//        ButterKnife.bind(this, view);
//        initView();
//        initDate();
//        setListener();
//        return view;
//    }
//
//    private void initView() {
//        mGridaLayoutManager = new GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false);
//        mNewGoodsAdapter = new NewGoodsAdapter(mContext, list);
//        recyclerviewBoutique.setLayoutManager(mGridaLayoutManager);
//        recyclerviewBoutique.setAdapter(mGridaLayoutManager);
//    }
//
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }
//
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
//}
