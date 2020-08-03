package edu.sharif.periodtracker;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.sharif.periodtracker.ui.calendar.CalendarFragment;
import edu.sharif.periodtracker.ui.content.ContentFragment;
import edu.sharif.periodtracker.ui.home.HomeFragment;
import edu.sharif.periodtracker.ui.report.ReportFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        loadFragment(new HomeFragment(), R.string.title_home);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                int title;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_content:
                        fragment = new ContentFragment();
                        title = R.string.title_content;
                        break;
                    case R.id.navigation_report:
                        fragment = new ReportFragment();
                        title = R.string.title_report;
                        break;
                    case R.id.navigation_calendar:
                        fragment = new CalendarFragment();
                        title = R.string.title_calendar;
                        break;
                    default:
                        fragment = new HomeFragment();
                        title = R.string.title_home;
                        break;
                }
                return loadFragment(fragment, title);
            }
        });
    }
    private boolean loadFragment(Fragment fragment, int title) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
            toolbar.setTitle(title);
            return true;
        }
        return false;
    }

}