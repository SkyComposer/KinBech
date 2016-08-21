package ittraining.revision.com.kinbech;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ittraining.revision.com.kinbech.CustomAdapter.AdListAdapter;
import ittraining.revision.com.kinbech.InterneConnector.JSONParser;
import ittraining.revision.com.kinbech.pojo.AdDetail;
import ittraining.revision.com.kinbech.pojo.UserDetail;
import ittraining.revision.com.kinbech.utils.GlobalState;

/**
 * Created by Akash on 27/01/2016.
 */
public class ViewAdActivity extends AppCompatActivity{
    JSONParser jsonParser = new JSONParser();
    private static String url = Public_URL.Base_URL+Public_URL.myadlsit_URL;
    int flag = 0;

    GlobalState state = GlobalState.singleton;
    ProgressDialog pDialog;
    AdDetail adDetail;
    UserDetail userDetail;

    String id,name, address, email, mobile_number, price, type, detail, first_name;
    JSONArray ad_list=null;
    ListView listView;
    ArrayList<AdDetail> adDetailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewad);
        listView=(ListView)findViewById(R.id.list_views_self);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adDetail = (AdDetail) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(ViewAdActivity.this, AdDetailsActivity.class);
                intent.putExtra("AdDetail", adDetail);
                startActivity(intent);
            }
            });
        Gson gson = new Gson();
        userDetail =  gson.fromJson(state.getPREFS_VALID_USER_INFO(),UserDetail.class);
        new myAdAccess().execute();

        }
    class myAdAccess extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ViewAdActivity.this);
            pDialog.setMessage("Loading the ad you have posted");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> AdList = new ArrayList<>();
            AdList.add(new BasicNameValuePair("id",userDetail.getId()));
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", AdList);
            try {
                if(json == null){
                    flag = 2;
                }
                else {
                    int success = json.getInt("success");
                    if (success==1){
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
            pDialog.dismiss();
            if (flag==1)
            {
                Toast.makeText(ViewAdActivity.this, "Soory", Toast.LENGTH_LONG).show();
            }
            else if(flag==2){
                Toast.makeText(ViewAdActivity.this,"Server issue or no internet connection",Toast.LENGTH_LONG).show();;
            }
            else {
                AdListAdapter adListAdapter = new AdListAdapter(ViewAdActivity.this,R.layout.listview, adDetailList);
                listView.setAdapter(adListAdapter);
                Toast.makeText(ViewAdActivity.this,"Success",Toast.LENGTH_LONG).show();
            }
        }
    }
    }
