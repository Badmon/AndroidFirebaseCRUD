package com.example.licoreriamateo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.licoreriamateo.models.Producto;
import com.example.licoreriamateo.models.ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;

public class PostsListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_list);

        //ActionBar
        ActionBar actionBar = getSupportActionBar();
        //set title
        actionBar.setTitle("Posts List");
        //recyclerView
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        //set layout as LinearLayout
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Producto");


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Producto, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Producto, ViewHolder>(
                        Producto.class,
                        R.layout.row,
                        ViewHolder.class,
                        mRef
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Producto producto, int i) {

                        viewHolder.setDetails(getApplicationContext(),producto.getNombre(),"Descripción: "+producto.getApellido(),"Precio: S/."+producto.getPassword(),producto.getCorreo());

                    }
                };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}


