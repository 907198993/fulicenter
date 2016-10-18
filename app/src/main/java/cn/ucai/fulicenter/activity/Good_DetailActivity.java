package cn.ucai.fulicenter.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.FlowIndicator;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.SlideAutoLoopView;
import cn.ucai.fulicenter.bean.AlbumBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.NetDao;
import cn.ucai.fulicenter.utils.OkHttpUtils;

public class Good_DetailActivity extends AppCompatActivity {
    GoodDetailsBean mGoods;
    int mCurrentColo = 0;
    int GoodsId;
    boolean isRight;
    @Bind(R.id.back)
    ImageView miv_back;
    @Bind(R.id.cart)
    ImageView cart;
    @Bind(R.id.count)
    TextView count;
    @Bind(R.id.collect)
    ImageView miv_collect;
    @Bind(R.id.share)
    ImageView share;
    @Bind(R.id.English_name)
    TextView mtv_English_Name;
    @Bind(R.id.Chinese_name)
    TextView mtv_Chinese_Name;
    @Bind(R.id.price)
    TextView mtv_Price;
    @Bind(R.id.aaaa)
    LinearLayout aaaa;
    @Bind(R.id.salv)
    SlideAutoLoopView mSlideAutoLoopView;
    @Bind(R.id.flowIndicator)
    FlowIndicator mFlowIndicator;
    @Bind(R.id.bbb)
    RelativeLayout bbb;
    @Bind(R.id.wvGoodBrief)
    WebView wb_Brief;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good__detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }






    private void initData() {
        GoodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        final OkHttpUtils<GoodDetailsBean> utils = new OkHttpUtils<>(mContext);
        NetDao.downloadGoodsDetail(mContext, GoodsId, new OkHttpUtils.OnCompleteListener<GoodDetailsBean>() {
            @Override
            public void onSuccess(GoodDetailsBean result) {
                if(result!=null){
                    showGoodsDetail(result);

                }

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void showGoodsDetail(GoodDetailsBean detailsBean){
        mtv_English_Name.setText(detailsBean.getGoodsEnglishName());
        mtv_Chinese_Name.setText(detailsBean.getGoodsName());
        mtv_Price.setText(detailsBean.getCurrencyPrice());
        wb_Brief.loadDataWithBaseURL(null, detailsBean.getGoodsBrief().trim(), I.TEXT_HTML, I.UTF_8, null);
        mSlideAutoLoopView.startPlayLoop(mFlowIndicator,getAlbumImgUrl(detailsBean),getAlbumImgCount(detailsBean));
    }

    private  int  getAlbumImgCount(GoodDetailsBean details){
        if(details.getProperties()!=null  && details.getProperties().length>0){
          return    details.getProperties()[0].getAlbums().length;
        }
        return 0 ;
    }
     private  String[] getAlbumImgUrl(GoodDetailsBean  mGoods) {
         String[] urls = new String[]{};
         if (mGoods.getProperties() != null && mGoods.getProperties().length > 0) {
             AlbumBean[] albums = mGoods.getProperties()[0].getAlbums();
             urls = new String[albums.length];
             for (int i = 0; i < albums.length; i++) {
                 urls[i] = albums[i].getImgUrl();
             }
         }
         return  urls;
     }







    ImageView miv_share;

    private void initView() {
        WebSettings settings = wb_Brief.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }





}
