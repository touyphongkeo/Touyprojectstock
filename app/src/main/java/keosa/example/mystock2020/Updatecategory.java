package keosa.example.mystock2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.KeyStore;

public class Updatecategory extends AppCompatActivity {
    ConnectDatabase myDb; //ຈຸດເຊືອມໂຍງ
    // ID from xml
    EditText cate_name,remark,cate_id;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatecategory);
        getSupportActionBar().setTitle("ຟອມແກ້ໄຂ ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDb = new ConnectDatabase(this);
        cate_id = (EditText) findViewById(R.id.cate_id);
        cate_name = (EditText) findViewById(R.id.cate_name);
        remark = (EditText) findViewById(R.id.remark);
        update = (Button) findViewById(R.id.update);


        UpdateCate();
    }

    //Update category
   public void UpdateCate(){
       update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (cate_id.length() == 0) {
                   cate_id.setError("ປ້ອນລະຫັດກ່ອນ");
                   cate_id.requestFocus();
                   //Toast.makeText(Updatecategory.this, "ປ້ອນຂໍ້ມູນກອນ", Toast.LENGTH_SHORT).show();
               }else if(cate_name.length() == 0){
                   cate_name.setError("ປ້ອນຊື່ປະເພດກ່ອນ");
                   cate_name.requestFocus();

               }else if(remark.length() == 0){
                   remark.setError("ປ້ອນຊື່ປະເພດກ່ອນ");
                   remark.requestFocus();

               } else {
                   boolean isUpdate = myDb.updateData(cate_id.getText().toString(),
                           cate_name.getText().toString(),
                           remark.getText().toString());


                   if (isUpdate == true)

                       Toast.makeText(Updatecategory.this, "ແກ້ໄຂສຳເລັດແລ້ວ", Toast.LENGTH_SHORT).show();
                   else

                       Toast.makeText(Updatecategory.this, "ຍັງບໍທັນສຳເລັດ", Toast.LENGTH_SHORT).show();

                   String Text = cate_id.getText().toString();
                   String Text1 = cate_name.getText().toString();
                   String Text2 = remark.getText().toString();

                   if (Text.isEmpty() & Text1.isEmpty() & Text2.isEmpty()) {


                   } else {
                       cate_id.setText("");
                       cate_name.setText("");
                       remark.setText("");


                   }
               }
           }
       });

   }
}
