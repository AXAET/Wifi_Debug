package com.axaet.wifi;

import com.axaet.wifi.util.Conversion;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetWifiActivity extends Activity implements OnClickListener {
	private EditText editIp;
	private EditText editPort;
	private Button btnSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_wifi);
		editIp = (EditText) findViewById(R.id.edit_ip);
		editPort = (EditText) findViewById(R.id.edit_port);
		btnSend = (Button) findViewById(R.id.btn_send);
		btnSend.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		String ipString = editIp.getText().toString();
		String portString = editPort.getText().toString();
		if (TextUtils.isEmpty(ipString) || TextUtils.isEmpty(portString)) {
			Toast.makeText(this, "Please enter the IP and port", Toast.LENGTH_SHORT).show();
			return;
		}
		int port = Integer.parseInt(portString);
		// MainActivity.Socket_Send(dat);
	}

}
