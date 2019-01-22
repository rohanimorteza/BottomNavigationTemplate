package com.example.mortrza.mybottomnavigationtemplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;


import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Course;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Education;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Student;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Teacher;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Term;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class dbHandler extends SQLiteOpenHelper {

    private static String DBNAME = "student.db";
    private static String DBPATH;
    public static String TBL_UNIT="tbl_units";
    public static String TBL_TRM="tbl_term";
    public static String TBL_TCH="tbl_teacher";
    public static String TBL_STD="tbl_student";
    public static String TBL_CRS="tbl_course";
    public static String TBL_EDU="tbl_education";
    public static String TBL_STD_J_CRS="tbl_std_join_crs";
    Context cntx;
    SQLiteDatabase db;

    public dbHandler(@Nullable Context context) {
        super(context, DBNAME, null, 1);
        cntx = context;
        DBPATH = context.getCacheDir().getPath()+"/"+DBNAME;
    }

    public void open(){

        if(DbExist()){
            try{
                File f = new File(DBPATH);
                db = SQLiteDatabase.openDatabase(DBPATH,null,SQLiteDatabase.OPEN_READWRITE);

            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            if (CopyDb()){
                open();
            }
        }
    }

    public boolean DbExist(){
        File f = new File(DBPATH);
        if (f.exists()){
            return true;
        }else {
            return false;
        }
    }

    public boolean CopyDb(){

        try {

            FileOutputStream out = new FileOutputStream(DBPATH);
            InputStream in = cntx.getAssets().open(DBNAME);

            byte[] buffer = new byte[1024];
            int ch;

            while ((ch=in.read(buffer))>0){
                out.write(buffer,0,ch);
            }

            out.flush();
            out.close();
            in.close();

            return true;
        }catch (Exception e){

            return false;
        }
    }

    public String displayEducation(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_education where id_education="+id,null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }
    public void insertStudent(String name ,  String education , byte[] pic){
        ContentValues cv = new ContentValues();
        cv.put("name_student",name);
        cv.put("education",education);
        cv.put("pic",pic);


        db.insert(TBL_STD,"name_student",cv);

    }

    public String displayTeacher(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM "+TBL_TCH+" where id="+id,null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }


    public List<Education> displayEducation(){
        Cursor cursor = db.rawQuery("SELECT * FROM  tbl_education ",null);
        cursor.moveToFirst();
        List<Education> EduList = new ArrayList<>();
        while (cursor.moveToNext()){
            Education edu = new Education();
            edu.setId_edu(cursor.getString(0));
            edu.setName_edu(cursor.getString(1));

            EduList.add(edu);
        }
        return EduList;
    }

    public List<Teacher> displayTeacher(){
        Cursor cursor = db.rawQuery("SELECT * FROM  "+TBL_TCH,null);
        cursor.moveToFirst();
        List<Teacher> TchList = new ArrayList<>();
        while (cursor.moveToNext()){
            Teacher tch = new Teacher();
            tch.setId(cursor.getString(0));
            tch.setName(cursor.getString(1));

            TchList.add(tch);
        }
        return TchList;
    }

    public List<Student> displayStudent(){
        Cursor cursor = db.rawQuery("SELECT * FROM  "+ TBL_STD,null);
        cursor.moveToFirst();
        List<Student> StdList = new ArrayList<>();
        do{
            Student std = new Student();
            std.setId(cursor.getString(0));
            std.setName(cursor.getString(1));
            std.setImg(cursor.getBlob(2));

            StdList.add(std);
        }while (cursor.moveToNext());
        return StdList;
    }


    public Student displayStudent(String id){


        Cursor cursor = db.rawQuery("SELECT id_student , name_student , pic , name_education FROM  "+ TBL_STD+" INNER JOIN "+ TBL_EDU +" on education = id_education where id_student="+id,null);
        cursor.moveToFirst();

            Student std = new Student();
            std.setId(cursor.getString(0));
            std.setName(cursor.getString(1));
            std.setImg(cursor.getBlob(2));
            std.setEducation(cursor.getString(3));

        return std;
    }

    public List<Course> displayCourse(){
        Cursor cursor = db.rawQuery("SELECT * FROM  "+TBL_CRS,null);
        cursor.moveToFirst();
        List<Course> CRSList = new ArrayList<>();
        do {
            Course crs = new Course();
            crs.setId(cursor.getString(0));
            crs.setCourseName(cursor.getString(1));
            crs.setCourseTuition(cursor.getString(2));

            CRSList.add(crs);
        }while (cursor.moveToNext());
        return CRSList;
    }

    public List<Term> displayTerm(){
        Cursor cursor = db.rawQuery("SELECT * FROM  "+TBL_TRM,null);
        cursor.moveToFirst();
        List<Term> TRMList = new ArrayList<>();
        do {
            Term trm = new Term();
            trm.setId(cursor.getString(0));
            trm.setName(cursor.getString(1));
            trm.setYear(cursor.getString(2));

            TRMList.add(trm);
        }while (cursor.moveToNext());
        return TRMList;
    }

    public List<Education> displayEducation(String a){
        Cursor cursor = db.rawQuery("SELECT * FROM  tbl_education where id_education<>1 and name_education like '"+a+"%'",null);
        cursor.moveToFirst();
        List<Education> EduList = new ArrayList<>();
        do {
            Education edu = new Education();
            edu.setId_edu(cursor.getString(0));
            edu.setName_edu(cursor.getString(1));

            EduList.add(edu);
        }while (cursor.moveToNext());
        return EduList;
    }

    public List<Teacher> displayTeacher(String a){
        Cursor cursor = db.rawQuery("SELECT * FROM  tbl_teacher where id<>1 and name_teacher like '"+a+"%'",null);
        cursor.moveToFirst();
        List<Teacher> TchList = new ArrayList<>();
        do {
            Teacher tch = new Teacher();
            tch.setId(cursor.getString(0));
            tch.setName(cursor.getString(1));

            TchList.add(tch);
        }while (cursor.moveToNext());
        return TchList;
    }




    public void insertEducation(String a){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name_education",a);

        db.insert("tbl_education","name_education",contentValues);
    }
    public boolean CheckDuplicateEducation(String a){
        Cursor cursor = db.rawQuery("SELECT name_education FROM  "+TBL_EDU+" where name_education like '"+a+"%'",null);
        cursor.moveToFirst();
        return cursor.isNull(0);
    }

    public boolean CheckDuplicateregisterdCrs(String idStd,String idCrs ){
        Cursor cursor = db.rawQuery("SELECT id_id_std_j_crs FROM  "+TBL_STD_J_CRS+" where id_std_std_j_crs="+idStd+" and id_crs_std_j_crs="+idCrs,null);
        cursor.moveToFirst();
        return cursor.isNull(0);

    }

    public void insertTeacher(String a){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name_teacher",a);

        db.insert(TBL_TCH,"name_teacher",contentValues);
    }

    public void insertCrsToStd(String idStd,String idCrs){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_std_std_j_crs",idStd);
        contentValues.put("id_crs_std_j_crs",idCrs);

        db.insert(TBL_STD_J_CRS,"namid_std_std_j_crse_teacher",contentValues);
    }

    public void insertCrs(String Name , String Tuition , String tchID,String termid){
        ContentValues contentValues = new ContentValues();
        contentValues.put("nameCrs",Name);
        contentValues.put("crsFee",Tuition);
        contentValues.put("id_teacher",tchID);
        contentValues.put("id_crsterm",termid);

        db.insert(TBL_CRS,"nameCrs",contentValues);
    }

    public void insertTrm(String Name , String Year){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name_term",Name);
        contentValues.put("year_term",Year);

        db.insert(TBL_TRM,"name_term",contentValues);
    }


    public int displayRowCount(String Table){
        Cursor cursor = db.rawQuery("SELECT * FROM  "+ Table,null);
        cursor.moveToFirst();
        return cursor.getCount();
    }


    public int DisplayEduCount(String a){
        Cursor cursor = db.rawQuery("SELECT * FROM  tbl_education where  id_education<>1 and  name_education like '"+a+"%'",null);
        cursor.moveToFirst();
        return cursor.getCount();
    }

    public int DisplayTchCount(String a){
        Cursor cursor = db.rawQuery("SELECT * FROM  tbl_teacher where  id<>1 and  name_teacher like '"+a+"%'",null);
        cursor.moveToFirst();
        return cursor.getCount();
    }


    @Override
    public synchronized void close() {
        super.close();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
