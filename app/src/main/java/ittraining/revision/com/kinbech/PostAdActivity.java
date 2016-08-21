package ittraining.revision.com.kinbech;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import ittraining.revision.com.kinbech.InterneConnector.JSONParser;
import ittraining.revision.com.kinbech.pojo.UserDetail;
import ittraining.revision.com.kinbech.utils.GlobalState;

/**
 * Created by Akash on 27/01/2016.
 */
public class PostAdActivity extends AppCompatActivity{
    private EditText mName, mType, mPrice, mDetail, mAddress;
    private String Name,Type,Price,Detail,Address;
    private Button mButton;
    ProgressDialog pDialogue;
    JSONParser jsonParser = new JSONParser();
    private static String url = Public_URL.Base_URL+Public_URL.postad_URL;

    int flag = 0;
    Gson gson = new Gson();
    GlobalState globalState = GlobalState.singleton;
    UserDetail userDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postad);
        userDetail = gson.fromJson(globalState.getPREFS_VALID_USER_INFO(),UserDetail.class);
        mName = (EditText)findViewById(R.id.post_name);
        /*mType = (EditText)findViewById(R.id.post_type);*/
        mType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(PostAdActivity.this,"List Shown",Toast.LENGTH_LONG).show();
                return false;
            }
        });
        mPrice = (EditText)findViewById(R.id.post_price);
        mDetail = (EditText)findViewById(R.id.post_detail);
        mAddress = (EditText)findViewById(R.id.post_address);
        mButton = (Button)findViewById(R.id.ad_post_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name =  mName.getText().toString();
                Type =  mType.getText().toString();
                Price =  mPrice.getText().toString();
                Detail =  mDetail.getText().toString();
                Address = mAddress.getText().toString();
                if (Name.length()<1){
                    Toast.makeText(PostAdActivity.this,"please enter nae of your product",Toast.LENGTH_LONG).show();
                }

                else if (Type.length()<1){
                    Toast.makeText(PostAdActivity.this,"please enter your product type",Toast.LENGTH_LONG).show();
                }
                else if (Price.length()<1){
                    Toast.makeText(PostAdActivity.this,"please enter the price of your product",Toast.LENGTH_LONG).show();
                }
                else if (Detail.length()<1){
                    Toast.makeText(PostAdActivity.this,"please enter details of your product",Toast.LENGTH_LONG).show();
                }

                else
                {

                    new postAd().execute();
                }
            }
        });


    }
    class postAd extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialogue = new ProgressDialog(PostAdActivity.this);
            pDialogue.setMessage("Posting");
            pDialogue.setIndeterminate(false);
            pDialogue.setCancelable(false);
            pDialogue.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> postList = new ArrayList<>();
            postList.add(new BasicNameValuePair("name",Name));
            postList.add(new BasicNameValuePair("type",Type));
            postList.add(new BasicNameValuePair("price",Price));
            postList.add(new BasicNameValuePair("detail",Detail));
            postList.add(new BasicNameValuePair("address",Address));
            postList.add(new BasicNameValuePair("userid",userDetail.getId()));
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", postList);

            try {
                int success = json.getInt("success");
                if (success == 1) {
                    flag = 1;
                } else {
                    flag = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pDialogue.dismiss();
            if (flag == 1) {
                Toast.makeText(PostAdActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                Intent i = new Intent(PostAdActivity.this, LoginActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(PostAdActivity.this, "Sorry Registered failed... please try again", Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);

        }

    }
}


