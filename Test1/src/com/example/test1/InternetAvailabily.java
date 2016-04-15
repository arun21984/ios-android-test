package com.example.test1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetAvailabily 
{
	 public static boolean isNetworkAvailable(Context context) 
	 {
	        boolean outcome = false;


	        if (context != null) {
	            ConnectivityManager cm = (ConnectivityManager) context
	                    .getSystemService(Context.CONNECTIVITY_SERVICE);

	            if (cm != null)
	            {
	                NetworkInfo[] info = cm.getAllNetworkInfo();
	                if (info != null)
	                    for (int i = 0; i < info.length; i++)
	                        if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                        {
	                        	outcome = true;
	                        	break;
	                        }
	            }
	        }
	        return outcome;
	   
	    }
}
