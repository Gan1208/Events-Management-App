package com.example.eventsmanagementapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;


public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for (SmsMessage currentMessage : messages) {
            String message = currentMessage.getDisplayMessageBody();
            Intent msgIntent = new Intent();

            msgIntent.setAction(KeyStore.SMS_FILTER);
            msgIntent.putExtra(KeyStore.SMS_MSG_KEY, message);

            context.sendBroadcast(msgIntent);
        }
    }
}