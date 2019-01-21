package com.example.mortrza.mybottomnavigationtemplate.ADAPTERS;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Teacher;
import com.example.mortrza.mybottomnavigationtemplate.R;

import java.util.List;

public class TchAdapter extends RecyclerView.Adapter<TchAdapter.TchViewholder> {

    private Context context;
    private List<Teacher> teacherList;

    public TchAdapter(Context context, List<Teacher> teacherList){
        this.context = context;
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public TchViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_edu,parent,false);

        return new TchViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TchViewholder holder, int position) {

        holder.name.setText(teacherList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public class TchViewholder extends RecyclerView.ViewHolder {

        CardView card;
        TextView name;

        public TchViewholder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_listedu_title);

            card = itemView.findViewById(R.id.crd_listedu);
        }
    }
}
