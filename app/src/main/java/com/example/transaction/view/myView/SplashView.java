package com.example.transaction.view.myView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.example.transaction.R;

public class SplashView extends View {
    //动画
    private ValueAnimator valueAnimator;
    //绘制圆的画笔
    private Paint mPaint = new Paint();
    //绘制背景的画笔
    private Paint mPaintBackground = new Paint();

    //当前大圆旋转角度(弧度)
    private float mCurrentRotationAngle = 0F;
    // 大圆(里面包含很多小圆的)的半径
    private float mRotationRadius = 90;
    // 每一个小圆的半径
    private float mCircleRadius = 18;
    //当前大圆的半径
    private float mCurrentRotationRadius = mRotationRadius;

    //颜色的集合
    private int[] mCircleColors;

    //圆圈旋转的时间
    private int mRotationDuration = 1200;

    // 屏幕正中心点坐标
    private float mCenterX;
    private float mCenterY;

    //屏幕对角线的一般
    private int DisBank;

    //空心圆初始半径
    private float mHoleRadius = 0F;

    public SplashView(Context context) {
        this(context, null, 0);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaintBackground.setAntiAlias(true);
        //描边
        mPaintBackground.setStyle(Paint.Style.STROKE);
        mPaintBackground.setColor(Color.WHITE);

        //颜色集合
        mCircleColors = context.getResources().getIntArray(R.array.splash_circle_colors);
    }

    //初始状态
    SplashState state = null;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (state == null) {
            //初始状态 旋转
            state = new RotateState();
        }
        //画圆
        state.drawState(canvas);
    }

    //状态转变
    public void splashDisappear() {
        if (state != null && state instanceof RotateState) {
            //动画取消
            state.cancel();

            post(new Runnable() {
                @Override
                public void run() {
                    //开始聚合动画
                    state = new MergingState();

                }
            });
        }
    }

    //业务逻辑  状态
    //1.刚进来时，执行旋转动画
    private class RotateState extends SplashState {
        public RotateState() {
            //2π == 360度
            valueAnimator = ValueAnimator.ofFloat(0, (float) (Math.PI * 2));
            //设置匀速插值器
            valueAnimator.setInterpolator(new LinearInterpolator());
            //设置监听
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //当前圆的角度
                    mCurrentRotationAngle = (float) animation.getAnimatedValue();
                    //重新绘制
                    postInvalidate();
                }
            });
            valueAnimator.setDuration(mRotationDuration);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.start();
        }

        @Override
        public void drawState(Canvas canvas) {
            //画背景
            drawBackground(canvas);
            //画圆
            drawCircle(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;

        //屏幕对角线的一半
        DisBank = (int) (Math.sqrt(w * w + h * h) / 2f);
    }

    private void drawCircle(Canvas canvas) {
        //每个圆对应的角度(360/6 = 60°)
        float rotationAngle = (float) (Math.PI * 2 / mCircleColors.length);

        //关于每个点的坐标
        for (int i = 0; i < mCircleColors.length; i++) {
            //角度是动态变化的
            float angle = i * rotationAngle + mCurrentRotationAngle;
            float cx = (float) (mCenterX + mCurrentRotationRadius * Math.cos(angle));
            float cy = (float) (mCenterY + mCurrentRotationRadius * Math.sin(angle));

            //开始画
            mPaint.setColor(mCircleColors[i]);
            canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
        }
    }

    private void drawBackground(Canvas canvas) {
        if (mHoleRadius > 0) {
            //画圆
            float strokeWidth = DisBank - mHoleRadius;
            mPaintBackground.setStrokeWidth(strokeWidth);

            float radius = mHoleRadius + strokeWidth / 2;
            canvas.drawCircle(mCenterX, mCenterY, radius, mPaintBackground);

        } else {
            canvas.drawColor(Color.WHITE);
        }
    }

    //2.数据加载完毕之后，聚合逃逸动画
    private class MergingState extends SplashState {
        public MergingState() {
            valueAnimator = ValueAnimator.ofFloat(mRotationRadius, 0);
            valueAnimator.setDuration(mRotationDuration);
            valueAnimator.setInterpolator(new OvershootInterpolator(10));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotationRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            //监听动画完成
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    //启动扩散动画
                    state = new ExpandState();
                }
            });
            valueAnimator.start();
        }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawCircle(canvas);
        }
    }

    //3.扩散动画  以屏幕到中心线的对角线为半径
    private class ExpandState extends SplashState {
        public ExpandState() {
            valueAnimator = ValueAnimator.ofFloat(mCircleRadius, DisBank);
            valueAnimator.setDuration(mRotationDuration);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mHoleRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.start();
        }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
        }
    }

    //策略模式
    private abstract class SplashState {
        //画动画状态
        public abstract void drawState(Canvas canvas);

        //取消动画
        public void cancel() {
            valueAnimator.cancel();
        }
    }

}