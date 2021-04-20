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

import Model.Income;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeViewHolder>
        //implements Filterable
{
    private Context context;
    private ArrayList<Income> listIncome;
    private ArrayList<Income> mArrayList;
    static DBManager database;

    IncomeAdapter(Context context, ArrayList<Income> listIncome) {
        this.context = context;
        this.listIncome = listIncome;
        this.mArrayList = listIncome;
        database = new DBManager(context);

    }
    @Override
    public IncomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_recycler_data, parent, false);
        return new IncomeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position)
    {
        final Income income = listIncome.get(position);
        holder.tvAmount.setText(income.getAmount()+"");
        holder.tvType.setText(income.getType());
        holder.tvNote.setText(income.getNote());
        holder.tvDate.setText(income.getDate());
        holder.editIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(income);
            }
        });
        holder.deleteIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.deleteIncome(income.getUserId());
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });

    }
    @Override
    public int getItemCount()
    {
        return listIncome.size();
    }
    private void editTaskDialog(final Income income) {


        LayoutInflater inflater = LayoutInflater.from(context);
        View subView=inflater.inflate(R.layout.custom_layout_for_data_update,null);

        final EditText amountField = subView.findViewById(R.id.amount_edit);
        final EditText typeField = subView.findViewById(R.id.type_edit);
        final EditText noteField = subView.findViewById(R.id.note_edit);


        if (income != null) {

            amountField.setText(String.valueOf(income.getAmount()));
            typeField.setText(income.getType());
            noteField.setText(income.getNote());

        }
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Edit Income");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("EDIT INCOME", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final int amount = Integer.parseInt(amountField.getText().toString());
                final String type = typeField.getText().toString();
                final String note = noteField.getText().toString();
                final String date = noteField.getText().toString();

                if (TextUtils.isEmpty(type))
                {
                    Toast.makeText(context, "The value for TYPE", Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(note))
                {
                    Toast.makeText(context, "The value for NOTE", Toast.LENGTH_LONG).show();
                }
                else {
                    database.updateIncome(new
                            Income(Objects.requireNonNull(income).getUserId(), 0, amount, type, note, date));
                    ((Activity) context).finish();
                    context.startActivity(((Activity)
                            context).getIntent());
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled",Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}

