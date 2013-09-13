RoboMe Android SDK
===============

The RoboMe Android SDK lets you send and receive commands to and from your [RoboMe robot](http://www.wowwee.com).

Documentation of the SDK is available here: http://www.wowweelabs.com/SDKs/RoboMe/Android/index.html

Support is available at the RoboCommunity forums: http://www.robocommunity.com/robome

For iOS or Unity SDKs visit: http://www.wowweelabs.com

For information on WowWee products visit: http://www.wowwee.com

Getting Started
---------------------------------------

Download the [RoboMe Android SDK](https://github.com/WowWeeLabs/RoboMe-Android-SDK).

The quickest way to get started is to open the sample app under the SampleProject directory. The RoboMe sample app starts listening to events from RoboMe and prints these to the text view. It also displays buttons to send a few movement commands to RoboMe. For a full list of commands see RoboMeCommands class.

In Eclipse import the sample project by clicking Edit->Import then selecting Android / Existing Android code into workspace.

Creating your own app using the RoboMe Android SDK
---------------------------------------------------

Here are the basic steps in creating your own app:

1. In Eclipse, create a new Android Application project.

2. Copy the RoboMeLib.jar from the RoboMe-Android-SDK directory into your project's libs directory.

3. Copy the RoboMe directory from the SampleProject/assets to your project's assets directory.

Next steps:

1. In your new activity import the required libraries:

		import com.wowwee.robome.*;
		import com.wowwee.robome.RoboMeCommands.*;

2. Implement the RoboMeListener class so that you can receive callbacks e.g.

		public class YourActivity extends Activity implements RoboMe.RoboMeListener

3. Add a property for the RoboMe object.

		private RoboMe robome;

4. In the onCreate method add the following code to initialize and start the connection to RoboMe.

		robome = new RoboMe(this, this);
		robome.setVolume(12);
		robome.startListening();

5. Implement the required callback methods for RoboMeListener.

		public void commandReceived(RoboMeCommands.IncomingRobotCommand command){
			// Command received from RoboMe	
		}

		public void roboMeConnected(){
			// RoboMe was connected
		}
			
		public void roboMeDisconnected(){
			// RoboMe was disconnected
		}

		public void headsetPluggedIn(){
			// Headset plugged in
		}

		public void headsetUnplugged(){
			// Headset unplugged
		}
		
		public void volumeChanged(float volume){
			// Media volume changed (value between 0..15)
		}
		
		

License
-----------------------------------------------

RoboMe Android SDK is available under the Apache License, Version 2.0 license. See the LICENSE.txt file for more info.