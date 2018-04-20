package cn.qssq666.switchbtn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by qssq on 2018/4/21 qssq666@foxmail.com
 */
public class QSSQSwitchBtn extends View {

    private static final int NORMAL_MODE_ALPHA = 255;
    private  final int MAIN_THREAD_SLOW_QUERY_THRESHOLD = 300;

    public void setInnerBtnHeight(int innerBtnHeight) {
        this.innerBtnHeight = innerBtnHeight;
        invalidate();
    }

    private  int innerBtnHeight;

    public void setInnerBtnWidth(float innerBtnWidth) {
        this.innerBtnWidth = innerBtnWidth;
        invalidate();
    }

    private float innerBtnWidth;

    public void setInnerbtnColor(int mInnerbtnColor) {
        this.mInnerbtnColor = mInnerbtnColor;
        invalidate();
    }

    public void setInnerUncheckBgColor(int mInnerUncheckBgColor) {
        this.mInnerUncheckBgColor = mInnerUncheckBgColor;
        invalidate();
    }

    public void setInnercheckBgColor(int mInnercheckBgColor) {
        this.mInnercheckBgColor = mInnercheckBgColor;
        invalidate();
    }
    public void setInnerPadding(int innerPadding) {
        this.innerPadding = innerPadding;

        invalidate();
    }


    public void setBtnRadius(float btnRadius) {
        this.btnRadius = btnRadius;
        invalidate();
    }

    private int mInnerbtnColor;
    private int mInnerUncheckBgColor;
    private int mInnercheckBgColor;

    private static final String COLORGRAY = "#999999";
    private int scaledTouchSlop;
    private Paint paint = new Paint(1);
    private int currentHeight;
    private int currentWidth;
    private int DEFAULT_VALUE;
    private boolean doJudgeIngLock = false;
    private boolean changeing = false;



    private int innerPadding;
    private int uHE = 80;
    private int minClickTime = MAIN_THREAD_SLOW_QUERY_THRESHOLD;//300毫秒


    private float btnRadius;


    public boolean isChecked() {
        return isCheck;
    }


    public boolean isCheck = false;
    private RectF recfF1 = new RectF();
    private RectF blockRectF = new RectF();
    private MyAnim myAnim = new MyAnim(this);


    private float downX;
    private float downY;
    private long downTime;
    private int halfWidth;
    private int maxWidth;

    public QSSQSwitchBtn(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, null);
    }

    @SuppressLint("NewApi")
    public QSSQSwitchBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public QSSQSwitchBtn(Context context) {
        super(context, null, 0);
        init(context, null);
    }

    public QSSQSwitchBtn(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public static int dip2px(Context context, float dpValue) {
     /*   final float scale = context.getResources().getDisplayMetrics().density;//density=dpi/160
        return (int) (dpValue * scale + 0.5f);
*/
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,

                dpValue, context.getResources().getDisplayMetrics());

    }

    private void init(Context context, AttributeSet attributeSet) {
        this.btnRadius = dip2px(context, 12);
        this.mInnerbtnColor = Color.WHITE;
        this.innerBtnWidth = dip2px(context, 11);
        this.innerPadding = dip2px(context, 2f);
        this.innerBtnHeight = dip2px(context, 22);
        this.btnRadius = (innerBtnHeight + innerPadding) / 2;
        this.mInnercheckBgColor = Color.parseColor("#F84444");
        this.mInnerUncheckBgColor = Color.parseColor(COLORGRAY);

        if (attributeSet != null) {
            Resources.Theme theme = context.getTheme();
            TypedArray typedArray = theme.obtainStyledAttributes(attributeSet, R.styleable.QSSQSwitchBtn, 0, 0);
            this.mInnercheckBgColor = typedArray.getColor(R.styleable.QSSQSwitchBtn_checkBgColor, mInnercheckBgColor);
            this.mInnerUncheckBgColor = typedArray.getColor(R.styleable.QSSQSwitchBtn_checkBgColor, mInnerUncheckBgColor);
            this.mInnerbtnColor = typedArray.getColor(R.styleable.QSSQSwitchBtn_innerBtnColor, mInnerbtnColor);
            this.innerPadding = typedArray.getDimensionPixelSize(R.styleable.QSSQSwitchBtn_innerPadding, innerPadding);
            this.innerBtnWidth = typedArray.getDimensionPixelSize(R.styleable.QSSQSwitchBtn_innerBtnWidth, (int) innerBtnWidth);
            this.innerBtnHeight = typedArray.getDimensionPixelSize(R.styleable.QSSQSwitchBtn_innerBtnHeight, innerBtnHeight);
            this.btnRadius = typedArray.getDimensionPixelSize(R.styleable.QSSQSwitchBtn_btnRadius, (int) btnRadius);
            if (BuildConfig.DEBUG) {
//                Log.w(TAG,)
            }

            typedArray.recycle();


        } else {


        }


//        this.mInnercheckBgColor = Color.parseColor("#1AAD19");
//        this.mInnercheckBgColor = Color.parseColor("#1AAD19");
        this.scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

 /*   public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean ischeck);
    }*/

    private class MyAnim extends Animation {
        CheckBox.OnCheckedChangeListener LIS = null;

        int direction = 0;
        float uHQ = 0.0f;
        long uHR = 0;
        final /* synthetic */ QSSQSwitchBtn switchBtn;

        public MyAnim(final QSSQSwitchBtn mMSwitchBtn) {
            this.switchBtn = mMSwitchBtn;
            setInterpolator(new AccelerateDecelerateInterpolator());
            setAnimationListener(new AnimationListener() {
                final MyAnim myAnim = MyAnim.this;

                public final void onAnimationStart(Animation animation) {
                }

                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationEnd(Animation animation) {
                    boolean check = true;
                    if (QSSQSwitchBtn.isCheck(this.myAnim.switchBtn) != (this.myAnim.direction == 1)) {
                        final QSSQSwitchBtn mMSwitchBtn = this.myAnim.switchBtn;
                        if (this.myAnim.direction != 1) {
                            check = false;
                        }


                        QSSQSwitchBtn.isCheck(mMSwitchBtn, check);
                        final boolean finalCheck = check;
                        this.myAnim.switchBtn.post(new Runnable() {
                            @Override
                            public void run() {
                                if (QSSQSwitchBtn.getCallBack(switchBtn) != null) {
                                    QSSQSwitchBtn.getCallBack(switchBtn).onCheckedChanged(null, finalCheck);
                                }
                            }
                        });
                    }
                    QSSQSwitchBtn.cancelCheckLock(this.myAnim.switchBtn);
                }
            });
        }

        @Override
        protected final void applyTransformation(float f, Transformation transformation) {
            //GMTracei(13052271394816L, 97247);
            if (this.direction == 0) {
                QSSQSwitchBtn.getRecfF(this.switchBtn).left = this.uHQ - (((float) this.uHR) * f);
            } else {
                QSSQSwitchBtn.getRecfF(this.switchBtn).left = this.uHQ + (((float) this.uHR) * f);
            }
            QSSQSwitchBtn.requestLayoutFix(this.switchBtn);
            this.switchBtn.invalidate();
        }
    }

    static /* synthetic */ boolean isCheck(QSSQSwitchBtn mMSwitchBtn) {
        boolean isCheck = mMSwitchBtn.isCheck;
        return isCheck;
    }

    static /* synthetic */ boolean isCheck(QSSQSwitchBtn mMSwitchBtn, boolean check) {
        mMSwitchBtn.isCheck = check;
        return check;
    }

    static /* synthetic */ CompoundButton.OnCheckedChangeListener getCallBack(QSSQSwitchBtn mMSwitchBtn) {
        CompoundButton.OnCheckedChangeListener onCheckedChangeListenerVar = mMSwitchBtn.onCheckedChangeListener;
        return onCheckedChangeListenerVar;
    }

    /**
     * 鎖住
     *
     * @param mMSwitchBtn
     * @return
     */
    static /* synthetic */ boolean cancelCheckLock(QSSQSwitchBtn mMSwitchBtn) {
        mMSwitchBtn.doJudgeIngLock = false;
        return false;
    }

    static /* synthetic */ RectF getRecfF(QSSQSwitchBtn mMSwitchBtn) {
        RectF rectF = mMSwitchBtn.blockRectF;
        return rectF;
    }

    static /* synthetic */ void requestLayoutFix(QSSQSwitchBtn mMSwitchBtn) {
        mMSwitchBtn.widthLeftjisuan();
    }



    public final void setChecked(boolean check) {
        if (this.isCheck != check) {
            clearAnimation();
            this.isCheck = check;
            sameValueWidth();
            this.doJudgeIngLock = false;
            invalidate();
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int down) {
        this.currentWidth = right - left;
        this.currentHeight = down - top;
        this.maxWidth = (this.currentWidth - ((int) (this.innerBtnWidth * 2.0f))) - (this.innerPadding * 2);
        this.halfWidth = this.maxWidth / 2;
        this.DEFAULT_VALUE = innerBtnHeight;
        if (this.DEFAULT_VALUE < this.currentHeight) {
            this.recfF1.top = (float) ((this.currentHeight - this.DEFAULT_VALUE) / 2);
            this.recfF1.bottom = this.recfF1.top + ((float) this.DEFAULT_VALUE);
        } else {
            this.recfF1.top = 0.0f;
            this.recfF1.bottom = (float) this.currentHeight;
        }
        this.recfF1.left = 0.0f;
        this.recfF1.right = (float) this.currentWidth;
        sameValueWidth();
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(this.mInnerUncheckBgColor);
    }

    private void sameValueWidth() {
        if (this.DEFAULT_VALUE < this.currentHeight) {
            this.blockRectF.top = (float) (((this.currentHeight - this.DEFAULT_VALUE) / 2) + this.innerPadding);
            this.blockRectF.bottom = (this.blockRectF.top + ((float) this.DEFAULT_VALUE)) - ((float) (this.innerPadding * 2));
        } else {
            this.blockRectF.top = (float) this.innerPadding;
            this.blockRectF.bottom = (float) (this.currentHeight - this.innerPadding);
        }
        if (this.isCheck) {
            this.blockRectF.left = (float) (this.maxWidth + this.innerPadding);
            this.blockRectF.right = (float) (this.currentWidth - this.innerPadding);
            return;
        }
        this.blockRectF.left = (float) this.innerPadding;
        this.blockRectF.right = (float) (((int) (this.innerBtnWidth * 2.0f)) + this.innerPadding);
    }

    private void widthLeftjisuan() {
        if (this.blockRectF.left < ((float) this.innerPadding)) {
            this.blockRectF.left = (float) this.innerPadding;
        }
        if (this.blockRectF.left > ((float) (this.maxWidth + this.innerPadding))) {
            this.blockRectF.left = (float) (this.maxWidth + this.innerPadding);
        }
        this.blockRectF.right = this.blockRectF.left + ((float) ((int) (this.innerBtnWidth * 2.0f)));
    }


    public void setCheckedInner(boolean check) {
        CheckBox checkBox = null;
        this.doJudgeIngLock = true;
        this.myAnim.reset();
        if (check) {
            this.myAnim.uHR = (((long) this.maxWidth) - ((long) this.blockRectF.left)) + ((long) this.innerPadding);
            this.myAnim.direction = 1;
        } else {
            this.myAnim.uHR = (long) this.blockRectF.left;
            this.myAnim.direction = 0;
        }
        this.myAnim.uHQ = this.blockRectF.left;
        this.myAnim.setDuration((((long) this.uHE) * this.myAnim.uHR) / ((long) this.maxWidth));
        startAnimation(this.myAnim);
    }

    private void requestInceptEvent(boolean z) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    private void resetJudgecheckOrNoCheck() {
        //GMTracei(13068377522176L, 97367);
        if (this.blockRectF.left > ((float) this.halfWidth)) {
            setCheckedInner(true);
            return;
        }
        setCheckedInner(false);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean distanceChange = false;
        if (this.doJudgeIngLock) {
        } else {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    clearAnimation();
                    this.downX = motionEvent.getX();
                    this.downY = motionEvent.getY();
                    this.downTime = SystemClock.elapsedRealtime();
                    this.changeing = false;
                    break;

                case MotionEvent.ACTION_MOVE:
                    float XDistance;
                    if (this.changeing) {
                        requestInceptEvent(true);
                        XDistance = motionEvent.getX() - this.downX;
                        RectF rectF = this.blockRectF;
                        rectF.left = XDistance + rectF.left;
                        widthLeftjisuan();
                    } else {
                        float yDistance = motionEvent.getX() - this.downX;
                        XDistance = motionEvent.getY() - this.downY;
                        if (Math.abs(yDistance) >= ((float) this.scaledTouchSlop) / 10.0f) {
                            if (XDistance == 0.0f) {
                                XDistance = 1.0f;
                            }
                            if (Math.abs(yDistance / XDistance) > 3.0f) {
                                distanceChange = true;
                            }
                        }
                        if (distanceChange) {
                            this.changeing = true;
                            requestInceptEvent(true);
                        }
                    }
                    this.downX = motionEvent.getX();
                    this.downY = motionEvent.getY();
                    break;

                case MotionEvent.ACTION_UP:
                    if (SystemClock.elapsedRealtime() - this.downTime < ((long) this.minClickTime)) {//直接点开
                        setCheckedInner(!this.isCheck);
                    } else {
                        resetJudgecheckOrNoCheck();
                    }
                    requestInceptEvent(false);
                    this.changeing = false;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    if (this.changeing) {
                        resetJudgecheckOrNoCheck();
                    }
                    requestInceptEvent(false);
                    this.changeing = false;
                    break;
            }
            if (this.changeing) {
                invalidate();
            }
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        this.paint.setColor(this.mInnerUncheckBgColor);
        this.paint.setAlpha(NORMAL_MODE_ALPHA);
        canvas.drawRoundRect(this.recfF1, this.btnRadius, this.btnRadius, this.paint);
        this.paint.setColor(this.mInnercheckBgColor);
        this.paint.setAlpha(Math.min(NORMAL_MODE_ALPHA, (int) (255.0f * ((this.blockRectF.left - ((float) this.innerPadding)) / ((float) this.maxWidth)))));
        canvas.drawRoundRect(this.recfF1, this.btnRadius, this.btnRadius, this.paint);
        this.paint.setColor(this.mInnerbtnColor);
        canvas.drawRoundRect(this.blockRectF, this.innerBtnWidth, this.innerBtnWidth, this.paint);
    }
}