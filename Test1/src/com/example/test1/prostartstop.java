package com.example.test1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Message;
import android.widget.ProgressBar;



public class prostartstop 
{
Context c;
ProgressDialog pg;
	public prostartstop(Context ctx) 
	{
		// TODO Auto-generated constructor stub
		c=ctx;
	}
	
	public void startprogress(String title)
	{
		pg=new ProgressDialog(c);
		pg.setCancelable(true);
		pg.setCanceledOnTouchOutside(false);
		pg.setMessage(title);
		pg.show();
		
	}
	
	public void stopprogress()
	{
		pg.dismiss();
	}
	
	
}
