package com.example.mortrza.mybottomnavigationtemplate.ADAPTERS;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mortrza.mybottomnavigationtemplate.DetailActivity;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Student;
import com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS.FragmentStudentDetail;
import com.example.mortrza.mybottomnavigationtemplate.R;

import java.util.List;

public class STDAdapter extends RecyclerView.Adapter<STDAdapter.STDViewHolder> {

    private Context context;
    private List<Student> studentList;

    public STDAdapter(Context context, List<Student> studentList){
        this.context = context;

        this.studentList = studentList;
    }

    @NonNull
    @Override
    public STDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_std,parent,false);

        return new STDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull STDViewHolder holder, final int position) {

        holder.name.setText(studentList.get(position).getName());


        byte[] p = studentList.get(position).getImg();
        Bitmap bm = BitmapFactory.decodeByteArray(p,0,p.length);
        holder.img.setImageBitmap(bm);

        holder.crd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FragmentStudentDetail fragmentStudentDetail = new FragmentStudentDetail();
                Bundle b = new Bundle();
                b.putString("ID",studentList.get(position).getId());
                fragmentStudentDetail.setArguments(b);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentStudentDetail).addToBackStack(null).commit();


/*
                Intent i = new Intent(context,DetailActivity.class);
                i.putExtra("ID",studentList.get(position).getId());
                context.startActivity(i);
*/
            }
        });



    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class STDViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView img;
        CardView crd;

        public STDViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_liststd_name);
            img = itemView.findViewById(R.id.img_liststd);
            crd = itemView.findViewById(R.id.crd_liststd);

        }
    }

}
