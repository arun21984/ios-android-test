package db;





import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.OpenableColumns;
import android.widget.Toast;

public class fixturetable extends SQLiteOpenHelper{

	
	String Table_name="CREATE TABLE fixture_table(date varchar,hometeamname varchar, awayteamname varchar, "
			+ "status varchar,matchday varchar,goalsAwayTeam varchar,goalsHomeTeam varchar);";
	
	Context context;
	
	public fixturetable(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
	
		
		
		db.execSQL(Table_name);
		
		//Toast.makeText(context,"Table Created",Toast.LENGTH_LONG).show();
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + "fixture_table");
	}

}
