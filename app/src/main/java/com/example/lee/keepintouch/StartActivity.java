package com.example.lee.keepintouch;


import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class StartActivity extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final String TAG = "Contacts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        askForPermission();

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//                cursor.moveToNext();
//
                if (cursor.getCount() != 0) {
                    int numContacts = cursor.getCount();


//                CharSequence text = "Total number is" + numContacts;
////
//                Toast.makeText(this,text, Toast.LENGTH_LONG).show();

                    String myID;


                    ArrayList<String> idList = new ArrayList<>();
                    Random rand = new Random();
                    int randomNum = rand.nextInt(numContacts);

                    //TODO: Create Toast message here

                    while (cursor.moveToNext()) {

                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        idList.add(id);
                    }

                    myID = idList.get(randomNum);
                    String myString = Integer.toString(randomNum);
                    TextView myTextView = (TextView) findViewById(R.id.textViewID);
                    myTextView.setText(myString);
                    if (myID != null) {

                        myTextView.setText(myID);
                    } else {

                        myTextView.setText("Try Again!");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Your have no contacts.", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }//end onClick
        });
    }

    private void askForPermission() {
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_CONTACTS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                showMessageOKCancel("You need to allow access to Contacts",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
    }//end askForPermission()

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(StartActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(StartActivity.this, " Permission Granted", Toast.LENGTH_SHORT)
                            .show();
                    // Permission Granted put your code here

                } else {
                    // Permission Denied
                    Toast.makeText(StartActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}




