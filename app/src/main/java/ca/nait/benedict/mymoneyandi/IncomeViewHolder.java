package ca.nait.benedict.mymoneyandi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class IncomeViewHolder extends RecyclerView.ViewHolder
{
        TextView tvType;
        TextView tvAmount;
        TextView tvNote;
        TextView tvDate;
        TextView tvTotalSum;
        ImageView editIncome;
        ImageView deleteIncome;

    IncomeViewHolder(View itemView)
    {
        super(itemView);
        tvAmount = itemView.findViewById(R.id.amt_txt_income);
        tvType = itemView.findViewById(R.id.type_txt_income);
        tvNote = itemView.findViewById(R.id.note_txt_income);
        tvDate = itemView.findViewById(R.id.date_txt_income);
        deleteIncome = itemView.findViewById(R.id.deleteIncome);
        editIncome = itemView.findViewById(R.id.editIncome);



        //tvTotalSum = itemView.findViewById(R.id.income_txt_output);



    }

    public void setOnClickListener(View.OnClickListener onClickListener)
    {

    }
}
