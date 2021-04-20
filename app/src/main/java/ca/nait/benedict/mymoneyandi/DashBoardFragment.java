package ca.nait.benedict.mymoneyandi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Model.Expense;
import Model.Income;
import Model.Savings;

public class DashBoardFragment extends Fragment
{
    static SQLiteDatabase database;
    static DBManager manager;
    static final String TAG = "MainActivity";
    ProgressDialog Dialog;

    Context context;

    RecyclerView recyclerView;


    //Floating Button
    FloatingActionButton fab_main_btn;
    FloatingActionButton fab_income_btn;
    FloatingActionButton fab_expense_btn;
    FloatingActionButton fab_savings_btn;

    //Floating Text
    TextView fab_income_txt;
    TextView fab_expense_txt;
    TextView fab_savings_txt;

    //boolean
    boolean isOpen = false;

    //Animation
    Animation FadeOpen, FadeClose;



    public static DashBoardFragment newInstance(String param1, String param2)
    {
        DashBoardFragment fragment = new DashBoardFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //Recycler View
    RecyclerView mRecyclerIncome;
    RecyclerView mRecyclerExpense;
    RecyclerView mRecyclerSavings;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_dash_board, container, false);

        manager = new DBManager(getActivity().getApplicationContext());



        //connecting floating button
        fab_main_btn=myView.findViewById(R.id.fb_main_plus_btn);
        fab_income_btn=myView.findViewById(R.id.income_ft_btn);
        fab_expense_btn=myView.findViewById(R.id.expense_ft_btn);
        fab_savings_btn=myView.findViewById(R.id.savings_ft_btn);

        //connect floating text.
        fab_income_txt=myView.findViewById(R.id.income_ft_text);
        fab_expense_txt=myView.findViewById(R.id.expense_ft_text);
        fab_savings_txt=myView.findViewById(R.id.savings_ft_text);

        //Animation Connect
        FadeOpen= AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
        FadeClose= AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);

        //Recycler
        mRecyclerIncome = myView.findViewById(R.id.recycler_income);
        mRecyclerExpense = myView.findViewById(R.id.recycler_expense);
        mRecyclerSavings = myView.findViewById(R.id.recycler_savings);

        fab_main_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addData();
                if (isOpen)
                {
                    fab_income_btn.startAnimation(FadeClose);
                    fab_expense_btn.startAnimation(FadeClose);
                    fab_savings_btn.startAnimation(FadeClose);

                    fab_income_btn.setClickable(false);
                    fab_expense_btn.setClickable(false);
                    fab_savings_btn.setClickable(false);

                    fab_income_txt.startAnimation(FadeClose);
                    fab_expense_txt.startAnimation(FadeClose);
                    fab_savings_txt.startAnimation(FadeClose);

                    fab_income_txt.setClickable(false);
                    fab_expense_txt.setClickable(false);
                    fab_savings_txt.setClickable(false);
                    isOpen=false;
                }
                else
                {
                    fab_income_btn.startAnimation(FadeOpen);
                    fab_expense_btn.startAnimation(FadeOpen);
                    fab_savings_btn.startAnimation(FadeOpen);

                    fab_income_btn.setClickable(true);
                    fab_expense_btn.setClickable(true);
                    fab_savings_btn.setClickable(true);

                    fab_income_txt.startAnimation(FadeOpen);
                    fab_expense_txt.startAnimation(FadeOpen);
                    fab_savings_txt.startAnimation(FadeOpen);

                    fab_income_txt.setClickable(true);
                    fab_expense_txt.setClickable(true);
                    fab_savings_txt.setClickable(true);
                    isOpen=true;

                }
            }
        });


        manager = new DBManager(getActivity());

        //Dashboard Income Recycler
        LinearLayoutManager layoutManagerIncome = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerIncome.setStackFromEnd(true);
        layoutManagerIncome.setReverseLayout(true);
        mRecyclerIncome.setHasFixedSize(true);
        mRecyclerIncome.setLayoutManager(layoutManagerIncome);

        manager = new DBManager(getActivity());
        ArrayList<Income> allIncome = manager.listIncomes();
        if (allIncome.size() > 0)
        {
            myView.setVisibility(View.VISIBLE);
            IncomeAdapter mAdapter = new IncomeAdapter(context, allIncome);
            mRecyclerIncome.setAdapter(mAdapter);
        }
        else
        {
            myView.setVisibility(View.GONE);
        }


        //Dashboard Expense Recycler
        LinearLayoutManager layoutManagerExpense = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerExpense.setStackFromEnd(true);
        layoutManagerExpense.setReverseLayout(true);
        mRecyclerExpense.setHasFixedSize(true);
        mRecyclerExpense.setLayoutManager(layoutManagerExpense);

        manager = new DBManager(getActivity());
        ArrayList<Expense> allExpense = manager.listExpenses();
        if (allExpense.size() > 0) {
            myView.setVisibility(View.VISIBLE);
            ExpenseAdapter mAdapter = new ExpenseAdapter(context, allExpense);
            mRecyclerExpense.setAdapter(mAdapter);
        }
        else {
            myView.setVisibility(View.GONE);
        }


        //Dashboard Savings Recycler
        LinearLayoutManager layoutManagerSavings = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerSavings.setStackFromEnd(true);
        layoutManagerSavings.setReverseLayout(true);
        mRecyclerSavings.setHasFixedSize(true);
        mRecyclerSavings.setLayoutManager(layoutManagerSavings);

        manager = new DBManager(getActivity());
        ArrayList<Savings> allSavings = manager.listSavings();
        if (allSavings.size() > 0) {
            myView.setVisibility(View.VISIBLE);
            SavingsAdapter mAdapter = new SavingsAdapter(context, allSavings);
            mRecyclerSavings.setAdapter(mAdapter);
        }
        else {
            myView.setVisibility(View.GONE);
        }

        return myView;
    }
    //Floating button animation
    private void ftAnimation()
    {
        if (isOpen)
        {
            fab_income_btn.startAnimation(FadeClose);
            fab_expense_btn.startAnimation(FadeClose);
            fab_savings_btn.startAnimation(FadeClose);

            fab_income_btn.setClickable(false);
            fab_expense_btn.setClickable(false);
            fab_savings_btn.setClickable(false);

            fab_income_txt.startAnimation(FadeClose);
            fab_expense_txt.startAnimation(FadeClose);
            fab_savings_txt.startAnimation(FadeClose);

            fab_income_txt.setClickable(false);
            fab_expense_txt.setClickable(false);
            fab_savings_txt.setClickable(false);
            isOpen=false;
        }
        else
        {
            fab_income_btn.startAnimation(FadeOpen);
            fab_expense_btn.startAnimation(FadeOpen);
            fab_savings_btn.startAnimation(FadeOpen);

            fab_income_btn.setClickable(true);
            fab_expense_btn.setClickable(true);
            fab_savings_btn.setClickable(true);

            fab_income_txt.startAnimation(FadeOpen);
            fab_expense_txt.startAnimation(FadeOpen);
            fab_savings_txt.startAnimation(FadeOpen);

            fab_income_txt.setClickable(true);
            fab_expense_txt.setClickable(true);
            fab_savings_txt.setClickable(true);
            isOpen=true;

        }
    }
    private void addData()
    {
        // Fab Button income
        fab_income_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                incomeDataInsert();
            }
        });
        fab_expense_btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                expenseDataInsert();
            }
        });
        fab_savings_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                saveDataInsert();
            }
        });
    }
    public void incomeDataInsert(){
        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View iview=inflater.inflate(R.layout.custom_layout_for_data_insert,null);
        mydialog.setView(iview);
        AlertDialog dialog=mydialog.create();

        dialog.setCancelable(false);

        EditText editAmount=iview.findViewById(R.id.amount_edit);
        EditText editType=iview.findViewById(R.id.type_edit);
        EditText editNote=iview.findViewById(R.id.note_edit);

        Button btnSave = iview.findViewById(R.id.btn_save);
        Button btnCancel = iview.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String type=editType.getText().toString().trim();
                String amount=editAmount.getText().toString().trim();
                String note=editNote.getText().toString().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new Date());

                if(TextUtils.isEmpty(type))
                {
                    editType.setError("Required Field...");
                    return;
                }
                if(TextUtils.isEmpty(amount))
                {
                    editAmount.setError("Required Field...");
                    return;
                }
                int inamount=Integer.parseInt(amount);
                if(TextUtils.isEmpty(note))
                {
                    editNote.setError("Required Field...");
                    return;
                }
                ContentValues values = new ContentValues();
                values.put(DBManager.C_UID, getId());
                values.put(DBManager.C_AMOUNT, inamount);
                values.put(DBManager.C_TYPE, type);
                values.put(DBManager.C_NOTE, note);
                values.put(String.valueOf(DBManager.C_DATE), date);

                try
                {
                    database = manager.getWritableDatabase();
                    database.insertOrThrow(DBManager.TABLE_INCOME, null, values);
                    Toast.makeText(getActivity(), "You added: " + values, Toast.LENGTH_SHORT).show();
                    Dialog.dismiss();
                    //Dialog.show();
                    database.close();

                } catch (Exception e)
                {
                    Log.d(TAG, "Error" + e);
                    Toast.makeText(getActivity(), "Error: " + e, Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ftAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();


    }
    public void expenseDataInsert(){
        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View eview=inflater.inflate(R.layout.custom_layout_for_data_insert,null);
        mydialog.setView(eview);
        AlertDialog dialog=mydialog.create();

        dialog.setCancelable(false);

        EditText editAmount=eview.findViewById(R.id.amount_edit);
        EditText editType=eview.findViewById(R.id.type_edit);
        EditText editNote=eview.findViewById(R.id.note_edit);

        Button btnSave = eview.findViewById(R.id.btn_save);
        Button btnCancel = eview.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String etype=editType.getText().toString().trim();
                String eamount=editAmount.getText().toString().trim();
                String enote=editNote.getText().toString().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new Date());


                if(TextUtils.isEmpty(etype))
                {
                    editType.setError("Required Field...");
                    return;
                }
                if(TextUtils.isEmpty(eamount))
                {
                    editAmount.setError("Required Field...");
                    return;
                }
                int examount=Integer.parseInt(eamount);
                if(TextUtils.isEmpty(enote))
                {
                    editNote.setError("Required Field...");
                    return;
                }
                ContentValues values = new ContentValues();
                values.put(DBManager.C_UID, getId());
                values.put(DBManager.C_AMOUNT, examount);
                values.put(DBManager.C_TYPE, etype);
                values.put(DBManager.C_NOTE, enote);
                values.put(String.valueOf(DBManager.C_DATE), date);

                try
                {
                    database = manager.getWritableDatabase();
                    database.insertOrThrow(DBManager.TABLE_EXPENSE, null, values);
                    Toast.makeText(getActivity(), "You added: " + values, Toast.LENGTH_SHORT).show();
                    Dialog.dismiss();
                    Dialog.show();
                    database.close();

                } catch (Exception e)
                {
                    Log.d(TAG, "Error" + e);
                    Toast.makeText(getActivity(), "Error: " + e, Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ftAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();


    }
    public void saveDataInsert(){
        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View sview=inflater.inflate(R.layout.custom_layout_for_data_insert,null);
        mydialog.setView(sview);
        AlertDialog dialog=mydialog.create();

        dialog.setCancelable(false);

        EditText editAmount=sview.findViewById(R.id.amount_edit);
        EditText editType=sview.findViewById(R.id.type_edit);
        EditText editNote=sview.findViewById(R.id.note_edit);

        Button btnSave = sview.findViewById(R.id.btn_save);
        Button btnCancel = sview.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String stype=editType.getText().toString().trim();
                String samount=editAmount.getText().toString().trim();
                String snote=editNote.getText().toString().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new Date());

                if(TextUtils.isEmpty(stype))
                {
                    editType.setError("Required Field...");
                    return;
                }
                if(TextUtils.isEmpty(samount))
                {
                    editAmount.setError("Required Field...");
                    return;
                }
                int ouramount=Integer.parseInt(samount);
                if(TextUtils.isEmpty(snote))
                {
                    editNote.setError("Required Field...");
                    return;
                }
                ContentValues values = new ContentValues();
                values.put(DBManager.C_UID, getId());
                values.put(DBManager.C_AMOUNT, ouramount);
                values.put(DBManager.C_TYPE, stype);
                values.put(DBManager.C_NOTE, snote);
                values.put(String.valueOf(DBManager.C_DATE), date);

                try
                {
                    database = manager.getWritableDatabase();
                    database.insertOrThrow(DBManager.TABLE_SAVE, null, values);
                    Toast.makeText(getActivity(), "You added: " + values, Toast.LENGTH_SHORT).show();
                    //Dialog.dismiss();
//                    Dialog.show();
                    database.close();

                } catch (Exception e)
                {
                    Log.d(TAG, "Error" + e);
                    Toast.makeText(getActivity(), "Error: " + e, Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ftAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    @Override
    public void onStart()
    {
        super.onStart();

    }
}