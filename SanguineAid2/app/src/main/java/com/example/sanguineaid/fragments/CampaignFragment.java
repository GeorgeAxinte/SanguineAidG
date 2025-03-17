package com.example.sanguineaid.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanguineaid.R;
import com.example.sanguineaid.adapter.CampaignAdapter;
import com.example.sanguineaid.model.Campaign;

import java.util.ArrayList;
import java.util.List;

public class CampaignFragment extends Fragment {

    private RecyclerView recyclerView;
    private CampaignAdapter campaignAdapter;
    private List<Campaign> campaignList;

    public CampaignFragment() {
    }
    public List<Campaign> getCampaignList() {
        return campaignList;
    }
    public Campaign getFirstCampaign() {
        if (campaignList != null && !campaignList.isEmpty()) {
            return campaignList.get(0);
        }
        return null;
    }
    public String getTitle() {
        if (campaignList != null && !campaignList.isEmpty()) {
            return campaignList.get(0).getTitle();
        }
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campaign, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        campaignList = new ArrayList<>();
        campaignList.add(new Campaign(
                "eMAG Campaign – Donate blood and earn discounts",
                R.drawable.emag,
                "01-02-2025 - 28-02-2025",
                15
        ));

        campaignList.add(new Campaign(
                "FashionDays – Donate blood and get 20% off",
                R.drawable.fashion_days,
                "01-03-2025 - 31-03-2025",
                20
        ));

        campaignList.add(new Campaign(
                "Altex – Get rewarded for donating blood",
                R.drawable.altex,
                "05-03-2025 - 15-03-2025",
                25
        ));

        campaignList.add(new Campaign(
                "Flanco – Donate blood and get 10% off on tech products",
                R.drawable.flanco,
                "01-04-2025 - 15-04-2025",
                10
        ));

        campaignList.add(new Campaign(
                "Lidl – Donate blood and receive a 15 RON voucher",
                R.drawable.lidl,
                "10-04-2025 - 30-04-2025",
                15
        ));

        campaignAdapter = new CampaignAdapter(campaignList);
        recyclerView.setAdapter(campaignAdapter);

        return view;
    }
}
