package ca.nait.benedict.mymoneyandi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class SavingsViewHolder extends RecyclerView.ViewHolder
{
    TextView tvType;
    TextView tvAmount;
    TextView tvNote;
    TextView tvDate;
    TextView tvTotalSum;
    ImageView editSavings;
    ImageView deleteSavings;
    SavingsViewHolder(View itemView)
    {
        super(itemView);
        tvAmount = itemView.findViewById(R.id.amt_txt_savings);
        tvType = itemView.findViewById(R.id.type_txt_savings);
        tvNote = itemView.findViewById(R.id.note_txt_savings);
        tvDate = itemView.findViewById(R.id.date_txt_savings);
        tvTotalSum = itemView.findViewById(R.id.expense_txt_output);
        editSavings = itemView.findViewById(R.id.editSavings);
        deleteSavings = itemView.findViewById(R.id.deleteSavings);

    }
}
