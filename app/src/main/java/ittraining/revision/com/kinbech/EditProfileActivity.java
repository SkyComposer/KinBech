package ittraining.revision.com.kinbech;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * Created by Akash on 5/02/2016.
 */
public class EditProfileActivity extends AppCompatActivity {
    EditText mFirst_Name, mLast_Name, mAddress, mEmail, mNumber;
    Button mEdit_Button;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    private  static  String url = Public_URL.Base_URL+Public_URL.editprofile_URL;
    String first_name, last_name, address, email, mobile, userid, phone;
    UserDetail userDetail;
    ProgressDialog pDialog;
    GlobalState globalState = GlobalState.singleton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mFirst_Name = (EditText)findViewById(R.id.edit_profile_name);
        mAddress = (EditText)findViewById(R.id.edit_profile_address);
        mEmail = (EditText)findViewById(R.id.edit_profile_email);
        mNumber = (EditText)findViewById(R.id.edit_profile_number);
        mLast_Name = (EditText)findViewById(R.id.edit_last_name);

        userDetail = (UserDetail)getIntent().getSerializableExtra("userDetail");
        mFirst_Name.setText(userDetail.getFirstName());
        mAddress.setText(userDetail.getAddress());
        mLast_Name.setText(userDetail.getLastName());
        mNumber.setText(userDetail.getMobileNumber());
        mEmail.setText(userDetail.getEmail());


        mEdit_Button=(Button)findViewById(R.id.edit_profile_button);
        mEdit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name = mFirst_Name.getText().toString();
                last_name = mLast_Name.getText().toString();
                address = mAddress.getText().toString();
                mobile = mNumber.getText().toString();
                email = mEmail.getText().toString();
                /*if (isOnline()) {

                    }*/
                new EditAccess().execute();

            }
        });

    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    class EditAccess extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(EditProfileActivity.this);
            pDialog.setMessage("Updating, Please wait.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> EditParams = new ArrayList<NameValuePair>();
            EditParams.add(new BasicNameValuePair("id", userDetail.getId()));
            EditParams.add(new BasicNameValuePair("first_name", first_name));
            EditParams.add(new BasicNameValuePair("last_name",last_name ));
            EditParams.add(new BasicNameValuePair("email_id", email));
            EditParams.add(new BasicNameValuePair("mobile_number", mobile));
            EditParams.add(new BasicNameValuePair("address", address));

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", EditParams);

            try {
                if (json==null){
                    flag=2;
                }else {
                    int success = json.getInt("success");

                    if (success == 1) {
                        flag = 1;
                    } else {
                        flag = 0;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;


        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            if (flag == 1) {
                Toast.makeText(EditProfileActivity.this, "Update successfully", Toast.LENGTH_LONG).show();

                GlobalState state = GlobalState.singleton;
                /*state.setPrefsIsLoggedIn("true", 1);*/
                userDetail = new UserDetail(address,email,first_name,userDetail.getId(),last_name,mobile);
                Gson gson = new Gson();
                state.setPrefsValidUserInfo(gson.toJson(userDetail), 1);
                Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(i);

            } else if(flag == 2)
                {
                    Toast.makeText(EditProfileActivity.this,"Server issue or no internet connection",Toast.LENGTH_LONG).show();;
            } else {
                Toast.makeText(EditProfileActivity.this, "something is wrong, please try again", Toast.LENGTH_LONG).show();

                GlobalState state = GlobalState.singleton;
                state.setPrefsIsLoggedIn("true", 1);
                UserDetail userDetail = new UserDetail(address, email, first_name, userid, last_name, phone);
                Gson gson = new Gson();
                state.setPrefsValidUserInfo(gson.toJson(userDetail), 1);
                Intent i = new Intent(EditProfileActivity.this,DashActivity.class);
                startActivity(i);
                /*Intent i = new Intent(EditProfileActivity.this, LoginActivity.class);
                startActivity(i);*/
        }
            super.onPostExecute(s);
        }
    }
}
