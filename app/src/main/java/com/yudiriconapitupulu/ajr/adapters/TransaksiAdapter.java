package com.yudiriconapitupulu.ajr.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yudiriconapitupulu.ajr.R;
import com.yudiriconapitupulu.ajr.models.Promo;
import com.yudiriconapitupulu.ajr.models.Transaksi;
import com.yudiriconapitupulu.ajr.models.TransaksiResponse;

import java.util.ArrayList;
import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder>
        implements Filterable {

    private List<Transaksi> transaksiList, filteredTransaksiList;
    private Context context;

    public TransaksiAdapter(List<Transaksi> transaksiList, Context context) {
        this.transaksiList = transaksiList;
        filteredTransaksiList = new ArrayList<>(transaksiList);
        this.context = context;
    }

    @NonNull
    @Override
    public TransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_transaksi, parent, false);
        return new TransaksiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.ViewHolder holder, int position) {
        Transaksi transaksi = filteredTransaksiList.get(position);

        holder.tvIdTransaksi.setText(transaksi.getFormat_id_transaksi() + String.valueOf(transaksi.getId_transaksi()));
        holder.tvTglTransaksi.setText(transaksi.getTgl_transaksi());
        holder.tvNamaCus.setText("CUST: " +transaksi.getNama_customer());
        holder.tvNamaPegawai.setText("CS: "+transaksi.getNama_pegawai());
        holder.tvNamaMobil.setText("CAR: "+transaksi.getNama_mobil());
        holder.tvStatTransaksi.setText(transaksi.getStatus_transaksi());

        if(transaksi.getNama_driver()==null){
            holder.tvNamaDrv.setText("DRV: -");
        } else {
            holder.tvNamaDrv.setText("DRV: "+ transaksi.getNama_driver());
        }

        if (transaksi.getKode_promo()==null){
            holder.tvPromo.setText("PRO: -");
        } else {
            holder.tvPromo.setText("PRO: "+ transaksi.getKode_promo());
        }
    }

    @Override
    public int getItemCount() {
        return filteredTransaksiList.size();
    }

    public void setTransaksiList(List<Transaksi> transaksiList) {
        this.transaksiList = transaksiList;
        filteredTransaksiList = new ArrayList<>(transaksiList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Transaksi> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(transaksiList);
                } else {
                    for (Transaksi transaksi : transaksiList) {
                        if (transaksi.getFormat_id_transaksi().toLowerCase()
                                .contains(charSequenceString.toLowerCase()) || String.valueOf(transaksi.getKode_promo()).toLowerCase()
                                .contains(charSequenceString.toLowerCase()))
                            filtered.add(transaksi);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredTransaksiList.clear();
                filteredTransaksiList.addAll((List<Transaksi>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdTransaksi, tvTglTransaksi, tvNamaCus, tvNamaDrv, tvPromo, tvNamaPegawai, tvNamaMobil, tvStatTransaksi;
        CardView cvTransaksi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdTransaksi = itemView.findViewById(R.id.tv_idTransaksi);
            tvTglTransaksi= itemView.findViewById(R.id.tv_tglTransaksi);
            tvNamaCus= itemView.findViewById(R.id.tv_nama);
            tvNamaDrv = itemView.findViewById(R.id.tv_namadriver);
            tvPromo= itemView.findViewById(R.id.tv_promo);
            tvNamaPegawai= itemView.findViewById(R.id.tv_namaPegawai);
            tvNamaMobil= itemView.findViewById(R.id.tv_namaMobil);
            tvStatTransaksi= itemView.findViewById(R.id.tv_statTransaksi);
            cvTransaksi = itemView.findViewById(R.id.cv_transaksi);
        }
    }
}
