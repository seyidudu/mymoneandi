package ca.nait.benedict.mymoneyandi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ExpenseViewHolder extends RecyclerView.ViewHolder
{
    TextView tvType;
    TextView tvAmount;
    TextView tvNote;
    TextView tvDate;
    ImageView editExpense;
    ImageView deleteExpense;

    ExpenseViewHolder(View itemView)
    {
        super(itemView);
        tvAmount = itemView.findViewById(R.id.amt_txt_expense);
        tvType = itemView.findViewById(R.id.type_txt_expense);
        tvNote = itemView.findViewById(R.id.note_txt_expense);
        tvDate = itemView.findViewById(R.id.date_txt_expense);
        editExpense = itemView.findViewById(R.id.deleteExpense);
        deleteExpense = itemView.findViewById(R.id.editExpense);

    }
}
