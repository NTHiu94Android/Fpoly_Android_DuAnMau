package com.example.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanmau.DAO.ThuThuDAO;

public class Login extends AppCompatActivity {
    private ThuThuDAO thuThuDAO;
    private EditText edtUser, edtPass;
    private CheckBox chkRemember;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.edtUsername);
        edtPass = findViewById(R.id.edtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCancel = findViewById(R.id.btnCancelLogin);
        chkRemember = findViewById(R.id.chkRemember);

        intent = new Intent(Login.this, Home.class);
        thuThuDAO = new ThuThuDAO(getApplicationContext());
        restoringPreferences();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePreferences();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtPass.setText("");
                edtUser.setText("");
            }
        });
    }
    private void restoringPreferences() {
        SharedPreferences preferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        boolean chk = preferences.getBoolean("check", false);
        if(chk){
            String user = preferences.getString("user", "");
            String pass = preferences.getString("pass", "");
            chk = true;
            edtUser.setText(user);
            edtPass.setText(pass);
        }
        chkRemember.setChecked(chk);
    }

    private void savePreferences() {
        String user = edtUser.getText().toString();
        String pass = edtPass.getText().toString();
        boolean chk = chkRemember.isChecked();
        SharedPreferences preferences = getSharedPreferences("dataLogin",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(user.equals("")||pass.equals(""))
            Toast.makeText(Login.this, "Vui lòng nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
        else{
            Boolean checkuserpass = thuThuDAO.checkusernamepassword(user, pass);
            if(checkuserpass){
                //Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                if(chk){
                    editor.putString("user", user);
                    editor.putString("pass", pass);
                }else {
                    editor.clear();
                }
                editor.putBoolean("check", chk);
                editor.apply();
                intent.putExtra("USERNAME", user);
                startActivity(intent);
            }else{
                Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

}