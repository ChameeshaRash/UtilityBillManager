package com.mobileapp.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DecimalFormat;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SavedBillsAdapter extends FirebaseRecyclerAdapter<SavedBillsModel,SavedBillsAdapter.myViewHolder> {

    private static final DecimalFormat decfor = new DecimalFormat("0.00");
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

        holder.billAmount.setText(""+(decfor.format(model.getAmount())));
        holder.billDate.setText(model.getDate());
        holder.billType.setText(model.getUtilityType());
        if(Objects.equals(model.getUtilityType(), "Electricity")){
            Glide.with(holder.img.getContext())
                    .load(R.drawable.electricity_card_img)
                    .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                    .into(holder.img);
        }else if(Objects.equals(model.getUtilityType(), "Water")){
            Glide.with(holder.img.getContext())
                    .load(R.drawable.water_card_img)
                    .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                    .into(holder.img);
        }else if(Objects.equals(model.getUtilityType(), "Internet")){
            Glide.with(holder.img.getContext())
                    .load(R.drawable.internet_card_img)
                    .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                    .into(holder.img);

        }else if(Objects.equals(model.getUtilityType(), "Fuel")){
            Glide.with(holder.img.getContext())
                    .load(R.drawable.fuel_card_img)
                    .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                    .into(holder.img);

        }


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_card,parent,false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView billType,billAmount,billDate;
        ImageView img;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            billAmount=(TextView) itemView.findViewById(R.id.billAmountHistory);
            billDate=(TextView) itemView.findViewById(R.id.billDateHistory);
            billType=(TextView) itemView.findViewById(R.id.billTitleHistory);

            img=(ImageView) itemView.findViewById(R.id.billTypeIconSavedBIlls);

        }
    }
}
