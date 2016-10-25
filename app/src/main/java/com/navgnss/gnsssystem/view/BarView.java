package com.navgnss.gnsssystem.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.navgnss.gnsssystem.R;

import java.util.ArrayList;
import java.util.List;

import com.navgnss.gnsssystem.bean.Satellite;
import com.navgnss.gnsssystem.listener.UpdateCoordWithCollectionListerner;

/**
 * Created by ZhuJinWei on 2016/9/29.
 *
 * 属性有
 *  1.XY轴坐标 2.坐标字体大小 3.柱形颜色
 *统一接口更新，数据只需要List<Satellite>
 *
 */

public class BarView extends View implements UpdateCoordWithCollectionListerner{
    private String xTitle;
    private String yTitle;
    private int textSize= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics());
    private int barColor;
    private int barWidth;//柱形的宽度

    private Paint axisPaint;
    private Paint titlePaint;
    private Paint linePaint;
    private Paint barPaint;

    private Path mPath;

    private int mWidth=0;
    private int mHeigth=0;
    private List<Satellite> satellites;
    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.BarView,0,0);
        try{
            xTitle=ta.getString(R.styleable.BarView_x_title);
            yTitle=ta.getString(R.styleable.BarView_y_title);
            textSize= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,ta.getDimension(R.styleable.BarView_text_size,10f),getResources().getDisplayMetrics());
            barColor=ta.getColor(R.styleable.BarView_bar_color,0xffE5E5E5);
            barWidth=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,ta.getDimension(R.styleable.BarView_bar_width,5f),getResources().getDisplayMetrics());
        }
        finally {
            ta.recycle();
        }


        axisPaint=new Paint();
        titlePaint=new Paint();
        linePaint=new Paint();
        barPaint=new Paint();
        mPath=new Path();

        axisPaint.setColor(Color.WHITE);
        axisPaint.setAntiAlias(true);
        axisPaint.setStyle(Paint.Style.FILL);
        axisPaint.setStrokeWidth(1f);

        titlePaint.setColor(Color.WHITE);
        titlePaint.setTextSize(textSize);
        titlePaint.setAntiAlias(true);
        titlePaint.setStrokeWidth(2f);
        titlePaint.setTextAlign(Paint.Align.CENTER);

        linePaint.setColor(Color.WHITE);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setPathEffect(new DashPathEffect(new float[]{5,5,5,5},1));
        linePaint.setStrokeWidth(1f);


        barPaint.setColor(barColor);
        barPaint.setStyle(Paint.Style.FILL);
        barPaint.setAntiAlias(true);
        barPaint.setStrokeWidth(3f);

        satellites=new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mWidth=getWidth();
        mHeigth=getHeight();
        //绘制X，Y轴
        drawXYaxis(canvas);
        //绘制虚线，Y轴刻度
        drawLineAndYserise(canvas);
        //绘制柱形和X轴坐标
        drawBarAndXScale(canvas,satellites);
        //绘制X轴坐标线
        drawXserise(canvas);
    }

    private void drawLineAndYserise(Canvas canvas) {
        for(int i=0;i<37;i++){
            if(i%4==0||i==0){
                mPath.reset();
                mPath.moveTo(60,i*(mHeigth/36-10/3)+20);
                mPath.lineTo(90,i*(mHeigth/36-10/3)+20);
                canvas.drawTextOnPath(String.valueOf(60-i),mPath,0,0,titlePaint);

                mPath.reset();
                mPath.moveTo(100,i*(mHeigth/36-10/3)+20);
                mPath.lineTo(mWidth-10,i*(mHeigth/36-10/3)+20);
                canvas.drawPath(mPath,linePaint);


                canvas.drawLine(100,i*(mHeigth/36-10/3)+20,115,i*(mHeigth/36-10/3)+20,axisPaint);
            }
            else{
                canvas.drawLine(100,i*(mHeigth/36-10/3)+20,110,i*(mHeigth/36-10/3)+20,axisPaint);
            }
        }
    }

    private void drawXYaxis(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(40,mHeigth/2+60);
        mPath.lineTo(40,mHeigth/2-100);
        canvas.drawTextOnPath(yTitle,mPath,0,0,titlePaint);

        mPath.reset();
        mPath.moveTo(mWidth/2-65,mHeigth-25);
        mPath.lineTo(mWidth/2+115,mHeigth-25);
        canvas.drawTextOnPath(xTitle,mPath,0,0,titlePaint);

        canvas.drawLine(100,20,100,mHeigth-100,axisPaint);
        canvas.drawLine(100,mHeigth-100,mWidth-10,mHeigth-100,axisPaint);

    }

    private void drawXserise(Canvas canvas) {
        for(int i=0;i<15;i++){
            canvas.drawLine(140+i*(mWidth-150)/15,mHeigth-100,140+i*(mWidth-150)/15,mHeigth-110,axisPaint);
        }
    }

    private void drawBarAndXScale(Canvas canvas, List<Satellite> satellites) {
        for(int i=0;i<satellites.size();i++){
            mPath.reset();
            mPath.moveTo(140+i*(mWidth-150)/15-15,mHeigth-70);
            mPath.lineTo(140+i*(mWidth-150)/15+15,mHeigth-70);
            canvas.drawTextOnPath(String.valueOf(satellites.get(i).getID()),mPath,0,0,titlePaint);
            //绘制柱形
            drawBar(canvas,satellites.get(i),140+i*(mWidth-150)/15,mHeigth-100,barColor);
        }
    }

    private void drawBar(Canvas canvas, Satellite satellite, int x, int y, int barColor) {
        if(satellite.getCNO()<24){
           canvas.drawRoundRect(new RectF(x-barWidth/2,y,x+barWidth/2,y),5,5,barPaint);
        }
        else if(satellite.getCNO()>60){
            canvas.drawRoundRect(new RectF(x-barWidth/2,20,x+barWidth/2,y),5,5,barPaint);
        }
        else{
            canvas.drawRoundRect(new RectF(x-barWidth/2,(60*(mHeigth-60)-satellite.getCNO()*(mHeigth-80)-480)/36,x+barWidth/2,y),5,5,barPaint);
        }
    }

    @Override
    public void updateCoordWithCollection(List<Satellite> list) {
        satellites.clear();
        satellites.addAll(list);
        invalidate();
    }
}
