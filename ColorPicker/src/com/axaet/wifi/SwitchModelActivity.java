package com.axaet.wifi;

import com.axaet.wifi.util.Conversion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SwitchModelActivity extends Activity implements OnClickListener {
	private Button btnServer, btnWifi;
	private EditText editIp;
	private EditText editPort;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_switch_model);

		btnServer = (Button) findViewById(R.id.btn_server);
		btnWifi = (Button) findViewById(R.id.btn_wifi);
		editIp = (EditText) findViewById(R.id.edit_ip);
		editPort = (EditText) findViewById(R.id.edit_port);
		btnServer.setOnClickListener(this);
		btnWifi.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent();
		intent.setClass(SwitchModelActivity.this, MainActivity.class);
		if (view == btnServer) {
			String ipString = editIp.getText().toString();
			String portString = editPort.getText().toString();
			if (TextUtils.isEmpty(ipString) || TextUtils.isEmpty(portString)) {
				Toast.makeText(this, "Please enter the IP and port", Toast.LENGTH_SHORT).show();
				return;
			}
			int port = Integer.parseInt(portString);
			intent.putExtra("ip", ipString);
			intent.putExtra("port", port);
		} else {
			intent.putExtra("ip", "192.168.4.1");
			intent.putExtra("port", 8266);
		}
		startActivity(intent);
	}
}
