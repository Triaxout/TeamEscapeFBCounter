package de.teamescape.dev.teamescapefbcounter.utils;

/**
 * Created by Triax Sijambo on 25.11.2015.
 */

import android.os.AsyncTask;
import android.util.Log;


public class MailSenderTask extends AsyncTask {
    private String mailContent;

    public MailSenderTask(String mailcontent){
        this.mailContent = mailcontent;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            //send log report to dev mail account
            GMailSender sender = new GMailSender("developteamescape@gmail.com", "binichschondrin?");
            sender.sendMail("Log Report TeamEscape Facebook Counter",
                    mailContent,
                    "developteamescape@gmail.com",
                    "developteamescape@gmail.com");
            return true;
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
            return false;
        }
    }
}
