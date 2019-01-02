package tech.wd.com.shaowenlongproject.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import tech.wd.com.shaowenlongproject.R;
import tech.wd.com.shaowenlongproject.fragment.CommentFragment;
import tech.wd.com.shaowenlongproject.fragment.DetailFragment;
import tech.wd.com.shaowenlongproject.fragment.GoodsFragment;

public class DetailsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private RadioGroup group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        viewPager = findViewById(R.id.viewpager);
        group = findViewById(R.id.group);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                Fragment fragment=null;
                switch (i){
                    case 0:
                        fragment = new GoodsFragment();
                        break;
                    case 1:
                        fragment = new DetailFragment();
                        break;
                    case 2:
                        fragment = new CommentFragment();
                        break;
                }

                return fragment;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        group.check(R.id.btn_product);
                        break;
                    case 1:
                        group.check(R.id.btn_detail);
                        break;
                    case 2:
                        group.check(R.id.btn_comment);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_product:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.btn_detail:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.btn_comment:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });
    }
}
