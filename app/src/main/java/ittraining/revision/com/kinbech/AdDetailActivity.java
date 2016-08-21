package ittraining.revision.com.kinbech;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ittraining.revision.com.kinbech.pojo.AdDetail;

/**
 * Created by Akash on 4/02/2016.
 */
public class AdDetailActivity extends AppCompatActivity {
    TextView mAdname, mPrice, mSeller, mSellerAd, mSellerPhone, mType, mDetail;
    AdDetail adDetail;
    String Adname, Price, Seller, SellerAd, SellerPhone, Type, Detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_detail);
        mAdname = (TextView)findViewById(R.id.listgenerate_AdName);
        mPrice = (TextView)findViewById(R.id.listgenerate_price);
        mSeller = (TextView)findViewById(R.id.listgenerate_seller);
        mSellerAd = (TextView)findViewById(R.id.listgenerate_sellerAd);
        mSellerPhone = (TextView)findViewById(R.id.listgenerate_sellerPhone);
        mType = (TextView)findViewById(R.id.listgenerate_type);
        mDetail = (TextView)findViewById(R.id.listgenerate_detail);

        adDetail=(AdDetail)getIntent().getSerializableExtra("AdDetail");

        mAdname.setText(adDetail.getName());
        mPrice.setText(adDetail.getPrice());
        mSeller.setText(adDetail.getFirst_name());
        mSellerAd.setText(adDetail.getAddress());
        mSellerPhone.setText(adDetail.getMobile_number());
        mType.setText(adDetail.getType());
        mDetail.setText(adDetail.getDetail());






    }
}
