package com.example.test1;


import java.util.ArrayList;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;






















import com.example.test1.model.values;

import db.fixturetable;
import adapter.fixturelist;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity 
{

	
	
	
	
	
	prostartstop prog;
	
	//SQLiteDatabase dbobj;
	
	fixturetable dbobj;
	
	String url="http://api.football-data.org/alpha/soccerseasons/398/fixtures";
	
	String jsonStr=" ",status="  ";
	
	ArrayList list_fixture = new ArrayList();
	
	ListView lv;
	static int count;
	SQLiteDatabase sdb;
	
	
	
	public int getcount()
    {
	
	sdb=dbobj.getWritableDatabase();
	String selectQuery = "SELECT * FROM fixture_table";           
    
    Cursor c = sdb.rawQuery(selectQuery, null);
    c.moveToFirst();
    int total = c.getCount();
    c.close();

    return total;
}

	
	
	public void Retrivevalues()
	{

		sdb=dbobj.getReadableDatabase();
		String selectQuery = "SELECT * FROM fixture_table";           
	    
	    Cursor c = sdb.rawQuery(selectQuery, null);
	    c.moveToFirst();
	    
	    while(!c.isAfterLast())
	    {
	    	
	    	
	    	
	    	
	    	
	    	String date=c.getString(c.getColumnIndex("date"));
			String status=c.getString(c.getColumnIndex("status"));
			String matchday=c.getString(c.getColumnIndex("matchday"));
			String hometeamname=c.getString(c.getColumnIndex("hometeamname"));
			String awayteamname=c.getString(c.getColumnIndex("awayteamname"));
			String goalsHomeTeam=c.getString(c.getColumnIndex("goalsHomeTeam"));
			String goalsAwayTeam=c.getString(c.getColumnIndex("goalsAwayTeam"));
			
			list_fixture.add(new values(date, hometeamname, awayteamname, status, matchday, goalsAwayTeam, goalsHomeTeam));
	    c.moveToNext();
	    }
	    
	    
		c.close();
		sdb.close();
		
		
		
		fixturelist adapter=new fixturelist(MainActivity.this,list_fixture);
		lv.setAdapter(adapter);
	
	}
	
	
	
	class background extends AsyncTask<Void,Void,Void>
	{

		
		
		@Override
		protected void onPreExecute() 
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
			prog.startprogress("Loading");
		}
		
		
		@Override
		protected Void doInBackground(Void... params) 
		{
			
			
			

			// Toast.makeText(getActivity(),
			// "Lattitude is "+langitude,Toast.LENGTH_LONG).show();
			// nameValuePairs.add(new
			// BasicNameValuePair("email",input_username));
			// nameValuePairs.add(new
			// BasicNameValuePair("password",input_password));

			// nameValuePairs.add(new BasicNameValuePair("lon",langi));

			try {

				HttpClient httpclient = new DefaultHttpClient();

				HttpGet httppost = new HttpGet(url);
				
				// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();

				if (statusCode == 200) 
				{
					status="success";
					HttpEntity entity = response.getEntity();
					jsonStr = EntityUtils.toString(entity);
					//JSONArray mainObj = new JSONArray(jsonStr);
					JSONObject mainObj= new JSONObject(jsonStr);
					if (mainObj != null) 
					{
						
						String temp=mainObj.getString("count");
						if(temp!=null)
						count=Integer.parseInt(temp);
					
					
					
							
						
						
						
						JSONArray list = mainObj.getJSONArray("fixtures");
						
						//JSONObject elem = list.getJSONObject(0);
						if (list != null) 
						{

							
								for (int j = 0; j < list.length(); j++) 
								{
									JSONObject innerElem = list.getJSONObject(j);
									
									String date=innerElem.getString("date");
									String status=innerElem.getString("status");
									String matchday=innerElem.getString("matchday");
									String hometeamname=innerElem.getString("homeTeamName");
									String awayteamname=innerElem.getString("awayTeamName");
									
									
									JSONObject innner1=innerElem.getJSONObject("result");
									
									
									String goalsHomeTeam=innner1.getString("goalsHomeTeam");
									String goalsAwayTeam=innner1.getString("goalsAwayTeam");
									
									list_fixture.add(new values(date, hometeamname, awayteamname, status, matchday, goalsAwayTeam, goalsHomeTeam));
									
									ContentValues cv=new ContentValues();
		
									/*String Table_name="CREATE TABLE fixture_table(date varchar, hometeamname varchar, awayteamname varchar, "
											+ "status varchar,matchday varchar,goalsAwayTeam varchar,goalsHomeTeam varchar);";*/				
									
									
									
									
								
									cv.put("date",date);
									cv.put("hometeamname",hometeamname);
									cv.put("awayteamname",awayteamname);
									cv.put("status",status);
									cv.put("matchday",matchday);
									cv.put("goalsAwayTeam",goalsAwayTeam);
									cv.put("goalsHomeTeam",goalsHomeTeam);
									
									
									
									
									sdb.insert("fixture_table", null, cv);
									
									
								}
								
								sdb.close();
								
								
								
							}

					
					}

				}
				else 
				{
					status = "failure";
				}

		
				
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				Log.d("Exception", e.toString());
				e.printStackTrace();
			}
			
			
			
			
			// TODO Auto-generated method stub
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) 
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			try {
			
				
				//values adapter=new values(date, hometeamname, awayteamname, status, matchday, goalsAwayTeam, goalsHomeTeam)
				
				fixturelist adapter=new fixturelist(MainActivity.this,list_fixture);
				
				
				lv.setAdapter(adapter);
			
			//Toast.makeText(getApplicationContext(),"dataBase value is"+String.valueOf(getcount()),Toast.LENGTH_LONG).show();
			//Toast.makeText(getApplicationContext(),"dataBase value is"+String.valueOf(getcount()),Toast.LENGTH_LONG).show();
				prog.stopprogress();
				//Toast.makeText(getApplicationContext(), "Finished"+status+jsonStr,Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//Toast.makeText(getApplicationContext(), "Exception"+status+jsonStr,Toast.LENGTH_LONG).show();
				prog.stopprogress();
				AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this);
				
			ad.setMessage("Exception\n"+e.toString());
				ad.show();
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	
	
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_main);
			prog=new prostartstop(MainActivity.this);
			
			dbobj=new fixturetable(MainActivity.this,"fixturetable",null,8);
			sdb=dbobj.getWritableDatabase();
		
			
			lv=(ListView)findViewById(R.id.listView1);
			
			if (android.os.Build.VERSION.SDK_INT > 9) {

				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
				if (getcount()==0) 
				{
					
				InternetAvailabily  icheck=new InternetAvailabily();
				
				if(icheck.isNetworkAvailable(getApplicationContext()))
				{
					
					new background().execute();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Connect The Internet",Toast.LENGTH_LONG).show();
				}
				} 
				else 
				{

					//Toast.makeText(getApplicationContext(),"Count is" + String.valueOf(getcount()),Toast.LENGTH_LONG).show();

					Retrivevalues();

				}

			
			
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
		
			Toast.makeText(getApplicationContext(), "Exception"+e.toString(),Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
	
		AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this);
		ad.setTitle("Are You Sure Want to Exit Application!");
		//ad.setMessage("Yes To Exit No to Cancel");
		ad.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		
		ad.setNegativeButton("No",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		ad.show();
		
		
		//	super.onBackPressed();
	}
	
}
