package com.linhv.eventhub.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.linhv.eventhub.R;
import com.linhv.eventhub.custom.RoundedImageView;
import com.linhv.eventhub.fragment.EventStoragedFragment;
import com.linhv.eventhub.fragment.HomeFragment;
import com.linhv.eventhub.fragment.MyTicketFragment;
import com.linhv.eventhub.fragment.NotificationFragment;
import com.linhv.eventhub.fragment.OwnEventFragment;
import com.linhv.eventhub.fragment.SearchFragment;
import com.linhv.eventhub.model.response_model.LoginResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewHolder viewHolder;
    private SearchView.OnQueryTextListener queryTextListener;
    private SearchView searchView = null;
    private RestService restService;
    private String userId;
    boolean isOrganizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadUserInfor();
        menuFatory();
    }

    private void init(){
        viewHolder = new ViewHolder();
        restService = new RestService();
        viewHolder.toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(viewHolder.toolbar);
        viewHolder.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewHolder.layoutContentMain = (CoordinatorLayout) findViewById(R.id.main_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, viewHolder.drawerLayout, viewHolder.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                viewHolder.layoutContentMain.setTranslationX(slideOffset * drawerView.getWidth());
                viewHolder.drawerLayout.bringChildToFront(drawerView);
                viewHolder.drawerLayout.requestLayout();
            }
        };
        viewHolder.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        viewHolder.nvMenu = (NavigationView) findViewById(R.id.nav_view);
        viewHolder.nvMenu.setNavigationItemSelectedListener(this);
        viewHolder.nvMenu.setCheckedItem(R.id.nav_home);
        viewHolder.headerView = viewHolder.nvMenu.inflateHeaderView(R.layout.nav_header_main);
        viewHolder.ivAvatar = (RoundedImageView) viewHolder.headerView.findViewById(R.id.iv_user_avatar);
        viewHolder.tvFullname = (TextView) viewHolder.headerView.findViewById(R.id.txt_user_fullname);
        viewHolder.tvEmail = (TextView) viewHolder.headerView.findViewById(R.id.txt_user_email);
        isOrganizer = DataUtils.getINSTANCE(this).getmPreferences().getBoolean(QuickSharePreferences.SHARE_IS_ORGANIZER,false);
        userId = DataUtils.getINSTANCE(getApplicationContext()).getmPreferences().getString(QuickSharePreferences.SHARE_USERID,"");

    }

    private void loadUserInfor(){
        restService.getUserService().getUserInfo(userId, new Callback<LoginResponseModel>() {
            @Override
            public void success(LoginResponseModel responseModel, Response response) {
                if (responseModel.isSucceed()) {
                    SharedPreferences.Editor editor = DataUtils.getINSTANCE(getApplicationContext()).getmPreferences().edit();
                    boolean isOrganizer = responseModel.getUser().getRole().trim().toUpperCase().equalsIgnoreCase("Organizer".trim().toUpperCase());
                    editor.putBoolean(QuickSharePreferences.SHARE_IS_ORGANIZER,isOrganizer);
                    editor.commit();
                    viewHolder.tvFullname.setText(responseModel.getUser().getFullName());
                    viewHolder.tvEmail.setText(responseModel.getUser().getEmail());
                    String url = responseModel.getUser().getImageUrl();
                    if (url!=null){
                        if (!url.contains("http")) {
                            url = DataUtils.URL + url;
                        }
                    }else{
                        url = "";
                    }

                    Picasso.with(MainActivity.this).load(Uri.parse(url))
                            .placeholder(R.drawable.image_default_avatar)
                            .error(R.drawable.image_default_avatar)
                            .into(viewHolder.ivAvatar);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "Kiểm tra lại kết nối internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void menuFatory(){
        Menu menu = viewHolder.nvMenu.getMenu();
        Fragment fragment = null;
        if (isOrganizer){
            MenuItem item = menu.findItem(R.id.nav_home).setVisible(false);
            item = menu.findItem(R.id.nav_save).setVisible(false);
            item=menu.findItem(R.id.nav_my_ticket).setVisible(false);
            item = menu.findItem(R.id.nav_own_event).setVisible(true);
            getSupportActionBar().setTitle("Sự kiện của tôi");
            fragment = new OwnEventFragment();
        }else{
            MenuItem item = menu.findItem(R.id.nav_home).setVisible(true);
            item = menu.findItem(R.id.nav_save).setVisible(true);
            item=menu.findItem(R.id.nav_my_ticket).setVisible(true);
            item = menu.findItem(R.id.nav_own_event).setVisible(false);
            getSupportActionBar().setTitle("Trang chủ");
            fragment = new HomeFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        switch (id){
            case R.id.nav_home:
                viewHolder.toolbar.setTitle("Trang chủ");
                fragment = new HomeFragment();
                break;
            case R.id.nav_save:
                viewHolder.toolbar.setTitle("Sự kiện đã lưu");
                fragment = new EventStoragedFragment();
                break;
            case R.id.nav_own_event:
                viewHolder.toolbar.setTitle("Sự kiện của tôi");
                fragment = new OwnEventFragment();
                break;
            case R.id.nav_my_ticket:
                viewHolder.toolbar.setTitle("Vé của tôi");
                fragment = new MyTicketFragment();
                break;
            case R.id.nav_notice:
                viewHolder.toolbar.setTitle("Thông báo");
                fragment = new NotificationFragment();
                break;
            case R.id.nav_logout:
                DataUtils.getINSTANCE(getApplicationContext()).getmPreferences().edit().clear().commit();
                FacebookSdk.sdkInitialize(getApplicationContext());
                LoginManager.getInstance().logOut();
                Intent intent  = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in,R.anim.right_out);
                finish();
                return true;

        }
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_main, fragment)
                    .commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class ViewHolder {
        DrawerLayout drawerLayout;
        NavigationView nvMenu;
        CoordinatorLayout layoutContentMain;
        Toolbar toolbar;
        RoundedImageView ivAvatar;
        TextView tvFullname;
        TextView tvEmail;
        public View headerView;
    }
}
