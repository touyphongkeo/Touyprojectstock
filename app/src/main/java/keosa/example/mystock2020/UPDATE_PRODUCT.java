package keosa.example.mystock2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UPDATE_PRODUCT extends AppCompatActivity {

    ConnectDatabase myDb;//ເອີ້ນນຳໃຊ້ ຖ່ານຂໍ້ມູນ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__product);


        myDb = new ConnectDatabase(this);

        getSupportActionBar().setTitle("ແກ້ໄຂລາຍການສິ້ນຄ້າ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
