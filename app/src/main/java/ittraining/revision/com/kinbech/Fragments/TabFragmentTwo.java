package ittraining.revision.com.kinbech.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ittraining.revision.com.kinbech.CustomAdapter.AdListAdapter;
import ittraining.revision.com.kinbech.DashActivity;
import ittraining.revision.com.kinbech.InterneConnector.JSONParser;
import ittraining.revision.com.kinbech.Public_URL;
import ittraining.revision.com.kinbech.R;
import ittraining.revision.com.kinbech.pojo.AdDetail;

/**
 * Created by Akash on 17/02/2016.
 */
public class TabFragmentTwo extends Fragment{
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_two,container,false);
        Button b = (Button)v.findViewById(R.id.StartButton);
        new detailAccess().execute();

        return v;
        /*return inflater.inflate(R.layout.fragment_two, container, false);*/
    }
    class detailAccess extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            PDialog=new ProgressDialog(getActivity());
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
                Toast.makeText(getActivity(), "Sorry", Toast.LENGTH_LONG).show();
            }
            else if(flag==2){
                Toast.makeText(getActivity(),"Server issue or no internet connection",Toast.LENGTH_LONG).show();;
            }
            else {
                AdListAdapter adListAdapter = new AdListAdapter(getActivity(),R.layout.listview, adDetailList);
                listView.setAdapter(adListAdapter);
                Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }
}
