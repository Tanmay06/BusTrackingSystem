package com.example.tanmayvakare.bustrackingsystem;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import static android.R.attr.start;

/**
 * Created by tanmayvakare on 12/06/17.
 */

class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.ItemHolder>{

    Context context;
    ArrayList<BusDetails> dataList;

    public BusListAdapter(Context context, ArrayList<BusDetails> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public BusListAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_card,parent,false);
        return new ItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(BusListAdapter.ItemHolder holder, final int position) {

        holder.busno.setText(dataList.get(position).getNo());
        holder.source.setText(dataList.get(position).getSource());
        holder.dest.setText(dataList.get(position).getDest());
        holder.loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent track = new Intent(context,MapsActivity.class);
                track.putExtra("BusNo",dataList.get(position).getNo());
                track.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(track);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        TextView busno;
        TextView source;
        TextView dest;
        ImageView loc;
        public ItemHolder(View itemView) {
            super(itemView);

            busno = (TextView) itemView.findViewById(R.id.bus_no);
            source = (TextView) itemView.findViewById(R.id.source);
            dest = (TextView) itemView.findViewById(R.id.dest);
            loc = (ImageView) itemView.findViewById(R.id.locate);
        }
    }
}
