package com.example.mortrza.mybottomnavigationtemplate.ADAPTERS;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Education;
import com.example.mortrza.mybottomnavigationtemplate.R;

import java.util.List;

public class EduAdapter extends RecyclerView.Adapter<EduAdapter.EduViewholder> {

    private Context context;
    private List<Education> educationList;

    public EduAdapter (Context context, List<Education> educationList){
        this.context = context;
        this.educationList = educationList;
    }

    @NonNull
    @Override
    public EduViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_edu,parent,false);

        return new EduViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EduViewholder holder, int position) {

        holder.name.setText(educationList.get(position).getName_edu());

    }

    @Override
    public int getItemCount() {
        return educationList.size();
    }

    public class EduViewholder extends RecyclerView.ViewHolder {

        CardView card;
        TextView name;

        public EduViewholder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_listedu_title);

            card = itemView.findViewById(R.id.crd_listedu);
        }
    }
}
