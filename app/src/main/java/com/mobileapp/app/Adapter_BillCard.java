package com.mobileapp.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_BillCard extends RecyclerView.Adapter<Adapter_BillCard.ViewHolder> {


    private List<BillCard> billCardList;

    public Adapter_BillCard(List<BillCard> billCardList) {
        this.billCardList = billCardList;
    }

// Render design in Home
    @NonNull
    @Override
    public Adapter_BillCard.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_BillCard.ViewHolder holder, int position) {
         String res_billTitle = billCardList.get(position).getBillTitle();
         String res_billAmount = billCardList.get(position).getBillAmount();
         String res_billDate = billCardList.get(position).getBillDate();

         holder.setData(res_billTitle,res_billAmount,res_billDate);


    }

    @Override
    public int getItemCount() {
        return billCardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView billTitle;
        private TextView billAmount;
        private TextView billDate;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            billTitle=itemView.findViewById(R.id.billTitle);
            billAmount=itemView.findViewById(R.id.billAmount);
            billDate=itemView.findViewById(R.id.billDate);

        }

        public void setData(String res_billTitle, String res_billAmount, String res_billDate) {

            billTitle.setText(res_billTitle);
            billAmount.setText(res_billAmount);
            billDate.setText(res_billDate);
        }
    }
}
