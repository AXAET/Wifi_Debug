package com.axaet.wifi;

import com.axaet.wifi.util.ColorPickView;
import com.axaet.wifi.util.ColorPickView.OnColorChangedListener;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ColorPickActivity extends Activity {
	private TextView txtColor;
	private ColorPickView myView;
	private SeekBar seekBar1;
	private byte PWM[] = new byte[10];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PWM[0] = 0x22;
		setContentView(R.layout.activity_colorpick);
		myView = (ColorPickView) findViewById(R.id.color_picker_view);
		txtColor = (TextView) findViewById(R.id.txt_color);
		seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				PWM[9] = (byte) arg1;
				MainActivity.Socket_Send(PWM);
			}
		});
		myView.setOnColorChangedListener(new OnColorChangedListener() {

			@Override
			public void onColorChange(int color) {
				txtColor.setTextColor(color);
				int red = Color.red(color);
				int green = Color.green(color);
				int blue = Color.blue(color);

				
				PWM[1] = (byte) (red >> 4);
				PWM[2] = (byte) (red & 0x0f);
				PWM[3] = (byte) (green >> 4);
				PWM[4] = (byte) (green & 0x0f);
				PWM[5] = (byte) (blue >> 4);
				PWM[6] = (byte) (blue & 0x0f);
				MainActivity.Socket_Send(PWM);
			}
		});
	}
}
