package ca.nait.benedict.mymoneyandi;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import Model.Expense;
import Model.Income;


public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseViewHolder>
//implements Filterable
{
    private Context context;
    private ArrayList<Expense> listExpense;
    private ArrayList<Expense> mArrayList;
    static DBManager database;

    ExpenseAdapter(Context context, ArrayList<Expense> listExpense)
    {
        this.context = context;
        this.listExpense = listExpense;
        this.mArrayList = listExpense;
        database = new DBManager(context);
        // manager = new DBManager(getActivity());
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_recycler_data, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position)
    {
        Expense expense = listExpense.get(position);
        holder.tvAmount.setText(expense.getAmount() + "");
        holder.tvType.setText(expense.getType());
        holder.tvNote.setText(expense.getNote());
        holder.tvDate.setText(expense.getDate());
        holder.editExpense.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                editTaskDialog(expense);
            }
        });
        holder.deleteExpense.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                database.deleteIncome(expense.getUserId());
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });

    }


    @Override
    public int getItemCount()
    {
        return listExpense.size();
    }
    private void editTaskDialog(final Expense expense)
    {


        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.custom_layout_for_data_update, null);



        final EditText amountField = subView.findViewById(R.id.amount_edit);
        final EditText typeField = subView.findViewById(R.id.type_edit);
        final EditText noteField = subView.findViewById(R.id.note_edit);


        if (expense != null)
        {

            amountField.setText(String.valueOf(expense.getAmount()));
            typeField.setText(expense.getType());
            noteField.setText(expense.getNote());

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Expense");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("EDIT EXPENSE", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                final int amount = Integer.parseInt(amountField.getText().toString());
                final String type = typeField.getText().toString();
                final String note = noteField.getText().toString();
                final String date = noteField.getText().toString();

                if (TextUtils.isEmpty(type))
                {
                    Toast.makeText(context, "Check the value for TYPE", Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(note))
                {
                    Toast.makeText(context, "Check the value for NOTE", Toast.LENGTH_LONG).show();
                } else
                {
                    database.updateExpense(new
                            Expense(Objects.requireNonNull(expense).getUserId(), 0, amount, type, note, date));
                    ((Activity) context).finish();
                    context.startActivity(((Activity)
                            context).getIntent());
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();

    }
}
