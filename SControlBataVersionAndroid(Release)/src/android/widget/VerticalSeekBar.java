//  : 'All code (c) Samsung Techwin Co,Ltd. all rights reserved.'

package android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class VerticalSeekBar extends SeekBar {
	
    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(),0);

        super.onDraw(c);
    }

    private OnSeekBarChangeListener onChangeListener;
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onChangeListener){
    	this.onChangeListener = onChangeListener;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
	    	case MotionEvent.ACTION_MOVE:  
	        case MotionEvent.ACTION_DOWN:
	        	int i=0;
	            onChangeListener.onStartTrackingTouch(this);
	            setPressed(true);
	            setSelected(true);	        	
	        	i=getMax() - (int) (getMax() * event.getY() / getHeight());
	            setProgress(i);
	            Log.i("Progress",getProgress()+"");
	            onSizeChanged(getWidth(), getHeight(), 0, 0);
	            if(i >= 200)
	            	 i = 200;
	            else if(i <= 0)
	            	 i = 0;
	        	onChangeListener.onProgressChanged(this, i , true);
	            break;            	
	        case MotionEvent.ACTION_UP:	    	
	        	onChangeListener.onStopTrackingTouch(this);
	        	setProgress(100);
	            onSizeChanged(getWidth(), getHeight(), 0, 0);	
	        	onChangeListener.onProgressChanged(this, 100 , true);
            break;	        	
	        case MotionEvent.ACTION_CANCEL:
	        	super.onTouchEvent(event);
	            setPressed(false);
	            setSelected(false);	        	
	            break;
        }
        return true;
    }
    
}