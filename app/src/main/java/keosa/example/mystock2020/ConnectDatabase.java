package keosa.example.mystock2020;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConnectDatabase extends SQLiteOpenHelper {
    private  static final String cateids = "cate_id"; //ວ່າງເພືອກັນບອກລະຫັດປະເພດໂດຍຕົງ
    SQLiteDatabase myDb;//ວ່າງເພືອກັນບອກລະຫັດປະເພດໂດຍຕົງ


    //Create database
    public static final String DATABASE_NAME ="strock.db";
    public static  final String TABLE_NAME1 = "category";
    public static  final String TABLE_NAME2 = "products";
    public static  final String TABLE_NAME3 = "receives";
    public static  final String TABLE_NAME4 = "sell";
    public static  final String TABLE_NAME5 = "users";




    public ConnectDatabase(@Nullable Context context) {
        super(context,DATABASE_NAME, null,1);


        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create Tables category
        db.execSQL("create table category(cate_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cate_name VARCHAR(30),remark VARCHAR(30))");

        // create table Product
        db.execSQL("create table products(proid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "proname VARCHAR(30),qty INTEGER(3),bprice DECIMAL(12,2)" +
                ",sprice DECIMAL(12,2),cate_id INTEGER,FOREIGN KEY(cate_id)REFERENCES category(cate_id))");
        // create receives table
        db.execSQL("create table receives(recid INTEGER PRIMARY KEY AUTOINCREMENT,proid INTEGER,bprice DECIMAL(12,2),bdate DATE,qty INTEGER,amount DECIMAL(12,2),FOREIGN KEY(proid)REFERENCES products(proid))");
        // create table Order (Sell)
        db.execSQL("create table sell(Orid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "proid INTEGER,sprice decimal(12,2),Ordate date,qty INTEGER,amount decimal(12,2),FOREIGN KEY (proid)REFERENCES products(proid))");
        //create table users
        db.execSQL("create table users(userid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username VARCHAR(20),password VARCHAR(10),pass VARCHAR(10),status VARCHAR(15))");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);
        onCreate(db);
    }
    //select status and user
  public Boolean Touser(String username,String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username = ? and pass = ? and status = 'Users'",new String[]{username,password});
        if (cursor.getCount()>0)return true;
        else return false;
  }
//select admin
public Boolean Touy(String username,String password){
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("select * from users where username = ? and pass = ? and status = 'Admin'",new String[]{username,password});
    if (cursor.getCount()>0)return true;
    else return false;
}






    //Update products=================================================================================
    public boolean updateproducts(String proid,String proname, Integer qty,double bprice,double sprice,Integer cate_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("proid", proid);
        contentValues.put("proname", proname);
        contentValues.put("qty", qty);
        contentValues.put("bprice", bprice);
        contentValues.put("sprice", sprice);
        contentValues.put("cate_id", cate_id);
        int updated = db.update(TABLE_NAME2, contentValues, "proid= ?", new String[]{proid});
        if (updated == 0) {
            return false;
        } else {
            return true;
        }
    }

    //ສ້າງ method ເພຶ່ອບັນທຶກ users====================================================================================================
    public  boolean insertusers(String username, String password, String pass,String status){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);
        values.put("pass",pass);
        values.put("status",status);
        long Inserted = db.insert(TABLE_NAME5,null,values);
        if (Inserted == -1){
            return false;
        }else {
            return true;
        }
    }

    //ສ້າງ method ເພຶ່ອບັນທຶກ sell====================================================================================================
    public  boolean insertsell(Integer proid, Double sprice, String Ordate, Integer qty, Double amount){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("proid",proid);
        values.put("sprice",sprice);
        values.put("Ordate",Ordate);
        values.put("qty",qty);
        values.put("amount",amount);
        long Inserted = db.insert(TABLE_NAME4,null,values);
        if (Inserted == -1){
            return false;
        }else {
            return true;
        }
    }
    //ສ້າງselectsell  "proid INTEGER,sprice decimal(12,2),Ordate date,qty INTEGER,amount decimal(12,2),FOREIGN KEY (proid)REFERENCES products(proid))");
    public Cursor selectsell(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res1 = db.rawQuery("select a.Orid,b.proid,b.proname,a.sprice,a.Ordate,a.qty,a.amount from sell  as a,products as b where a.proid = b.proid",null);
        return res1;
    }





    // Insert Table category ບັນທຶກຂໍ້ມມູນຕາຕະລາງ ປະເພດສິ້ນຄ້າ

    public boolean insertcategory(String cate_name, String remark){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cate_name", cate_name);
        contentValues.put("remark", remark);
        long result = db.insert(TABLE_NAME1, null, contentValues);
        if (result == -1)
            return false;

        else
            return true;
    }
//ສ້າງ Method ນີ້ຂື້ນມາເພຶ່ອກຳນົດໃຫ້ສາມາດເຂົ້ານຳ ພາສາ Sql ຢູ່ເບຶ່ອງຂອງ Class order
    public Cursor getDate(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return  db.rawQuery(sql,null);

    }
//ປິດ



    //select category
    //ພາກສ່ວນສະແດງຂໍ້ມູນ

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME1, null);
        return res;

    }
    //ສະແດງຕາຕະລາງ ເກັບກຳສິ້ນຄ້າ Select * from products
    //public Cursor selectPro() {
     //   SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor res = db.rawQuery("select * from " + TABLE_NAME2, null);
     //   return res;

   // }
    //select two table
    public  Cursor selectPro(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select p.proid,p.proname,p.qty,p.sprice,c.cate_name from  category  as c,products as p where c.cate_id=p.cate_id",null);
        return res;
    }

    public Cursor selectreceives(){
        SQLiteDatabase db = this.getReadableDatabase();
        // ວາງ b ເປັນຕາຕະລາງ products
        //  db.execSQL("create table receives(recid INTEGER PRIMARY KEY AUTOINCREMENT,proid INTEGER,bprice DECIMAL(12,2),bdate DATE,qty INTEGER,amount DECIMAL(12,2),FOREIGN KEY(proid)REFERENCES products(proid))");
        //ວາງ a ເປັນຕາຕະລາງ  receives
        Cursor res1 = db.rawQuery("select a.recid,a.bdate,b.proid,b.proname,a.bprice,a.qty,a.amount from receives  as a,products as b where a.proid = b.proid",null);
                return res1;
    }


    //public Cursor selectP() {
       // SQLiteDatabase db = this.getWritableDatabase();
       // Cursor resr = db.rawQuery("selecte b.proid,b.proname,a.cate_name from as a,products as b where a.cate_id=b.cate_id",null");
      //  return resr;

   // }


    //delete category ລົບຂໍ້ມູນ

    public Integer deletecategory (String cate_id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME1,"cate_id = ?",new String[]{cate_id});
    }


    //Update category=================================================================================
    public boolean updateData(String cate_id,String cate_name, String remark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cate_id", cate_id);
        contentValues.put("cate_name", cate_name);
        contentValues.put("remark", remark);

        int updated = db.update(TABLE_NAME1, contentValues, "cate_id= ?", new String[]{cate_id});
        if (updated == 0) {
            return false;
        } else {
            return true;
        }
    }

    //Insert Products ບັນທຶກຂໍ້ມູນເກັບກຳສິ້ນຄ້າ
    public boolean insertproducts(String proname,Integer qty,Double bprice, Double sprice,Integer cate_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("proname",proname);
        values.put("qty",qty);
        values.put("bprice",bprice);
        values.put("sprice",sprice);
        values.put("cate_id",cate_id);
        long Inserted = db.insert("products",null,values);
        if (Inserted == -1){
            return false;
            }else {
            return true;
        }
    }

    //ລົບ
    public Integer deleteproducs (String proid){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME2,"proid = ?",new String[]{proid});
    }
    //ສ້າງ method ເພຶ່ອບັນທຶກ Receives===============================================================================================================
    public  boolean insertreceives(Integer proid, Integer bprice, Double bdate, String qty, Double amount){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("proid",proid);
        values.put("bprice",bprice);
        values.put("bdate",bdate);
        values.put("qty",qty);
        values.put("amount",amount);
        long Inserted = db.insert("receives",null,values);
        if (Inserted == -1){
            return false;
        }else {
            return true;
        }
    }







//Greate method ພາກສ່ວນກັນບ່໋ອກລະຫັດແບບປ້ອນຕົວເລກ
    public String getcategory(long l1){
        myDb = this.getReadableDatabase();
        String[] colum = new String[] {cateids};
       Cursor cursor = myDb.query(TABLE_NAME1,colum,cateids+"="+
               l1,null,null,null,null
               );
       if(cursor!=null){
           cursor.moveToNext();
           String cate_name = cursor.getString(0);
           return cate_name;
       }
       return null;
    }
//ຮັບຂໍ້ມູນມາສະແດງໃນບ໋ອກ spinner
    public List<String> getcate(){
        List<String> list = new ArrayList<String>(  );
        String selectQuery = "select * from "+TABLE_NAME1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( selectQuery,null );
        list.add("ເລືອກລະຫັດ");
        if (cursor.moveToFirst()){
            do {
                list.add( cursor.getString( 1 ) );//ຢາກໃຫ້ມັນສະແດງລະຫັດປະເພດແມ່ນປຽນຈາກ 1 ເປັນ 0 ມັນຈະນັບອາເລແຕ່ 0
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    //ເວລາປ້ອນລະຫັດໃຫ້ດຶງເອົາຊຶ່ມາສະແດງໃນບ໋ອກຊຶ່ສິນຄ້າ
    public  String getProname(long l1){
        SQLiteDatabase db = this.getReadableDatabase();
                String[] proname = new  String[]{"proid","proname","qty","bprice","sprice","cate_id"};
                Cursor cursor = db.query("products",proname,"proid="+l1,null,null,null,null);
                if (cursor!= null){
                    cursor.moveToFirst();
                    String pro_name = cursor.getString(1);
                    return pro_name;
        }
                return null;
    }
    //ປິດ
    //ເວລາປ້ອນລະຫັດໃຫ້ດຶງເອົາຊຶ່ມາສະແດງໃນບ໋ອກຊຶ່ສິນຄ້າຂາຍສິນຄ້າອອກ
    public  String getsell(long touy1){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] proname1 = new  String[]{"proid","proname","qty","bprice","sprice","cate_id"};
        Cursor cursor = db.query("products",proname1,"proid="+touy1,null,null,null,null);
        if (cursor!= null){
            cursor.moveToFirst();
            String pro_name1 = cursor.getString(1);
            return pro_name1;
        }
        return null;
    }
    //
    public  String getsell1(long touy2){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] proname1 = new  String[]{"proid","proname","qty","bprice","sprice","cate_id"};
        Cursor cursor = db.query("products",proname1,"proid="+touy2,null,null,null,null);
        if (cursor!= null){
            cursor.moveToFirst();
            String pro_name1 = cursor.getString(4);
            return pro_name1;
        }
        return null;
    }

    //ເວລາປ້ອນລະຫັດໃຫ້ດຶງເອົາຊຶ່ມາສະແດງໃນບ໋ອກຊຶ່ສິນຄ້າສີນສຸດ





    //ເວລາປ້ອນລະຫັດໃຫ້ດຶງເອົາຊຶ່ມາສະແດງໃນບ໋ອກຊຶ່ສິນຄ້າ
    public  String getBprice(long l2){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] bprice = new  String[]{"proid","proname","qty","bprice","sprice","cate_id"};
        Cursor cursor = db.query("products",bprice,"proid="+l2,null,null,null,null);
        if (cursor!= null){
            cursor.moveToFirst();
            String bprices = cursor.getString(3);
            return bprices;
        }
        return null;
    }
    //ເວລາປ້ອນລະຫັດໃຫ້ດຶງເອົາຊຶ່ມາສະແດງໃນບ໋ອກຊຶ່ສິນຄ້າສີນສຸດ





    //ດຶງເອົາລະຫັດປະເພດສິ້ນຄ້າມາສະແດງໃນບ໋ອກລຸ່ມເວລາເລືອກຊືປະເພດສິ້ນຄ້າ
    public List<String> getselectcateid(){
        List<String> list = new ArrayList<String>(  );
        String selectQuery = "select * from "+TABLE_NAME1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( selectQuery,null );
        list.add("");
        if (cursor.moveToFirst()){
            do {
                list.add( cursor.getString( 0 ) );//ຢາກໃຫ້ມັນສະແດງລະຫັດປະເພດແມ່ນປຽນຈາກ 1 ເປັນ 0 ມັນຈະນັບອາເລແຕ່ 0
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    //ເວລາຮັບສິນຄ້າເຂົາມາເພິມ ໃຫ້ຈຳນວນສິນຄ້າຢູຕາຕະລາງ  product ເພິມຂຶ້ນຕາມທີຮັບເຂົ້າມາ ຫຼຶ ເພີມຂຶນ
    public void AddQtyProducts(String rqty,double Proid){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteStatement statement = db.compileStatement("UPDATE products SET qty = qty+? where proid=?");
        statement.clearBindings();
        statement.bindString(1,rqty);
        statement.bindDouble(2,Proid);
        statement.execute();
        db.close();
    }
    //ປິດ


    //ເວລາຮັບສິນຄ້າເຂົາມາເພິມ ໃຫ້ຈຳນວນສິນຄ້າຢູຕາຕະລາງ  product ເພິມຂຶ້ນຕາມທີຮັບເຂົ້າມາ ຫຼຶ ເພີມຂຶນ
    public void AddQtyProductssell(String sqty,double Proids){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteStatement statement = db.compileStatement("UPDATE products SET qty = qty-? where proid=?");
        statement.clearBindings();
        statement.bindString(1,sqty);
        statement.bindDouble(2,Proids);
        statement.execute();
        db.close();
    }
    //ປິດ
}

