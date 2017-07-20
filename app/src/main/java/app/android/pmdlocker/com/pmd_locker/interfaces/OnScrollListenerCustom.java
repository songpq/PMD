package app.android.pmdlocker.com.pmd_locker.interfaces;


import android.util.Log;
import android.widget.AbsListView;

public abstract class OnScrollListenerCustom
        implements AbsListView.OnScrollListener {
    String TAG = OnScrollListenerCustom.class.getName();
    boolean isLoading=false;
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int threshold = 1;
        int count = view.getCount();

        if (scrollState == SCROLL_STATE_IDLE) {
            if (view.getLastVisiblePosition() >= count - threshold) {
                Log.i(TAG, "loading more data");
                // Execute LoadMoreDataTask AsyncTask

                    onLoadMore(isLoading,count);

            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
    }
    public void onLoadMore(boolean isLoading,int count) {

    }
    public void completeLoading()
    {
        isLoading=false;
    }
}