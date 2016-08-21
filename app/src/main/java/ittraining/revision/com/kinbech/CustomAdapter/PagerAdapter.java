package ittraining.revision.com.kinbech.CustomAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ittraining.revision.com.kinbech.Fragments.TabFragmentOne;
import ittraining.revision.com.kinbech.Fragments.TabFragmentThree;
import ittraining.revision.com.kinbech.Fragments.TabFragmentTwo;

/**
 * Created by Akash on 17/02/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                TabFragmentOne tab1 = new TabFragmentOne();
                return tab1;
            case 1:
                TabFragmentTwo tab2 = new TabFragmentTwo();
                return tab2;
            case 2:
                TabFragmentThree tab3 = new TabFragmentThree();
                return tab3;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
