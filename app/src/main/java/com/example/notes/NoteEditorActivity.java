package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {
    int nodeId;
    EditText editText;
    Intent intent;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_note_editor );

        editText = findViewById( R.id.editText);
         intent = getIntent();
        nodeId=intent.getIntExtra( "noteId" ,-1);

        if(nodeId !=-1){
            editText.setText( MainActivity.notes.get( nodeId ) );
        }else{
            MainActivity.notes.add("");
            nodeId = MainActivity.notes.size()-1;
        }

        editText.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set( nodeId,String.valueOf( s ));
                MainActivity.arrayAdapter.notifyDataSetChanged();
//                sharedPreferences = getApplicationContext().getSharedPreferences( "com.example.notes" , Context.MODE_PRIVATE );
//                HashSet<String>set = new HashSet<>( MainActivity.notes );
//                sharedPreferences.edit().putStringSet( "notes",  set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        } );
    }
    public void SaveButton(View view){
        sharedPreferences = getApplicationContext().getSharedPreferences( "com.example.notes" , Context.MODE_PRIVATE );
        HashSet<String>set = new HashSet<>( MainActivity.notes );
        sharedPreferences.edit().putStringSet( "notes",  set).apply();
        Intent intent = new Intent( getApplicationContext(),MainActivity.class );
        startActivity(intent);

    }
    public void BackButton(View view){
        new AlertDialog.Builder( NoteEditorActivity.this)
                .setIcon( android.R.drawable.ic_dialog_alert )
                .setTitle( "Are you sure ?" )
                .setMessage( "Changes Made Would Not Be Saved!!" )
                .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent( getApplicationContext(),MainActivity.class );
                        startActivity(intent);
                    }
                } )
                .setNegativeButton( "No",null )
                .show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate( R.menu.back_note_menue,menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected( item );

        if(item.getItemId()==R.id.back){
            new AlertDialog.Builder( NoteEditorActivity.this)
                    .setIcon( android.R.drawable.ic_dialog_alert )
                    .setTitle( "Are you sure ?" )
                    .setMessage( "Changes Made Would Not Be Saved!!" )
                    .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent( getApplicationContext(),MainActivity.class );
                            startActivity(intent);
                        }
                    } )
                    .setNegativeButton( "No",null )
                    .show();
            return false;
        }else{
            return false;
        }
    }
}