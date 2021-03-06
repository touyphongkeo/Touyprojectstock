package keosa.example.mystock2020;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class tableReceives extends AppCompatActivity {
    ConnectDatabase myDb;
    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_receives);
        myDb = new ConnectDatabase(this);
        getSupportActionBar().setTitle("ຕາຕະລາງລາຍງານສິນຄ້ານຳເຂົ້າມາ");
        tableLayout = findViewById(R.id.myTable);
        //ສ້າງຫົວຂໍ້ຂອງຕາຕະລາງ ລາຍງານຂໍ້ມູນປະເພດສິນຄ້າ
        TableRow tableRow = new TableRow(this);
        tableRow.setBackgroundColor(Color.parseColor("olive"));
        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        String[] HeaderText = {"ລະຫັດ","ສີນຄ້າ","ລາຄ່າຊຶ່","ຈ/ນ","ວ.ດ.ປ","ເປັນເງິນ"};
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
        SQLiteDatabase db = myDb.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "select a.recid,b.proname,a.bprice,a.qty,a.bdate,a.amount from receives  as a,products as b where a.proid = b.proid";
            Cursor cursor = db.rawQuery(selectQuery,null);
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    int recid = cursor.getInt(cursor.getColumnIndex("recid"));
                    String proname = cursor.getString(cursor.getColumnIndex("proname"));
                    String bprice = cursor.getString(cursor.getColumnIndex("bprice"));
                    String bdate = cursor.getString(cursor.getColumnIndex("bdate"));
                    String qty = cursor.getString(cursor.getColumnIndex("qty"));
                    String amount = cursor.getString(cursor.getColumnIndex("amount"));

                    TableRow row = new TableRow(this);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] columnText = {recid +"",proname,bprice,bdate,qty,amount};
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
