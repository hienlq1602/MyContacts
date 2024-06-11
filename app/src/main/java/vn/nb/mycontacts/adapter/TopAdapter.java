package vn.nb.mycontacts.adapter;

import android.annotation.SuppressLint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.nb.mycontacts.R;
import vn.nb.mycontacts.entity.ContactWithInteractionCount;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.VH> {
    private List<ContactWithInteractionCount> list = new ArrayList<>();

    @NonNull
    @Override
    public TopAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TopAdapter.VH holder, int position) {
        ContactWithInteractionCount contactWithInteractionCount = list.get(holder.getAdapterPosition());
        holder.content.setText(Html.fromHtml(holder.getAdapterPosition()+1+". "+contactWithInteractionCount.getContact().getPhoneNumber()
                +" - "+contactWithInteractionCount.getContact().getNickName()));
    }

    @Override
    public int getItemCount() {
        return Math.min(list.size(), 3);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItem(List<ContactWithInteractionCount> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {

        private TextView content;

        public VH(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
        }
    }
}
