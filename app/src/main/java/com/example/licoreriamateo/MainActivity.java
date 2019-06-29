package com.example.licoreriamateo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nomP, appP, correoP, passwordP;
    ListView listV_personas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomP = findViewById(R.id.txt_nombre);
        appP = findViewById(R.id.txt_apellido);
        correoP = findViewById(R.id.txt_correo);
        passwordP = findViewById(R.id.txt_password);

        listV_personas = findViewById(R.id.lv_datosProductos);

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
        switch (item.getItemId()){
            case R.id.icon_add:{
                if(nombre.equals("")){
                    validacion();
                }
                Toast.makeText(this,"Agregar",Toast.LENGTH_LONG).show();
                break;
            }

            case R.id.icon_save:{
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

    private void validacion() {
        String nombre = nomP.getText().toString();

        if (nombre.equals("")){
            nomP.setError("Required");
        }
    }
}
