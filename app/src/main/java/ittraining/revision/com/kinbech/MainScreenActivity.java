package ittraining.revision.com.kinbech;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by Akash on 23/01/2016.
 */
public class MainScreenActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ListView listView;
    private String[] planets;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        planets=getResources().getStringArray(R.array.planets);
        listView = (ListView)findViewById(R.id.drawer);
        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,planets));
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.drawer_closed);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.drawer_opened);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
/*
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,new HomeFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Home Fragment");
*/


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}

