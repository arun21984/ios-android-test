package adapter;

import java.util.ArrayList;

import com.example.test1.R;
import com.example.test1.model.values;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class fixturelist extends ArrayAdapter<values>
{

	private Context context;
	private ArrayList<values> items;
	private LayoutInflater vi;
	
	
	TextView textHomeTeamName,textAwayTeamName,textStatus,textGoals,textDateandtime;
	
	
	
	public fixturelist(Context context,ArrayList<values> items) 
	{
		super(context,0,items);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.items = items;
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		View v = convertView;

		final values i = items.get(position);

		values ei = (values) i;
		v = vi.inflate(R.layout.designing,null);
	
		
		try 
		{
			textHomeTeamName=(TextView)v.findViewById(R.id.textHomeTeamName);
			textAwayTeamName=(TextView)v.findViewById(R.id.textAwayTeamName);
			textStatus=(TextView)v.findViewById(R.id.textStatus);
			textGoals=(TextView)v.findViewById(R.id.textGoals);
			textDateandtime=(TextView)v.findViewById(R.id.textDateandtime);
		
			
			if(ei.hometeamname!=null)
			textHomeTeamName.setText(ei.hometeamname);
			if(ei.awayteamname!=null)
			textAwayTeamName.setText(ei.awayteamname);
			if(ei.status!=null)
			textStatus.setText(ei.status);
			if(ei.goalsHomeTeam!=null&&ei.goalsAwayTeam!=null)
			textGoals.setText("Home: "+ei.goalsHomeTeam+"  Away: "+ei.goalsAwayTeam);
			
			if(ei.date!=null)
			{
			String s=ei.date;
				
				String dateformated=ei.date.substring(0,s.indexOf("T"))+" Time:"+ei.date.substring(s.indexOf("T")+1,s.length()-1);
				textDateandtime.setText(dateformated);
			}
			
			//tv6.setText(ei.goalsAwayTeam);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			
			
			Toast.makeText(getContext(), "Designing Exception"+e.toString(),Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		
		
		// TODO Auto-generated method stub
		return v;
	
	
	
	}

	

}
