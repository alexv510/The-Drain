package com.game.alex.thedrain;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.content.Context;

/**
 * Created by Alex on 5/25/2015.
 */
public class Draw extends View {

    public static double accel_x, accel_y;
    public static double fric_x, fric_y = 1;
    public static double hole = 65;
    public float hole_x, hole_y, left1, left2, top1, top2, right1, right2, bottom1, bottom2;

    private Paint paint = new Paint();

    public Draw(Context context) {super(context);}
    public Draw(Context context, AttributeSet attributes) {super(context, attributes);}

    protected void daMath() {

        double sub_x = CherryTomato.x - (hole_x);
        double sub_y = CherryTomato.y - (hole_y);
        double diff = Math.sqrt(sub_x*sub_x + sub_y*sub_y);

        CherryTomato.vel_x += .1 * accel_x;
        CherryTomato.vel_y += .1 *accel_y;
        CherryTomato.x = CherryTomato.x + (.6 * CherryTomato.vel_x) - fric_x;
        CherryTomato.y = CherryTomato.y + (.6 * CherryTomato.vel_y) - fric_y;

        if(CherryTomato.x + CherryTomato.rad > getMeasuredWidth()) {
            CherryTomato.x = getMeasuredWidth() - CherryTomato.rad;
            CherryTomato.vel_x = -CherryTomato.vel_x *.5;
        }

        if(CherryTomato.y + CherryTomato.rad > getMeasuredHeight()) {
            CherryTomato.y = getMeasuredHeight() - CherryTomato.rad;
            CherryTomato.vel_y = -CherryTomato.vel_y *.5;
        }

        if(CherryTomato.y + CherryTomato.rad > 0 && CherryTomato.x + CherryTomato.rad > 0 && CherryTomato.y + CherryTomato.rad > top1
                && CherryTomato.x - CherryTomato.rad < right1 - 3 && CherryTomato.y < bottom1 - 5) {
            CherryTomato.y = top1 - CherryTomato.rad;
            CherryTomato.vel_y = -CherryTomato.vel_y *.5;
        }

        if(CherryTomato.y + CherryTomato.rad < getMeasuredHeight() && CherryTomato.x + CherryTomato.rad > 0 &&
                CherryTomato.y - CherryTomato.rad > top1 + 5 && CherryTomato.x - CherryTomato.rad < right1 - 3 &&
                CherryTomato.y - CherryTomato.rad < bottom1) {
            CherryTomato.y = bottom1 + CherryTomato.rad;
        }

        if((CherryTomato.x - CherryTomato.rad) < 0) {
            CherryTomato.x = 0 + CherryTomato.rad;
            CherryTomato.vel_x = -CherryTomato.vel_x *.5;
        }

        if(CherryTomato.y - CherryTomato.rad < 0) {
            CherryTomato.y = 0 + CherryTomato.rad;
            CherryTomato.vel_y = -CherryTomato.vel_y *.5;
        }

        if(CherryTomato.y - CherryTomato.rad > 0 && CherryTomato.x - CherryTomato.rad > 0 && CherryTomato.y + CherryTomato.rad > top2
                && CherryTomato.x + CherryTomato.rad > left2 && CherryTomato.y < bottom2 - 1) {
            CherryTomato.y = top2 - CherryTomato.rad;
            CherryTomato.vel_y = -CherryTomato.vel_y *.5;
        }

        if(CherryTomato.y - CherryTomato.rad < getMeasuredHeight() && CherryTomato.x - CherryTomato.rad > 0 &&
                CherryTomato.y - CherryTomato.rad > top2 + 5 && CherryTomato.x + CherryTomato.rad > left2 &&
                CherryTomato.y - CherryTomato.rad < bottom2) {
            CherryTomato.y = bottom2 + CherryTomato.rad;
        }

        if(diff < (CherryTomato.rad + hole - 80)) {
            CherryTomato.vel_x = 0;
            CherryTomato.vel_y = 0;
            CherryTomato.x = 200;
            CherryTomato.y = 200;
        }
    }

    protected void onDraw(Canvas c) {
        paint.setColor(Color.WHITE);
        c.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        paint.setARGB(188, 45, 179, 255);
        c.drawRect(left1, top1, right1, bottom1, paint);

        paint.setARGB(188, 45, 179, 255);
        c.drawRect(left2, top2, right2, bottom2, paint);

        paint.setColor(Color.GRAY);
        c.drawCircle(hole_x, hole_y, (float) hole, paint);

        paint.setColor(Color.RED);
        c.drawCircle((float) CherryTomato.x, (float) CherryTomato.y, CherryTomato.rad, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //drain placement
        hole_x = (getMeasuredWidth() - 500);
        hole_y = (getMeasuredHeight() - 225);

        //first box
        left1 = 0;
        top1 = getMeasuredHeight() - 1000;
        right1 = getMeasuredWidth() - 420;
        bottom1 = getMeasuredHeight() - 990;

        //second box
        left2 = 300;
        top2 = getMeasuredHeight() - 460;
        right2 = getMeasuredWidth() ;
        bottom2 = getMeasuredHeight() - 450;

        setMeasuredDimension(width, height);
    }

    public Runnable drawIt = new Runnable(){
        @Override
        public void run() {
            invalidate();
            postDelayed(this, 5);
            daMath();
        }
    };
}
