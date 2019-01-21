package com.example.mortrza.mybottomnavigationtemplate.ADAPTERS;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Course;
import com.example.mortrza.mybottomnavigationtemplate.R;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS.FragmentStudentDetail.SelectCrs;
import static com.example.mortrza.mybottomnavigationtemplate.MainActivity.Flag_called_from_stdDetail_frg;

public class CrsAdapter extends RecyclerView.Adapter<CrsAdapter.CRSViewHolder> {

    private Context context;
    private List<Course> CRSlist;
    static DecimalFormat formatter;

    public CrsAdapter(Context context, List<Course> courseList){
        this.context = context;
        this.CRSlist = courseList;
    }

    @NonNull
    @Override
    public CRSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_crs,parent,false);

        return new CRSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CRSViewHolder holder, final int position) {

        holder.name.setText(CRSlist.get(position).getCourseName());
        //holder.tuition.setText(formatter.format(Integer.parseInt(CRSlist.get(position).getCourseTuition())));

        formatter = new DecimalFormat("#,###,###,###");

        holder.tuition.setText(formatter.format(Integer.parseInt(CRSlist.get(position).getCourseTuition())));
        final Typeface tf_yekan = Typeface.createFromAsset(context.getAssets(),"byekan.ttf");
        holder.tuition.setTypeface(tf_yekan);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Flag_called_from_stdDetail_frg.equals("-")){
                    SelectCrs(CRSlist.get(position).getId());
                }else{
                    Toast.makeText(context,"called from def",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return CRSlist.size();
    }

    public  class CRSViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        TextView name,tuition;

        public CRSViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_listcrs_name);
            tuition = itemView.findViewById(R.id.txt_listcrs_tuition);
            card = itemView.findViewById(R.id.crd_listedu);


        }
    }
}
