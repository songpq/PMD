package app.android.pmdlocker.com.pmd_locker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class LangDataBaseHelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "LangPMD";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_NAME = "language_PMD";
	public static final String FILE_DIR = "language_PMD";

	private static final String DATABASE_USER_CREATE = "CREATE TABLE "
			+ TABLE_NAME + "("
			+ LangDataManager.COLUMN_Lang_TextName + " TEXT PRIMARY KEY NOT NULL, " 
			+ LangDataManager.COLUMN_Lang_TextEN + " TEXT, "
			+ LangDataManager.COLUMN_Lang_TextVI + " TEXT );";

	public LangDataBaseHelper(Context context) {
		super(context, context.getCacheDir()
	            + File.separator + FILE_DIR
	            + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_USER_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

}
