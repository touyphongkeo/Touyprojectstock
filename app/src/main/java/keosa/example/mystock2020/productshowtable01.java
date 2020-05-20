package keosa.example.mystock2020;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class productshowtable01 extends AppCompatActivity {
    ConnectDatabase myDb;
    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productshowtable01);
        myDb = new ConnectDatabase(this);
        getSupportActionBar().setTitle("ຕາຕະລາງລາຍງານປະເພດສິນຄ້າ");
        tableLayout = findViewById(R.id.myTable);
        //ສ້າງຫົວຂໍ້ຂອງຕາຕະລາງ ລາຍງານຂໍ້ມູນປະເພດສິນຄ້າ
        TableRow tableRow = new TableRow(this);
        tableRow.setBackgroundColor(Color.parseColor("olive"));
        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        String[] HeaderText = {"ລະຫັດ","ສີນຄ້າ","ຈຳນວນ","ລາຄ່າຊຶ່","ລາຄ່າຂາຍ"};
        //
        for (String colum : HeaderText){
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
            tv.setTextSize(18);
            tv.setPadding(5,20,5,20);
            tv.setText(colum);
            tableRow.addView(tv);
        }
        tableLayout.addView(tableRow);
        //   ດຶງເອົາຂໍ້ມູນມາຈາກຖານຂໍ້ມູນ
        SQLiteDatabase db = myDb.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM products";
            Cursor cursor = db.rawQuery(selectQuery,null);
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    int proid = cursor.getInt(cursor.getColumnIndex("proid"));
                    String proname = cursor.getString(cursor.getColumnIndex("proname"));
                    String qty = cursor.getString(cursor.getColumnIndex("qty"));
                    String bprice = cursor.getString(cursor.getColumnIndex("bprice"));
                    String sprice = cursor.getString(cursor.getColumnIndex("sprice"));

                    TableRow row = new TableRow(this);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] columnText = {proid +"",proname,qty,bprice,sprice};
                    for (String text : columnText){
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setTextSize(14);
                        tv.setPadding(5,20,5,20);
                        tv.setText(text);
                        row.addView(tv);

                    }
                    tableLayout.addView(row);
                }
                db.setTransactionSuccessful();
            }
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            db.endTransaction();
            db.close();
        }

    }
}
