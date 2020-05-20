package keosa.example.mystock2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProdustDelete extends AppCompatActivity {
    ConnectDatabase myDb;
    EditText edit_id;
    Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produst_delete);
        myDb = new ConnectDatabase(this);
        edit_id = (EditText) findViewById(R.id.edit_id);
        delete = (Button) findViewById(R.id.delete);
        DeleteData();


    }

    public void DeleteData() {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_id.length() == 0) {
                    edit_id.setError("ປ້ອນລະຫັດກ່ອນ");
                    edit_id.requestFocus();
                    Toast.makeText(ProdustDelete.this, "ປ້ອນຂໍ້ມູນກອນ", Toast.LENGTH_SHORT).show();
                } else {

                    Integer deletedBrows = myDb.deleteproducs(edit_id.getText().toString());
                    if (deletedBrows > 0)
                        Toast.makeText(ProdustDelete.this, "ລົບສຳເລັດແລ້ວ!!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(ProdustDelete.this, "ຍັງບໍທັນສຳເລັດ!!", Toast.LENGTH_SHORT).show();

                    //settext
                    String Text = edit_id.getText().toString();

                    if(Text.isEmpty() & Text.isEmpty()){


                    }
                    else {
                        edit_id.setText("");

                    }

                }
            }
        });

    }
}

