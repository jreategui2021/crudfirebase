package com.example.crudnube_2021.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.crudnube_2021.R;
import com.example.crudnube_2021.modelo.Usuario;

import java.util.List;

public class UsuarioAdapter extends BaseAdapter {
    private Context context;
    private List<Usuario> lista;
    public UsuarioAdapter(Context context, List<Usuario> model){
        this.context = context;
        this.lista = model;
    }
    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Usuario usuario = lista.get(i);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.usuarios, null);
        }
        TextView txtNom = (TextView) view.findViewById(R.id.usuario_lista_nombre);
        txtNom.setText((i+1)+".- "+usuario.getNombres()+" -> "+usuario.getCorreo());

        return view;
    }
}
