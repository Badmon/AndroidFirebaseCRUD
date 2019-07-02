package com.example.licoreriamateo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.licoreriamateo.models.Producto;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private List<Producto> listProducto = new ArrayList<Producto>();
    ArrayAdapter<Producto> arrayAdapterProducto;

    EditText nomP, appP, correoP, passwordP;
    ListView listV_personas;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Producto productoSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomP = findViewById(R.id.txt_nombre);
        appP = findViewById(R.id.txt_apellido);
        correoP = findViewById(R.id.txt_correo);
        passwordP = findViewById(R.id.txt_password);

        listV_personas = findViewById(R.id.lv_datosProductos);
        inicializarFirebase();
        listarDatos();

        listV_personas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productoSelected = (Producto) adapterView.getItemAtPosition(i);

                nomP.setText(productoSelected.getNombre());
                appP.setText(productoSelected.getApellido());
                correoP.setText(productoSelected.getCorreo());
                passwordP.setText(productoSelected.getPassword());
            }
        });
    }

    private void listarDatos() {
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listProducto.clear();
                for(DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Producto p = objSnaptshot.getValue(Producto.class);
                    listProducto.add(p);

                    arrayAdapterProducto = new ArrayAdapter<Producto>(MainActivity.this, android.R.layout.simple_list_item_1, listProducto);
                    listV_personas.setAdapter(arrayAdapterProducto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //switch
        String nombre = nomP.getText().toString();
        String correo = correoP.getText().toString();
        String password = passwordP.getText().toString();
        String app = appP.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add:{
                if(nombre.equals("")||correo.equals("")||password.equals("")||app.equals("")){
                    validacion();
                }else {
                    Producto p = new Producto();
                    p.setPid(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellido(app);
                    p.setCorreo(correo);
                    p.setPassword(password);
                    databaseReference.child("Producto").child(p.getPid()).setValue(p);
                    Toast.makeText(this, "Agregar", Toast.LENGTH_LONG).show();
                    limpiarCajar();

                }break;
            }

            case R.id.icon_save:{
                Producto p = new Producto();

                Toast.makeText(this,"Guardar",Toast.LENGTH_LONG).show();
                break;
            }

            case R.id.icon_delete:{
                Toast.makeText(this,"Eliminar",Toast.LENGTH_LONG).show();
                break;
            }
            default:break;
        }
        return true;
    }

    private void limpiarCajar() {

        nomP.setText("");
        correoP.setText("");
        passwordP.setText("");
        appP.setText("");
    }

    private void validacion() {
        String nombre = nomP.getText().toString();
        String correo = correoP.getText().toString();
        String password = passwordP.getText().toString();
        String app = appP.getText().toString();
        if (nombre.equals("")){
            nomP.setError("Required");
        }
        else if(app.equals("")){
            appP.setError("Required");
        }
        else if(correo.equals("")){
            correoP.setError("Required");
        }
        else if(password.equals("")){
            passwordP.setError("Required");
        }

    }
}
