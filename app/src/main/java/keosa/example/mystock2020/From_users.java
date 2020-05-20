package keosa.example.mystock2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class From_users extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ConnectDatabase myDb;
    EditText ed1,ed2,ed3;
     Button button;
     TextView textView13;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_users);
        myDb = new ConnectDatabase(this);
        getSupportActionBar().setTitle("ຟອມພະນັກງານ");
        ed1 = (EditText)findViewById(R.id.username);
        ed2 = (EditText)findViewById(R.id.password);
        spinner = (Spinner) findViewById(R.id.Spinner);
        ed3 = (EditText)findViewById(R.id.passconf);
        button = (Button)findViewById(R.id.button);
        textView13 = findViewById(R.id.textView13);
        Inseted();

        final ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.item,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);




        textView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextpage = new Intent(getApplicationContext(), Login.class);
                startActivity(nextpage);
            }
        });
    }
    // insert table category ບ່ອນຊົງຂໍ້ມູນ
    public void Inseted() {
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                        String username = ed1.getText().toString();
                        String password = ed2.getText().toString();
                        String pss = ed3.getText().toString();
                            String spinner1 = spinner.getSelectedItem().toString();
                            if (ed1.getText().toString().isEmpty()){
                                ed1.setError("ກະລຸນາປ້ອນຊຶ່ຜູ້ນຳໃຊ້ກອນ");
                                ed1.requestFocus();
                            }else if (ed2.getText().toString().isEmpty()){
                                ed2.setError("ກະລຸນາປ້ອນລະຫັດຜ່ານກອນ");
                                ed2.requestFocus();
                            }else if (ed3.getText().toString().isEmpty()){
                                ed3.setError("ກະລຸນາປ້ອນລະຫັດຢຶນຢັນ");
                                ed3.requestFocus();
                            }else {
                        if (!password.equals(pss)){
                            Toast.makeText(From_users.this, "ລະຫັດບໍຕົງກັນກະລຸນາກວດສອບ ?", Toast.LENGTH_SHORT).show();
                        }else {
                            boolean isInserted = myDb.insertusers(username, password, pss,spinner1);
                            if (isInserted = true) {
                                Toast.makeText(From_users.this,
                                        "ບັນທຶກສຳເລັດແລ້ວ", Toast.LENGTH_SHORT).show();
                                ed1.setText("");
                                ed2.setText("");
                                ed3.setText("");
                                spinner.setSelection(0);
                                button.requestFocus();
                            } else{
                                Toast.makeText(From_users.this,
                                        "ບັນທຶກບໍທັນສຳເລັດແລ້ວ", Toast.LENGTH_SHORT).show();
                        }}}

                        }catch (Exception e){

                        }
                        }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
