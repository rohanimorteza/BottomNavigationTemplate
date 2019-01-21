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

import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Term;
import com.example.mortrza.mybottomnavigationtemplate.R;

import java.util.List;

import static com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS.DefFrgCRS.SelectedTerm;
import static com.example.mortrza.mybottomnavigationtemplate.MainActivity.Flag_called_from_crsfrg;

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
    public void onBindViewHolder(@NonNull TermViewHolder holder, final int position) {
        holder.name.setText(termList.get(position).getName());
        holder.year.setText(termList.get(position).getYear());

        final Typeface tf_yekan = Typeface.createFromAsset(context.getAssets(),"byekan.ttf");
        holder.year.setTypeface(tf_yekan);


        holder.crd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Flag_called_from_crsfrg.equals("1")){
                    SelectedTerm(termList.get(position).getId(),termList.get(position).getName(),termList.get(position).getYear());
                }else {
                    Toast.makeText(context,"عملیات را انتخاب کنید",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }

    public class TermViewHolder extends RecyclerView.ViewHolder{

        TextView name,year;
        CardView crd;


        public TermViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_listtrm_name);
            year = itemView.findViewById(R.id.txt_listtrm_year);

            crd = itemView.findViewById(R.id.crd_list_trm);
        }
    }
}
