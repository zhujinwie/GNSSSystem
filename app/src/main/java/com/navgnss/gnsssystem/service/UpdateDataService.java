package com.navgnss.gnsssystem.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.navgnss.gnsssystem.R;
import com.navgnss.gnsssystem.ui.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import android_serialport_api.Application;
import android_serialport_api.SerialPort;


/**
 * Created by ZhuJinWei on 2016/10/31.
 */

public class UpdateDataService extends Service {

    protected Application mApplication;
    protected SerialPort mSerialPort;
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private List<byte[]> bufferList;

    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();

            int size;
            //定义数据包的最大长度
            int maxLength=2048;
            byte[] buffer = new byte[maxLength];
            //定义每次收到的实际长度
            int available=0;
            //实际接受到的数据包
            int currentLength=0;
            //协议头长度2个字节
            int headerLength=2;

            //获取串口输出的循环
            while(!isInterrupted()) {

                try {
                    if (mInputStream == null) {
                        //以10Hz的频率不断询问串口是否有数据
                        sleep(100);
                        continue;
                    }
                    available=mInputStream.available();

                    if(available>0){
                        Log.d("TAG","xyz 串口数据接收到了 数据包的长度为："+available);
                        //防止超出数组最大长度导致溢出
                        if(available>maxLength-currentLength){
                            available=maxLength-currentLength;
                        }
                        mInputStream.read(buffer,currentLength,available);
                        currentLength+=available;
                        /**
                         * 测试用，打印数组
                         * */
                        showbuffer(buffer,available);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int cursor=0;
                //解析帧的循环
                //如果当前收到的包大于头的长度，则循环解析当前包，直到截获的长度等于帧的长度
                while (currentLength>=headerLength){
                    //取到头部第一个字节
                    if(buffer[cursor]!=0xEB&buffer[cursor]!=0x90){
                        --currentLength;
                        ++cursor;
                        continue;
                    }
                    //获取帧的长度
                    int contentLength=parseLen(buffer,cursor,headerLength);
                    //判断帧是不是有效数据
                    if(contentLength<=0||contentLength>maxLength){
                        currentLength=0;
                        break;
                    }
                    //如果截获的长度小于整个包的长度，则跳出循环等待继续接收数据
                    int factPackLen=contentLength;
                    if(currentLength<contentLength){
                        break;
                    }
                    //产生一个完整包
                    onDataReceived(buffer,cursor,factPackLen);
                    currentLength-=factPackLen;
                    cursor+=factPackLen;
                }
                //残留字节移到缓冲区首
                if(currentLength>0&&cursor>0){
                    System.arraycopy(buffer,cursor,buffer,0,currentLength);
                }
            }
        }
    }
    //获取协议内容的长度
    private int parseLen(byte[] buffer, int index, int headerLength) {
        if(buffer.length-index<headerLength){
            return 0;
        }
        //Length字段的位置
        byte a=buffer[index+3];
        byte b=buffer[index+4];

        int rlt=0;
        //TODO 可能有补码的判断
        char[] tmp=new char[2];
        tmp[0]=(char)b;
        tmp[1]=(char) a;
        String s=new String(tmp,0,2);
        rlt=Integer.parseInt(s,16);
        return rlt;

    }

    private void DisplayError(int resourceId) {

        Toast.makeText(mApplication, "出错了", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setTitle("出错了");
        b.setMessage(resourceId);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        AlertDialog dialog=b.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    @Override
    public  void  onCreate(){
        Log.d("TAG","xyz 后台服务启动！");

        bufferList=new ArrayList<>();
        mApplication = (Application) getApplication();
        try {
            mSerialPort = mApplication.getSerialPort();
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();

			/* Create a receiving thread */
            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (SecurityException e) {
            DisplayError(R.string.error_security);
        } catch (IOException e) {
            DisplayError(R.string.error_unknown);
        } catch (InvalidParameterException e) {
            DisplayError(R.string.error_configuration);
        }
    }

    protected  void onDataReceived(final byte[] buffer, final int index,final int packlen){

        Log.d("TAG","xyz 后台服务接受到数据了！");
        Log.d("DATA","xyz service接受到的数据为 btye[]buffer="+buffer);


        byte[] buf=new byte[packlen];
        System.arraycopy(buffer,index,buf,0,packlen);
        for(int i=0;i<packlen;i++){
            Log.d("DATA","xyz buf"+"["+i+"]"+"="+buf[i]);
        }

    }

    @Override
    public void onDestroy() {
        if (mReadThread != null)
            mReadThread.interrupt();
        mApplication.closeSerialPort();
        mSerialPort = null;

        try {
            mOutputStream.close();
            mInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 测试用 ，打印数组
     * */
    public void showbuffer(byte[] buffer,int ava){
        for(int i=0;i<ava;i++){
            Log.d("TAG","xyz 打印数组的元素 byte["+i+"]"+"="+buffer[i]);
        }
    }
}
