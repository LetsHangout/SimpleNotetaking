package com.letshangout.simplenotetaking.slyvr;

import java.net.InetAddress;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.letshangout.simplenotetaking.MainActivity;
import com.letshangout.simplenotetaking.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SlyvrActivity extends Activity {

	Button button;
	private static String name;
	private static String input;
	private static Client client;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slyvr);
        addListenerOnButton();
        
        //startClient();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void addListenerOnButton() {
		final Context context = this;
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
			}
		});
	}
    
    public void startClient(){
    	name = "";
		client = new Client();
		RegisterClasses.register(client.getKryo());
		client.start();
    	
    	//Input address
		String address = "127.0.0.1";
		String addressInput = "";
		if (!"".equals(addressInput)){
			address = addressInput;
		}
		else{
			//Search for address
			System.out.println("Searching for local address...");
			InetAddress addr = client.discoverHost(54777, 5000);
			if (addr!=null){
				address = new String(addr.getHostAddress());
			}
			else{
				System.out.println("No local address could be found.  Please start up a local server or input the IP of a machine that has a server running.");
				System.exit(0);
			}
		}
		
		//Request 'login' details
		name = "";
		if (name.length()==0) name = "Guest";
		
		//Connect to server
		try{
			System.out.println("Connecting to : "+address+" (TCP:54555) (UDP:54777)");
			client.connect(5000, address, 54555, 54777);
			SomeRequest request = new SomeRequest();
			request.text = "("+name+")"+" has connected";
			client.sendTCP(request);
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Could not connect to server.  Exitting...");
			System.exit(0);
		}
		
		//Listen to server
		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
			   if (object instanceof SomeResponse) {
				  SomeResponse response = (SomeResponse)object;
				  System.out.println(response.text);
			   }
			}
		 });
		
		System.out.println("Welcome ("+name+").  Hit ctrl+c at any point to leave.  Type a message below to start chatting");
		
		//Loop application input
		while(true){
			input="";
			input = "["+name+"] ";
		
			if (input.length()>0){
				SomeRequest request = new SomeRequest();
				request.text = input;
				client.sendTCP(request);
			}
		}
    }
    
}
