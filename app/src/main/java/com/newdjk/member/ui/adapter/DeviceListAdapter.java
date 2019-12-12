package com.newdjk.member.ui.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.member.R;

import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.MyViewHolder> {

    private Context mContext;
    private List<BluetoothDevice> mLists;

    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public DeviceListAdapter(Context context, List lists) {
        this.mContext = context;
        this.mLists = lists;
    }

    public void addDevice(BluetoothDevice device) {
        if(!mLists.contains(device)) {
            mLists.add(device);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.device_list_item_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        holder.device_address_tv.setText(mLists.get(i).getName());
        holder.device_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(holder.itemView, pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView device_address_tv;
        private RelativeLayout device_rl;
        public MyViewHolder(View itemView) {
            super(itemView);
            device_address_tv = (TextView) itemView.findViewById(R.id.device_address_tv);
            device_rl = (RelativeLayout) itemView.findViewById(R.id.device_rl);
        }
    }
}
