package com.axaet.wifi;

import com.axaet.wifi.util.Conversion;
import com.axaet.wifi.util.WifiListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ScanWifiActivity extends Activity implements OnClickListener {

	private Button btnScanWifi;
	private ListView listView;
	private WifiAdmin mWifiAdmin;
	private WifiListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_wifi);
		btnScanWifi = (Button) findViewById(R.id.btn_scan_wifi);
		btnScanWifi.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.listView);
		adapter = new WifiListAdapter(this);
		listView.setAdapter(adapter);
		mWifiAdmin = new WifiAdmin(ScanWifiActivity.this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ScanResult result = adapter.getItem(arg2);
				verifyPassword(result.SSID);
			}
		});
	}

	@SuppressLint("InflateParams")
	private void verifyPassword(final String wifiName) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.dialog_pass, null);
		final EditText editText = (EditText) view.findViewById(R.id.edit_pass);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(view);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String wifiPass = editText.getText().toString();
				if (TextUtils.isEmpty(wifiPass)) {
					Toast.makeText(getApplicationContext(), "Please enter the WiFi password", Toast.LENGTH_SHORT)
							.show();
					return;
				} else {
					byte[] bs = Conversion.wifiToByte(wifiName, wifiPass);
					MainActivity.Socket_Send(bs);
				}

			}
		});
		builder.setNegativeButton("Cancel", null);
		builder.show();
	}

	@Override
	public void onClick(View arg0) {
		mWifiAdmin.startScan();
		adapter.addScanResult(mWifiAdmin.getWifiList());
	}
}
