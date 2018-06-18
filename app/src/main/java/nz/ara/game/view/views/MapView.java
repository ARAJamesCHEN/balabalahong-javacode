package nz.ara.game.view.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.yac0105.game.R;

import java.util.ArrayList;
import java.util.List;

import nz.ara.game.model.impl.game.GameImpl;
import nz.ara.game.model.in.point.Point;

/**
 * Created by yac0105 on 18/05/2018.
 */

public class MapView extends View {

    private static final String TAG = "MapView";

    private int stepWidthX = 100;

    private int stepWidthY = 100;

    private int startPointX = 100;

    private int startPointY = 200;

    private String itemsWallLeftStr;

    private String itemsWallAboveStr;

    private String wallSquareStr;

    private String thePointStr;

    private String minPointStr;

    private Paint drawPaint;

    private Path path = new Path();

    private int mHeight = this.getMeasuredHeight();
    private int mWidth = this.getMeasuredHeight();

    public MapView(Context context){
        super(context);

        this.drawMapByAttrs();

    }

    public MapView(Context context, AttributeSet attrs){
        super(context,attrs );

        this.drawMapByAttrs();

    }

    private void drawMapByAttrs(){
        drawPaint = new Paint(Paint.DITHER_FLAG);
        drawPaint.setAntiAlias(true);
        drawPaint.setColor(Color.BLACK);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int width, int height) {
        super.onSizeChanged(w, h, width, height);
    }


    public int getStepWidthX() {
        return stepWidthX;
    }

    public void setStepWidthX(int stepWidthX) {
        this.stepWidthX = stepWidthX;
    }

    public int getStepWidthY() {
        return stepWidthY;
    }

    public void setStepWidthY(int stepWidthY) {
        this.stepWidthY = stepWidthY;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        mHeight = this.getMeasuredHeight();
        mWidth = this.getMeasuredHeight();


    }

    private void  calParas(){
        int countX = 4;
        int countY = 4;

        if(wallSquareStr!=null && wallSquareStr.trim().length()>0){
            Log.d(TAG, "Wall suare:" + wallSquareStr);

            String[] wallSqurArray = wallSquareStr.split(",");

            countX = Integer.parseInt(wallSqurArray[0]);
            countY = Integer.parseInt(wallSqurArray[1]);

        }

        this.stepWidthX = mWidth/(countX);
        this.stepWidthY = mHeight/(countY);

        this.startPointX = this.stepWidthX;

        TextView v = ((Activity)getContext()).findViewById(R.id.textView_move_name);

        int hTx = v.getMeasuredHeight();
        this.startPointY = hTx + 12 + this.stepWidthY/2;
    }

    private void drawMap(Canvas canvas, String wallStr, String type){
        if(wallStr!=null && wallStr.trim().length()>0){
            String[] wallStrArray = wallStr.split("\\|");

            for(String pointStr : wallStrArray){

                String[] pointStrArray = pointStr.split(",");

                int pointX = Integer.parseInt(pointStrArray[0]);
                int pointY = Integer.parseInt(pointStrArray[1]);
                Log.d(TAG, "Point: " + pointX + "," + pointY);

                int drawPointX = startPointX + pointX*this.stepWidthX - this.stepWidthX/2;
                int drawPointY = startPointY + pointY*this.stepWidthX - this.stepWidthY/2;

                if(type!=null && type.equals(getResources().getString(R.string.WALL_TYPE_ABOVE))){
                    canvas.drawLine(drawPointX, drawPointY, drawPointX + this.stepWidthX, drawPointY, drawPaint);
                }else if(type!=null && type.equals(getResources().getString(R.string.WALL_TYPE_LEFT))){
                    canvas.drawLine(drawPointX, drawPointY, drawPointX, drawPointY + this.stepWidthY, drawPaint);
                }else{
                    Log.e(TAG, "Error Type:" + type);
                }

            }

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.calParas();

        this.drawMap(canvas, this.itemsWallAboveStr, getResources().getString(R.string.WALL_TYPE_ABOVE));
        this.drawMap(canvas,this.itemsWallLeftStr, getResources().getString(R.string.WALL_TYPE_LEFT));

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {





        return true;
    }

    public String getItemsWallLeftStr() {
        return itemsWallLeftStr;
    }

    public void setItemsWallLeftStr(String itemsWallLeftStr) {
        this.itemsWallLeftStr = itemsWallLeftStr;
    }

    public String getItemsWallAboveStr() {
        return itemsWallAboveStr;
    }

    public void setItemsWallAboveStr(String itemsWallAboveStr) {
        this.itemsWallAboveStr = itemsWallAboveStr;
    }

    public String getWallSquareStr() {
        return wallSquareStr;
    }

    public void setWallSquareStr(String wallSquareStr) {
        this.wallSquareStr = wallSquareStr;
    }

    public String getThePointStr() {
        return thePointStr;
    }

    public void setThePointStr(String thePointStr) {
        this.thePointStr = thePointStr;
    }

    public String getMinPointStr() {
        return minPointStr;
    }

    public void setMinPointStr(String minPointStr) {
        this.minPointStr = minPointStr;
    }

}
