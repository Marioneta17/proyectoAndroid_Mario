package com.example.mario.realm.activities;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mario.realm.R;
import com.example.mario.realm.adapters.BoardAdapter;
import com.example.mario.realm.models.Board;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class BoardActivity  extends AppCompatActivity  implements RealmChangeListener<RealmResults<Board>> {

    private Realm realm;
    private RealmResults<Board> boards;
    private ListView listViewBoard;
    private BoardAdapter adapterBoard;
    private static ArrayList<Board> boardarray =new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private List<Board> list;
    private static BoardActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        //Conexiòn a la bd realm
        realm = Realm.getDefaultInstance();
        boards = realm.where(Board.class).findAll();
        boards.addChangeListener(this);

        adapterBoard = new BoardAdapter(this, boards,R.layout.list_view_board_item);
        listViewBoard = (ListView) findViewById(R.id.listViewBoard);
        listViewBoard.setAdapter(adapterBoard);




        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabAddBoard);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

       showAddBoard("Agregar un nuevo tablero", "Ingresese el nombre del tableo");
                //createNewBoard("Nuevo Tablero");
                //adapterBoard.notifyDataSetChanged();
            }
        });
        registerForContextMenu(listViewBoard);
    }


    /**
     * CRUD Actions
     *
     *
     */

    public static BoardActivity getInstance() {
        return instance;
    }

    private void createNewBoard(String boarRead){
        realm.beginTransaction();
        Board board = new Board(boarRead);
        realm.copyToRealm(board);
        realm.commitTransaction();
    }

    private void editBoard(String newName, Board board){
        realm.beginTransaction();
        board.setTitle(newName);
        realm.copyToRealmOrUpdate(board);
        realm.commitTransaction();
    }

    private void deleteBoard(Board board){
        realm.beginTransaction();
        board.deleteFromRealm();
        realm.commitTransaction();
    }

    private void deleteAll(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Override
    public void onChange(RealmResults realmResults) {
        Log.d("Tag","Mensaje");
    }

    public void showAddBoard(String tiltle, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tiltle);
        builder.setMessage(message);
        View viewIfalte = LayoutInflater.from(this).inflate(R.layout.dialog_create_board, null);
        builder.setView(viewIfalte);

        final EditText input  = (EditText) viewIfalte.findViewById(R.id.editTextBoard);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            String bordName = input.getText().toString().trim();
            if (bordName.length()> 0){
                createNewBoard(bordName);
            }else{
                Toast.makeText(getApplicationContext(),"se requiere un nombre para el tablero", Toast.LENGTH_LONG).show();
            }
        }
    });

    AlertDialog dialog = builder.create();
    dialog.show();
}

    public void showEditBoard(String tiltle, String message, final Board board){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tiltle);
        builder.setMessage(message);
        View viewIfalte = LayoutInflater.from(this).inflate(R.layout.dialog_create_board, null);
        builder.setView(viewIfalte);
        final EditText input  = (EditText) viewIfalte.findViewById(R.id.editTextBoard);
        input.setText(board.getTitle());
        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String bordName = input.getText().toString().trim();

                if(bordName.length() == 0 ){

                    Toast.makeText(getApplicationContext(),"No puedes escribir el mismo nombre", Toast.LENGTH_LONG).show();
                }else if(bordName.equals(board.getTitle())){

                    Toast.makeText(getApplicationContext(),"Se requiere un nombre para editar el tablero", Toast.LENGTH_LONG).show();
                }else{
                    editBoard(bordName, board);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_board_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.borrar_todo:
               deleteAll();
               adapterBoard.notifyDataSetChanged();
               return true;
            default:
                return  super.onOptionsItemSelected(item);
       }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle(boards.get(info.position).getTitle());
        getMenuInflater().inflate(R.menu.context_menu_board_activity, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete_board:
                deleteBoard(boards.get(info.position));
                adapterBoard.notifyDataSetChanged();
                return true;

            case R.id.editar_board:
                showEditBoard("Editar Board", "Edita el nombre", boards.get(info.position));
                return true;

                default:return super.onContextItemSelected(item);
        }
    }

    //public void deletePerson(int id, int i) {
      //  RealmResults<Board> results = realm.where(Board.class).equalTo("id", id).findAll();
      //  realm.beginTransaction();
       // results.remove(i);
       // realm.commitTransaction();
       // list.remove(i);
       // adapterBoard.notifyDataSetChanged();
    //}


}