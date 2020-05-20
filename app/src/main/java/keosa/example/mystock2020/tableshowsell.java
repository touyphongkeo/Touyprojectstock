package keosa.example.mystock2020;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class tableshowsell extends AppCompatActivity {
    ConnectDatabase myDb;
    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableshowsell);
        myDb = new ConnectDatabase(this);
        getSupportActionBar().setTitle("ຕາຕະລາງລາຍງານສິນຄ້ານຳເຂົ້າມາ");
        tableLayout = findViewById(R.id.myTable);
        //ສ້າງຫົວຂໍ້ຂອງຕາຕະລາງ ລາຍງານຂໍ້ມູນປະເພດສິນຄ້າ
        TableRow tableRow = new TableRow(this);
        tableRow.setBackgroundColor(Color.parseColor("olive"));
        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        String[] HeaderText = {"ລະຫັດ","ສີນຄ້າ","ລາຄ່າຂາຍ","ວ.ດ.ປ","ຈ/ນ","ເປັນເງິນ"};
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
            String selectQuery = "select a.Orid,b.proname,a.sprice,a.Ordate,a.qty,a.amount from sell  as a,products as b where a.proid = b.proid";
            Cursor cursor = db.rawQuery(selectQuery,null);
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    int Orid = cursor.getInt(cursor.getColumnIndex("Orid"));
                    String proname = cursor.getString(cursor.getColumnIndex("proname"));
                    String sprice = cursor.getString(cursor.getColumnIndex("sprice"));
                    String Ordate = cursor.getString(cursor.getColumnIndex("Ordate"));
                    String qty = cursor.getString(cursor.getColumnIndex("qty"));
                    String amount = cursor.getString(cursor.getColumnIndex("amount"));

                    TableRow row = new TableRow(this);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] columnText = {Orid +"",proname,sprice,Ordate,qty,amount};
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
