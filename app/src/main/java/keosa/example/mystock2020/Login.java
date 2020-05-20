package keosa.example.mystock2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    ConnectDatabase myDb;
    private EditText username,password;
    private Button add;
    TextView Vuser;
    private int counter = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new ConnectDatabase(this);
        getSupportActionBar().setTitle("ລ໋ອກອີນ");
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Vuser = findViewById(R.id.Vuser);
        add = findViewById(R.id.add);
        Vuser.setText("ທ່ານມີສິດ 5 ຄັ້ງໃນການປ້ອນຂໍ້ມູນ");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUser(username.getText().toString(), password.getText().toString());
            }
        });

        
        
        
        
        
//        Vuser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent nextpage = new Intent(getApplicationContext(), From_users.class);
//                startActivity(nextpage);
//            }
//        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });
    }
    public void login(){
        String user = username.getText().toString();
        String pass = password.getText().toString();
        if (username.getText().toString().isEmpty()){
            username.setError("ກະລຸນາປ້ອນຊຶ່ຜູ້ນຳໃຊ້ກອນ");
            username.requestFocus();
        }else if (password.getText().toString().isEmpty()){
            password.setError("ກະລຸນາປ້ອນລະຫັດຜ່ານກອນ");
            password.requestFocus();
        }else {



        if (user.equals("") || pass.equals("")){
        }else if (null!=checkUser(user,pass)){
            String userDb = checkUser(user,pass);
            Intent i = new Intent(Login.this,MainActivity.class);
            Toast.makeText(this, "ຊຶ່ຜູ້ນຳໃຊ້ ແລະ ລະຫັດຜ່ານຖຶກຕ້ອງແລັວ", Toast.LENGTH_SHORT).show();
            i.putExtra("uname",userDb);
            startActivity(i);

        }else {

            username.setText("");
            password.setText("");
            add.requestFocus();
        }}
    }
    public String checkUser(String user,String pass){
        //ການເອີນ Metthod ໃນຖານຂໍ້ມູນມາໃຊ້ງານ
        Boolean ttt = myDb.Touser(user,pass);
        Boolean touys = myDb.Touy(user,pass);




        if((user.equals("admin")) && (pass.equals("admin"))){
            Intent intent = new Intent(Login.this, From_users.class);
            startActivity(intent);
        }else if (ttt == true){
            Intent intent = new Intent(Login.this, indext_user.class);
            Toast.makeText(this, "ຊຶ່ຜູ້ນຳໃຊ້ ແລະ ລະຫັດຜ່ານຖຶກຕ້ອງແລັວ", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else if (touys == true){
            Intent intent = new Intent(Login.this, MainActivity.class);
            Toast.makeText(this, "ຊຶ່ຜູ້ນຳໃຊ້ ແລະ ລະຫັດຜ່ານຖຶກຕ້ອງແລັວ", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else{
            counter--;

            Vuser.setText(String.valueOf(counter)+"ຄັ້ງທີ: " );
            Toast.makeText(this, "ຊຶ່ຜູ້ນຳໃຊ້້ ຫຼື ລະຫັດຜ່ານບໍຖືກຕ້ອງ ?", Toast.LENGTH_SHORT).show();
            if(counter == 0){
                add.setEnabled(false);
                Vuser.setText("ທ່ານຫມົດສີດໃນການປ້ອນຂໍ້ມູນແລັວ");

            }else {

        SQLiteDatabase db = myDb.getReadableDatabase();
        Cursor cursor = db.rawQuery("select userid,username,password from users where username = ? and password = ?",new String[]{user,pass});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            String usernam = cursor.getString(1);
            String password = cursor.getString(2);
            SharedPreferences.Editor sp = getSharedPreferences("username",MODE_PRIVATE).edit();
            sp.putString("uname",usernam);
            sp.apply();
            cursor.close();
            return usernam;
        }

            }
        }return null;
    }





//    private void validate(String userName, String userPassword){
//        if((userName.equals("admin")) && (userPassword.equals("123"))){
//            Intent intent = new Intent(Login.this, From_users.class);
//            startActivity(intent);
//        }else{
//            counter--;
//
//            Vuser.setText("ຄັ້ງທີ: " + String.valueOf(counter));
//
//            if(counter == 0){
//                add.setEnabled(false);
//                Vuser.setText("ທ່ານຫມົດສີດໃນການປ້ອນຂໍ້ແລັວ");
//
//            }
//        }
//    }

}
