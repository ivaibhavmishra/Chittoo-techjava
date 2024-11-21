package com.example.chitto_techjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView cardView = new CardView(parent.getContext());
        cardView.setLayoutParams(new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));


        cardView.setCardBackgroundColor(0xFFFFFFFF); // White background
        cardView.setCardElevation(10); // Shadow (floating effect)
        cardView.setRadius(16); // Rounded corners
        cardView.setContentPadding(16, 16, 16, 16);

        // Create a Linearlayout inside the cardview to hold textviews
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));


        TextView nameTextView = new TextView(parent.getContext());
        nameTextView.setTextSize(18);
        nameTextView.setTextColor(0xFF000000); // Black color

        TextView emailTextView = new TextView(parent.getContext());
        emailTextView.setTextSize(16);
        emailTextView.setTextColor(0xFF000000); // Black color

        TextView phoneTextView = new TextView(parent.getContext());
        phoneTextView.setTextSize(16);
        phoneTextView.setTextColor(0xFF000000); // Black color

        // Add textviews tothe linearlayout
        linearLayout.addView(nameTextView);
        linearLayout.addView(emailTextView);
        linearLayout.addView(phoneTextView);


        cardView.addView(linearLayout);

        return new UserViewHolder(cardView, nameTextView, emailTextView, phoneTextView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = users.get(position);

        // Setuser data to textView
        holder.nameTextView.setText("Name: " + user.getName());
        holder.emailTextView.setText("Email: " + user.getEmail());
        holder.phoneTextView.setText("Phone: " + user.getPhone());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView, emailTextView, phoneTextView;

        public UserViewHolder(View itemView, TextView nameTextView, TextView emailTextView, TextView phoneTextView) {
            super(itemView);
            this.nameTextView = nameTextView;
            this.emailTextView = emailTextView;
            this.phoneTextView = phoneTextView;
        }
    }
}
