package com.navgnss.gnsssystem.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.navgnss.gnsssystem.R;
import com.navgnss.gnsssystem.bean.Satellite;
import com.navgnss.gnsssystem.listener.UpdateCoordListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhuJinWei on 2016/10/9.
 */

public class StarView extends View implements UpdateCoordListener {
    private int Width;
    private int Heigth;
    private int gpsColor;
    private int gloColor;
    private int bdColor;
    private int textSize;
    private RectF mRect;
    private Paint textPaint,rectPaint,markPaint,linePaint;
    private Path mPath;
    private int radius;
    private List<Satellite> gpsList;
    private List<Satellite> gloList;
    private List<Satellite> bdList;

    public StarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray at=context.obtainStyledAttributes(attrs,R.styleable.StarView);
        try{
            gpsColor=at.getColor(R.styleable.StarView_gps_color,0xffFE0000);
            gloColor=at.getColor(R.styleable.StarView_glo_color,0xff00FF00);
            bdColor=at.getColor(R.styleable.StarView_bd_color,0xff0200FA);
            textSize= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,at.getDimension(R.styleable.StarView_mark_text_size,10f),getResources().getDisplayMetrics());
        }
        finally {
           at.recycle();
        }

        Log.d("TAG","xyz+输入的属性有："+"gpsColor="+gpsColor+";gloColor="+gloColor+";bdColor="+bdColor+";textSize="+textSize);
        gpsList=new ArrayList<>();
        gloList=new ArrayList<>();
        bdList=new ArrayList<>();

        mRect=new RectF();
        textPaint=new Paint();
        linePaint=new Paint();
        markPaint=new Paint();
        rectPaint=new Paint();

        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(1f);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);

        linePaint.setColor(Color.BLACK);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(2f);
        linePaint.setStyle(Paint.Style.STROKE);

        markPaint.setAntiAlias(true);
        markPaint.setStrokeWidth(1f);
        markPaint.setStyle(Paint.Style.FILL);

        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setAntiAlias(true);
        rectPaint.setStrokeWidth(1f);
        rectPaint.setColor(Color.LTGRAY);
        rectPaint.setPathEffect(new DashPathEffect(new float[]{5,5,5,5},2f));

        mPath=new Path();
        radius=0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Width=getWidth();
        Heigth=getHeight();

        if(Width>(Heigth-20)){
            radius=(Heigth-20)/2-30;
        }
        else{
            radius=(Width-10)/2-30;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        mPath.reset();
        //绘制虚线矩形
        mRect.set(10,20,Width-10,Heigth-20);
        mPath.addRect(mRect, Path.Direction.CCW);
        canvas.drawPath(mPath,rectPaint);
        //绘制XY轴
       canvas.drawLine(mRect.centerX()-radius,mRect.centerY(),mRect.centerX()+radius,mRect.centerY(),linePaint);
        canvas.drawLine(mRect.centerX(),mRect.centerY()-radius,mRect.centerX(),mRect.centerY()+radius,linePaint);
        //绘制3层圆
        canvas.drawCircle(mRect.centerX(),mRect.centerY(),radius,linePaint);
        canvas.drawCircle(mRect.centerX(),mRect.centerY(),2*radius/3,linePaint);
        canvas.drawCircle(mRect.centerX(),mRect.centerY(),radius/3,linePaint);
        //绘制3个mark示范
        markPaint.setColor(bdColor);
        canvas.drawCircle(mRect.right-40,mRect.top+40,20,markPaint);
        markPaint.setColor(gpsColor);
        canvas.drawCircle(mRect.left+40,mRect.top+40,20,markPaint);
        markPaint.setColor(gloColor);
        canvas.drawCircle(mRect.left+40,mRect.bottom-40,20,markPaint);
        //绘制坐标
        mPath.reset();
        mPath.moveTo(mRect.centerX()-20,mRect.centerY()-radius-10);
        mPath.lineTo(mRect.centerX(),mRect.centerY()-radius-10);
        canvas.drawTextOnPath("N",mPath,0,0,textPaint);

        mPath.reset();
        mPath.moveTo(mRect.centerX()-radius-25,mRect.centerY()-10);
        mPath.lineTo(mRect.centerX()-radius-10,mRect.centerY()-10);
        canvas.drawTextOnPath("W",mPath,0,0,textPaint);

        mPath.reset();
        mPath.moveTo(mRect.centerX()-30,mRect.centerY()+radius+25);
        mPath.lineTo(mRect.centerX()-10,mRect.centerY()+radius+25);
        canvas.drawTextOnPath("S",mPath,0,0,textPaint);

        mPath.reset();
        mPath.moveTo(mRect.centerX()+radius+10,mRect.centerY()-10);
        mPath.lineTo(mRect.centerX()+radius+25,mRect.centerY()-10);
        canvas.drawTextOnPath("E",mPath,0,0,textPaint);

        mPath.reset();
        mPath.moveTo(mRect.right-120,mRect.top+50);
        mPath.lineTo(mRect.right-80,mRect.top+50);
        canvas.drawTextOnPath("BD",mPath,0,0,textPaint);

        mPath.reset();
        mPath.moveTo(mRect.left+80,mRect.top+50);
        mPath.lineTo(mRect.left+120,mRect.top+50);
        canvas.drawTextOnPath("GPS",mPath,0,0,textPaint);

        mPath.reset();
        mPath.moveTo(mRect.left+80,mRect.bottom-30);
        mPath.lineTo(mRect.left+120,mRect.bottom-30);
        canvas.drawTextOnPath("GLO",mPath,0,0,textPaint);

        for(int i=0;i<4;i++){
            mPath.reset();
            mPath.moveTo(mRect.centerX()+5,mRect.centerY()-i*radius/3-10);
            mPath.lineTo(mRect.centerX()+30,mRect.centerY()-i*radius/3-10);
            canvas.drawTextOnPath(String.valueOf(90-i*30),mPath,0,0,textPaint);
        }

        //根据数据在地图上绘制圆形标记
        if(!gpsList.isEmpty())
        drawSatellitePos(canvas,gpsList,gpsColor);
        if(!gpsList.isEmpty())
        drawSatellitePos(canvas,gloList,gloColor);
        if(!bdList.isEmpty())
        drawSatellitePos(canvas,bdList,bdColor);
        textPaint.setColor(Color.BLACK);
    }

    private void drawSatellitePos(Canvas canvas,List<Satellite> list,int color) {
        markPaint.setColor(color);
        textPaint.setColor(Color.WHITE);
        for(Satellite ss:list){
            int pit=ss.getPitchAngle();//俯仰角
            int id=ss.getID();
            int azi=ss.getAzimuth();//方位角

            float pointY=(float)(mRect.centerY()-radius*Math.cos(Math.PI*pit/180)*Math.cos(Math.PI*azi/180));
            float pointX=(float)(mRect.centerX()+radius*Math.cos(Math.PI*pit/180)*Math.sin(Math.PI*azi/180));
            Log.d("TAG", "xyz"+"pointY="+pointY+"pointX="+pointX+"mRectF.centerY="+mRect.centerY()+"mRectF.centerX="+mRect.centerX()+"radius="+radius);
            canvas.drawCircle(pointX,pointY,20,markPaint);

            mPath.reset();
            mPath.moveTo(pointX-20,pointY+10);
            mPath.lineTo(pointX+20,pointY+10);
            canvas.drawTextOnPath(String.valueOf(id),mPath,0,0,textPaint);
        }
        markPaint.setColor(gloColor);
    }

    @Override
    public void updateCoordListener(List<Satellite>[] list) {
        Log.d("TAG","xyz+传入的数据是:"+list[0]+list[1]+list[2]+list[3]);
        gpsList.clear();
        gloList.clear();
        bdList.clear();
        gpsList.addAll(list[0]);
        gloList.addAll(list[1]);
        bdList.addAll(list[2]);
        bdList.addAll(list[3]);
        if(Looper.myLooper()==Looper.getMainLooper()){
            invalidate();
        }
        else{
            postInvalidate();
        }

    }




}
