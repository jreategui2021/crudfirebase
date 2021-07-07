package com.example.crudnube_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crudnube_2021.adapter.UsuarioAdapter;
import com.example.crudnube_2021.modelo.Usuario;
import com.example.crudnube_2021.util.Mensajes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener{
    private ListView lista1;
    private List<Usuario> listaList;
    private UsuarioAdapter adapter;
    private int idposi;
    private String key;
    private AlertDialog alertDialog, alertconfirm;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alertDialog = Mensajes.crearAlertaDialog(this);
        alertconfirm = Mensajes.crearDialogConfirmacion(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        lista1 = (ListView) findViewById(R.id.lvUsuarios);
        listarUsuarios();
        lista1.setOnItemClickListener(this);
    }
    private void listarUsuarios(){
        listaList = new ArrayList<>();

        databaseReference.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for(DataSnapshot ds: snapshot.getChildren()){
                        Usuario user = new Usuario();
                        user.setId(ds.getKey());
                        user.setNombres(ds.child("nombres").getValue().toString());
                        user.setCorreo(ds.child("correo").getValue().toString());
                        listaList.add(user);
                    }
                    adapter = new UsuarioAdapter(getApplicationContext(),listaList);
                    lista1.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_usuarios, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu_guardar) {
            startActivity(new Intent(this, UsuarioActivity.class));
        }
        if(id==R.id.action_menu_salir1){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        key = listaList.get(idposi).getId();

        switch (which){
            case 0:
                Intent intent = new Intent(this, UsuarioActivity.class);
                intent.putExtra("USUARIO_KEY",key);
                startActivity(intent);
                break;
            case 1:alertconfirm.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                listaList.remove(idposi);
                databaseReference.child("Usuarios").child(key).removeValue();
                lista1.invalidateViews();
                startActivity(getIntent());
                finish();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertconfirm.dismiss();break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idposi = position;
        alertDialog.show();
    }
}