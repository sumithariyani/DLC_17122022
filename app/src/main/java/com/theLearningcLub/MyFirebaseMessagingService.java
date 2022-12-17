package com.theLearningcLub;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.theLearningcLub.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    String title,message,timestamp,s_new_date;
    SessionManager sessionManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        sessionManager = new SessionManager(getBaseContext());

        String s_date=remoteMessage.getData().toString();
        s_new_date = s_date.replaceAll("=",":");

        if (s_new_date.length() > 0) {
          try {
              JSONObject object=new JSONObject(s_new_date);
              sendPushNotification(object);
          }catch (Exception e) {
              Log.e(TAG, "Exception: " + e.getMessage());
          }
        }

    }


    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log

       // Log.e(TAG, "Notification JSON " + json.toString());
        try {

            JSONObject data = json.getJSONObject("data");


              title = data.getString("title");
              message = data.getString("message");
              timestamp = data.getString("timestamp");

//              System.out.println("title >>>>>>>>>>>>>>"+title);
//              System.out.println("message >>>>>>>>>>>>>>"+message);
//              System.out.println("timestamp >>>>>>>>>>>>>> "+timestamp);

            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("message",message);

            sessionManager.setnokey("1");
            sessionManager.setnotime(timestamp);
            sessionManager.setnotitle(title);
            sessionManager.setnomsg(message);

            mNotificationManager.showSmallNotification(title, message, intent);

        }catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

}
