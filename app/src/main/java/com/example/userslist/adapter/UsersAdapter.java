package com.example.userslist.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.userslist.databinding.ItemListBinding;
import com.example.userslist.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private static final String TAG = "UserAdapter";
    Context context;
    List<User> users;

    public UsersAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemListBinding itemBinding = ItemListBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        User user = users.get(position);
        holder.setData(user);
        holder.binding.executePendingBindings();
        holder.binding.itemCard.setOnClickListener(v -> onClickItem.onClick(user, position, v));
        Glide.with(context)
                .load(user.getAirline().get(0).getLogo())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.img);
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {

        ItemListBinding binding;

        public ViewHolder(@NonNull ItemListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void setData(User user) {
            binding.setData(user);
        }
    }

    public interface OnClickItem {
        void onClick(User user, int position, View view);
    }

    OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    private void toastMsg(String msg) {

        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "toastMsg:-- " + msg + " --");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
