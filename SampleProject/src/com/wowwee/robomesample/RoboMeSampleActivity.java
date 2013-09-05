package com.wowwee.robomesample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wowwee.robomesample.R;
import com.wowwee.robome.*;
import com.wowwee.robome.RoboMeCommands.*;

/** 
 * RoboMe Sample Activity demonstrates how to get started with the RoboMe Android SDK
 * 
 * - Creates a connection to RoboMe and starts listening for events. 
 * - Displays buttons for basic movement. 
 * 
 */
public class RoboMeSampleActivity extends Activity implements RoboMe.RoboMeListener {
	/** Connection to RoboMe */
	private RoboMe robome; 
	
	/** Handler to update log view */
	private Handler handler;
	/** Log scroll view */
	private ScrollView logScrollView;
	/** Log view to print events to */
	private TextView logView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// create RoboMe connection with this as the context and listener
		robome = new RoboMe(this, this);
				
		// use robome.xml
		setContentView(R.layout.robome);
		
		logView = (TextView) findViewById(R.id.output);
		logScrollView = (ScrollView) findViewById(R.id.outputScrollView);
		
		// show version
		logView.append("Version " + robome.getLibVersion() + "\n");
		
		// handler to display received event
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// display the received event
				if (msg.what == 0x99 )  
					logView.append(msg.obj + "\n");
				logScrollView.smoothScrollTo(0, logView.getHeight());
			}
		};
	}
	
	/** Start listening to events from the gun when the app starts or resumes from background */
	@Override
	public void onResume(){
		super.onResume();
		
		// set media volume to 12
		robome.setVolume(12);
		// start listening to events from the headset
		robome.startListening();
	}
	
	/** Stop listening to events from the gun when the app goes into the background */
	@Override
	public void onStop(){
		super.onStop();
		
		// stop listening to events from the headset
		robome.stopListening();
	}
		
	/** Sends message to our handler to display the text in the output */
	public void showText(String text){
		Message msg = new Message();
		msg.what = 0x99;
		msg.obj = text;
		handler.sendMessage(msg);
	}
	
	/* ----- RoboMe Listener callbacks ------ */
	
	/** Called when an event is received from the gun */
	@Override
	public void commandReceived(RoboMeCommands.IncomingRobotCommand command){
		android.util.Log.d("RoboMe","Received event " + command);
		
		this.showText("Received: " + command.toString());
		
		if(command.isSensorStatus()){
			SensorStatus status = command.readSensorStatus();
			this.showText(String.format("Edge: %b Chest 20cm: %b 50cm: %b 100cm: %b", status.edge, status.chest_20cm, status.chest_50cm, status.chest_100cm)); 
		}
	}
	
	/** Called when RoboMe is connected to the headphone jack and the library receives the connect command */
	@Override
	public void roboMeConnected(){
		this.showText("RoboMe Connected");
	}
	
	/** Called when RoboMe is disconnected or goes to sleep */
	@Override
	public void roboMeDisconnected(){
		this.showText("RoboMe Disconnected");
	}

	/** Called when the headset is plugged in */
	@Override
	public void headsetPluggedIn(){
		this.showText("Headset plugged in");
	}

	/** Called when the headset is unplugged */
	@Override
	public void headsetUnplugged(){
		this.showText("Headset unplugged");
	}
	
	/** Called when the media volume changes. On some devices if the volume is lowered to below 10 the library will not pick up command.
	 * 
	 *  @param volume Volume value between 0-15
	 *  
	 *  */ 
	@Override
	public void volumeChanged(float volume){
		this.showText("Volume changed to " + volume + "%");
	}
		
	/* ------ Button callbacks ------ */
	
	public void startBtnPressed(View view){
		robome.startListening();
	}
	
	public void stopBtnPressed(View view){
		robome.stopListening();
	}
	
	public void headUpBtnPressed(View view){
		robome.sendCommand(RobotCommand.kRobot_HeadTiltAllUp);
	}
	
	public void headDownBtnPressed(View view){
		robome.sendCommand(RobotCommand.kRobot_HeadTiltAllDown);
	}
	
	public void forwardBtnPressed(View view){
		robome.sendCommand(RobotCommand.kRobot_MoveForwardSpeed1);
	}
	
	public void backwardBtnPressed(View view){
		robome.sendCommand(RobotCommand.kRobot_MoveBackwardSpeed1);
	}
	
	public void leftBtnPressed(View view){
		robome.sendCommand(RobotCommand.kRobot_TurnLeftSpeed1);
	}
	
	public void rightBtnPressed(View view){
		robome.sendCommand(RobotCommand.kRobot_TurnRightSpeed1);
	}
	
}
