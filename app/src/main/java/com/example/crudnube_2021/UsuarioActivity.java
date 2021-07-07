package com.example.crudnube_2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crudnube_2021.modelo.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UsuarioActivity extends AppCompatActivity {
    private EditText edtNombre, edtCorreo;
    private TextView txtTitulo;
    private Usuario usuario;
    private int iduser;
    private String key = "";
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtTitulo = (TextView) findViewById(R.id.text_edit_user);
        setContentView(R.layout.activity_usuario);
        edtNombre = (EditText) findViewById(R.id.txtNombre);
        edtCorreo = (EditText) findViewById(R.id.txtUser);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        key  = getIntent().getStringExtra("USUARIO_KEY");
        setTitle("CREAR");
        if(key!=null ){
            databaseReference.child("Usuarios").child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    edtNombre.setText(snapshot.child("nombres").getValue().toString());
                    edtCorreo.setText(snapshot.child("correo").getValue().toString());
                    setTitle("ACTUALIZAR");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"xxx: "+key, Toast.LENGTH_SHORT).show();
        }
    }
    private void registrar(){
        boolean validar = true;
        String nombre = edtNombre.getText().toString();
        String correo = edtCorreo.getText().toString();
        if(nombre == null || nombre.equals("")){
            validar = false;
            edtNombre.setError(getString(R.string.Login_validaNombre));
        }
        if(correo == null || correo.equals("")){
            validar = false;
            edtNombre.setError(getString(R.string.Login_validaUsuario));
        }
        usuario  = new Usuario(nombre, correo);
        if(validar && key==null) {

             databaseReference.child("Usuarios").push().setValue(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void aVoid) {
                     Toast.makeText(UsuarioActivity.this,"Usuario Registrado correctamente...",Toast.LENGTH_LONG).show();
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Toast.makeText(UsuarioActivity.this,"Error al guardar el Usuario...",Toast.LENGTH_LONG).show();
                 }
             });
        }else{
            Map<String,Object> user = new HashMap<>();
            user.put("nombres",nombre);
            user.put("correo",correo);
            databaseReference.child("Usuarios").child(key).updateChildren(user);
        };

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_menu_guardar:
                this.registrar();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.action_menu_sair:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}