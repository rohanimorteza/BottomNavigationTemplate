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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class dbHandler extends SQLiteOpenHelper {

    private static String DBNAME = "student.db";
    private static String DBPATH;
    private static String TBL_UNIT="tbl_units";
    private static String TBL_STD="tbl_student";
    private static String TBL_CRS="tbl_course";
    private static String TBL_EDU="tbl_education";
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




    public int DisplayEduCount(String a){
        Cursor cursor = db.rawQuery("SELECT * FROM  tbl_education where  id_education<>1 and  name_education like '"+a+"%'",null);
        cursor.moveToFirst();
        return cursor.getCount();
    }


    public int DisplaySTDCount(){
        Cursor cursor = db.rawQuery("SELECT * FROM "+TBL_STD,null);
        cursor.moveToFirst();
        return cursor.getCount();
    }

    public void insertEducation(String a){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name_education",a);

        db.insert("tbl_education","name_education",contentValues);
    }

    public void insertCrs(String Name , String Tuition){
        ContentValues contentValues = new ContentValues();
        contentValues.put("nameCrs",Name);
        contentValues.put("crsFee",Tuition);

        db.insert(TBL_CRS,"nameCrs",contentValues);
    }

/*
    public List<Unit> displayUnits(String str){
        Cursor cursor = db.rawQuery("SELECT * FROM  "+TBL_UNIT+" where UnitName like '%"+str+"%'",null);
        cursor.moveToFirst();
        List<Unit> UnitList = new ArrayList<>();
        do{
            Unit unit = new Unit();
            unit.setId(cursor.getString(0));
            unit.setUnitName(cursor.getString(1));

            UnitList.add(unit);
        }while (cursor.moveToNext());
        return UnitList;
    }
*/
    public int displayEducationCount(){
        Cursor cursor = db.rawQuery("SELECT * FROM  tbl_education ",null);
        cursor.moveToFirst();

        return cursor.getCount();
    }


    public int displayCourseCount(){
        Cursor cursor = db.rawQuery("SELECT * FROM  "+ TBL_CRS,null);
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
