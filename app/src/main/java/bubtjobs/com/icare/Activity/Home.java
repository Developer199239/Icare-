package bubtjobs.com.icare.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import bubtjobs.com.icare.Fragment.Add_Profile;
import bubtjobs.com.icare.Fragment.ChangePassword;
import bubtjobs.com.icare.Fragment.Emergency_Call;
import bubtjobs.com.icare.Fragment.View_All_Profile;
import bubtjobs.com.icare.R;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Fragment currentFragment;
    FragmentManager manager;
    FragmentTransaction transaction;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ICare");
        toolbar.setLogo(R.drawable.app_logo_2);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        init();


        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {

        currentFragment=new View_All_Profile();
        manager=getFragmentManager();
        transaction=manager.beginTransaction();
        transaction.add(R.id.homeFragment,currentFragment);
        transaction.commit();
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
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.nav_add_profile)
        {
            toolbar.setTitle("Add Profile");
            currentFragment = new Add_Profile();
        }
        else if(id==R.id.nav_view_profiles)
        {
            toolbar.setTitle("View Profiles");
            currentFragment=new View_All_Profile();
        }
        else if(id==R.id.nav_health_center)
        {

        }
        else if(id==R.id.nav_emergency_call)
        {
            toolbar.setTitle("Set Emergency Call");
            currentFragment=new Emergency_Call();
        }
        else if(id==R.id.nav_change_password)
        {
            toolbar.setTitle("Change Password");
            currentFragment=new ChangePassword();
        }
        else if(id==R.id.nav_logout)
        {
            Intent intent=new Intent(Home.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        transaction = manager.beginTransaction();
        transaction.replace(R.id.homeFragment, currentFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
