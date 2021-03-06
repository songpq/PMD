package app.android.pmdlocker.com.pmd_locker.apdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.models.objects.HCreditCard;

/**
 * Created by Ca. Phan Thanh on 5/18/2016.
 */

public class CreditCardAdapterRecycleView extends RecyclerView.Adapter<ViewHolderCredit>{
    private String TAG = CreditCardAdapterRecycleView.class.getName();

    @Override
    public ViewHolderCredit onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_credit, parent, false);

        return new ViewHolderCredit(itemView);
    }
    @Override
    public int getItemCount() {
        if(lstLocker!=null)
            return lstLocker.size();
        else
            return 0;
    }
    @Override
    public void onBindViewHolder(ViewHolderCredit vh, int position)
    //private void showDataOnView(final ViewHolder vh,final HApp item)
    {
        final HCreditCard item = lstLocker.get(position);
        Picasso.with(context).load(item.getIcon()).placeholder(R.mipmap.ic_launcher).into(vh.sivIcon);
        

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
    private List<HCreditCard> lstLocker = new ArrayList<>();
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
    public CreditCardAdapterRecycleView(Context context, List<HCreditCard> objects, int widthItemApp, int heightItemApp, String cityName, String location) {
        lstLocker = objects;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.widthItemApp = widthItemApp;
        this.heightItemApp = heightItemApp;
    }
    public CreditCardAdapterRecycleView(Context context, List<HCreditCard> objects, int widthItemApp, int heightItemApp, int TYPE_VIEW) {
        lstLocker = objects;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.widthItemApp = widthItemApp;
        this.heightItemApp = heightItemApp;

    }

}

