package com.mobileapp.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SavedBillsAdapter extends FirebaseRecyclerAdapter<SavedBillsModel,SavedBillsAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SavedBillsAdapter(@NonNull FirebaseRecyclerOptions<SavedBillsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull SavedBillsModel model) {

        holder.billAmount.setText(""+(model.getAmount()));
        holder.billDate.setText(model.getDate());
        holder.billType.setText(model.getUtilityType());

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_card,parent,false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView billType,billAmount,billDate;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            billAmount=(TextView) itemView.findViewById(R.id.billAmountHistory);
            billDate=(TextView) itemView.findViewById(R.id.billDateHistory);
            billType=(TextView) itemView.findViewById(R.id.billTitleHistory);

        }
    }
}
