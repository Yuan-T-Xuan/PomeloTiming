package cn.edu.fudan.xuan.mytimemanagement;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MTMPlotView extends View {
    private int color, fsize;
    private List<Integer> Y;
    private List<String> Labels;
    private String title = "It's a test title.";
    private Paint titlePaint = new Paint();
    private Paint barPaint1 = new Paint(), barPaint2 = new Paint();

    public MTMPlotView(Context context) {
        super(context);
        // for debug only
        Y = new ArrayList<Integer>();
        Y.add(1);Y.add(2);Y.add(3);Y.add(4);
        Y.add(5);Y.add(6);Y.add(7);
        Labels = new ArrayList<String>();
        Labels.add("11/1");Labels.add("11/2");Labels.add("11/3");Labels.add("11/4");
        Labels.add("11/5");Labels.add("11/6");Labels.add("11/7");
        color = Color.RED; fsize = 30;
        //
    }

    public MTMPlotView(Context context, AttributeSet attrs)  {
        super(context, attrs);
        // for debug only
        Y = new ArrayList<Integer>();
        Y.add(1);Y.add(2);Y.add(3);Y.add(4);
        Y.add(5);Y.add(6);Y.add(7);
        Labels = new ArrayList<String>();
        Labels.add("11/1");Labels.add("11/2");Labels.add("11/3");Labels.add("11/4");
        Labels.add("11/5");Labels.add("11/6");Labels.add("11/7");
        color = Color.RED; fsize = 25;
        //
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setData(List<Integer> Y, List<String> Labels) {
        this.Y = Y;
        this.Labels = Labels;
    }

    public void setFontSize(int fsize) {
        this.fsize = fsize;
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.drawColor(Color.rgb(220, 220, 220));
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        titlePaint.setTextSize(50);
        titlePaint.setAntiAlias(true);
        float titleWidth = titlePaint.measureText(title);
        canvas.drawText(title, (width-titleWidth)/2, 55, titlePaint);
        // each bar is 40dp
        barPaint1.setColor(Color.GRAY);
        barPaint1.setAntiAlias(true);
        barPaint1.setTextSize(this.fsize);
        barPaint2.setColor(this.color);
        barPaint2.setAntiAlias(true);
        barPaint2.setTextSize(this.fsize);
        float spacing = ((float)width - 40 * Y.size() - 40) / (Y.size() - 1);
        //canvas.drawRoundRect(30, 100, 70, 400, 20, 20, new Paint());
        int max = -1;
        for(int i = 0; i < Y.size(); i++) {
            if(Y.get(i) > max)
                max = Y.get(i);
        }
        for(int i = 0; i < Y.size(); i++) {
            canvas.drawRoundRect(20+i*(40+spacing), 150, 60+(i*(40+spacing)), 450, 20, 20, barPaint1);
            canvas.drawRoundRect(20+i*(40+spacing), 150, 60+(i*(40+spacing)), 150+300*((float)Y.get(i) / max), 20, 20, barPaint2);
        }
        for(int i = 0; i < Y.size(); i++) {
            float tWidth = barPaint2.measureText(String.format("%d", Y.get(i)));
            canvas.drawText(String.format("%d", Y.get(i)), 40+(i*(40+spacing)) - tWidth/2, 110, barPaint2);
        }
        for(int i = 0; i < Y.size(); i++) {
            float tWidth = barPaint1.measureText(Labels.get(i));
            canvas.drawText(Labels.get(i), 40+(i*(40+spacing)) - tWidth/2, 490, barPaint1);
        }
    }
}
