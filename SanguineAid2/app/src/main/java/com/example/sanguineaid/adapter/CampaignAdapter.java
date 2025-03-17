package com.example.sanguineaid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanguineaid.R;
import com.example.sanguineaid.model.Campaign;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.CampaignViewHolder> {
    private List<Campaign> campaignList;

    public CampaignAdapter(List<Campaign> campaignList) {
        this.campaignList = campaignList;
    }

    @NonNull
    @Override
    public CampaignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_campaign, parent, false);
        return new CampaignViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CampaignViewHolder holder, int position) {
        Campaign campaign = campaignList.get(position);
        holder.nameTextView.setText(campaign.getName());
        holder.durationTextView.setText(campaign.getDuration());
        holder.voucherTextView.setText(campaign.getVoucherPercentage() + "%");

        Picasso.get().load(campaign.getImageUrl()).into(holder.campaignImageView);
    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    public static class CampaignViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView durationTextView;
        TextView voucherTextView;
        ImageView campaignImageView;

        public CampaignViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.campaign_name);
            durationTextView = itemView.findViewById(R.id.campaign_duration);
            voucherTextView = itemView.findViewById(R.id.campaign_voucher);
            campaignImageView = itemView.findViewById(R.id.campaign_image);
        }
    }
}
