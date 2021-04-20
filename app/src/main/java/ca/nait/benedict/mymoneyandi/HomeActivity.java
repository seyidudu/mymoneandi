package ca.nait.benedict.mymoneyandi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    //Fragment
    DashBoardFragment dashBoardFragment;
    IncomeFragment incomeFragment;
    ExpenseFragment expenseFragment;
    SavingsFragment savingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Money Manager");


        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        bottomNavigationView=findViewById(R.id.bottomNavigationBar);
        frameLayout=findViewById(R.id.main_frame);

        NavigationView navigationView =findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        dashBoardFragment=new DashBoardFragment();
        incomeFragment=new IncomeFragment();
        expenseFragment=new ExpenseFragment();
        savingsFragment=new SavingsFragment();

        setFragment(dashBoardFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
{
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.dashboard:
                setFragment(dashBoardFragment);
                bottomNavigationView.setItemBackgroundResource(R.color.dashboard_color);
                return true;
            case R.id.income:
                setFragment(incomeFragment);
                bottomNavigationView.setItemBackgroundResource(R.color.income_color);
                return true;
            case R.id.expense:
                setFragment(expenseFragment);
                bottomNavigationView.setItemBackgroundResource(R.color.expense_color);
                return true;
            case R.id.save:
                setFragment(savingsFragment);
                bottomNavigationView.setItemBackgroundResource(R.color.savings_color);
                return true;
            default:
            return false;
        }
    }
});

    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.END))
        {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        else
        {
            super.onBackPressed();
        }
    }
    public void displaySelectedListener(int itemId){

       Fragment fragment = null;
        switch(itemId)
        {
            case R.id.dashboard:
                fragment=new DashBoardFragment();
                break;
            case R.id.income:
                fragment=new IncomeFragment();
                break;
            case R.id.expense:
                fragment=new ExpenseFragment();
                break;
            case R.id.save:
                fragment=new SavingsFragment();
                break;
        }
        if(fragment != null)
        {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        displaySelectedListener(item.getItemId());
        return false;
    }
}