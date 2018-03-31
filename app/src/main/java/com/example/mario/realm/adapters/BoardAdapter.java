package com.example.mario.realm.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mario.realm.R;
import com.example.mario.realm.activities.BoardActivity;
import com.example.mario.realm.models.Board;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mario on 24/03/2018.
 */

public class BoardAdapter extends BaseAdapter {

    private Realm realm;
    private RealmResults<Board> boards;
    private Context context;
    private List<Board> list;
    private int layout;
    private int position;



    public BoardAdapter(Context context, List<Board> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Board getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewInterface viewInterface;

        if (view == null) {

            view = LayoutInflater.from(context).inflate(layout, null);
            viewInterface = new ViewInterface();
            viewInterface.title = (TextView) view.findViewById(R.id.textViewBoardTitle);
            viewInterface.createDat = (TextView) view.findViewById(R.id.textViewBoardDate);
            //viewInterface.botonImagen = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(viewInterface);


        } else {

            viewInterface = (ViewInterface) view.getTag();

        }


        //viewInterface.botonImagen.setTag(i);
        //viewInterface.botonImagen.getTag(i);
        //viewInterface.botonImagen.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View view) {


              //  RealmResults<Board> results = realm.where(Board.class).equalTo("id", i).findAll();
                //realm.beginTransaction();
                //results.deleteFromRealm(i);
                //realm.commitTransaction();
                //notifyDataSetChanged();



                //ShowConfirmDialog(context,list.get(i).getId(),i);

               // Board board = obtenerBoderporID(i);
               // realm.beginTransaction();
               // board.deleteFromRealm();
            //    realm.commitTransaction();
          //  }
        //});

        Board currentBoard = list.get(i);
        viewInterface.title.setText(currentBoard.getTitle());
        viewInterface.createDat.setText(currentBoard.getCreatedAT().toString());


        return view;



    }


    public class ViewInterface {

        TextView title;
        TextView createDat;
        ImageView botonImagen;

    }



   // public static void ShowConfirmDialog(Context context,final int id,final int i)
    //{
      //  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
       // alertDialogBuilder
       //         .setMessage("Quieres Borrar este registro")
               // .setCancelable(true)
               // .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 //   public void onClick(DialogInterface dialog,int id2) {

                   //     BoardActivity.getInstance().deletePerson(id,i);

                   // }
                //})
               // .setNegativeButton("No", new DialogInterface.OnClickListener() {
                 //   public void onClick(DialogInterface dialog,int id2) {
                   //     dialog.cancel();
                //    }
               // });
        //AlertDialog alertDialog = alertDialogBuilder.create();
       // alertDialog.show();
    //}

}



