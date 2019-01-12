package com.example.mortrza.mybottomnavigationtemplate.ADAPTERS;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Term;
import com.example.mortrza.mybottomnavigationtemplate.R;

import java.util.List;

public class TrmAdapter extends RecyclerView.Adapter<TrmAdapter.TermViewHolder>{

    private Context context;
    private List<Term> termList;

    public TrmAdapter(Context context, List<Term> termList){

        this.context = context;
        this.termList = termList;
    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_trm,parent,false);
        return new TermViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        holder.name.setText(termList.get(position).getName());
        holder.year.setText(termList.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }

    public class TermViewHolder extends RecyclerView.ViewHolder{

        TextView name,year;


        public TermViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_listtrm_name);
            year = itemView.findViewById(R.id.txt_listtrm_year);
        }
    }
}
