package ittraining.revision.com.kinbech;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ittraining.revision.com.kinbech.CustomAdapter.PagerAdapter;


/**
 * Created by Akash on 17/02/2016.
 */
public class TabActivity extends AppCompatActivity{
    /*Toolbar mToolbar;*/
    TabLayout mTabLayout;
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        /*mToolbar = (Toolbar)findViewById(R.id.activity_tab_toolbar);*/
        mTabLayout = (TabLayout)findViewById(R.id.activity_tab_tab_layout);
        mViewPager = (ViewPager)findViewById(R.id.activity_tab_viewpager);

        //setSupportActionBar(mToolbar);

        mTabLayout.addTab(mTabLayout.newTab().setText("TabOne"));
        mTabLayout.addTab(mTabLayout.newTab().setText("TabTwo"));
        mTabLayout.addTab(mTabLayout.newTab().setText("TabThree"));

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),mTabLayout.getTabCount());
        mViewPager.setAdapter(pagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
