package ittraining.revision.com.kinbech;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ittraining.revision.com.kinbech.InterneConnector.JSONParser;

public class MainActivity extends Activity {
    Button mRegister;
    EditText mName, mEmail, mPassword, mPhone, mCPassword, mAddress, mLastName, emailValidate;
    ProgressDialog pDialog;
    String username, password, email, phone, address, last_name;
    JSONParser jsonParser = new JSONParser();
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = (EditText) findViewById(R.id.reg_username);
        mEmail = (EditText) findViewById(R.id.reg_email);
        mPassword = (EditText) findViewById(R.id.reg_password);
        mPhone = (EditText) findViewById(R.id.reg_number);
        mCPassword = (EditText) findViewById(R.id.reg_confirm);
        mAddress = (EditText) findViewById(R.id.reg_address);
        mRegister = (Button) findViewById(R.id.register);
        mLastName = (EditText) findViewById(R.id.reg_lastName);
        emailValidate = (EditText) findViewById(R.id.reg_email);


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mName.getText().toString();
                password = mPassword.getText().toString();
                /*email=mEmail.getText().toString();*/
                phone = mPhone.getText().toString();
                address = mAddress.getText().toString();
                last_name = mLastName.getText().toString();
                email = emailValidate.getText().toString().trim();
                /*String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";*/


                if (mName.getText().length() < 1) {
                    Toast.makeText(MainActivity.this, "Please input name", Toast.LENGTH_SHORT).show();
                }/* else if (email != null) {
                    if (email.matches(emailPattern)) {
                        Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    }
                }*/

                /*else if (mEmail.getText().length()<1)
                {
                    Toast.makeText(MainActivity.this,"Please input email",Toast.LENGTH_SHORT).show();
                }*/
                else if (mPassword.getText().length() < 1) {
                    Toast.makeText(MainActivity.this, "Please input password", Toast.LENGTH_SHORT).show();
                } else if (mCPassword.getText().length() < 1) {
                    Toast.makeText(MainActivity.this, "Please input confirm password", Toast.LENGTH_SHORT).show();
                } else if (mPhone.getText().length() < 1) {
                    Toast.makeText(MainActivity.this, "Please input Phone", Toast.LENGTH_SHORT).show();
                } else if (mAddress.getText().length() < 1) {
                    Toast.makeText(MainActivity.this, "Please input Address", Toast.LENGTH_SHORT).show();
                } else if (!mPassword.getText().toString().equals(mCPassword.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Password do not match. Please try again", Toast.LENGTH_SHORT).show();
                } else {
                    new RegisterAccess().execute();

                }
            }
        });
    }

    class RegisterAccess extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Registering... Please wait");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> RegisterParams = new ArrayList<NameValuePair>();
            /*RegisterParams.add(new BasicNameValuePair("phone", phone));
            RegisterParams.add(new BasicNameValuePair("password", password));
            RegisterParams.add(new BasicNameValuePair("email", email));
            RegisterParams.add(new BasicNameValuePair("username", username));
            RegisterParams.add(new BasicNameValuePair("address", address));*/
            RegisterParams.add(new BasicNameValuePair("mobile_number", phone));
            RegisterParams.add(new BasicNameValuePair("password", password));
            RegisterParams.add(new BasicNameValuePair("name", username));
            RegisterParams.add(new BasicNameValuePair("email_id", email));
            RegisterParams.add(new BasicNameValuePair("last_name", last_name));
            RegisterParams.add(new BasicNameValuePair("address", address));


            JSONObject json = jsonParser.makeHttpRequest(Public_URL.Base_URL + Public_URL.register_URL, "POST", RegisterParams);

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
            pDialog.dismiss();
            if (flag == 1) {
                Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(MainActivity.this, "Sorry Registered failed... please try again", Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }

    }

    public final static boolean isValidEmail(CharSequence email) {
        if (email == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}