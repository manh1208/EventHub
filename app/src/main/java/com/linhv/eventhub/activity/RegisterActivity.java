package com.linhv.eventhub.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.linhv.eventhub.R;
import com.linhv.eventhub.custom.CustomImage;
import com.linhv.eventhub.model.request_model.LoginRequestModel;
import com.linhv.eventhub.model.request_model.RegisterRequestModel;
import com.linhv.eventhub.model.response_model.RegisterResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener{
    private ViewHolder viewHolder;
    private RestService restService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        viewHolder = new ViewHolder();
        restService = new RestService();
        progressDialog = new ProgressDialog(this);
        viewHolder.btnBack = (CustomImage) findViewById(R.id.lb_register_back);
        viewHolder.btnRegister = (ImageButton) findViewById(R.id.btn_register_next);
        viewHolder.etEmail = (EditText) findViewById(R.id.txt_register_email);
        viewHolder.etUsername = (EditText) findViewById(R.id.txt_register_username);
        viewHolder.etFullname = (EditText) findViewById(R.id.txt_register_fullname);
        viewHolder.etPassword = (EditText) findViewById(R.id.txt_register_password);
        viewHolder.etConfirmPassword = (EditText) findViewById(R.id.txt_register_confirm_password);
        viewHolder.btnBack.setOnClickListener(this);
        viewHolder.btnRegister.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }

    @Override
    public void onClick(View v) {
        DataUtils.getAlphaAmination(v);
        int id = v.getId();
        switch (id){
            case R.id.btn_register_next:
                
                String username = viewHolder.etUsername.getText().toString();
                String email = viewHolder.etEmail.getText().toString();
                String fullname = viewHolder.etFullname.getText().toString();
                String password = viewHolder.etPassword.getText().toString();
                String confirmpassword = viewHolder.etConfirmPassword.getText().toString();
                View focus = null;
                if (email.trim().length()<=0){
                    viewHolder.etEmail.setError("Xin hãy điền email");
                    focus = viewHolder.etEmail;
                }
                if (username.trim().length()<=0){
                    viewHolder.etUsername.setError("Xin hãy điền username");
                    if (focus==null){
                        focus = viewHolder.etUsername;
                    }
                }
                if (fullname.trim().length()<=0){
                    viewHolder.etFullname.setError("Xin hãy điền họ và tên");
                    if (focus==null){
                        focus = viewHolder.etFullname;
                    }
                }
                if (password.trim().length()<=0){
                    viewHolder.etPassword.setError("Xin hãy điền username");
                    if (focus==null){
                        focus = viewHolder.etPassword;
                    }
                }else if (password.trim().length()<6){
                    viewHolder.etPassword.setError("Mật khẩu phải lớn hơn hoặc bằng 6 ký tự");
                    if (focus==null){
                        focus = viewHolder.etPassword;
                    }
                }else if (!password.trim().equals(confirmpassword.trim())){
                    viewHolder.etConfirmPassword.setError("Mật khẩu xác nhận không giống với mật khẩu ở trên");
                    if (focus==null){
                        focus = viewHolder.etConfirmPassword;
                    }
                }
                if (focus!=null){
                    focus.requestFocus();
                }else{
                    progressDialog.show();
                    progressDialog.setMessage("Đang đăng ký tài khoản...");
                    RegisterRequestModel requestModel = new RegisterRequestModel(email,username,fullname,password);
                   restService.getUserService().register(requestModel, new Callback<RegisterResponseModel>() {
                       @Override
                       public void success(RegisterResponseModel responseModel, Response response) {
                           progressDialog.dismiss();
                           if (responseModel.isSucceed()){
                               Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                               onBackPressed();
                           }else{
                               Log.d("Login Activity", responseModel.getErrors().toString());
                               Toast.makeText(RegisterActivity.this, responseModel.getErrorsString(), Toast.LENGTH_SHORT).show();
                           }
                       }

                       @Override
                       public void failure(RetrofitError error) {
                           progressDialog.dismiss();
                           Toast.makeText(RegisterActivity.this, "Login fail. Please check your connection and try again.", Toast.LENGTH_SHORT).show();
                       }
                   });
                }
                break;
            case R.id.lb_register_back:
                onBackPressed();
                break;
        }
    }

    private class ViewHolder{
        EditText etEmail;
        EditText etUsername;
        EditText etFullname;
        EditText etPassword;
        EditText etConfirmPassword;
        ImageButton btnRegister;
        CustomImage btnBack;
    }
}
