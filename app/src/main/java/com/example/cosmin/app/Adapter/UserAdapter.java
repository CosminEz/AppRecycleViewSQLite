package com.example.cosmin.app.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cosmin.app.DBHelper;
import com.example.cosmin.app.Model.User;
import com.example.cosmin.app.R;

import java.util.List;

/**
 * Created by Cosmin on 6/1/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>  {

    private List<User> listUser;
    private Context mContext;
    private DBHelper db;

    public UserAdapter(List<User> listUser, Context mContext) {
        this.listUser = listUser;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.user_item,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int  position) {

        final User user=listUser.get(position);
        final RecyclerView.ViewHolder holder1=holder;
        final int position1=position;
        holder.textViewUser.setText(user.getName());
        holder.buttonDeleteUser.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                db=DBHelper.getInstance(v.getContext());
                listUser.remove(position1);
                notifyItemRemoved(position1);
                notifyItemRangeChanged(position1, listUser.size());
                holder1.itemView.setVisibility(View.GONE);
                db.deleteUser(user);


            }

        });


    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewUser;
        public ImageView buttonDeleteUser;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewUser=(TextView)itemView.findViewById(R.id.tv_user_item);
            buttonDeleteUser=(ImageView)itemView.findViewById(R.id.delete_user);
        }
    }
}
