package benkoreatech.me.tour.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import benkoreatech.me.tour.BuildConfig;
import benkoreatech.me.tour.MapsActivity;
import benkoreatech.me.tour.R;

/**
 * @author msahakyan
 */

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager sInstance;

    private FragmentManager mFragmentManager;
    private MapsActivity mActivity;

    public static FragmentNavigationManager obtain(MapsActivity activity) {
        if (sInstance == null) {
            sInstance = new FragmentNavigationManager();
        }
        sInstance.configure(activity);
        return sInstance;
    }

    private void configure(MapsActivity activity) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragmentComedy(String title) {
    }


}
