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
import Model.Savings;

public class SavingsAdapter extends RecyclerView.Adapter<SavingsViewHolder>
//implements Filterable
{
    private Context context;
    private ArrayList<Savings> listSaving;
    private ArrayList<Savings> mArrayList;
    static DBManager database;

    SavingsAdapter(Context context, ArrayList<Savings> listSavings)
    {
        this.context = context;
        this.listSaving = listSavings;
        this.mArrayList = listSavings;
        database = new DBManager(context);

    }

    @Override
    public SavingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.savings_recycler_data, parent, false);
        return new SavingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavingsViewHolder holder, int position)
    {
        Savings savings = listSaving.get(position);
        holder.tvAmount.setText(savings.getAmount() + "");
        holder.tvType.setText(savings.getType());
        holder.tvNote.setText(savings.getNote());
        holder.tvDate.setText(savings.getDate());
        holder.editSavings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editTaskDialog(savings);
            }
        });
        holder.deleteSavings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                database.deleteSavings(savings.getUserId());
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return listSaving.size();
    }

    private void editTaskDialog(final Savings savings)
    {


        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.custom_layout_for_data_update, null);

//        mydialog.setView(subView);
//        AlertDialog dialog=mydialog.create();
//
//        dialog.setCancelable(false);

        final EditText amountField = subView.findViewById(R.id.amount_edit);
        final EditText typeField = subView.findViewById(R.id.type_edit);
        final EditText noteField = subView.findViewById(R.id.note_edit);


        if (savings != null)
        {

            amountField.setText(String.valueOf(savings.getAmount()));
            typeField.setText(savings.getType());
            noteField.setText(savings.getNote());

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Savings");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("EDIT SAVING", new DialogInterface.OnClickListener()
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
                    database.updateSavings(new
                            Savings(Objects.requireNonNull(savings).getUserId(), 0, amount, type, note, date));
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