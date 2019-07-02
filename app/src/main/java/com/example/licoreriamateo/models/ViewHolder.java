package com.example.licoreriamateo.models;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licoreriamateo.R;
import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;
    }

    public void setDetails(Context ctx, String Nombre,String Apellido, String Correo){

        TextView mTitleTv = mView.findViewById(R.id.rTitleTv);
        TextView mApellidoTv = mView.findViewById(R.id.rDescriptionTv);
        ImageView mImageTv = mView.findViewById(R.id.rImageView);
        //set data
        mTitleTv.setText(Nombre);
        mApellidoTv.setText(Apellido);
        Picasso.get().load(Correo).into(mImageTv);
    }
}
