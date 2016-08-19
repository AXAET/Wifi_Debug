package com.axaet.wifi.util;

import java.util.ArrayList;
import java.util.List;

import com.axaet.wifi.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WifiListAdapter extends BaseAdapter {

	private ArrayList<ScanResult> scanResults;
	private LayoutInflater mInflator;

	public WifiListAdapter(Activity context) {
		super();
		scanResults = new ArrayList<ScanResult>();
		this.mInflator = context.getLayoutInflater();
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (view == null) {
			view = mInflator.inflate(R.layout.item_wifi, null);
			viewHolder = new ViewHolder();
			viewHolder.wifiName = (TextView) view.findViewById(R.id.text_wifi_name);
			viewHolder.wifiRssi = (TextView) view.findViewById(R.id.text_wifi_rssi);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		ScanResult result = scanResults.get(position);
		viewHolder.wifiName.setText(result.SSID);
		viewHolder.wifiRssi.setText(String.valueOf(result.level));
		return view;
	}

	public void addScanResult(List<ScanResult> scanResults) {
		this.scanResults.addAll(scanResults);
		notifyDataSetChanged();
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public ScanResult getItem(int arg0) {
		return scanResults.get(arg0);
	}

	@Override
	public int getCount() {
		return scanResults.size();
	}

	static class ViewHolder {
		TextView wifiName;
		TextView wifiRssi;
	}
};