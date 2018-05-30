package nz.ara.game.view.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.yac0105.game.R;

import nz.ara.game.viewmodel.MainViewModel;

/**
 * Created by yac0105 on 26/05/2018.
 */

public class RoleView extends View {

    private static final String TAG = "RoleView";

    private int stepWidthX = 100;

    private int stepWidthY = 100;

    private int startPointX = 100;

    private int startPointY = 200;

    private int rolePointXShort = 100;

    private int rolePointXLong = 100;

    private int rolePointYShort = 200;

    private int rolePointYLong = 200;

    private String heightStr;

    private String wallSquareStr;

    private String pointStr;

    private int pointStrX;

    private int pointStrY;

    private String roleStr;

    private Paint drawPaint;

    private Path path = new Path();

    public RoleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        int[] set = {
            android.R.attr.background, // idx 0
            android.R.attr.text        // idx 1
        };

        TypedArray a = context.obtainStyledAttributes(attrs, set);
        Drawable d = a.getDrawable(0);
        CharSequence t = a.getText(1);

        roleStr = t.toString();

        a.recycle();
    }

    private int mHeight = this.getMeasuredHeight();
    private int mWidth = this.getMeasuredHeight();

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

    private void drapRole(Canvas canvas, String pointStr, String type){
        if(pointStr!=null && pointStr.trim().length()>0){

            String[] pointStrArray = pointStr.split(",");

            int pointX = Integer.parseInt(pointStrArray[0]);
            int pointY = Integer.parseInt(pointStrArray[1]);

            int left = startPointX + pointX*this.stepWidthX - this.stepWidthX/2 + 5;
            int top =  startPointY + pointY*this.stepWidthY - this.stepWidthY/2 + 5;
            int right = startPointX + pointX*this.stepWidthX + this.stepWidthX/2 - 5;
            int bottom = startPointY + pointY*this.stepWidthY + this.stepWidthY/2 - 5;

            rolePointXShort = left;
            rolePointYShort = top;
            rolePointXLong = right;
            rolePointYLong = bottom;

            Rect rectangle = new Rect(left,top,right,bottom);


            Bitmap bitmap = null;


            if(type!=null && type.equals(getResources().getString(R.string.ROLE_TYPE_THESEUS))){
                bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.theseus);

            }else if(type!=null && type.equals(getResources().getString(R.string.ROLE_TYPE_MINOTAUR))){
                bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.minotaur);
            }else{
                Log.e(TAG, "Error Type:" + type);
                return;
            }

            canvas.drawBitmap(bitmap, null, rectangle, null);


        }



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.calParas();

        this.drapRole(canvas, this.pointStr, this.roleStr );

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

    public int getStartPointX() {
        return startPointX;
    }

    public void setStartPointX(int startPointX) {
        this.startPointX = startPointX;
    }

    public int getStartPointY() {
        return startPointY;
    }

    public void setStartPointY(int startPointY) {
        this.startPointY = startPointY;
    }

    public String getWallSquareStr() {
        return wallSquareStr;
    }

    public void setWallSquareStr(String wallSquareStr) {
        this.wallSquareStr = wallSquareStr;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public String getPointStr() {
        return pointStr;
    }

    public void setPointStr(String pointStr) {
        this.pointStr = pointStr;
    }


    public String getRoleStr() {
        return roleStr;
    }

    public void setRoleStr(String roleStr) {
        this.roleStr = roleStr;
    }

    public String getHeightStr() {
        return heightStr;
    }

    public void setHeightStr(String heightStr) {
        this.heightStr = heightStr;
    }


    public int getRolePointXShort() {
        return rolePointXShort;
    }

    public void setRolePointXShort(int rolePointXShort) {
        this.rolePointXShort = rolePointXShort;
    }

    public int getRolePointXLong() {
        return rolePointXLong;
    }

    public void setRolePointXLong(int rolePointXLong) {
        this.rolePointXLong = rolePointXLong;
    }

    public int getRolePointYShort() {
        return rolePointYShort;
    }

    public void setRolePointYShort(int rolePointYShort) {
        this.rolePointYShort = rolePointYShort;
    }

    public int getRolePointYLong() {
        return rolePointYLong;
    }

    public void setRolePointYLong(int rolePointYLong) {
        this.rolePointYLong = rolePointYLong;
    }


    public int getPointStrX() {
        return pointStrX;
    }

    public void setPointStrX(int pointStrX) {
        this.pointStrX = pointStrX;
    }

    public int getPointStrY() {
        return pointStrY;
    }

    public void setPointStrY(int pointStrY) {
        this.pointStrY = pointStrY;
    }


}
