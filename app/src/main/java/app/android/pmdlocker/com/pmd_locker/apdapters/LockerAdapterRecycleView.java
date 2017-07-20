package app.android.pmdlocker.com.pmd_locker.apdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.fragments.FragmentCustomProfileLocation;
import app.android.pmdlocker.com.pmd_locker.fragments.FragmentCustomProfileLocker;
import app.android.pmdlocker.com.pmd_locker.fragments.FragmentCustomProfileTime;
import app.android.pmdlocker.com.pmd_locker.fragments.ManagerChangeFragment;
import app.android.pmdlocker.com.pmd_locker.models.objects.HLocker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ca. Phan Thanh on 5/18/2016.
 */

public class LockerAdapterRecycleView extends RecyclerView.Adapter<ViewHolderLocker>{
    private String TAG = LockerAdapterRecycleView.class.getName();

    @Override
    public ViewHolderLocker onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transport, parent, false);

        return new ViewHolderLocker(itemView);
    }
    @Override
    public int getItemCount() {
        if(lstLocker!=null)
            return lstLocker.size();
        else
            return 0;
    }
    @Override
    public void onBindViewHolder(ViewHolderLocker vh, int position)
    //private void showDataOnView(final ViewHolder vh,final HApp item)
    {
        final HLocker item = lstLocker.get(position);
        Picasso.with(context).load(item.getIcon()).placeholder(R.mipmap.ic_launcher).into(vh.sivIcon);
        vh.textVName.setText(Html.fromHtml(item.getName()));

        vh.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastestSelected = item.getName();
            }
        });
        vh.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastestSelected = item.getName();
            }
        });

    }
    public String lastestSelected=null;

//    public void removeListApp(HApp item)
//    {
//        int idx = this.lstLocker.indexOf(item);
//        if(this.lstLocker.remove(item))
//            notifyItemRemoved(idx);
//        if(textVEmpty!=null) {
//            if (lstLocker.size() == 0) {
//                String txt = Utility.getText(R.string.text_no_result, context);
//                txt = Utility.getText(R.string.text_no_item_my_app, context);
//            textVEmpty.setText(Html.fromHtml(txt));
//                textVEmpty.setVisibility(View.VISIBLE);
//            } else
//                textVEmpty.setVisibility(View.GONE);
//        }
//    }




    private LayoutInflater mInflater;
    Context context;
    int widthItemApp;
    int heightItemApp;
    private List<HLocker> lstLocker = new ArrayList<>();
    TextView textVEmpty;
    // Constructors
//    public void addListApp(List<HApp> lstLocker)
//    {
//
//        int start = this.lstLocker.size()-1;
//        if(start<0)
//            start = 0;
//        this.lstLocker.addAll(lstLocker);
//        notifyItemRangeInserted(start,lstLocker.size());
//    }
//    @Override
//    public long getItemId(int position) {
//        HApp hApp = lstLocker.get(position);
//        return (hApp.getId()+hApp.getName()+position).hashCode();
//    }

    String cityName,location;
    public LockerAdapterRecycleView(Context context, List<HLocker> objects, int widthItemApp, int heightItemApp,String cityName,String location) {
        lstLocker = objects;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.widthItemApp = widthItemApp;
        this.heightItemApp = heightItemApp;
    }
    public LockerAdapterRecycleView(Context context, List<HLocker> objects, int widthItemApp,int heightItemApp,int TYPE_VIEW) {
        lstLocker = objects;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.widthItemApp = widthItemApp;
        this.heightItemApp = heightItemApp;

    }

}

