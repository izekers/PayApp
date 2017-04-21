package com.zekers.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 三角形textView
 * Created by Administrator on 2016/8/31.
 */
public class TriangleTextView extends TextView{
    public TriangleTextView(Context context) {
        super(context);
    }

    public TriangleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TriangleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TriangleTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public static final int PAINT_FILL=0;
    public static final int PAINT_STROKE=1;
    public static final int DIRECTION_RIGHT_TOP=0;
    public static final int DIRECTION_RIGHT_BOTTOM=1;
    public static final int DIRECTION_LEFT_TOP=2;
    public static final int DIRECTION_LEFT_BOTTOM=3;

    private int paint_type=PAINT_FILL;
    private int direction=DIRECTION_RIGHT_BOTTOM;
    private int bgColor=Color.WHITE;
    public void setTriangleColorRes(int color){
        bgColor=getResources().getColor(color);
    }
    public void setTriangleColor(String colorStr){
        bgColor=Color.parseColor(colorStr);
    }
    public void setPaint_type(int paint_type) {
        this.paint_type = paint_type;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*画一个实心三角形*/
        Path path=new Path();
        Paint paint=new Paint();
		/*去锯齿*/
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
		/*设置paint的颜色*/
        paint.setColor(bgColor);
        /*设置paint　的style为　FILL：实心*/
        if (paint_type==PAINT_FILL)
            paint.setStyle(Paint.Style.FILL);
        else if (paint_type==PAINT_STROKE)
            paint.setStyle(Paint.Style.STROKE);
        if (direction==DIRECTION_RIGHT_BOTTOM){
            this.setGravity(Gravity.BOTTOM|Gravity.RIGHT);
            path.moveTo(0,getHeight());
            path.lineTo(getWidth(),getHeight());
            path.lineTo(getWidth(),0);
            path.close();
        }else if (direction==DIRECTION_RIGHT_TOP){
            this.setGravity(Gravity.RIGHT);
            path.moveTo(0,0);
            path.lineTo(getWidth(),getHeight());
            path.lineTo(getWidth(),0);
            path.close();
        }else if (direction==DIRECTION_LEFT_TOP){
            path.moveTo(0,0);
            path.lineTo(getWidth(),0);
            path.lineTo(0,getHeight());
            path.close();
        }else if (direction==DIRECTION_LEFT_BOTTOM){
            this.setGravity(Gravity.BOTTOM);
            path.moveTo(0,0);
            path.lineTo(getWidth(),getHeight());
            path.lineTo(0,getHeight());
            path.close();
        }
        canvas.drawPath(path, paint);
        this.setPadding(5,5,5,5);
        super.onDraw(canvas);
    }
}
