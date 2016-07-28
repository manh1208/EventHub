package com.linhv.eventhub.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.linhv.eventhub.GCM.QuickstartPreferences;
import com.linhv.eventhub.GCM.RegistrationIntentService;
import com.linhv.eventhub.R;
import com.linhv.eventhub.model.User;
import com.linhv.eventhub.model.request_model.ExternalLoginRequestModel;
import com.linhv.eventhub.model.request_model.LoginRequestModel;
import com.linhv.eventhub.model.response_model.ExternalLoginResponseModel;
import com.linhv.eventhub.model.response_model.LoginResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 1994;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1995;
    ViewHolder viewHolder;
    private CallbackManager callbackManager;
    private User user;
    private RestService restService;
    private Context mContext;
    private ProgressDialog progressDialog;
    private GoogleApiClient mGoogleApiClient;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unit();

        viewHolder.btnLogin.setOnClickListener(this);
        viewHolder.txtRegister.setOnClickListener(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        viewHolder.btnLoginWithFacebook = (LoginButton) findViewById(R.id.btn_login_with_facebook);
        viewHolder.btnLoginWithFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        if (DataUtils.getINSTANCE(getApplicationContext()).getmPreferences().getString(QuickSharePreferences.SHARE_USERID, "").length() > 0) {
            login();
        }
        viewHolder.btnLoginWithFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;
            private Profile profile;

            @Override
            public void onSuccess(final LoginResult loginResult) {
                progressDialog.show();
                progressDialog.setMessage("Đang đăng nhập...");
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {

                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            profile = currentProfile;
                            user = new User(profile);
                            requestEmail(loginResult);
                            mProfileTracker.stopTracking();

                        }
                    };
                } else {

                    profile = Profile.getCurrentProfile();
                    user = new User(profile);
                    requestEmail(loginResult);
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Hủy đăng nhập", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Đăng nhập không thành công: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
//                .requestScopes(Plus.SCOPE_PLUS_PROFILE)
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();





    }

    private void unit() {
        mContext = this;
        restService = new RestService();
        viewHolder = new ViewHolder();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCanceledOnTouchOutside(false);
        viewHolder.txtUsername = (EditText) findViewById(R.id.txt_login_username_email);
        viewHolder.txtPassword = (EditText) findViewById(R.id.txt_login_password);
        viewHolder.btnLogin = (ImageButton) findViewById(R.id.btn_login_next);
        viewHolder.txtRegister = (TextView) findViewById(R.id.lb_login_register);
        viewHolder.btnLoginWithgoogle = (Button) findViewById(R.id.btn_login_with_google);
        viewHolder.btnLoginWithgoogle.setOnClickListener(this);
    }

    private void login() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();
    }

    private void requestEmail(LoginResult loginResult) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
//                            Profile profile = Profile.getCurrentProfile();
//                            user = new User(profile);
                    user.setEmail(object.getString("email"));
//                            user.setFacebookId(object.getString("id"));
//                            user.setName(object.getString("name"));
//                            user.setPhotoUri("http://graph.facebook.com/"+user.getFacebookId()+"/picture?type=large");
                    sendLoginToServer();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

    }

    private void sendLoginToServer() {
        final ExternalLoginRequestModel requestModel = new ExternalLoginRequestModel(user);
        restService.getUserService().externalLogin(requestModel, new Callback<ExternalLoginResponseModel>() {
            @Override
            public void success(ExternalLoginResponseModel responseModel, Response response) {
                progressDialog.dismiss();
                if (responseModel.isSucceed()) {
                    SharedPreferences.Editor editor = DataUtils.getINSTANCE(getApplicationContext()).getmPreferences().edit();
                    editor.putString(QuickSharePreferences.SHARE_USERID, responseModel.getUser().getId());

//                    boolean isOrganizer = responseModel.getUser().getRole().trim().toUpperCase().equalsIgnoreCase("Organizer".trim().toUpperCase());
                    editor.putBoolean(QuickSharePreferences.SHARE_IS_ORGANIZER,false);
                    editor.commit();
//                    SharedPreferences sharedPreferences =
//                            PreferenceManager.getDefaultSharedPreferences(mContext);
//                    // Start IntentService to register this application with GCM.
//                    sharedPreferences.edit().putString(QuickstartPreferences.USER_ID, externalLoginResponseModel.getUser().getUserId()).apply();
                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(mContext);
                    // Start IntentService to register this application with GCM.
                    sharedPreferences.edit().putString(QuickstartPreferences.USER_ID, responseModel.getUser().getId()).apply();
                    regisGCM();
                    login();
                } else {
//                    viewHolder.btnLoginWithFacebook;
                    LoginManager.getInstance().logOut();
                    Log.d("Login Activity", responseModel.getErrors().get(0).toString());
                    Toast.makeText(LoginActivity.this, responseModel.getErrors().get(0).toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                LoginManager.getInstance().logOut();
//                LoginManager.getInstance().logOut();
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Login fail. Please check your connection and try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            progressDialog.show();
            progressDialog.setMessage("Waiting for login");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInGoogleResult(result);

        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
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
                    restService.getUserService().login(new LoginRequestModel(username, password), new Callback<LoginResponseModel>() {
                        @Override
                        public void success(LoginResponseModel responseModel, Response response) {
                            if (responseModel.isSucceed()) {
                                SharedPreferences.Editor editor = DataUtils.getINSTANCE(getApplicationContext()).getmPreferences().edit();
                                editor.putString(QuickSharePreferences.SHARE_USERID, responseModel.getUser().getId());
                                boolean isOrganizer = responseModel.getUser().getRole().trim().toUpperCase().equalsIgnoreCase("Organizer".trim().toUpperCase());
                                editor.putBoolean(QuickSharePreferences.SHARE_IS_ORGANIZER,isOrganizer);
                                editor.commit();

                                SharedPreferences sharedPreferences =
                                        PreferenceManager.getDefaultSharedPreferences(mContext);
                                // Start IntentService to register this application with GCM.
                                sharedPreferences.edit().putString(QuickstartPreferences.USER_ID, responseModel.getUser().getId()).apply();
                                regisGCM();
                                login();
                            } else {
                                viewHolder.txtUsername.setError(responseModel.getMessage());
                                viewHolder.txtUsername.requestFocus();
//                                Toast.makeText(LoginActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }
                break;
            case R.id.lb_login_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;

            case R.id.btn_login_with_google:
                viewHolder.btnLoginWithgoogle.setEnabled(false);
                loginWithGoogle();
                break;
        }
    }

    private void loginWithGoogle() {
        DataUtils.mGoogleApiClient = mGoogleApiClient;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInGoogleResult(GoogleSignInResult result) {
//        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acc = result.getSignInAccount();
            user = new User();
            user.setFullName(acc.getDisplayName());
            user.setEmail(acc.getEmail());
            user.setImageUrl(acc.getPhotoUrl() != null ? acc.getPhotoUrl().toString() : "");
            sendLoginToServer();

//            Toast.makeText(LoginActivity.this, "You were login with google at "+acct.getDisplayName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "Fail to login with google. Please check your connection and try again.", Toast.LENGTH_SHORT).show();
        }
        viewHolder.btnLoginWithgoogle.setEnabled(true);
    }

    private void regisGCM() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
//                    loadUserProfile();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Something wrong. Please check your network and try again!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        registerReceiver();

        if (checkPlayServices()) {

            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        } else {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, "Application need google play service!. Please install google play services and try agains", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("LoginActivity", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
    }

    private final class ViewHolder {
        EditText txtUsername;
        EditText txtPassword;
        ImageButton btnLogin;
        TextView txtRegister;
        LoginButton btnLoginWithFacebook;
        Button btnLoginWithgoogle;
    }
}
