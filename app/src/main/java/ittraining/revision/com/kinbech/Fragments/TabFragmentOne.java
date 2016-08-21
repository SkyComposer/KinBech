package ittraining.revision.com.kinbech.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import ittraining.revision.com.kinbech.R;

/**
 * Created by Akash on 17/02/2016.
 */
public class TabFragmentOne extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one,container,false);
        Button b = (Button)v.findViewById(R.id.StartButton);
        b.setOnClickListener(this);
        return v;
        /*return inflater.inflate(R.layout.fragment_one, container, false);*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.StartButton:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Wrte your message here");
                builder1.setCancelable(true);

                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                /*Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                break;*/
        }
    }


}
