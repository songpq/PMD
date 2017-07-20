package app.android.pmdlocker.com.pmd_locker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class LangDataManager {

	private LangDataBaseHelper helper;
	private SQLiteDatabase database;

	private static final String TABLE_LANG = LangDataBaseHelper.TABLE_NAME;

	public static final String COLUMN_Lang_TextName = "Lang_Name";
	public static final String COLUMN_Lang_TextVI = "Lang_TextVI";
	public static final String COLUMN_Lang_TextEN = "Lang_TextEN";
	Context mContext;
	private static LangDataManager mInstantLang;
	public static LangDataManager getLangDataManagerInstant(Context context)
	{
		if(mInstantLang==null)
			mInstantLang = new LangDataManager(context);
		return mInstantLang;
	}
	public LangDataManager(Context context) {
		mContext = context;
		helper = new LangDataBaseHelper(context);
		openRead();
	}

	private void openRead() {
		database = helper.getReadableDatabase();
	}

	private void openWrite() {
		database = helper.getWritableDatabase();
	}

	private void closeDatabase() {
		database.close();
	}

	public String getText(int idString,String textLanguage)
	{
		String textName = mContext.getResources().getResourceEntryName(idString);
		String result = getText(textName,textLanguage);
		if(result==null)
			result = mContext.getString(idString);
		return result;
	}

	public String getText(String textName,String textLanguage) {
		
		try {
//			openRead();
			String sql = String.format(
					"SELECT %s FROM %s Where `%s` = '%s'",
					textLanguage,TABLE_LANG,COLUMN_Lang_TextName,textName);

			Cursor cursor = database.rawQuery(sql, null);
			try{
				if (cursor.moveToFirst()) {
					return cursor.getString(cursor.getColumnIndex(textLanguage));
				}
			}catch(Exception ee)
			{
				
			}
			cursor.close();
//			closeDatabase();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void deleteAll() {
		try {
			openWrite();
			database.delete(TABLE_LANG, null, null);
			closeDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//
	public void deleteLang(String textName) {
		if (TextUtils.isEmpty(textName)) 
		{
			return;
		}
		try {
			openWrite();
			String whereClause = String.format("%s = %s", COLUMN_Lang_TextName,textName);
			database.delete(TABLE_LANG, whereClause, null);
			closeDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//
//
	private ContentValues getData(String textName,String textLang,String textValue) {
		ContentValues result = new ContentValues();
		result.put(COLUMN_Lang_TextName, textName);
		result.put(textLang, textValue);		
		return result;
	}
	public void insertTextLang(String textName,String textLanguage,String textValue) {
		// ObuutLog.d("=== insert msg: " + item.toJson());
		try {
			openWrite();
			ContentValues values = getData(textName,textLanguage,textValue);
			database.insert(TABLE_LANG, null, values);
			closeDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//
//	
	public void updatetTextLang(String textName,String textLanguage,String textValue) {
		try {
			openWrite();
			ContentValues result = new ContentValues();			
			result.put(textLanguage, textValue);
			String whereClause = String.format("`%s` = '%s'", COLUMN_Lang_TextName,textName);
			database.update(TABLE_LANG, result, whereClause, null);
			database.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateOrInsert(String textName,String textLanguage,String textValue)
	{
		
		if(getText(textName,textLanguage)!=null){						    		    	
	    	updatetTextLang(textName, textLanguage, textValue);
	    }
	    else{						    		    	
			insertTextLang(textName, textLanguage, textValue);
	    }		
	}
	

}
