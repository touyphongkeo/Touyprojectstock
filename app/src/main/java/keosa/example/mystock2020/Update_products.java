package keosa.example.mystock2020;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Update_products extends AppCompatActivity {
    ConnectDatabase myDb;//ເອີ້ນນຳໃຊ້ ຖ່ານຂໍ້ມູນ
    EditText proname, qty, bprice, sprice,cateid,proid;//ລະຫັດຂອງແຕ່ລະບອ໋ກ
    Spinner spinner;
    Button btnupdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_products);
        getSupportActionBar().setTitle("ແກ້ໄຂ້ສິນຄ້າ");
        myDb = new ConnectDatabase(this);
        proname = (EditText) findViewById(R.id.proname);
        qty = (EditText) findViewById(R.id.qty);
        bprice = (EditText) findViewById(R.id.bprice);
        sprice = (EditText) findViewById(R.id.sprice);
        spinner = (Spinner) findViewById(R.id.Spinner);
        btnupdate = (Button) findViewById(R.id.btnupdate);
        cateid = (EditText) findViewById(R.id.cateid);
        proid = (EditText) findViewById(R.id.proid);
        productSelectItem();
        UpdateCate();
    }
    //Update category
    public void UpdateCate(){
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (proname.length() == 0) {
                    proname.setError("ປ້ອນຊືປະເພດສິ້ນຄ້າກ່ອນ");
                    proname.requestFocus();
                } else if (qty.length() == 0) {
                    qty.setError("ປ້ອນຈຳນວນກ່ອນ");
                    qty.requestFocus();
                } else if (bprice.length() == 0) {
                    bprice.setError("ປ້ອນລາຄ່າຊືກ່ອນ");
                    bprice.requestFocus();
                } else if (sprice.length() == 0) {
                    sprice.setError("ປ້ອນລາຄ່າຂ່າຍກ່ອນ");
                    sprice.requestFocus();
                }else if (spinner.getSelectedItem().equals("ເລືອກລະຫັດ") ){
                    TextView errorText = (TextView)spinner.getSelectedView();
                    errorText.setTextColor(Color.RED);
                    errorText.setError("");

                    //cateid.setError("ເລືອກລະຫັດປະເພດກ່ອນ");
                    // Toast.makeText(products.this, "ເລືອກລະຫັດປະເພດກ່ອນ!!", Toast.LENGTH_SHORT).show();
                }else if (proid.length() == 0) {
                    proid.setError("ປ້ອນລະຫັດສິນຄ້າ");
                    proid.requestFocus();
                } else {
                String prd = proid.getText().toString();
                String pron = proname.getText().toString();
                Integer qty1 = Integer.parseInt(qty.getText().toString());
                Double bp = Double.parseDouble(bprice.getText().toString());
                Double sp = Double.parseDouble(sprice.getText().toString());
                Integer catid = Integer.parseInt(cateid.getText().toString());
                String s = spinner.getSelectedItem().toString();



                    boolean isUpdate = myDb.updateproducts(prd, pron, qty1, bp, sp, catid);
                    if (isUpdate == true) {
                        Toast.makeText(Update_products.this, "ແກ້ໄຂສຳເລັດແລ້ວ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Update_products.this, "ຍັງບໍທັນສຳເລັດ", Toast.LENGTH_SHORT).show();


                }}
            }
        });

    }






    //select Item
    private void productSelectItem() {
        ConnectDatabase db = new ConnectDatabase(getApplicationContext());
        List<String> lables = db.getcate();
        final List<String> Cateid = db.getselectcateid();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, lables);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item);

        //simple_spinner_dropdown_item ໃຊ້ແທ່ນບອ່ນ select_dialog_item ກໍ່ໄດ້
        spinner.setAdapter(adapter);//cate_id ໃນນີ້ແມ່ນລະຫັດໄອດີຂອງ ບ໋ອກ spinner


        // ລະຫັດ


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String cal = Cateid.get(position);
                cateid.setText(cal);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
