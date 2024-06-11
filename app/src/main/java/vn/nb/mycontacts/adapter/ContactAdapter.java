package vn.nb.mycontacts.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.nb.mycontacts.R;
import vn.nb.mycontacts.entity.Contact;
import vn.nb.mycontacts.interfaces.OnContactListener;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.VH> {
    private List<Contact> contactList = new ArrayList<>();
    private OnContactListener onContactListener;

    public void setOnContactListener(OnContactListener onContactListener) {
        this.onContactListener = onContactListener;
    }


    @NonNull
    @Override
    public ContactAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.VH holder, int position) {
        Contact contact = contactList.get(holder.getAdapterPosition());

        if (!contact.getNickName().isEmpty()) {
            String first = String.valueOf(contact.getNickName().charAt(0));
            holder.tvIcon.setText(first);
        }

        holder.tvName.setText(contact.getNickName());

        holder.itemView.setOnClickListener(v -> onContactListener.onItemClick(contact, holder.getAdapterPosition()));


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(List<Contact> allContact) {
        this.contactList = allContact;
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        private TextView tvIcon, tvName;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvIcon = itemView.findViewById(R.id.tvIcon);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
