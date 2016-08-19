package com.axaet.wifi;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.axaet.wifi.util.Conversion;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	private TabHost tabHost;
	private static Socket socket = null;
	private MyThread thread;

	@SuppressLint("HandlerLeak")
	public Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x11) {
				Bundle bundle = msg.getData();
				Toast.makeText(getApplicationContext(), bundle.getString("msg"), Toast.LENGTH_LONG).show();
			} else if (msg.what == 0x12) {
				byte[] bs = (byte[]) msg.obj;
				Toast.makeText(getApplicationContext(), "server:" + Conversion.bytesToHexString(bs), Toast.LENGTH_LONG)
						.show();
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		tabHost = this.getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(this, GPIOActivity.class);
		spec = tabHost.newTabSpec("test1").setIndicator("test1").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, ColorPickActivity.class);
		spec = tabHost.newTabSpec("test2").setIndicator("test2").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, ScanWifiActivity.class);
		spec = tabHost.newTabSpec("test3").setIndicator("test3").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, SetWifiActivity.class);
		spec = tabHost.newTabSpec("test4").setIndicator("test4").setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(1);

		RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.main_tab_group);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.main_tab_addExam:
					tabHost.setCurrentTabByTag("test1");
					break;
				case R.id.main_tab_myExam:
					tabHost.setCurrentTabByTag("test2");
					break;
				case R.id.main_tab_message:
					tabHost.setCurrentTabByTag("test3");
					break;
				case R.id.main_tab_settings:
					tabHost.setCurrentTabByTag("test4");
					break;
				}
			}
		});
		String ip = getIntent().getStringExtra("ip");
		int port = getIntent().getIntExtra("port", 0);
		thread = new MyThread(ip, port);
		thread.start();
	}

	class MyThread extends Thread {
		public String address;
		private int port;

		public MyThread(String address, int port) {
			this.address = address;
			this.port = port;
		}

		private byte[] bytes;

		@Override
		public void run() {
			// 定义消息
			Message msg = new Message();
			msg.what = 0x11;
			Bundle bundle = new Bundle();
			bundle.clear();
			try {
				// 连接服务器 并设置连接超时为5秒
				socket = new Socket();
				socket.connect(new InetSocketAddress(address, port), 5000);
				InputStream inputstream = socket.getInputStream();
				byte[] serverSay = new byte[100];// 读取<1KB
				int len;
				while ((len = inputstream.read(serverSay)) != 0) {
					bytes = new byte[len];
					System.arraycopy(serverSay, 0, bytes, 0, len);
					Message message = myHandler.obtainMessage();
					message.what = 0x12;
					message.obj = bytes;
					message.sendToTarget();
				}
			} catch (SocketTimeoutException aa) {
				// 连接超时 在UI界面显示消息
				bundle.putString("msg", "Connection failed! Please check whether the network connection is correct.");
				msg.setData(bundle);
				// 发送消息 修改UI线程中的组件
				myHandler.sendMessage(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void Socket_Close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void Socket_Send(byte dat[]) {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			for (byte b : dat) {
				out.write(b);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Socket_Close();
		if (thread.isAlive()) {
			thread.interrupt();
			thread = null;
		}
	}
}