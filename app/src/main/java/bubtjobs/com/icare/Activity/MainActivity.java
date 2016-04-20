package bubtjobs.com.icare.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bubtjobs.com.icare.Fragment.Login;
import bubtjobs.com.icare.R;

public class MainActivity extends AppCompatActivity {

    FragmentManager manager;
    Fragment currentFrag;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        // fragment load
        currentFrag=new Login();

        manager=getFragmentManager();
        transaction=manager.beginTransaction();
        transaction.add(R.id.myFragment,currentFrag);
        transaction.commit();


    }
}
