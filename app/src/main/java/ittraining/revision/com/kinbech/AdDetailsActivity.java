package ittraining.revision.com.kinbech;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ittraining.revision.com.kinbech.pojo.AdDetail;

/**
 * Created by Akash on 4/02/2016.
 */
public class AdDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    TextView mAdname, mPrice, mSeller, mSellerAd, mSellerPhone, mType, mDetail;
    AdDetail adDetail;
    String Adname, Price, Seller, SellerAd, SellerPhone, Type, Detail;
    Button mCallSeller, mMessageSeller;
    private static final LatLng AddressMap = new LatLng(7.07222, 125.6131);
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_details);

        /*Declaring TextViews*/
        mAdname = (TextView) findViewById(R.id.listgenerate_AdName);
        mPrice = (TextView) findViewById(R.id.listgenerate_price);
        mSeller = (TextView) findViewById(R.id.listgenerate_seller);
        mSellerAd = (TextView) findViewById(R.id.listgenerate_sellerAd);
        mSellerPhone = (TextView) findViewById(R.id.listgenerate_sellerPhone);
        mType = (TextView) findViewById(R.id.listgenerate_type);
        mDetail = (TextView) findViewById(R.id.listgenerate_detail);

        /*Declaring Buttons*/
        mCallSeller = (Button) findViewById(R.id.call_seller);
        mMessageSeller = (Button) findViewById(R.id.message_seller);
        mCallSeller.setOnClickListener(this);
        mMessageSeller.setOnClickListener(this);

        adDetail = (AdDetail) getIntent().getSerializableExtra("AdDetail");

        mAdname.setText(adDetail.getName());
        mPrice.setText("Rs" + adDetail.getPrice());
        mSeller.setText("By" + adDetail.getFirst_name());
        mSellerAd.setText(adDetail.getAddress());
        mSellerPhone.setText(adDetail.getMobile_number());
        /*mType.setText(adDetail.getType());*/
        mDetail.setText(adDetail.getDetail());
        /* for maps */
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        Marker ownMarker = map.addMarker(new MarkerOptions().position(AddressMap).title("KTM City").snippet("IT Training Nepal"));

        //zoom in the camera to IT Training

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(AddressMap, 15));

        //animate zoom process

        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.call_seller):
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mSellerPhone.getText()));
                startActivity(intent);
                break;
            case R.id.message_seller:
                LayoutInflater factory = LayoutInflater.from(this);
                final View deleteDialogView = factory.inflate(
                        R.layout.message_alert_dialog_layout, null);


                final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
                deleteDialog.setView(deleteDialogView);
                deleteDialogView.findViewById(R.id.seller_send_btn).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //your business logic

                        deleteDialog.dismiss();
                        Toast.makeText(AdDetailsActivity.this,"Message  Sent Successfully",Toast.LENGTH_LONG).show();

                    }
                });
                deleteDialogView.findViewById(R.id.seller_cancel_btn).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                });

                deleteDialog.show();

                break;

        }

    }
    public void sendSMS(String phoneNo, String msg){
        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(),"Message Sent",Toast.LENGTH_LONG).show();
        }
        catch(Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),Toast.LENGTH_LONG).show();
            ex.printStackTrace();

        }

    }

}
