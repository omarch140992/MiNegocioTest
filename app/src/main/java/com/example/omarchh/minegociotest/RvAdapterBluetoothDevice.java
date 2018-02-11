package com.example.omarchh.minegociotest;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 28/12/2017.
 */

public class RvAdapterBluetoothDevice extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<BluetoothDevice> list;
    DeviceListener deviceListener;

    public RvAdapterBluetoothDevice() {
        list = new ArrayList<>();
    }

    public void setDeviceListener(DeviceListener deviceListener) {
        this.deviceListener = deviceListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_device, parent, false);
        return new DeviceSyncViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DeviceSyncViewHolder deviceSyncViewHolder = (DeviceSyncViewHolder) holder;
        deviceSyncViewHolder.txtDevice.setText(list.get(position).getName() + "||" + list.get(position).getAddress());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void Add(List<BluetoothDevice> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface DeviceListener {

        public void getDeviceBluetooth(BluetoothDevice device);
    }

    public class DeviceSyncViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtDevice;
        LinearLayout ll;

        public DeviceSyncViewHolder(View itemView) {
            super(itemView);
            txtDevice = (TextView) itemView.findViewById(R.id.txtDeviceBt);
            ll = (LinearLayout) itemView.findViewById(R.id.llItemDevice);

            ll.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.llItemDevice) {

                deviceListener.getDeviceBluetooth(list.get(getAdapterPosition()));
            }

        }
    }
}
