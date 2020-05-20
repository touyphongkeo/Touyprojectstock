package keosa.example.mystock2020;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class from_receives extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ConnectDatabase myDb;
    private EditText mDisplayDate;
private  EditText date,proid,proname,bprices,qty,amonte;
private Button insert,show,Delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_receives);
        getSupportActionBar().setTitle("ຮັບສີນຄ້າເຂົ້າ");
        date = (EditText)findViewById(R.id.date);
        proid = (EditText)findViewById(R.id.proid);
        myDb = new ConnectDatabase(this);
        proname = (EditText)findViewById(R.id.proname);
        bprices = (EditText)findViewById(R.id.bprices);
        qty = (EditText)findViewById(R.id.qty);
        amonte = (EditText)findViewById(R.id.amonte);
        insert = (Button)findViewById(R.id.insert);
        show = (Button)findViewById(R.id.show);
        Delete = (Button)findViewById(R.id.Delete);
        insertreceives2();
        selectreceives();
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent receives = new Intent(getApplicationContext(),Delete_Receives.class);
                startActivity(receives);
            }
        });
        //ແມ່ນຄຳສັງດຶງເອົາຂໍ້ມູນມາສະແດງເມຶ່ອມິການເຄຶອນໄຫວໃນບ໋ອກຂອງລະຫັດ
        proid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //ເຂົ້າມາຂຽນໃນພາກສ່ວນນິ
                String ProId = proid.getText().toString();
                    try {
                        long l1=Long.parseLong(ProId);
                        String PRONAME = myDb.getProname(l1);
                        String BPRICE = myDb.getBprice(l1);
                        // ຊຶ່ຫ້ອງຕ້ອງການ
                        proname.setText(PRONAME);
                        bprices.setText(BPRICE);
                        //ໃຫ້ມີຂໍ້ມຄວາມແຈ້ງເຕຶ່ອນວ່າ ປ້ອນລະຫັດຖຶກຕ້ອງແລັວ
                        Toast.makeText(from_receives.this, "ຮັບເອົາຊຶ່ສິນຄ້າສຳເລັດ", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        Toast.makeText(from_receives.this, "ລະຫັດນິິບໍໃນລະບົບສິນຄ້າເຈົ້າ  ?", Toast.LENGTH_SHORT).show();
                    }
             }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // ຄຳສັງເວລາປ້ອນຈຳນວນໃຫ້ມັນຄຸນກັນອອກມາເປັນເງິນລວມພ້ອມ
    qty.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                amonte.setText(String.valueOf(Integer.parseInt(qty.getText().toString()) * Integer.parseInt(bprices.getText().toString())));
            }catch (Exception e){
                amonte.setText("");
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    });
// ສິນສຸດ
        //==============================================

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tdate = (EditText) findViewById(R.id.date);
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
    //ສ້າງ method ເພຶ່ອບັນທຶກຂໍ້ມູນ

    public void insertreceives2(){

        insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (proid.getText().toString().isEmpty()){
                    proid.setError("ປ້ອນລະຫັດສິນຄ້າກອນ");
                    proid.requestFocus();
                }else if (qty.getText().toString().isEmpty()){
                   qty.setError("ກະລຸນາປ້ອນຈຳນວນກອນ");
                   qty.requestFocus();
                }
                else {
                Integer proid1 = Integer.parseInt(proid.getText().toString());
                Integer bprices1 = Integer.parseInt(bprices.getText().toString());
                Double qty1 = Double.parseDouble(qty.getText().toString());
                String date1 = date.getText().toString();
                Double amonte1 = Double.parseDouble(amonte.getText().toString());
                    boolean isInserted1 = myDb.insertreceives(proid1, bprices1, qty1, date1, amonte1);
                    if (isInserted1 = true) {
                        //ຫຼັງຈາກບັນທຶກສຳເລັດໃຫ້ຈຳນວນສິນຄ້າເພີມຂື້ນຢູ່ ຕາຕະລາງເກັບກຳຂໍ້ມູນສິນຄ້າ
                        Toast.makeText(from_receives.this, "ບັນທຶກສຳເລັດແລັວ ?", Toast.LENGTH_SHORT).show();
                        try {
                            myDb.AddQtyProducts(qty.getText().toString().trim(), proid1);
                        } catch (Exception e) {
                            Toast.makeText(from_receives.this, "ອັບເດັດຂໍ້ມູນສິນຄ້າບໍສຳເລັດແລັວ ?", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(from_receives.this, "ບັນທຶກຂໍ້ມູນບໍສຳເລັດກະລຸນາກວດສອບລະບົບກອນ", Toast.LENGTH_SHORT).show();
                }
                String Text1 = date.getText().toString();
                String Text2 = proid.getText().toString();
                String Text3 = proname.getText().toString();
                String Text4 = bprices.getText().toString();
                String Text5 = qty.getText().toString();
                String Text6 = amonte.getText().toString();
                if (Text1.isEmpty() & Text2.isEmpty() & Text3.isEmpty() & Text4.isEmpty() & Text5.isEmpty() & Text6.isEmpty()){

                }else {
                    date.setText("");
                    proid.setText("");
                    proname.setText("");
                    bprices.setText("");
                    qty.setText("");
                    amonte.setText("");
                }}
            }
        });
    }
    //ສີນສຸດ
public void selectreceives(){
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.selectreceives();
                if (res.getCount()==0){
                    showMessage("Error","ບໍມິຂໍ້ມູນ");
                    return;

                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("ລະຫັດ:" + res.getString(0) + "\n");
                    buffer.append("ຈຳນວນ:" + res.getString(1) + "\n");
                    buffer.append("ລະຫັດປະເພດສິນຄ້າ:" + res.getString(2) + "\n");
                    buffer.append("ຊຶ່ສິນຄ້າ:" + res.getString(3) + "\n");
                    buffer.append("ລາຄ່າຊຶ່:" + res.getString(4) + "\n");
                    buffer.append("ວັນທຶເດຶອນປິນຳເຂົ້າມາ:" + res.getString(5) + "\n");
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
