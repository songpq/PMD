package app.android.pmdlocker.com.pmd_locker.apdapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;


/**
 * Created by Ca. Phan Thanh on 4/17/2017.
 */
public class ViewHolderLocker extends RecyclerView.ViewHolder{
    public View rlRoot;
    public RelativeLayout rlItem;
    public ScaleImageView sivIcon;
    public TextView textVName;


    public ViewHolderLocker(View view) {
        super(view);
        this.rlRoot = view;
        this.rlItem = (RelativeLayout) view.findViewById(R.id.rlLocker);
        this.sivIcon = (ScaleImageView) view.findViewById(R.id.sivIcon);
        this.textVName = (TextView)view.findViewById(R.id.textVName);

    }
}