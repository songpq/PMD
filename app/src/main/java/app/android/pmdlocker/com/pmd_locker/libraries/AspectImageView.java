package app.android.pmdlocker.com.pmd_locker.libraries;



import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by PC on 3/1/2017.
 */

public class AspectImageView extends android.support.v7.widget.AppCompatImageView {
    public AspectImageView(Context context) {
        super(context);
    }

    public AspectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Figure out the aspect ratio of the image content
        int desiredSize;
        float aspect;
        Drawable d = getDrawable();
        if (d == null) {
            desiredSize = 0;
            aspect = 1f;
        } else {
            desiredSize = d.getIntrinsicWidth();
            aspect = (float) d.getIntrinsicWidth() / (float) d.getIntrinsicHeight();
        }
        //Get the width based on the measure specs
        int widthSize = View.resolveSize(desiredSize, widthMeasureSpec);

        //Calculate height based on aspect
        int heightSize = (int) (widthSize / aspect);

        //Make sure the height we want is not too large
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY) {
            //If our measurement exceeds the max height, shrink back
            if (heightSize > specSize) {
                heightSize = specSize;
                widthSize = (int) (heightSize * aspect);
            }
        }

        //MUST do this to store the measurements
        setMeasuredDimension(widthSize, heightSize);
    }
}