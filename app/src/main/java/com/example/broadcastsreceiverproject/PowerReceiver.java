package com.example.broadcastsreceiverproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.telephony.SmsMessage;
import android.widget.Toast;

//import java.util.Calendar;

public class PowerReceiver extends BroadcastReceiver {

    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;

        Calendar calendar = Calendar.getInstance();

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            for (int i = 0; i < pdus.length; i++) {
                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                calendar.setTimeInMillis(currentMessage.getTimestampMillis());

                addEvent("B17 Demo", "RJT", 1553112000000, 1553112500000);

            }
        }
    }

    public void addEvent (String title, String location, long begin, long end) {
        Intent intent = new Intent (Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }
}
