package com.navgnss.gnsssystem.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.navgnss.gnsssystem.R;

/**
 * Created by ZhuJinWei on 2016/10/19.
 *  自定义滑动圆，监控手势，输出位移量
 */

public class ChannelControlView extends View{
    private int bigRadius;//大圆的半经
    private int littleRadius;//小圆的半径
    private Paint linePaint;//黑边画笔
    private Paint bigPaint;//圆盘画笔
    private Paint littlePaint;
    private RadialGradient bigRadialGradient;//大圆的颜色渲染器
    private RadialGradient littleRadialGradient;//小圆的颜色渲染器
    private int width;
    private int heigth;
    private int l;//细线的长度
    private double angle,varAngle;//小圆与大圆连线和Y轴所成的角度
    private boolean isMoving;//判断当前小圆是否在移动
    private float startPointX;
    private float startPointY;
    float slope=0;
    private float x,y;
    double value=0;
    public ChannelControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta=context.getTheme().obtainStyledAttributes(attrs,R.styleable.ChannelControlView,0,0);
        try{
            littleRadius= (int) ta.getDimension(R.styleable.ChannelControlView_littel_radius,20);
            l= (int) ta.getDimension(R.styleable.ChannelControlView_indicatrix_line,10);
        }finally {
            ta.recycle();
        }
        angle=Math.PI/6;
        varAngle=0;

        linePaint=new Paint();
        linePaint.setStrokeWidth(1f);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.BLACK);

        bigPaint=new Paint();
        bigPaint.setAntiAlias(true);
        bigPaint.setStyle(Paint.Style.FILL);
        bigPaint.setStrokeWidth(1f);

        littlePaint=new Paint();
        littlePaint.setAntiAlias(true);
        littlePaint.setStyle(Paint.Style.FILL);
        littlePaint.setStrokeWidth(1f);

        isMoving=false;
        startPointX=0f;
        startPointY=0f;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
       width= getWidth();
       heigth=getHeight();
        if(width>heigth){
            bigRadius=heigth/2;
        }
        else{
            bigRadius=width/2;
        }
        bigRadialGradient=new RadialGradient(width/2,heigth/2,bigRadius,Color.rgb(255,255,255),Color.rgb(220,220,220), Shader.TileMode.REPEAT);
        littleRadialGradient=new RadialGradient(width/2-(float)((bigRadius-l-littleRadius)*Math.sin(angle)),heigth/2+(float)((bigRadius-l-littleRadius)*Math.cos(angle)),littleRadius,Color.rgb(225,225,225),Color.rgb(215,215,215),Shader.TileMode.REPEAT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制带颜色渐变特效和黑边的大圆

        bigPaint.setShader(bigRadialGradient);
        canvas.drawCircle(width/2,heigth/2,bigRadius,bigPaint);
        canvas.drawCircle(width/2,heigth/2,bigRadius,linePaint);
        //绘制带颜色渐变和黑边的小圆

        littlePaint.setShader(littleRadialGradient);
        canvas.drawCircle(width/2-(float)((bigRadius-l-littleRadius)*Math.sin(angle)),heigth/2+(float)((bigRadius-l-littleRadius)*Math.cos(angle)),littleRadius,littlePaint);
        canvas.drawCircle(width/2-(float)((bigRadius-l-littleRadius)*Math.sin(angle)),heigth/2+(float)((bigRadius-l-littleRadius)*Math.cos(angle)),littleRadius,linePaint);
        //绘制小圆与大圆内侧的链接线
        canvas.drawLine(width/2-(float)((bigRadius-l)*Math.sin(angle)),heigth/2+(float)((bigRadius-l)*Math.cos(angle)),width/2-(float)(bigRadius*Math.sin(angle)),heigth/2+(float)(bigRadius*Math.cos(angle)),linePaint);
    }


    @Override
    public boolean onTouchEvent( MotionEvent motionEvent) {

        x=motionEvent.getX();
        y=motionEvent.getY();

        Log.d("TAG","xyz onTouch()触发了！"+"x="+x+";y="+y);
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("TAG","xyz down事件触发了！");
                startPointX=x;
                startPointY=y;
                Log.d("TAG","xyz sPx="+startPointX+"; sPy="+startPointY);
            case MotionEvent.ACTION_MOVE:
                Log.d("TAG","xyz move事件触发了！");
                isMoving=true;
                //判断是顺时针还是逆时针拖动,通过向量叉乘的方法判断
                slope=((startPointX-width/2)*(y-heigth/2)-(x-width/2)*(startPointY-heigth/2));
                double a=getDistance(x,y,width/2,heigth/2);
                double b=getDistance(startPointX,startPointY,width/2,heigth/2);
                if(a==0|b==0){
                    Log.d("TAG","xyz 停止移动！");
                    return true;
                }
               // Log.d("TAG","xyz slope="+slope+";slopeNow="+slopeNow+"; a="+a+"; b="+b+"; c="+c+"夹角为="+Math.acos((a+b-c)/(2*Math.sqrt(a*b))));
                if(slope>0){
                    //顺时针
                   varAngle=Math.acos(((startPointX-width/2)*(x-width/2)+(startPointY-heigth/2)*(y-heigth/2))/(a*b));
                   angle+=varAngle;
                   angle=angle%Math.PI;
                    value+=varAngle;
                    Log.d("TAG","xyz 顺时针拖动"+";varAngel="+varAngle);
                }
                else if(slope==0){
                    //同向拖动
                    Log.d("TAG","xyz slope==0轴向拖动执行了!");


                }
                else{
                    //逆时针
                    varAngle=Math.acos(((startPointX-width/2)*(x-width/2)+(startPointY-heigth/2)*(y-heigth/2))/(a*b));
                    angle-=varAngle;
                    angle=angle%Math.PI;
                    value-=varAngle;
                    Log.d("TAG","逆时针拖动"+";varAngel="+varAngle);
                }

                invalidate();
                startPointX=x;
                startPointY=y;
                Log.d("TAG","xyz move事件执行后angle="+angle);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("TAG","xyz up事件触发了！");
                isMoving=false;
                invalidate();
                //TODO 添加输出value
                Log.d("TAG","xyz value="+String.valueOf(value));
                value=0;
                break;
        }
        return true;
    }


    private double getDistance(float x1,float y1,float x2,float y2){
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }
}
