package com.newtonkarani98gmail.sgbv_db2.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.newtonkarani98gmail.sgbv_db2.R;

public class CasesViewHolder extends RecyclerView.ViewHolder {
   public TextView time,category,gender,location,contact;
   public  Button action;
   public AppCompatButton map;
    public CasesViewHolder(@NonNull View itemView) {
        super(itemView);
        time=(TextView)itemView.findViewById(R.id.time);
        gender=(TextView)itemView.findViewById(R.id.c_gender);
        category=(TextView)itemView.findViewById(R.id.category);
        location=(TextView)itemView.findViewById(R.id.location);
        action=(Button)itemView.findViewById(R.id.action);
        map=(AppCompatButton)itemView.findViewById(R.id.action_map);
        contact=(TextView)itemView.findViewById(R.id.contact);

    }
}
