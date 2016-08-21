package ittraining.revision.com.kinbech;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ittraining.revision.com.kinbech.CustomAdapter.AdListAdapter;
import ittraining.revision.com.kinbech.InterneConnector.JSONParser;
import ittraining.revision.com.kinbech.pojo.AdDetail;
import ittraining.revision.com.kinbech.utils.GlobalState;

public class DashActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener{
    JSONParser jsonParser = new JSONParser();
    private  static  String url = Public_URL.Base_URL+Public_URL.listad_URL;
    ProgressDialog PDialog;
    int flag = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    AdDetail adDetail;
    String id,name, address, email, mobile_number, price, type, detail, first_name;
    JSONArray ad_list=null;
    ListView listView;
    ArrayList<AdDetail> adDetailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        new detailAccess().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.activity_dashboard_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        listView=(ListView)findViewById(R.id.list_views);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adDetail = (AdDetail)adapterView.getItemAtPosition(i);
                Intent intent = new Intent(DashActivity.this,AdDetailsActivity.class);
                intent.putExtra("AdDetail",adDetail);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onRefresh() {
        new detailAccess().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.post_ad) {
            Intent i = new Intent(DashActivity.this, PostAdActivity.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.view_ad) {
            Intent i = new Intent(DashActivity.this, ViewAdActivity.class);
            startActivity(i);


        } else if (id == R.id.profile) {
            Intent i = new Intent(DashActivity.this, ProfileActivity.class);
            startActivity(i);

        } else if (id == R.id.settings) {
            Intent i = new Intent(DashActivity.this, SettingActivity.class);
            startActivity(i);
        } else if (id == R.id.about_app) {
            Intent i = new Intent(DashActivity.this, TabActivity.class);
            startActivity(i);
        }else if (id == R.id.logout) {
            Intent i = new Intent(DashActivity.this,LoginActivity.class);
            startActivity(i);
            GlobalState state = GlobalState.singleton;
            state.setPrefsIsLoggedIn("false", 1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    class detailAccess extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            PDialog=new ProgressDialog(DashActivity.this);
            PDialog.setMessage("Loading your ad");
            PDialog.setIndeterminate(false);
            PDialog.setCancelable(false);
            PDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            List<NameValuePair> AdList = new ArrayList<>();

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", AdList);

            try{
                if (json==null){
                    flag=2;
                }
                else {
                    int success = json.getInt("success");
                    if (success == 1) {
                        ad_list = json.getJSONArray("ad_list");
                        for (int i=0; i<ad_list.length(); i++)
                        {
                            JSONObject jsonObject=ad_list.getJSONObject(i);
                            id = jsonObject.getString("id");
                            name = jsonObject.getString("name");
                            address = jsonObject.getString("address");
                            email = jsonObject.getString("email_id");
                            mobile_number = jsonObject.getString("mobile_number");
                            price = jsonObject.getString("price");
                            type = jsonObject.getString("type");
                            detail = jsonObject.getString("detail");
                            first_name = jsonObject.getString("first_name");
                            adDetail = new AdDetail(address,detail,email,first_name,id,mobile_number,name,price,type);
                            adDetailList.add(adDetail);
                        }
                        flag = 0;

                    }
                    else {
                        flag = 1;
                    }
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            PDialog.dismiss();
            if (flag==1)
            {
                Toast.makeText(DashActivity.this,"Sorry",Toast.LENGTH_LONG).show();
        }
            else if(flag==2){
                Toast.makeText(DashActivity.this,"Server issue or no internet connection",Toast.LENGTH_LONG).show();;
            }
            else {
                AdListAdapter adListAdapter = new AdListAdapter(DashActivity.this,R.layout.listview, adDetailList);
                listView.setAdapter(adListAdapter);
                Toast.makeText(DashActivity.this,"Success",Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
    }
    }
}
