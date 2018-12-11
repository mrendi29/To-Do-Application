package com.example.caushie.todo_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    //Field Declarations  Objects that will be associated with any instance of mainActivity.

    // Model
    ArrayList<String> items;

    // The adapter: An intermediate opbject that wires  the model(our list) to the view. (Which we will resolve next)
    ArrayAdapter<String> itemsAdapter;

    // An instance of the listview itself.
    ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize everything in order.
//        items = new ArrayList<>();  We will have to replace this with the cal to read items.
        readItems();

        // Arrayadapters  requires 3 arguments passed to the constructor. (First is a reference to the activity), the second is the type item that it will wrap.
        // The third one is the List we created (items)
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        //A reference to the listview so we can wire the adaptor to it.
        // Resolving the instance that already exists. (We are using the id that we assigned before to listView)
        lvItems = findViewById(R.id.listItems);

        // Wire the adaptor to the listview.
        lvItems.setAdapter(itemsAdapter);

        //Mock Data (until we have add function implemented)

//        items.add("First Item");
//        items.add("Second Item");


        setupListViewListener();

    }

    //Add item functionality. The method is then assigned to the button in main.xml
    public void onAddItem(View v) {

        //First resolve edit text in the same way we resolved listview.
        EditText etNewItem = findViewById(R.id.addItemTxt);

        //Then we want to get its value as a string.
        String itemText = etNewItem.getText().toString();

        //Then we want to add it to our adapter.
        itemsAdapter.add(itemText);

        //Finally we want to clear etNewItem field so the user can enter next item. (without having to clear it itself)
        etNewItem.setText("");

        writeItems();
        Toast.makeText(getApplicationContext(), "Item added to list.", Toast.LENGTH_SHORT).show();
    }

    // Remove logic is done with long pressing.

    private void setupListViewListener() {

        // We will use logging to know when our code will be executed so we will not lose track of it.
        // Its never displayed to the user. (like the toast)
        Log.i("MainActivity", "Setting up Listener on list View.");

        //The content of this method will only be executed on the event of a long press on a item on the list.
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainAvtivity", "Item removed from List." + position);

                //Return is indicating whether or not the method we indicated consumed the long click.
                // Since we are consuming this we need to return true.

                //Since our position matches to our position in our array list we can use this "position" as index.
                items.remove(position);
                // Notifying Adapter that data has changed.
                itemsAdapter.notifyDataSetChanged();

                writeItems();
                return true;
            }
        });

    }

    //Will return a file that will allow us to acces the stored model.
    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }

    // 1 method to read files and the other to write.
    private void readItems() {

        try {
            // We are initialising our model. (we will use its contents)
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {

            Log.e("MainActivity", "Error reading file.", e);

            // Initialise items into an empty array just to make sure that the object is instantiated correctly So we dont end with nullPointerException.
            items = new ArrayList<>();

        }
    }

    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing file.", e);
        }
    }

}
