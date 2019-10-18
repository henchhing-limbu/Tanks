package com.mobilepps.techexcahnge.assignment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class BattleFieldView extends View implements GestureDetector.OnGestureListener {

    private static final String TAG = "BattleFieldView";
    private static Rect[] brickWalls;
    private Context context;

    private static final int WALL_WIDTH = 135;
    private static final int WALL_HEIGHT = 144;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private boolean moving;


    private static final double SLEEP_TIME = 0.003;
    private static int SPEED = 2000;

    private Tank tank;

    private GestureDetectorCompat mDetector;

    Bitmap wall;

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
    }

    public BattleFieldView(Context context) {
        super(context);
        this.context = context;
        wall = BitmapFactory.decodeResource(getResources(), R.drawable.bricksx64);
        tank = new Tank(this.context, 0, 0);

        mDetector = new GestureDetectorCompat(context,  this);
        moving = false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        SCREEN_WIDTH = w;
        SCREEN_HEIGHT = h;

        brickWalls = new Rect[] {new Rect(WALL_WIDTH, 0, 2*WALL_WIDTH , WALL_HEIGHT)
                , new Rect(WALL_WIDTH, WALL_HEIGHT, 2*WALL_WIDTH, 2*WALL_HEIGHT)
                , new Rect(WALL_WIDTH, WALL_HEIGHT*2, 2*WALL_WIDTH, 3*WALL_HEIGHT)
                , new Rect(WALL_WIDTH, WALL_HEIGHT*3, 2*WALL_WIDTH, 4*WALL_HEIGHT)
                , new Rect(2*WALL_WIDTH, WALL_HEIGHT*3, 3*WALL_WIDTH, 4*WALL_HEIGHT)
                , new Rect(3*WALL_WIDTH, WALL_HEIGHT*3, 4*WALL_WIDTH, 4*WALL_HEIGHT)
                , new Rect(5*WALL_WIDTH, 0, 6*WALL_WIDTH, WALL_HEIGHT)
                , new Rect(6*WALL_WIDTH, 0, 7*WALL_WIDTH, WALL_HEIGHT)
                , new Rect(2*WALL_WIDTH, 7*WALL_HEIGHT, 3*WALL_WIDTH, 8*WALL_HEIGHT)
                , new Rect(3*WALL_WIDTH, 7*WALL_HEIGHT, 4*WALL_WIDTH, 8*WALL_HEIGHT)
                , new Rect(4*WALL_WIDTH, 7*WALL_HEIGHT, 5*WALL_WIDTH, 8*WALL_HEIGHT)
                , new Rect(3*WALL_WIDTH, 6*WALL_HEIGHT, 4*WALL_WIDTH, 7*WALL_HEIGHT)
                , new Rect(5*WALL_WIDTH, 7*WALL_HEIGHT, 6*WALL_WIDTH, 8*WALL_HEIGHT)
                , new Rect(5*WALL_WIDTH, 8*WALL_HEIGHT, 6*WALL_WIDTH, 9*WALL_HEIGHT)
                , new Rect(5*WALL_WIDTH, 9*WALL_HEIGHT, 6*WALL_WIDTH, 10*WALL_HEIGHT)
                , new Rect(5*WALL_WIDTH, 10*WALL_HEIGHT, 6*WALL_WIDTH, 11*WALL_HEIGHT)

        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < brickWalls.length; i++) {
            Log.d(TAG, "Index is " + i);
            canvas.drawBitmap(wall, null, brickWalls[i], null);
        }
        tank.drawTank(canvas, WALL_WIDTH, WALL_HEIGHT);

        update();
        try {
            Thread.sleep(30);
        }
        catch (InterruptedException ex) {
            Log.e(TAG, "Sleep interrupted!", ex);
        }
        invalidate();
    }

    private void update() {
        if (tank.getX() != tank.getXDestination()) { // move along x-axis
            int newX = tank.getX() + (int)(SPEED*SLEEP_TIME);
            int XDest = tank.getXDestination();

            if (tank.getX() < XDest) { // if tank is moving right
                if (newX >= XDest) {
                    tank.setX(XDest);
                    moving = false;
                }
                else tank.setX(newX);
            }
            else {                                          // if tank is moving left
                if (newX <= XDest) {
                    tank.setX(XDest);
                    moving = false;
                }
                else tank.setX(newX);
            }
        }
        else if (tank.getY() != tank.getYDestination()) {   // move along y-axis
            int newY = tank.getY() + (int)(SPEED*SLEEP_TIME);
            int YDest = tank.getYDestination();

            if (tank.getY() < YDest) {                      // if tank is moving right
                if (newY >= YDest) {
                    tank.setY(YDest);
                    moving = false;
                }
                else tank.setY(newY);
            }
            else {                                          // if tank is moving left
                if (newY <= YDest) {
                    tank.setY(YDest);
                    moving = false;
                }
                else tank.setY(newY);
            }
        }
    }

    private void moveLeft() {
        int leftXCoordinate = tank.getX() - WALL_WIDTH;
        if (leftXCoordinate >= 0) {          // tank movement valid
            Rect newLocation = new Rect(leftXCoordinate, tank.getY(), tank.getX(), tank.getY() + WALL_HEIGHT);
            if (movePossible(newLocation)) {    // if move is possible
                moving = true;
                tank.setXDestination(newLocation.left);
                tank.setYDestination(newLocation.top);
                SPEED = Math.abs(SPEED) * (-1);
            }
        }
    }

    private void moveRight() {
        int rightXCoordinate = tank.getX() + 2*WALL_WIDTH;
        if (rightXCoordinate <= SCREEN_WIDTH) {
            Rect newLocation = new Rect(rightXCoordinate - WALL_WIDTH, tank.getY(), rightXCoordinate, tank.getY() + WALL_HEIGHT);
            if (movePossible(newLocation)) {    // if move is possible
                moving = true;
                tank.setXDestination(newLocation.left);
                tank.setYDestination(newLocation.top);
                SPEED = Math.abs(SPEED);
            }
        }
    }

    private void moveUp() {
        int topYCoordinate = tank.getY() - WALL_HEIGHT;
        if (topYCoordinate >= 0) {          // tank movement valid
            Rect newLocation = new Rect(tank.getX(), topYCoordinate, tank.getX() + WALL_WIDTH, tank.getY());
            if (movePossible(newLocation)) {    // if move is possible
                moving = true;
                tank.setXDestination(newLocation.left);
                tank.setYDestination(newLocation.top);
                SPEED = Math.abs(SPEED) * (-1);
            }

        }
    }

    private void moveDown() {
        int bottomYCoordinate = tank.getY() + 2*WALL_HEIGHT;
        if (bottomYCoordinate <= SCREEN_HEIGHT) {          // tank movement valid
            Rect newLocation = new Rect(tank.getX(), bottomYCoordinate - WALL_HEIGHT, tank.getX() + WALL_WIDTH, bottomYCoordinate);
            if (movePossible(newLocation)) {    // if move is possible
                moving = true;
                tank.setXDestination(newLocation.left);
                tank.setYDestination(newLocation.top);
                SPEED = Math.abs(SPEED);
            }

        }
    }

    private boolean movePossible(Rect loc) {
        for (int i = 0; i < brickWalls.length; i++) {
            Rect wall = brickWalls[i];
            if ((wall.centerX() == loc.centerX()) && (wall.centerY() == loc.centerY())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!moving) {
            if (Math.abs(velocityX) > Math.abs(velocityY))
            {
                if (velocityX < 0) moveLeft();
                else moveRight();
            }
            else {
                if (velocityY < 0) moveUp();
                else moveDown();
            }
        }
        return true;
    }
}
