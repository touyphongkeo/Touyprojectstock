package keosa.example.mystock2020;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Userscategories extends AppCompatActivity {
    ConnectDatabase myDb;

    EditText cateid, catename, remark;
    Button btnsaved, btnupdate, btndelete, btnshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userscategories);
        getSupportActionBar().setTitle("ຟອມປະເພດສິນຄ້າ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDb = new ConnectDatabase(this);
        catename = (EditText) findViewById(R.id.catename);
        remark = (EditText) findViewById(R.id.remark);
        btnsaved = (Button) findViewById(R.id.btnsaved);

        btnshow = (Button) findViewById(R.id.btnshow);
        Inseted();
        showcategory();

    }

    // insert table category ບ່ອນຊົງຂໍ້ມູນ
    public void Inseted() {
        btnsaved.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (catename.getText().toString().isEmpty()) {
                            catename.setError("ປ້ອນປະເພດສິ້ນຄ້າກ່ອນ");
                            catename.requestFocus();
                            Toast.makeText(Userscategories.this, "ປ້ອນຂໍ້ມູນກອນ", Toast.LENGTH_SHORT).show();

                        } else if (remark.getText().toString().isEmpty()) {
                            remark.setError("ປ້ອນໝາຍເຫດ");
                            remark.requestFocus();

                        } else {
                            boolean isInserted = myDb.insertcategory(catename.getText().toString(), remark.getText().toString());
                            if (isInserted = true)
                                Toast.makeText(Userscategories.this,
                                        "ບັນທຶກສຳເລັດແລ້ວ", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(Userscategories.this,
                                        "ບັນທຶກບໍທັນສຳເລັດແລ້ວ", Toast.LENGTH_SHORT).show();

                            String Text = catename.getText().toString();
                            String Text1 = remark.getText().toString();
                            if (Text.isEmpty() & Text1.isEmpty()) {


                            } else {
                                catename.setText("");
                                remark.setText("");
                            }
                        }
                    }
                });


    }



    //show category or select


    public void showcategory() {
        btnshow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {

                            showMessage("Error", "ບໍມີຂໍ້ມູນ");
                            return;
                        }

                        //ຂໍ້ມູນຕໍໆຕັນໄປເປັນລະບຽບ
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ລະຫັດ:" + res.getString(0) + "\n");
                            buffer.append("ຊື່ປະເພດ:" + res.getString(1) + "\n");
                            buffer.append("ໝາຍເຫດ:" + res.getString(2) + "\n");
                            buffer.append("___________________________"+"\n\n");
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

    //delete category

//method delete



    // Go to activity Updatecategory



}