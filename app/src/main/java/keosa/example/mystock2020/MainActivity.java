package keosa.example.mystock2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity {

    //database connect Helper
    ConnectDatabase myDb;
    Button tablesproduct;
    TextView from_users;
        //ປະກາດຕົວປຽນຂອງປຸ່ມທັງສອງ
    Button btncategory,btnproduct,receives;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("ຫນ້າຫຼັກ");
        myDb = new ConnectDatabase(this);
        btncategory = (Button) findViewById(R.id.btncategory);
        btnproduct = (Button) findViewById(R.id.btnproduct);

        btncategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gocategory();
            }
        });
        tablesproduct =(Button)findViewById(R.id.tableProducs);
        receives = (Button)findViewById(R.id.receives);
        from_users = (TextView)findViewById(R.id.from_users);
        tablesproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextpage = new Intent(getApplicationContext(), tableReceives.class);
                startActivity(nextpage);
            }
        });
        from_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextpage = new Intent(getApplicationContext(), tablesusers.class);
                startActivity(nextpage);
            }
        });

        receives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextpage = new Intent(getApplicationContext(), tableshowsell.class);
                startActivity(nextpage);
            }
        });

       // ------------------
        btnproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goproduct();
            }
        });
    }







    // vealaclick Button Haipaiha Mainactivity
    // When your click Button category but go to activity
    //ເວລາກົດປຸ່ມແລ້ວໃຫ້ມັນໄປຫາ ຟ່າຍ Activity
    public void Gocategory(){
        Intent intent = new Intent(this,tableproducts.class);
        startActivity(intent);
    }

    public void Goproduct(){

        Intent intent = new Intent(this,productshowtable01.class);
        startActivity(intent);

    }
    //ພາກສວນເມນູ bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar ,menu);
        return super.onCreateOptionsMenu(menu);

    }

    //ສິນສຸດ


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1){
            Toast.makeText(this, "ຟອມນຳເຂົາສິນຄ້າ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,from_receives.class ));

        }else {
            if (id == R.id.item01){
                Toast.makeText(this, "ຟອມປະເພດສິນຄ້າ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,category.class ));

            }else {
                if (id == R.id.item011){
                    Toast.makeText(this, "ຟອມສິນຄ້າ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,products.class ));

                }else {
                    if (id == R.id.itemsel){
                        Toast.makeText(this, "ຂາຍສິນຄ້າ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this,From_OrderSell.class));
                    }else {
                    if (id == R.id.item2){
                        ApplicationInfo api = getApplicationContext().getApplicationInfo();
                        String apkpath = api.sourceDir;
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("application/");
                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
                        startActivity(Intent.createChooser(intent,"ການແບງປັນຂໍ້ມູນ"));

                    }else {
                        if (id == R.id.users){
                            Toast.makeText(this, "ພະນັກງານ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this,From_users.class));
                        }else {
                            if (id == R.id.login){
                                Toast.makeText(this, "ອອກຈາກລະບົບແລັວ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(this,Login.class));
                            }
                        }
                    }

                }
                }

            }

        }
        return super.onOptionsItemSelected(item);
    }
}
