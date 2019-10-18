package com.mobilepps.techexcahnge.assignment3;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Tank {
    private int X, Y, XDestination, YDestination;
    private Bitmap tankBitmap;
    private Context context;

    public Tank(Context context, int x, int y) {
        this.context = context;
        X = x;
        Y = y;
        XDestination = x;
        YDestination = y;
        tankBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.multicolortanks);
        tankBitmap = Bitmap.createBitmap(tankBitmap, 0, 0, 85, 85);
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    protected void drawTank(Canvas canvas, int WALL_WIDTH, int WALL_HEIGHT) {
        canvas.drawBitmap(this.tankBitmap, null,
                new Rect(this.getX(), this.getY(), this.getX() + WALL_WIDTH, this.getY() + WALL_HEIGHT), null);
    }

    public int getXDestination() {
        return XDestination;
    }

    public void setXDestination(int XDestination) {
        this.XDestination = XDestination;
    }

    public int getYDestination() {
        return YDestination;
    }

    public void setYDestination(int YDestination) {
        this.YDestination = YDestination;
    }
}
