package com.sel.smartfood.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sel.smartfood.R;
import com.sel.smartfood.viewmodel.AdminViewModel;

public class FragmentStatistics extends Fragment {
    private TextView numberOfTransTv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_admin_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        numberOfTransTv = view.findViewById(R.id.tv_number_transactions);
        AdminViewModel adminViewModel = new ViewModelProvider(this).get(AdminViewModel.class);
        adminViewModel.getAllTransHistories();
        adminViewModel.getTranHistories().observe(getViewLifecycleOwner(), transHistories -> {
            if (transHistories != null){
                numberOfTransTv.setText(String.valueOf(transHistories.size()));
            }
        });
    }
}
