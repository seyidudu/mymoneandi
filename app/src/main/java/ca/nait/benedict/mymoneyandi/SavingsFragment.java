package ca.nait.benedict.mymoneyandi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import Model.Expense;
import Model.Savings;

import static ca.nait.benedict.mymoneyandi.DBManager.TABLE_SAVE;

public class SavingsFragment extends Fragment
{
    static SQLiteDatabase database;
    static DBManager manager;

    static int currentItemIndex = 0;
    ArrayList<Savings> aListSaving;

    Context context;

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_savings, container, false);

        //String id = aListIncome.get(currentItemIndex).getId();
        //database = manager.getReadableDatabase();

        recyclerView=myview.findViewById(R.id.recycler_id_savings);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);




        manager = new DBManager(getActivity());
        ArrayList<Savings> allSavings = manager.listSavings();
        if (allSavings.size() > 0) {
            myview.setVisibility(View.VISIBLE);
            SavingsAdapter mAdapter = new SavingsAdapter(context, allSavings);
            recyclerView.setAdapter(mAdapter);
        }
        else {
            myview.setVisibility(View.GONE);

        }
        return myview;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        //listSavings();
    }
//    public int getIncTotalOfAmount() {
//        int sum = 0;
//        Cursor c = database.rawQuery("SELECT amount FROM " + TABLE_INCOME, null);
//        while (c.moveToNext()) {
//            sum += Integer.parseInt(c.getString(0));
//        }
//        c.close();
//        return sum;
    //}





}