package cn.ucai.fulicenter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.MainActivity;
import cn.ucai.fulicenter.adapter.CategoryAdapter;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;

/**
 * Created by Administrator on 2016/10/20.
 */
public class CategoryFragment extends BaseFragment {
    MainActivity mContext;
    View layout;
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryGroupBean> GroupList;
    ArrayList<ArrayList<CategoryChildBean>> ChildList;

    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    int groupCount = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = (MainActivity) getContext();
        layout = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, layout);
        super.onCreateView(inflater,container,savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {
        GroupList = new ArrayList<>();
        ChildList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), GroupList, ChildList);
        expandableListView.setAdapter(categoryAdapter);
    }

    @Override
    protected void initData() {
        downloadGroup();
    }

    private void downloadGroup() {
        NetDao.downloadCategoryGroup(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] categoryGroupBeen) {
                if (categoryGroupBeen != null) {
                    GroupList = ConvertUtils.array2List(categoryGroupBeen);
                    int i = 0;
                    for (CategoryGroupBean group : categoryGroupBeen) {
                        int id = categoryGroupBeen[i].getId();
                        ChildList.add(i, new ArrayList<CategoryChildBean>());
                          downloadChlid(i,id);
                        i++;
                    }
                }
            }
            @Override
            public void onError(String error) {

            }
        });
    }

    private void downloadChlid(final int i,int parentId) {
        NetDao.downloadCategoryChild(mContext,parentId, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] categoryChildBeen) {
                groupCount++;
                if (categoryChildBeen != null) {
                    ArrayList<CategoryChildBean> childlist
                            = ConvertUtils.array2List(categoryChildBeen);
                    if (childlist != null) {
                        ChildList.set(i, childlist);
                    }
                }
                if (GroupList.size() == groupCount) {
                    categoryAdapter.addListItem(GroupList,ChildList);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
