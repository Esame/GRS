package com.example.gettingridofstuff;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "donationDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_DONATION = "donation";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String HOURS = "hours";
    private static final String CATEGORY = "category";
    private static final String ADDRESS = "address";

    public DatabaseManager( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    public void onCreate( SQLiteDatabase db ) {
        // build sql create statement
        //Fix create table query
        String sqlCreate = "create table " + TABLE_DONATION + "( ";
        sqlCreate +=  ID + "integer primary key autoincrement, ";
        sqlCreate +=  NAME + "text, ";
        sqlCreate +=    HOURS + "text, ";
        sqlCreate +=  CATEGORY + "text, ";
        sqlCreate +=  ADDRESS + "text )";

        db.execSQL( sqlCreate );
    }

    public void onUpgrade( SQLiteDatabase db,
                           int oldVersion, int newVersion ) {
        // Drop old table if it exists
        db.execSQL( "drop table if exists " + TABLE_DONATION );
        // Re-create tables
        onCreate( db );
    }

    public void insert( Charity charity ) {
        SQLiteDatabase db = this.getWritableDatabase( );
        String sqlInsert = "insert into " + TABLE_DONATION;
        sqlInsert += " values(" + charity.getId() + "', '";
        sqlInsert += charity.getName( ) + "', '";
        sqlInsert += charity.getHours( ) + "', '";
        sqlInsert += "', '" + charity.getCategory( ) + "' ";
        sqlInsert += "', '" + charity.getAddress( ) + "' )";

        db.execSQL( sqlInsert );
        db.close( );
    }


    public ArrayList<Charity> selectAll( ) {
        String sqlQuery = "select * from " + TABLE_DONATION;

        SQLiteDatabase db = this.getWritableDatabase( );
        Cursor cursor = db.rawQuery( sqlQuery, null );

        ArrayList<Charity> charities = new ArrayList<Charity>( );
        while( cursor.moveToNext( ) ) {
            Charity currentCharity
                    = new Charity(cursor.getString( 0 ),
                    cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString(3),cursor.getString(4) );
            charities.add( currentCharity );
        }
        db.close( );
        return charities;
    }

}
