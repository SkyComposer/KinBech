package ittraining.revision.com.kinbech;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ittraining.revision.com.kinbech.InterneConnector.JSONParser;
import ittraining.revision.com.kinbech.pojo.UserDetail;
import ittraining.revision.com.kinbech.utils.GlobalState;

/**
 * Created by Akash on 10/01/2016.
 */
public class LoginActivity extends Activity {
    Button mLogReg;
    Button mLogin;
    ProgressDialog pDialog;
    int flag=0;
    String user,pwd;
    JSONParser jsonParser = new JSONParser();
    private  static  String url = Public_URL.Base_URL+Public_URL.login_URL;
    EditText mNumber, mPassword;
    String userid,email,first_name,last_name, phone, address;

    /*private static final String TAG_SUCCESS = "success";
    *//*public static final String PREFS_NAME = "LoginPrefs";*//*
    private static final String TAG_USERARRAY = "user_detail";
    private static final String TAG_ID = "id";*/
    JSONArray user_detail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLogReg = (Button)findViewById(R.id.log_to_reg_btn);
        mNumber = (EditText)findViewById(R.id.log_username);
        mPassword = (EditText)findViewById(R.id.log_password);
        mLogin = (Button)findViewById(R.id.log_button);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = mNumber.getText().toString();
                pwd = mPassword.getText().toString();
                if (isOnline()) {
                    if (mNumber.getText().length()<1 || mPassword.getText().length()<1){
                        Toast.makeText(LoginActivity.this,"Fill the fields please",Toast.LENGTH_LONG).show();
                    }
                    else if(!isValidEmail(user)){

                    }
                    new loginAccess().execute();

                }
                else {
                    Toast.makeText(LoginActivity.this,"No internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });



        mLogReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
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
    public final static boolean isValidEmail(CharSequence email){
        if (email==null){
            return false;
        }
        else{
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
    class loginAccess extends AsyncTask<String, String, String> {

      protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mobile_number", user));
            params.add(new BasicNameValuePair("password", pwd));
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            try {
                if(json==null){
                    flag=2;
                }
                else {
                    int success = json.getInt("success");
                    if (success == 1) {
                        user_detail = json.getJSONArray("user_detail");
                        for (int i = 0; i < user_detail.length(); i++) {
                            JSONObject c = user_detail.getJSONObject(i);
                            userid = c.getString("id");
                            email = c.getString("email_id");
                            first_name = c.getString("first_name");
                            last_name = c.getString("last_name");
                            phone = c.getString("mobile_number");
                            address = c.getString("address");
                        }
                        flag = 0;

                    } else {
                        flag = 1;

                    }
                }
            }
                catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (flag == 1){
                Toast.makeText(LoginActivity.this, "Please Enter Correct Credentials", Toast.LENGTH_LONG).show();

            }
            else if(flag==2){
                    Toast.makeText(LoginActivity.this,"Server issue or no internet connection",Toast.LENGTH_LONG).show();;
            }
            else{
                Toast.makeText(LoginActivity.this," Welcome "+first_name+" Your email is : "+email+" your phone "+phone+" last name "+last_name,Toast.LENGTH_LONG).show();



                GlobalState state = GlobalState.singleton;
                state.setPrefsIsLoggedIn("true", 1);
                UserDetail userDetail = new UserDetail(address, email, first_name, userid, last_name, phone);
                Gson gson = new Gson();

                state.setPrefsValidUserInfo(gson.toJson(userDetail),1);
                Intent i = new Intent(LoginActivity.this,DashActivity.class);
                startActivity(i);
                finish();

            }
        }

    }

}
