package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.Category_DetaiActivity;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/20.
 */
public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> GroupList;
    ArrayList<ArrayList<CategoryChildBean>> ChilidList;
    int position;
    public CategoryAdapter(Context context, ArrayList<CategoryGroupBean> groupList,
                           ArrayList<ArrayList<CategoryChildBean>> chilidList) {
        this.mContext = context;
        GroupList = groupList;
        ChilidList = chilidList;
    }

    @Override
    public int getGroupCount() {
        return GroupList == null ? 0 : GroupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return ChilidList == null || ChilidList.get(i) == null ? 0 : ChilidList.get(i).size();
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return GroupList.get(groupPosition);
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return ChilidList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpan, View convertView, ViewGroup viewGroup) {
        CategoryGroupViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.category_group, null);
            holder = new CategoryGroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CategoryGroupViewHolder) convertView.getTag();
        }
        CategoryGroupBean group = getGroup(groupPosition);
        if (group != null) {
            ImageLoader.downloadImg(mContext, holder.categoryPhoto, group.getImageUrl());
            holder.categoryName.setText(group.getName());
            if (isExpan) {
                holder.categoryExpand.setImageResource(R.mipmap.arrow2_up);
            } else {
                holder.categoryExpand.setImageResource(R.mipmap.arrow2_down);
            }
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CategoryChildViewHolder holder = null;
        position = childPosition;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.category_child, null);
            holder = new CategoryChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CategoryChildViewHolder) convertView.getTag();
        }
        final CategoryChildBean child = getChild(groupPosition, childPosition);
        holder.categoryChildName.setText(child.getName());
        String imageUrl = child.getImageUrl();
        ImageLoader.downloadImg(mContext, holder.categoryChildPhtot, imageUrl);
        holder.categoryChildRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Category_DetaiActivity.class)
                        .putExtra("childList", ChilidList.get(groupPosition))
                        .putExtra(I.CategoryGroup.NAME, getGroup(groupPosition).getName())
                        .putExtra(I.CategoryChild.CAT_ID, child.getId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    public void addListItem(ArrayList<CategoryGroupBean> arrayList,ArrayList<ArrayList<CategoryChildBean>> arrayList1) {
        this.GroupList.addAll(arrayList);
        this.ChilidList.addAll(arrayList1);
        notifyDataSetChanged();
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    class CategoryGroupViewHolder  {
        @Bind(R.id.category_photo)
        ImageView categoryPhoto;
        @Bind(R.id.category_name)
        TextView categoryName;
        @Bind(R.id.category_Expand)
        ImageView categoryExpand;

        CategoryGroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

     class CategoryChildViewHolder {
        @Bind(R.id.category_child_phtot)
        ImageView categoryChildPhtot;
        @Bind(R.id.category_child_name)
        TextView categoryChildName;
        @Bind(R.id.category_child_rl)
        RelativeLayout categoryChildRl;

         CategoryChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
