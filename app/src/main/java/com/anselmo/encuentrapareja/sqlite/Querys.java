package com.anselmo.encuentrapareja.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.anselmo.encuentrapareja.model.Tip;
import com.anselmo.encuentrapareja.utils.Constants;

import java.util.ArrayList;

/**
 * Provides functions CRUD Database
 * 
 * @author Naranya
 * @version 1.1 date Oct 23, 2014
 */
public class Querys {
	private static SQLiteDatabase dataBaseHelper = SQLiteDatabase.openDatabase(Constants.DB_PATH_DATABASE + Constants.DB_NAME_DATABASE, null, SQLiteDatabase.OPEN_READWRITE);

	/**
	 * Add a new tip to database
	 *
	 * @param tipOfDay
	 */
	public static void addTip( Tip tipOfDay ) {
		ContentValues newRegister = new ContentValues();
		newRegister.put("id", tipOfDay.getId());
		newRegister.put("tip", tipOfDay.getTip());
		newRegister.put("date", tipOfDay.getDate());

		//Add
		dataBaseHelper.insert("tbl_tips", null, newRegister);
	}

	/**
	 * Get all elements from tbl_tips
	 *
	 * @return ArrayList<Tip>
	 */
	public static ArrayList<Tip> getAllTips() {
		//Query
		String query = "SELECT * FROM tbl_tips";
		
		//Cursor
		Cursor cursor = dataBaseHelper.rawQuery(query, null);
		
		//Ids
		ArrayList<Tip> items = null;
		
		if( cursor.getCount() != 0 ) {
			items = new ArrayList<Tip>(cursor.getCount()); //Create array
			while( cursor.moveToNext() ) {
				items.add( new Tip( cursor.getInt(0), cursor.getString(1), cursor.getString(2) ) );
			}
		}
        cursor.close();
		return items;
	}

	/**
	 * Gets justo one tip
	 *
	 * @param id ID TIP
	 *
	 * @return Tip
	 */
	public static Tip getTip( int id ) {
		//Query
		String query = "SELECT * FROM tbl_tips WHERE id = " + id;

		//TIP ID
		Tip currentTip = null;

		//Cursor
		Cursor cursor = dataBaseHelper.rawQuery(query, null);

		if( cursor.getCount() != 0 ) {
			currentTip = new Tip();
			while( cursor.moveToNext() ) {
				currentTip.setId(cursor.getInt(0));
				currentTip.setTip(cursor.getString(1));
				currentTip.setDate(cursor.getString(2));
			}
		}

		cursor.close();
		return currentTip;
	}

	/**
	 * Remove all elements from tips table
	 */
	public static void clearTipsTable() {
		String query = "DELETE FROM tbl_tips";
		dataBaseHelper.execSQL(query);
	}
}