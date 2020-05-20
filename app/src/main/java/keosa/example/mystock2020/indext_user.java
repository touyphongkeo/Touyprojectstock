package keosa.example.mystock2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class indext_user extends AppCompatActivity {
    private Button btncategory,btnproduct,receives,tableProducs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indext_user);
        getSupportActionBar().setTitle("ພະນັກງານຂາຍ");
        btncategory = findViewById(R.id.btncategory);
        btnproduct = findViewById(R.id.btnproduct);
        receives = findViewById(R.id.receives);
        tableProducs = findViewById(R.id.tableProducs);
        btncategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextpage = new Intent(getApplicationContext(), Userscategories.class);
                startActivity(nextpage);
            }
        });
        tableProducs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextpage = new Intent(getApplicationContext(), UserFrom_OrderSell.class);
                startActivity(nextpage);
            }
        });
        btnproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextpage = new Intent(getApplicationContext(), Usersproducts.class);
                startActivity(nextpage);
            }
        });
        receives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextpage = new Intent(getApplicationContext(), UsersRecives.class);
                startActivity(nextpage);
            }
        });
    }

    //ພາກສວນເມນູ bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_user ,menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.out){
            Toast.makeText(this, "ອອກຈາກລະບົບ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Login.class ));

        }
        return super.onOptionsItemSelected(item);
    }}
