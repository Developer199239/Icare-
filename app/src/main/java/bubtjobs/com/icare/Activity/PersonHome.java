package bubtjobs.com.icare.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import bubtjobs.com.icare.Fragment.Add_Profile;
import bubtjobs.com.icare.Fragment.Add_diet;
import bubtjobs.com.icare.Fragment.ChangePassword;
import bubtjobs.com.icare.Fragment.GeneralInfo;
import bubtjobs.com.icare.Fragment.View_All_Profile;
import bubtjobs.com.icare.R;

public class PersonHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Fragment currentFragment;
    FragmentManager manager;
    FragmentTransaction transaction;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_home);

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

        currentFragment=new GeneralInfo();
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.nav_home)
        {
            Intent intent=new Intent(this,Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if(id==R.id.nav_view_profiles)
        {
            toolbar.setTitle("View Profiles");
            currentFragment=new View_All_Profile();
        }
        else if(id==R.id.nav_add_diet)
        {
            toolbar.setTitle("Add Diet");
            currentFragment=new Add_diet();
        }
        else if(id==R.id.nav_view_diet)
        {

        }
        else if(id==R.id.nav_add_vaccination)
        {

        }
        else if(id==R.id.nav_all_vaccination)
        {

        }
        else if(id==R.id.nav_personal_doctor)
        {

        }
        else if(id==R.id.nav_medical_history)
        {

        }
        else if(id==R.id.nav_logout)
        {
            Intent intent=new Intent(PersonHome.this,MainActivity.class);
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
