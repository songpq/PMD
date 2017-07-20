package app.android.pmdlocker.com.pmd_locker.libraries;
//PQSong add




import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


public class ScaleImageView extends ImageView {
	 public ScaleImageView(Context context) {
		    super(context);
		  }
	 	int mHeight=0;
	 	int mWidth = 0;
	 	public static int heightBanner=0;
	 	public static int widthBanner=0;
		  public ScaleImageView(Context context, AttributeSet attrs) {
		    super(context, attrs);
		  }

		  public ScaleImageView(Context context, AttributeSet attrs, int defStyle) {
		    super(context, attrs, defStyle);
		  }
		  public int getWidthAfterDraw()
		  {
			  return mWidth;
		  }
		  public int getHeightAfterDraw()
		  {
			  heightBanner = mHeight;
			  return mHeight;
		  }
		  @Override
		  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		    try {
		      Drawable drawable = getDrawable();
		      
		      if (drawable == null) {
		    	mHeight = mWidth = 0;
		        setMeasuredDimension(0, 0);
		      } else {
		        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
		        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
		        if (measuredHeight == 0 && measuredWidth == 0) { //Height and width set to wrap_content
		        	mHeight = measuredHeight;
		        	mWidth = measuredWidth;
		          setMeasuredDimension(measuredWidth, measuredHeight);
		        } else
		      if (measuredHeight==0 || measuredHeight == -1) { //Height set to wrap_content
		        	
		          int width = measuredWidth;
		          int height = width *  drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
		          mHeight = height;
		          mWidth = width;
		          setMeasuredDimension(width, height);
		        }
		      else if (measuredWidth == 0||measuredWidth == -1){ //Width set to wrap_content
		          int height = measuredHeight;		          
		          int width = height * drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight();
		          mHeight = height;
		          mWidth = width;
		          setMeasuredDimension(width, height);
		        } else { //Width and height are explicitly set (either to match_parent or to exact value)
		        	mHeight = measuredHeight;
		        	mWidth = measuredWidth;
		          setMeasuredDimension(measuredWidth, measuredHeight);
		        }
		      }
		    } catch (Exception e) {
		      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		    }
		  }

}
