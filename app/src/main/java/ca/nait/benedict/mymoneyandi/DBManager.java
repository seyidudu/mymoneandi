package ca.nait.benedict.mymoneyandi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Model.Expense;
import Model.Income;
import Model.Savings;

import static ca.nait.benedict.mymoneyandi.DashBoardFragment.database;

public class DBManager extends SQLiteOpenHelper
{
    //public DBManager sInstance;
    static final String TAG = "DBManager";
    static final String DB_NAME = "MyMoneyAnDI.db";
    static final int DB_VERSION = 1;
    static final String TABLE_USERS = "Users";
    static final String TABLE_INCOME = "Income";
    static final String TABLE_EXPENSE = "Expense";
    static final String TABLE_SAVE = "Savings";

    static final String C_ID = BaseColumns._ID;
    static final String C_EMAIL = "email";
    static final String C_PASSWORD = "password";

    static final String  C_UID = "userId";
    static final String C_AMOUNT = "amount";
    static final String C_TYPE = "type";
    static final String C_NOTE = "note";
    static final String C_DATE = "date";



    List<Income> incomeList = new ArrayList<>();
    List<Expense> expenseList = new ArrayList<>();


    public DBManager (Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //User Table
        String sql = String.format("create table %s (%s integer primary key autoincrement, %s text, %s text) ", TABLE_USERS, C_UID, C_EMAIL, C_PASSWORD);
        Log.d(TAG, sql);
        database.execSQL(sql);

        String isql = String.format("create table %s (%s integer primary key autoincrement, %s integer, %s text, %s text, %s text, %s text) ", TABLE_INCOME, C_ID, C_UID, C_AMOUNT, C_TYPE, C_NOTE, C_DATE);
        Log.d(TAG, isql);
        database.execSQL(isql);
        String esql = String.format("create table %s (%s integer primary key autoincrement, %s integer, %s text, %s text, %s text, %s text) ", TABLE_EXPENSE, C_ID, C_UID, C_AMOUNT, C_TYPE, C_NOTE, C_DATE);
        Log.d(TAG, esql);
        database.execSQL(esql);
        String ssql = String.format("create table %s (%s integer primary key autoincrement, %s integer, %s text, %s text, %s text, %s text) ", TABLE_SAVE, C_ID, C_UID, C_AMOUNT, C_TYPE, C_NOTE, C_DATE);
        Log.d(TAG, ssql);
        database.execSQL(ssql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL("drop table if exists " + TABLE_USERS );
        database.execSQL("drop table if exists " + TABLE_INCOME);
        database.execSQL("drop table if exists " + TABLE_EXPENSE);
        database.execSQL("drop table if exists " + TABLE_SAVE);
        Log.d(TAG, "onUpdated");
        onCreate(database);
        //getIncTotalOfAmount();
        //getExpTotalOfAmount();
    }
    ArrayList<Income> listIncomes()
    {
        String sql = "select * from " + TABLE_INCOME;
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Income> storeIncomes = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int LogId = Integer.parseInt(cursor.getString(0));
                int userId = Integer.parseInt(cursor.getString(1));
                //Log.d("uid", "uid");
                //String uid = cursor.getString(0);
                int amount = Integer.parseInt(cursor.getString(2));
                String type = cursor.getString(3);
                String note = cursor.getString(4);
                String date = cursor.getString(5);
                storeIncomes.add(new Income(userId, LogId, amount, type, note, date));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeIncomes;

    }
    ArrayList<Expense> listExpenses()
    {
        String sql = "select * from " + TABLE_EXPENSE;
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Expense> storeExpenses = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int LogId = Integer.parseInt(cursor.getString(0));
                int userId = Integer.parseInt(cursor.getString(1));

                //String uid = cursor.getString(0);
                int amount = Integer.parseInt(cursor.getString(2));
                String type = cursor.getString(3);
                String note = cursor.getString(4);
                String date = cursor.getString(5);
                storeExpenses.add(new Expense(userId, LogId, amount, type, note, date));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeExpenses;
    }
    ArrayList<Savings> listSavings()
    {
        String sql = "select * from " + TABLE_SAVE;
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Savings> storeSavings = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {

                int LogId = Integer.parseInt(cursor.getString(0));
                int userId = Integer.parseInt(cursor.getString(1));

                //String uid = cursor.getString(0);
                int amount = Integer.parseInt(cursor.getString(2));
                String type = cursor.getString(3);
                String note = cursor.getString(4);
                String date = cursor.getString(5);
                storeSavings.add(new Savings(userId, LogId, amount, type, note, date));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeSavings;
    }

    public int getExpTotalOfAmount()
    {
        int sum = 0;
        Cursor c = database.rawQuery("SELECT amount FROM " + TABLE_SAVE, null);
        while (c.moveToNext()) {
            sum += Integer.parseInt(c.getString(2));
        }
        c.close();
        return sum;
    }
    public boolean insertUser(String email, String password)
    {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);


        database.insert(TABLE_USERS, null, contentValues);
        return true;
    }
    void updateIncome(Income income) {
        ContentValues values = new ContentValues();
        values.put(C_TYPE, income.getType());
        values.put(C_AMOUNT, income.getAmount()+"");
        values.put(C_NOTE, income.getType());
        SQLiteDatabase database = this.getWritableDatabase();
        database.update(TABLE_INCOME, values, C_ID + " = ?", new String[]{String.valueOf(income.getUserId())});
    }
    void deleteIncome(int uid) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_INCOME, C_ID + " = ?", new String[]{String.valueOf(uid)});
    }
    void updateExpense(Expense expense) {
        ContentValues values = new ContentValues();
        values.put(C_TYPE, expense.getType());
        values.put(C_AMOUNT, expense.getAmount()+"");
        values.put(C_NOTE, expense.getType());
        SQLiteDatabase database = this.getWritableDatabase();
        database.update(TABLE_EXPENSE, values, C_ID + " = ?", new String[]{String.valueOf(expense.getUserId())});
    }
    void deleteExpense(int userId) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_EXPENSE, C_ID + " = ?", new String[]{String.valueOf(userId)});
    }
    void updateSavings(Savings saving) {
        ContentValues values = new ContentValues();
        values.put(C_TYPE, saving.getType());
        values.put(C_AMOUNT, saving.getAmount()+"");
        values.put(C_NOTE, saving.getType());
        SQLiteDatabase database = this.getWritableDatabase();
        database.update(TABLE_SAVE, values, C_ID + " = ?", new String[]{String.valueOf(saving.getUserId())});
    }
    void deleteSavings(int userId) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_SAVE, C_ID + " = ?", new String[]{String.valueOf(userId)});
    }

}
