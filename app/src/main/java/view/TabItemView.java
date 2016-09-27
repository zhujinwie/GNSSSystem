package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.navgnss.gnsssystem.R;

/**
 * Created by ZhuJinWei on 2016/9/27.
 * 自定义文字tab栏，颜色可渐变
 */

public class TabItemView extends View {
    private String tabTextName = "标题栏";//文本
    private int tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10f, getResources().getDisplayMetrics());//文本大小
    private int tabTextColor = 0xff00000;//文本颜色
    private float mAlpha = 0f;//文本透明度
    private Paint mPaint;//画笔

    public TabItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.TabItemView, 0, 0
        );
        try {
            tabTextName = ta.getString(R.styleable.TabItemView_tab_text_name);
            tabTextColor = ta.getColor(R.styleable.TabItemView_tab_text_color, 0xff00000);
            tabTextSize = ta.getDimensionPixelSize(R.styleable.TabItemView_tab_text_size, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10f, getResources().getDisplayMetrics()));
        } finally {
            ta.recycle();
        }
        mPaint=new Paint();



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);









    }
}



