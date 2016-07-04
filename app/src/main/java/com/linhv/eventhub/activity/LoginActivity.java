package com.linhv.eventhub.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.linhv.eventhub.R;
import com.linhv.eventhub.utils.DataUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewHolder = new ViewHolder();
        viewHolder.txtUsername = (EditText) findViewById(R.id.txt_login_username_email);
        viewHolder.txtPassword = (EditText) findViewById(R.id.txt_login_password);
        viewHolder.btnLogin = (ImageButton) findViewById(R.id.btn_login_next);
        viewHolder.txtRegister = (TextView) findViewById(R.id.lb_login_register);
        viewHolder.btnLogin.setOnClickListener(this);
        viewHolder.txtRegister.setOnClickListener(this);
//        viewHolder.btnLoginWithFacebook = (LoginButton) findViewById(R.id.btn_login_with_facebook);


    }

    @Override
    public void onClick(View v) {
        DataUtils.getAlphaAmination(v);
        int id = v.getId();
        switch (id) {
            case R.id.btn_login_next:
                String username = viewHolder.txtUsername.getText().toString();
                String password = viewHolder.txtPassword.getText().toString();
                View focus = null;
                if (username.trim().length() <= 0) {
                    viewHolder.txtUsername.setError("Xin hãy điền username hoặc email!");
                    focus = viewHolder.txtUsername;
                }
                if (password.trim().length() <= 0) {
                    viewHolder.txtPassword.setError("Xin hãy điền mật khẩu!");
                    if (focus == null)
                        focus = viewHolder.txtPassword;
                }
                if (focus != null) {
                    focus.requestFocus();
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                }
                break;
            case R.id.lb_login_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
        }
    }

    private final class ViewHolder {
        EditText txtUsername;
        EditText txtPassword;
        ImageButton btnLogin;
        TextView txtRegister;
//        LoginButton btnLoginWithFacebook;
    }
}
