package keosa.example.mystock2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class delete extends AppCompatActivity {

    ConnectDatabase myDb;
    EditText edit_id;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        //Titel
        getSupportActionBar().setTitle("ລົບຂໍ້ມູນ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                    Toast.makeText(delete.this, "ປ້ອນຂໍ້ມູນກອນ", Toast.LENGTH_SHORT).show();
                } else {

                    Integer deletedBrows = myDb.deletecategory(edit_id.getText().toString());
                if (deletedBrows > 0)
                    Toast.makeText(delete.this, "ລົບສຳເລັດແລ້ວ!!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(delete.this, "ຍັງບໍທັນສຳເລັດ!!", Toast.LENGTH_SHORT).show();

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
