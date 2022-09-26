package com.example.tianqiyubao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class FragPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private List<Fragment>mlist;

    public FragPagerAdapter(@NonNull FragmentManager fm,Context context,List<Fragment> mlist) {
        super(fm);
        this.context=context;
        this.mlist=mlist;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
    int childCount=0;//表示ViewPager包含的页数
    /**
     * viewPager改变时，必须要重新写以下两个方法*/

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (childCount>0) {
             childCount--;
             return POSITION_NONE;

        }
        return super.getItemPosition(object);
    }

    @Override
    public void notifyDataSetChanged() {
        this.childCount = getCount();
        super.notifyDataSetChanged();
    }
}
