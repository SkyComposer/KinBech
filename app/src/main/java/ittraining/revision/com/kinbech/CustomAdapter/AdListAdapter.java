package ittraining.revision.com.kinbech.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import ittraining.revision.com.kinbech.R;
import ittraining.revision.com.kinbech.pojo.AdDetail;

/**
 * Created by Akash on 3/02/2016.
 */
public class AdListAdapter extends ArrayAdapter<AdDetail> {
    public AdListAdapter(Context context, int resource, List<AdDetail> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v==null){
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            v = layoutInflater.inflate(R.layout.listview,null);
        }
        AdDetail adDetail = getItem(position);
        if (adDetail !=null){
            TextView mName = (TextView)v.findViewById(R.id.list_name);
            TextView mAddress = (TextView)v.findViewById(R.id.list_details);
            TextView mPrice = (TextView)v.findViewById(R.id.list_price);

            if (mName != null){
                mName.setText(adDetail.getName());
            }
            if (mAddress != null){
                mAddress.setText(adDetail.getDetail());
            }
            if (mPrice != null){
                mPrice.setText(adDetail.getPrice());
            }
        }
        return v;

    }

}
