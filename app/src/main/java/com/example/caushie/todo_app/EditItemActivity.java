package com.example.caushie.todo_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import static com.example.caushie.todo_app.MainActivity.ITEM_POSITION;
import static com.example.caushie.todo_app.MainActivity.ITEM_TEXT;

public class EditItemActivity extends AppCompatActivity {

    //Track edit text
    EditText taskEdit;

    // Position of edited item in list.
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);


        //Resolve edit text from layout.
        taskEdit = findViewById(R.id.etItemText);

        //Set edit text value from intent extra
        taskEdit.setText(getIntent().getStringExtra(ITEM_TEXT));

        //Update position from intent extra
        position = getIntent().getIntExtra(ITEM_POSITION, 0);


        getSupportActionBar().setTitle("Edit Item");


//        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent data = new Intent();
//
//                data.putExtra(ITEM_TEXT, taskEdit.getText().toString());
//                data.putExtra(ITEM_POSITION,position);
//
//                finish();
//            }
//        });
    }

    // Handler for save button
    public void onSaveItem(View v) {
        //Prepare new intent for result
        Intent data = new Intent();
        //Pass updated item text as extra.
        data.putExtra(ITEM_TEXT, taskEdit.getText().toString());
        // Pass original position as extra.
        data.putExtra(ITEM_POSITION, position);
        //set intent as the result of the activity.

        setResult(RESULT_OK, data);
        finish();

    }
}
