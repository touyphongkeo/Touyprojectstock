package keosa.example.mystock2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;

public class From_OrderSell extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText rdate,rproid,rproname,rsprices,rqty,ramont;
    private Button button_sell,show_data;
    ConnectDatabase myDb;
    private EditText mDisplayDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from__order_sell);
        getSupportActionBar().setTitle("ຂາຍສິນຄ້າອອກ");
        myDb = new ConnectDatabase(this);
        rdate = (EditText)findViewById(R.id.rdate);
        rproid = (EditText)findViewById(R.id.rproid);
        rproname = (EditText)findViewById(R.id.rproname);
        rsprices = (EditText)findViewById(R.id.rsprices);
        rqty = (EditText)findViewById(R.id.rqty);
        ramont = (EditText)findViewById(R.id.ramont);
        button_sell = (Button)findViewById(R.id.button_sell);
        show_data = (Button)findViewById(R.id.show_data);
        insertreceives2();
        selectsell();
        //ແມ່ນຄຳສັງດຶງເອົາຂໍ້ມູນມາສະແດງເມຶ່ອມິການເຄຶອນໄຫວໃນບ໋ອກຂອງລະຫັດ
        rproid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //ເຂົ້າມາຂຽນໃນພາກສ່ວນນິ
                String ProId = rproid.getText().toString();
                try {
                    long l1=Long.parseLong(ProId);
                    String PRONAME = myDb.getsell(l1);
                    String SPRICE = myDb.getsell1(l1);
                    // ຊຶ່ຫ້ອງຕ້ອງການ
                    rproname.setText(PRONAME);
                    rsprices.setText(SPRICE);
                    //ໃຫ້ມີຂໍ້ມຄວາມແຈ້ງເຕຶ່ອນວ່າ ປ້ອນລະຫັດຖຶກຕ້ອງແລັວ
                    Toast.makeText(From_OrderSell.this, "ຮັບເອົາຊຶ່ສິນຄ້າສຳເລັດ", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(From_OrderSell.this, "ລະຫັດນິບໍມີຢູໃນຖານຂໍ້ມູນຂອງສິນຄ້າ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // ປິດ
        // ຄຳສັງເວລາປ້ອນຈຳນວນໃຫ້ມັນຄຸນກັນອອກມາເປັນເງິນລວມພ້ອມ
        rqty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    ramont.setText(String.valueOf(Integer.parseInt(rqty.getText().toString()) * Integer.parseInt(rsprices.getText().toString())));
                }catch (Exception e){
                    ramont.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
// ສິນສຸດ

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tdate = (EditText) findViewById(R.id.rdate);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                String dateString = sdf.format(date);
                                tdate.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }
    //ສ້າງ Method ບັນທຶກຂໍ້ມູນ
    public void insertreceives2(){
        button_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rproid.getText().toString().isEmpty()){
                    rproid.setError("ກະລຸນາປ້ອນລະຫັດສິນຄ້າກອນ");
                    rproid.requestFocus();
                }else if (rqty.getText().toString().isEmpty()){
                    rqty.setError("ກະລຸນາປ້ອນຈຳນວນຂາຍ");
                    rqty.requestFocus();
                } else {
                    //ສີນຄ້າບໍພຽງພໍບໍສາມາດຂາຍໄດ
                    try {
                        String Proid = rproid.getText().toString();
                        int orqty = Integer.parseInt(rqty.getText().toString());
                        Cursor cursor = myDb.getDate("select qty from products where proid = "+Proid);
                        while (cursor.moveToNext()){
                       int proQty = cursor.getInt(0);
                       if (proQty < orqty){
                           Toast.makeText(From_OrderSell.this, "ຈຳນວນສີນຄ້ານິ້ບໍພຽງພໍຂາຍອອກ", Toast.LENGTH_SHORT).show();

                       }else {



                    Integer rproid1 = Integer.parseInt(rproid.getText().toString());
                    Double rsprice = Double.parseDouble(rsprices.getText().toString());
                    Integer rqty1 = Integer.parseInt(rqty.getText().toString());
                    String rdate1 = rdate.getText().toString();
                    Double ramonte1 = Double.parseDouble(ramont.getText().toString());
                    boolean isInserted = myDb.insertsell(rproid1, rsprice, rdate1, rqty1, ramonte1);
                    if (isInserted = true) {
                        Toast.makeText(From_OrderSell.this, "ຂາຍສຳເລັດແລັວ", Toast.LENGTH_SHORT).show();
                        try {
                            myDb.AddQtyProductssell(rqty.getText().toString().trim(), rproid1);
                        } catch (Exception e) {
                            Toast.makeText(From_OrderSell.this, "ອັບເດັດຂໍ້ມູນຈຳນວນສິນຄ້າບໍສຳເລັດແລັວ ?", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(From_OrderSell.this, "ຂາຍບໍສຳເລັດ", Toast.LENGTH_SHORT).show();
                    }
                           String Text1 = rqty.getText().toString();
                           String Text2 = rproid.getText().toString();
                           String Text3 = rproname.getText().toString();
                           String Text4 = rsprices.getText().toString();
                           String Text5 = rqty.getText().toString();
                           String Text6 = ramont.getText().toString();
                           if (Text1.isEmpty() & Text2.isEmpty() & Text3.isEmpty() & Text4.isEmpty() & Text5.isEmpty() & Text6.isEmpty()){

                           }else {
                               rdate.setText("");
                               rproid.setText("");
                               rproname.setText("");
                               rsprices.setText("");
                               rqty.setText("");
                               ramont.setText("");
                           }


                       }//ປິດ else
                        }//ປິດwhile
                    }catch (Exception e){

                    }

                  }
                  }

        });
    }
    //ພາກສວນເມນູ bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarsell ,menu);
        return super.onCreateOptionsMenu(menu);

    }

    //ສິນສຸດ



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemsels){
            Toast.makeText(this, "ແກ້ໄຂຂໍ້ມູນ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Update_sell.class ));

        }else {
            if (id == R.id.item1e){
                Toast.makeText(this, "ລົບຂໍ້ມູນ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,Delete_sell.class ));

            }

        }
        return super.onOptionsItemSelected(item);
    }
    public void selectsell(){
        show_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.selectsell();
                if (res.getCount()==0){
                    showMessage("Error","ບໍມິຂໍ້ມູນ");
                    return;

                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("ລະຫັດ:" + res.getString(0) + "\n");
                    buffer.append("ລະຫັດສິນຄ້າ:" + res.getString(1) + "\n");
                    buffer.append("ຊຶ່ສິນຄ້າ:" + res.getString(2) + "\n");
                    buffer.append("ລາຄ່າຂາຍ:" + res.getString(3) + "\n");
                    buffer.append("ວັນທີເດຶອນປິ:" + res.getString(4) + "\n");
                    buffer.append("ຈຳນວນຂາຍ:" + res.getString(5) + "\n");
                    buffer.append("ລວມເປັນເງີນ:" + res.getString(6) + "\n");
                    buffer.append("___________________________"+"\n\n");
                }
                showMessage("ຂໍ້ມູນທັງໝົດ", buffer.toString());
            }
        });

    }
    public void showMessage(String title, String Message) {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}
