package com.example.gettingridofstuff;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*DatabaseManager class contains fields and methods that control a database of donation centers */
public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "donationDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_DONATION = "donation";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String HOURS = "hours";
    private static final String CATEGORY = "category";
    private static final String ADDRESS = "address";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();
    private  ArrayList<Charity> charities = new ArrayList<>();

    /*Constructor that connects to the Firebase database */
    public DatabaseManager( Context context, MainActivity parent ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                charities.clear();
                for (DataSnapshot child : snapshot.child("donation").getChildren()) {
                    Log.i("GRS-DEBUG", child.child("Address").getValue(String.class));
                    //Not sure what to put for cid here
                    Charity item = new Charity(child.child("Name").getValue(String.class), child.child("Hours").getValue(String.class), 0,
                            child.child("Category").getValue(String.class), child.child("Address").getValue(String.class),
                            Double.parseDouble(child.child("Latitude").getValue(String.class)), Double.parseDouble(child.child("Longitude").getValue(String.class)));
                    charities.add(item);
                }

                Log.i("GRS-DEBUG", "ARRAY: " + charities.toString());
                parent.update();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*Initializes our database */
    public void onCreate( SQLiteDatabase db ) {
        // build sql create statement
        //Fix create table query
        String sqlCreate = "create table " + TABLE_DONATION + "( ";
        sqlCreate +=  ID + " integer primary key autoincrement, ";
        sqlCreate +=  NAME + " text, ";
        sqlCreate +=  HOURS + " text, ";
        sqlCreate +=  CATEGORY + " text, ";
        sqlCreate +=  ADDRESS + " text, ";
        sqlCreate +=  LONGITUDE + " double, ";
        sqlCreate +=  LATITUDE + " double )";

        db.execSQL( sqlCreate );
    }

    /*Updates our database table, currently do not need to use */
    public void onUpgrade( SQLiteDatabase db,
                           int oldVersion, int newVersion ) {
        // Drop old table if it exists
        db.execSQL( "drop table if exists " + TABLE_DONATION );
        // Re-create tables
        onCreate( db );
    }

    /*Insert adds a donation center into the database */
    public void insert( Charity charity ) {
        SQLiteDatabase db = this.getWritableDatabase( );
        String sqlInsert = "insert into " + TABLE_DONATION;
        sqlInsert += " values( null, ' ";
        sqlInsert += charity.getName( ) + "', ' ";
        sqlInsert += charity.getHours( ) + "', ' ";
        sqlInsert += charity.getCategory( ) + "', ' ";
        sqlInsert += charity.getAddress( ) + "', ' ";
        sqlInsert += charity.getLongitude( ) + "', ' ";
        sqlInsert += charity.getLatitude( ) + "' )";

        db.execSQL( sqlInsert );
        db.close( );
    }


    /*selectAll returns all the Charity items currently in our database */
    public ArrayList<Charity> selectAll( ) {

        return charities;

//        String sqlQuery = "select * from " + TABLE_DONATION;
//
//        SQLiteDatabase db = this.getWritableDatabase( );
//        Cursor cursor = db.rawQuery( sqlQuery, null );
//
//        ArrayList<Charity> charities = new ArrayList<Charity>( );
//        while( cursor.moveToNext( ) ) {
//            Charity currentCharity
//                    = new Charity(cursor.getString( 1 ),
//                    cursor.getString( 2 ), cursor.getInt( 0 ), cursor.getString(3),cursor.getString(4), cursor.getDouble(6), cursor.getDouble(5));
//            charities.add( currentCharity );
//        }
//        db.close( );
//        return charities;
    }

    /*Currently leftover from local database, will later delete*/
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase( );
        String sqlDelete = "delete from " + TABLE_DONATION;
        //sqlDelete += " where " + ID + " = " + id;

        db.execSQL( sqlDelete );
        db.close( );
    }
}
