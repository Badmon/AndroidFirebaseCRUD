package com.example.licoreriamateo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;


import com.example.licoreriamateo.models.Producto;
import com.example.licoreriamateo.models.ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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


    //Busqueda
    private void firebaseSearch(String searchText) {
        Query firebaseSearchQuery = mRef.orderByChild("nombre").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<Producto, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Producto, ViewHolder>(
                        Producto.class,
                        R.layout.row,
                        ViewHolder.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Producto producto, int i) {

                        viewHolder.setDetails(getApplicationContext(),producto.getNombre(),"Descripción: "+producto.getApellido(),"Precio: S/."+producto.getPassword(),producto.getCorreo());

                    }
                };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                firebaseSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                firebaseSearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_settings){
            //TODO
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}


