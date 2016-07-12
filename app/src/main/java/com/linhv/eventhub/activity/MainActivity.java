package com.linhv.eventhub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.linhv.eventhub.R;
import com.linhv.eventhub.fragment.EventStoragedFragment;
import com.linhv.eventhub.fragment.HomeFragment;
import com.linhv.eventhub.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewHolder = new ViewHolder();
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
        viewHolder.toolbar.setTitle("Trang chủ");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main, new HomeFragment())
                .commit();
        viewHolder.nvMenu.setCheckedItem(R.id.nav_home);
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


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_search) {
//            viewHolder.toolbar.setTitle("Tìm kiếm sự kiện");
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.frame_main, new SearchFragment())
//                    .commit();
            Intent intent = new Intent(MainActivity.this,SearchActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


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
        if (id == R.id.nav_home) {
            viewHolder.toolbar.setTitle("Trang chủ");
            fragment = new HomeFragment();
        } else if (id == R.id.nav_save) {
            viewHolder.toolbar.setTitle("Trang lưu");
            fragment = new EventStoragedFragment();
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
    }
}
