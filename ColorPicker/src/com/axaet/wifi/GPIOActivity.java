package com.axaet.wifi;

import com.axaet.wifi.util.WiperSwitch;
import com.axaet.wifi.util.WiperSwitch.OnChangedListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

@SuppressLint("NewApi")
public class GPIOActivity extends Activity implements OnChangedListener {
	private WiperSwitch GPIO0, GPIO2, GPIO4, GPIO5, GPIO12, GPIO13, GPIO14, GPIO15, GPIO16;
	public byte GPIO_Value[] = new byte[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.drawable.bg_wiperswitch);
		
		GPIO0 = (WiperSwitch) findViewById(R.id.GPIO0);
		GPIO2 = (WiperSwitch) findViewById(R.id.GPIO2);
		GPIO4 = (WiperSwitch) findViewById(R.id.GPIO4);
		GPIO5 = (WiperSwitch) findViewById(R.id.GPIO5);
		GPIO12 = (WiperSwitch) findViewById(R.id.GPIO12);
		GPIO13 = (WiperSwitch) findViewById(R.id.GPIO13);
		GPIO14 = (WiperSwitch) findViewById(R.id.GPIO14);
		GPIO15 = (WiperSwitch) findViewById(R.id.GPIO15);
		GPIO16 = (WiperSwitch) findViewById(R.id.GPIO16);
		
		GPIO0.setChecked(false);
		GPIO2.setChecked(false);
		GPIO4.setChecked(false);
		GPIO5.setChecked(false);
		GPIO12.setChecked(false);
		GPIO13.setChecked(false);
		GPIO14.setChecked(false);
		GPIO15.setChecked(false);
		GPIO16.setChecked(false);

		GPIO0.setOnChangedListener(this);
		GPIO2.setOnChangedListener(this);
		GPIO4.setOnChangedListener(this);
		GPIO5.setOnChangedListener(this);
		GPIO12.setOnChangedListener(this);
		GPIO13.setOnChangedListener(this);
		GPIO14.setOnChangedListener(this);
		GPIO15.setOnChangedListener(this);
		GPIO16.setOnChangedListener(this);

		GPIO_Value[0] = 0x21;
		GPIO_Value[1] = 0x00;
		GPIO_Value[2] = 0x00;

	}

	@Override
	public void OnChanged(WiperSwitch wiperSwitch, boolean checkState) {
		switch (wiperSwitch.getId()) {
		case R.id.GPIO0: {
			if (checkState) {
				GPIO_Value[1] |= (1 << 0);
			} else if (checkState) {
				GPIO_Value[1] &= ~(1 << 0);
			}
		}
			break;

		case R.id.GPIO2: {
			if (checkState) {
				GPIO_Value[1] |= (1 << 1);
			} else if (checkState) {
				GPIO_Value[1] &= ~(1 << 1);
			}
		}
			break;

		case R.id.GPIO4: {
			if (checkState) {
				GPIO_Value[1] |= (1 << 2);
			} else if (checkState) {
				GPIO_Value[1] &= ~(1 << 2);
			}
		}
			break;

		case R.id.GPIO5: {
			if (checkState) {
				GPIO_Value[1] |= (1 << 3);
			} else if (checkState) {
				GPIO_Value[1] &= ~(1 << 3);
			}
		}
			break;

		case R.id.GPIO12: {
			if (checkState) {
				GPIO_Value[1] |= (1 << 4);
			} else if (checkState) {
				GPIO_Value[1] &= ~(1 << 4);
			}
		}
			break;

		case R.id.GPIO13: {
			if (checkState) {
				GPIO_Value[1] |= (1 << 5);
			} else if (checkState) {
				GPIO_Value[1] &= ~(1 << 5);
			}

		}
			break;

		case R.id.GPIO14: {
			if (checkState) {
				GPIO_Value[1] |= (1 << 6);
			} else if (checkState) {
				GPIO_Value[1] &= ~(1 << 6);
			}
		}
			break;

		case R.id.GPIO15: {
			if (checkState) {
				GPIO_Value[2] |= (1 << 0);
			} else if (checkState) {
				GPIO_Value[2] &= ~(1 << 0);
			}
		}
			break;

		case R.id.GPIO16: {
			if (checkState) {
				GPIO_Value[2] |= (1 << 1);
			} else if (checkState) {
				GPIO_Value[2] &= ~(1 << 1);
			}
		}
			break;

		default:
			break;
		}
		System.out.println(GPIO_Value[0]);
		System.out.println(GPIO_Value[1]);
		System.out.println(GPIO_Value[2]);
		MainActivity.Socket_Send(GPIO_Value);
	}
}
