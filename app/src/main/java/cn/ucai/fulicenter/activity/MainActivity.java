package cn.ucai.fulicenter.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.fragment.CarFragment;
import cn.ucai.fulicenter.fragment.CategoryFragment;
import cn.ucai.fulicenter.fragment.NewGoodsFragment;

/**
 * Created by Administrator on 2016/10/14.
 */
public class MainActivity extends AppCompatActivity {
    @Bind(R.id.newGoods)
    RadioButton newGoods;
    @Bind(R.id.Boutique)
    RadioButton Boutique;
    @Bind(R.id.Category)
    RadioButton Category;
    @Bind(R.id.tvCar)
    RadioButton tvCar;
    @Bind(R.id.me)
    RadioButton me;

    private int index;

    RadioButton[] rbs;
    NewGoodsFragment newGoodsFragment;
    CategoryFragment categoryFragment;
    CarFragment carFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuli_center_main);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {
        rbs = new RadioButton[5];
        rbs[0] = newGoods;
        rbs[1] = Boutique;
        rbs[2] = Category;
        rbs[3] = tvCar;
        rbs[4] = me;
        rbs[0].setSelected(true);
        FragmentManager fragmentManager =getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        newGoodsFragment = new NewGoodsFragment();
        ft.add(R.id.f1,newGoodsFragment);
        ft.commit();
    }


    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.newGoods:
                index = 0;

                break;
            case R.id.Boutique:
                index = 1;
                break;
            case R.id.Category:
                index = 2;
                break;
            case R.id.tvCar:
                index = 3;
                break;
            case R.id.me:
                index = 4;
                break;
        }
        setRadioChecked(index);
    }

    private void setRadioChecked(int index) {
        for (int i = 0; i < rbs.length; i++) {
            if (i == index) {
                rbs[i].setChecked(true);
            } else {
                rbs[i].setChecked(false);
            }
        }
    }


}
