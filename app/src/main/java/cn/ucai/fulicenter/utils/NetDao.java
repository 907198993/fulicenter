package cn.ucai.fulicenter.utils;

import android.content.Context;

import cn.ucai.fulicenter.bean.NewGoodBean;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NetDao {
    public static void  downloadNewGoods(Context context,
                                         int pageId,
                                         OkHttpUtils.OnCompleteListener<NewGoodBean[]> listener){
      OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(I.CAT_ID))
                .addParam(I.PAGE_ID, pageId + "")
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodBean[].class)
                .execute(listener);
    }
}
