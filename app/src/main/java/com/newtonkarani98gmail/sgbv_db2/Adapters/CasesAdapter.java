package com.newtonkarani98gmail.sgbv_db2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.newtonkarani98gmail.sgbv_db2.DetailsActivity;
import com.newtonkarani98gmail.sgbv_db2.R;
import com.newtonkarani98gmail.sgbv_db2.ViewHolder.CasesViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CasesAdapter extends RecyclerView.Adapter<CasesViewHolder> {
    JSONArray cases=new JSONArray();

    public  CasesAdapter(JSONArray cases){
        this.cases=cases;
    }
    Context context;
    @NonNull
    @Override
    public CasesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.case_layout,parent,false);
        return new CasesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CasesViewHolder holder, int position) {
        String gender,contact,location,category,time;
        try {
            JSONObject case_details=cases.getJSONObject(position);
            JSONArray case_a=case_details.getJSONArray("case");
            JSONObject case_o=case_a.getJSONObject(0);
            category=case_o.getString("type");
            time=case_o.getString("created_at");

            JSONArray vict_a=case_details.getJSONArray("victim");
            JSONObject victim_o=vict_a.getJSONObject(0);
            gender=victim_o.getString("gender");
            location=victim_o.getString("latitude");
            contact=victim_o.getString("contact");

            holder.contact.setText(contact);
            holder.location.setText(location);
            holder.category.setText(category);
            holder.time.setText(time);
            holder.gender.setText("INVOLVES A "+gender.toUpperCase());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return cases.length();
    }
}
