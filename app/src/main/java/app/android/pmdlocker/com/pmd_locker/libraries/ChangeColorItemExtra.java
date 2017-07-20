package app.android.pmdlocker.com.pmd_locker.libraries;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xdu.xhin.library.view.ChangeColorItem;

import app.android.pmdlocker.com.pmd_locker.R;


/**
 * Created by Ca. Phan Thanh on 9/7/2016.
 */
public class ChangeColorItemExtra extends ChangeColorItem {

    public String txtNotification="";
    Context context;
    int resBGNormal=0;
    int resBGSelected=0;
    public void setResBGNormal(int id)
    {
        resBGNormal = id;
    }
    public void setResBGSelected(int id)
    {
        resBGSelected = id;
    }
    public ChangeColorItemExtra(Context context) {
        super(context, null);
        this.context = context;
    }

    public ChangeColorItemExtra(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.context = context;
    }
    public ChangeColorItemExtra(Context context, AttributeSet attrs,
                           int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
//        canvas.save();
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.text_view_notification, null);
////        v1.layout(0, 0, getWindowManager().getDefaultDisplay().getWidth(),
////                getWindowManager().getDefaultDisplay().getHeight());
//
//        v.setDrawingCacheEnabled(true);
//        v.buildDrawingCache();
//        Bitmap bmp= v.getDrawingCache();
//        v.draw(canvas);
//
//        Bitmap bitMap = Bitmap.createBitmap(100,
//                100,
//                Bitmap.Config.ARGB_8888);
//        Canvas _canvas = new Canvas(bitMap);
//        v.layout(0,0,30,30);
//        v.draw(_canvas);
//        v.draw(canvas);
//        Paint paint = new Paint();
//        paint.setColor(0);
//        canvas.drawBitmap(bitMap,10,10,paint);
//        canvas.restore();
        if(resBGNormal>0) {
            Drawable d = getResources().getDrawable(resBGNormal);
            d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            d.draw(canvas);
        }
        if(resBGSelected>0) {
            Drawable d = getResources().getDrawable(resBGSelected);
            d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            d.draw(canvas);
        }
        super.onDraw(canvas);

        if(!TextUtils.isEmpty(txtNotification)) {
            int radius;
            Paint _paint = new Paint();
            _paint.setStyle(Paint.Style.FILL);
            _paint.setColor(Color.RED);
            Rect rect = new Rect();

            _paint.setTextAlign(Paint.Align.CENTER);
            _paint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_notification), getResources().getDisplayMetrics()));
            _paint.getTextBounds(txtNotification, 0, txtNotification.length(), rect);
            radius = Math.max(rect.width(), rect.height());
            int x = radius + 22;
            int y = radius + 6;
            canvas.drawCircle(x, y, radius, _paint);
            _paint.setColor(Color.WHITE);
            _paint.setFakeBoldText(true);
            _paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(txtNotification, x, y+(rect.height()/2) , _paint);
        }
    }
}
