package ittraining.revision.com.kinbech;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

import ittraining.revision.com.kinbech.pojo.UserDetail;
import ittraining.revision.com.kinbech.utils.GlobalState;

/**
 * Created by Akash on 27/01/2016.
 */
public class ProfileActivity extends AppCompatActivity {
    UserDetail userDetail;
    String first_name,address, number;
    Button mEdit_button;
    Gson gson = new Gson();
    GlobalState globalState = GlobalState.singleton;
    TextView mFirst_name, mAddress, mNumber, mLastName, mEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userDetail = gson.fromJson(globalState.getPREFS_VALID_USER_INFO(),UserDetail.class);
        mFirst_name=(TextView)findViewById(R.id.profile_name);
        mAddress = (TextView)findViewById(R.id.profile_address);
        mNumber = (TextView)findViewById(R.id.profile_number);
        mLastName = (TextView)findViewById(R.id.profile_lastName);
        mEmail = (TextView)findViewById(R.id.profile_email);
        mFirst_name.setText(userDetail.getFirstName());
        mLastName.setText(userDetail.getLastName());
        mAddress.setText(userDetail.getAddress());
        mNumber.setText(userDetail.getMobileNumber());
        mEmail.setText(userDetail.getEmail());
        mNumber.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mNumber.getText()));
                    startActivity(intent);
                } catch (Exception e) {

                }

            }
        });

        mEdit_button = (Button)findViewById(R.id.edit_button);
        mEdit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this,EditProfileActivity.class);
                i.putExtra("userDetail",userDetail);
                startActivity(i);
            }
        });

    }
}
