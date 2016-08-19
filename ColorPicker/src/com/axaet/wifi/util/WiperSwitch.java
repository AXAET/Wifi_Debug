package com.axaet.wifi.util;

import com.axaet.wifi.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class WiperSwitch extends View implements OnTouchListener {
	private Bitmap bg_on, bg_off, slipper_btn;
	private float downX, nowX;
	private boolean onSlip = false;
	private boolean nowStatus = false;
	private OnChangedListener listener;
	private Matrix matrix;
	private Paint paint;

	public WiperSwitch(Context context) {
		super(context);
		init();
	}

	public WiperSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@SuppressLint("ClickableViewAccessibility")
	public void init() {
		matrix = new Matrix();
		paint = new Paint();
		bg_on = BitmapFactory.decodeResource(getResources(), R.drawable.on_btn);
		bg_off = BitmapFactory.decodeResource(getResources(), R.drawable.off_btn);
		slipper_btn = BitmapFactory.decodeResource(getResources(), R.drawable.white_btn);
		setOnTouchListener(this);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		float x = 0;
		if (nowX < (bg_on.getWidth() / 2)) {
			canvas.drawBitmap(bg_off, matrix, paint);
		} else {
			canvas.drawBitmap(bg_on, matrix, paint);
		}

		if (onSlip) {
			if (nowX >= bg_on.getWidth())
				x = bg_on.getWidth() - slipper_btn.getWidth() / 2;
			else
				x = nowX - slipper_btn.getWidth() / 2;
		} else {
			if (nowStatus) {
				x = bg_on.getWidth() - slipper_btn.getWidth();
			} else {
				x = 0;
			}
		}

		if (x < 0) {
			x = 0;
		} else if (x > bg_on.getWidth() - slipper_btn.getWidth()) {
			x = bg_on.getWidth() - slipper_btn.getWidth();
		}

		canvas.drawBitmap(slipper_btn, x, 0, paint);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			if (event.getX() > bg_off.getWidth() || event.getY() > bg_off.getHeight()) {
				return false;
			} else {
				onSlip = true;
				downX = event.getX();
				nowX = downX;
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			nowX = event.getX();
			break;
		}
		case MotionEvent.ACTION_UP: {
			onSlip = false;
			if (event.getX() >= (bg_on.getWidth() / 2)) {
				nowStatus = true;
				nowX = bg_on.getWidth() - slipper_btn.getWidth();
			} else {
				nowStatus = false;
				nowX = 0;
			}

			if (listener != null) {
				listener.OnChanged(WiperSwitch.this, nowStatus);
			}
			break;
		}
		}
		invalidate();
		return true;
	}

	public void setOnChangedListener(OnChangedListener listener) {
		this.listener = listener;
	}

	public void setChecked(boolean checked) {
		if (checked) {
			nowX = bg_off.getWidth();
		} else {
			nowX = 0;
		}
		nowStatus = checked;
	}

	public interface OnChangedListener {
		public void OnChanged(WiperSwitch wiperSwitch, boolean checkState);
	}

}