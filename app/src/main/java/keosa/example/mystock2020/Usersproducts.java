package keosa.example.mystock2020;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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

public class Usersproducts extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ConnectDatabase myDb;//ເອີ້ນນຳໃຊ້ ຖ່ານຂໍ້ມູນ

    EditText proname, qty, bprice, sprice,cateid;//ລະຫັດຂອງແຕ່ລະບອ໋ກ

    Spinner spinner;

    Button btnsaved, btnshow;//ລະຫັດຂອງປຸ່ມບັນທຶກ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersproducts);
        getSupportActionBar().setTitle("ລາຍການສິ້ນຄ້າ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDb = new ConnectDatabase(this);
        //ຮັບເອົາ ລະຫັດ ຂອງບອ໋ກປ້ອນຂໍ້ມູນແຕ່່ລະບອ໋ກ
        proname = (EditText) findViewById(R.id.proname);
        qty = (EditText) findViewById(R.id.qty);
        bprice = (EditText) findViewById(R.id.bprice);
        sprice = (EditText) findViewById(R.id.sprice);
        spinner = (Spinner) findViewById(R.id.Spinner);
        btnsaved = (Button) findViewById(R.id.btnsaved);
        btnshow = (Button) findViewById(R.id.btnshow);
        cateid = (EditText) findViewById(R.id.cateid);
        InsertPro();
        showPro();
        productSelectItem();
    }
    //Insert Product ບັນທຶກຕາຕະລາງ ເກັບກຳສິ້ນຄ້າ ()
    public void InsertPro() {
        btnsaved.setOnClickListener(new View.OnClickListener() {
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

                } else {
                    String s = spinner.getSelectedItem().toString();

                    int qtys = Integer.parseInt(qty.getText().toString());
                    int Cateids = Integer.parseInt(cateid.getText().toString());
                    double buys = Double.parseDouble(bprice.getText().toString());
                    double sells = Double.parseDouble(sprice.getText().toString());

                    String pname = proname.getText().toString();

                    boolean Isaved = myDb.insertproducts(pname,qtys,buys,sells,Cateids);
                    // proname.getText().toString(),
                    //qty.getText().toString(),
                    // bprice.getText().toString(),
                    // sprice.getText().toString(),
                    // cateid.getText().toString());

                    if (Isaved = true) {
                        Toast.makeText(Usersproducts.this, "ບັນທຶກສຳເລັດແລ້ວ!!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(Usersproducts.this, "ບັນທຶກຍັງບໍທັນສຳເລັດແລ້ວ!!", Toast.LENGTH_SHORT).show();

                    //settext
                    String Text = proname.getText().toString();
                    String Text1 = qty.getText().toString();
                    String Text2 = bprice.getText().toString();
                    String Text3 = sprice.getText().toString();
                    String Text4 = cateid.getText().toString();
                    if (Text.isEmpty() & Text1.isEmpty() && Text2.isEmpty() && Text3.isEmpty() && Text4.isEmpty()) {


                    } else {
                        proname.setText("");
                        qty.setText("");
                        bprice.setText("");
                        sprice.setText("");
                        spinner.setSelection(0);


                    }
                }

            }

        });

    }

//select Products

    public void showPro() {
        btnshow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cursor res = myDb.selectPro();
                        if (res.getCount() == 0) {

                            showMessage("Error", "ບໍມີຂໍ້ມູນ");
                            return;
                        }

                        //ຂໍ້ມູນຕໍໆຕັນໄປເປັນລະບຽບ
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ລະຫັດ:" + res.getString(0) + "\n");
                            buffer.append("ຊືສິ້ນຄ້າ:" + res.getString(1) + "\n");
                            buffer.append("ຈຳນວນ:" + res.getString(2) + "\n");
                            buffer.append("ລາຄາ່ຊຶ່:" + res.getString(3) + "\n");
                            buffer.append("ປະເພດສີນ:" + res.getString(4) + "\n");
                            buffer.append("___________________________" + "\n\n");
                        }


                        //show all data

                        showMessage("ຂໍ້ມູນທັງໝົດ", buffer.toString());
                    }
                }
        );
    }

    // Greate Message Noficall

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
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
    //ເວລາຄິຣກປຸ່ມແກ້ໄຂມັນຈະໄປຫາຟ່າຍ UPDATE_PRODUCT

    public void updateproducts() {
        Intent intent = new Intent(this, UPDATE_PRODUCT.class);
        startActivity(intent);
    }

    public void deleteproducts() {
        Intent intent = new Intent(this, DELETE_PRODUCT.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }}

