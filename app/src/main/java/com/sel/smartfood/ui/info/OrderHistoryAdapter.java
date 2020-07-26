package com.sel.smartfood.ui.info;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sel.smartfood.R;
import com.sel.smartfood.data.model.OrderHistory;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryHolder> {
    private LayoutInflater layoutInflater;
    private List<OrderHistory> orderHistoryList;
    public OrderHistoryAdapter(){}

    @NonNull
    @Override
    public OrderHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.item_detail_orderhistory, parent, false);
        return new OrderHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryHolder holder, int position) {
        OrderHistory orderHistory = orderHistoryList.get(position);
        holder.orderHisItemNameTv.setText(orderHistory.getProductName());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.orderHisItemTotalPriceTv.setText(decimalFormat.format(orderHistory.getProductTotalPrice()) + "Đ");

        holder.orderHisItemNumberTv.setText(String.valueOf(orderHistory.getProductNumber()));


        Picasso.get().load(orderHistory.getProductImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(holder.orderHisItemIv);
    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }
    public void setDataChanged(List<OrderHistory> orderHistories){
        this.orderHistoryList = orderHistories;
        notifyDataSetChanged();
    }


    public static class OrderHistoryHolder extends RecyclerView.ViewHolder {
        ImageView   orderHisItemIv;
        TextView    orderHisItemNameTv;
        TextView    orderHisItemNumberTv;
        TextView    orderHisItemTotalPriceTv;


        public OrderHistoryHolder(@NonNull View itemView) {
            super(itemView);
            orderHisItemIv = itemView.findViewById(R.id.iv_order_history_item);
            orderHisItemNameTv = itemView.findViewById(R.id.tv_order_history_item_name);
            orderHisItemNumberTv = itemView.findViewById(R.id.tv_order_history_item_number);
            orderHisItemTotalPriceTv = itemView.findViewById(R.id.tv_order_history_item_total_price);
        }
    }
}
