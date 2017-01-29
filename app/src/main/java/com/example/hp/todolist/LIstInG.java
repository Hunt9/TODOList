package com.example.hp.todolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LIstInG extends AppCompatActivity {

    /**
     * Private members of the class
     */
    private TextView pDisplayDate;
    private Button pPickDate;
    private int pYear;
    private int pMonth;
    private int pDay;
    private EditText enterName;
    private EditText enterDiscription;
    private Button submit;
    private String username;
    private String dates;
    private ListView todoLists;
    private TODOAdapter mTODOADAPTER;
    private FirebaseDatabase fbdb;
    private DatabaseReference dbr;
    private ChildEventListener cel;
    private int pos;

    TODO todos = new TODO();

    /**
     * This integer will uniquely define the dialog to be used for displaying date picker.
     */
    static final int DATE_DIALOG_ID = 0;


    List<TODO> todoz = new ArrayList<>();
    /**
     * Callback received when the user "picks" a date in the dialog
     */
    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;

                    dates = Integer.toString(pDay).concat("/").concat(Integer.toString(pMonth + 1).concat("/").concat(Integer.toString(pYear)));
//                    updateDisplay();
                    displayToast(dates);
                }
            };

    /** Updates the date in the TextView */
//    private void updateDisplay() {
//        pDisplayDate.setText(
//                new StringBuilder()
//                        // Month is 0 based so add 1
//                        .append(pMonth + 1).append("/")
//                        .append(pDay).append("/")
//                        .append(pYear).append(" "));
//    }

    /**
     * Displays a notification when the date is updated
     */
    private void displayToast(String a) {
        Toast.makeText(this, "Date choosen is " + a, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_in_g);

        username = "Ahkam";

        fbdb = fbdb.getInstance();
        dbr = fbdb.getReference().child("Listing");


        //        /** Capture our View elements */
//        pDisplayDate = (TextView) findViewById(R.id.displayDate);
        enterName = (EditText) findViewById(R.id.enterName);
        enterDiscription = (EditText) findViewById(R.id.enterDescription);
        submit = (Button) findViewById(R.id.submit);

        pPickDate = (Button) findViewById(R.id.pickDate);

        /** Listener for click event of the button */
        pPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        /** Get the current date */
        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);

        /** Display the current date in the TextView */
        //updateDisplay();

        enterName = (EditText) findViewById(R.id.enterName);
        enterDiscription = (EditText) findViewById(R.id.enterDescription);

        todoLists = (ListView) findViewById(R.id.LList);
        registerForContextMenu(todoLists);


        mTODOADAPTER = new TODOAdapter(this, R.layout.listtodo, todoz);
        todoLists.setAdapter(mTODOADAPTER);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todos = new TODO(enterName.getText().toString(), dates, enterDiscription.getText().toString(), username, null);
                dbr.push().setValue(todos);

                enterDiscription.setText("");
                enterName.setText("");
            }
        });

        cel = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    todos = dataSnapshot.getValue(TODO.class);
                    todos.setKey(dataSnapshot.getKey());
                    mTODOADAPTER.add(todos);
                    todoLists.setAdapter(mTODOADAPTER);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();
                for (TODO todos : todoz){
                    if (key.equals(key)) {
                        todoz.remove(todos);
                        mTODOADAPTER.notifyDataSetChanged();
                        break;

                    }

                }


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        dbr.addChildEventListener(cel);


    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        pDateSetListener,
                        pYear, pMonth, pDay);
        }
        return null;
    }

    public void remove(TODO td) {

        dbr.child(td.getKey()).removeValue();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Action");

        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo)menuInfo;
        pos = info.position;

        if (v.getId() == R.id.LList) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.myop, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Delete:

                TODO td = mTODOADAPTER.getItem(pos);
                remove(td);
                Toast.makeText(this, "Deleted -> " + pos, Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Update:
                Toast.makeText(this, "U", Toast.LENGTH_SHORT).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    }
