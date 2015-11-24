package de.teamescape.dev.teamescapefbcounter.utils;

/**
 * Created by Triax Sijambo on 23.10.2015.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.teamescape.dev.teamescapefbcounter.R;
import de.teamescape.dev.teamescapefbcounter.SettingsActivity;

public class SharedPreferenceHandler {

    public SharedPreferences mPrefs;
    private SharedPreferences.OnSharedPreferenceChangeListener splistener;
    public static final String TEFBC_NAME = "TEFBC_PREFS";
    private static final String TAG = SharedPreferenceHandler.class.getSimpleName();
    public static Activity hostactivity;
    Activity settingsactivity;
    private Context fragmentcontext;

    private ImageView mContentView;

    public String   BACKGROUNDIMAGETITLE;
    public String   BACKGROUNDIMAGEINTERNALURL;
    public String   BACKGROUNDIMAGEURI;

    public String   LOGOIMAGETITLE;
    public String   LOGOIMAGEURI;
    public String   LOGOIMAGEINTERNALURL;
    public Double   LOGOIMAGESIZERATIO;
    public Double   LOGOIMAGELEFTMARGIN;
    public Double   LOGOIMAGETOPMARGIN;

    public String   FACEBOOKIMAGETITLE;
    public String   FACEBOOKIMAGEURI;
    public String   FACEBOOKIMAGEINTERNALURL;
    public Double   FACEBOOKIMAGESIZERATIO;
    public Double   FACEBOOKIMAGELEFTMARGIN; //Value 0.00 - 1.00
    public Double   FACEBOOKIMAGETOPMARGIN; //Value 0.00 - 1.00
    public String   FACEBOOKFQLCALLURL;

    public String   COUNTERTEXTSIZE;
    public Double   COUNTERTEXTLEFTMARGIN;
    public Double   COUNTERTEXTTOPMARGIN;
    public String   LASTKNOWNFACEBOOKCOUNT;
    public Double   COUNTERUPDATEINTEVAL;
    public Boolean  FIXEDALLOCATIONCOUNTERFB;

    public String   QRCODEIMAGETITLE;
    public String   QRCODEIMAGEINTERNALURL;
    public String   QRCODEIMAGEURI;
    public Double   QRCODEIMAGESIZERATIO;
    public Double   QRCODEIMAGELEFTMARGIN;       //Value 0.00 - 1.00
    public Double   QRCODEIMAGETOPMARGIN;        //Value 0.00 - 1.00

    public String   TEXTCONTENTCENTER;
    public Double   TEXTCONTENTCENTERLEFTMARGIN;    //Value 0.00 - 1.00
    public Double   TEXTCONTENTCENTERTOPMARGIN;     //Value 0.00 - 1.00
    public String   TEXTCONTENTCENTERFONTSIZE;

    public String   TEXTCONTENTBUTTOM;
    public Double   TEXTCONTENTBUTTOMLEFTMARGIN;    //Value 0.00 - 1.00
    public Double   TEXTCONTENTBUTTOMTOPMARGIN;     //Value 0.00 - 1.00
    public Boolean  TEXTCONTENTBUTTOMHYPERLINK;
    public String   TEXTCONTENTBUTTOMFONTSIZE;

    public String   LANGUAGE;
    public String   FIRSTRUN;

    public SharedPreferenceHandler(Activity context) {
        super();
        this.hostactivity = context;
        this.settingsactivity = SettingsActivity.activity;

        mContentView =  (ImageView)hostactivity.findViewById(R.id.fullscreen_content);

        mPrefs = context.getSharedPreferences(TEFBC_NAME, Context.MODE_PRIVATE);
        //if(true){
        String first = getValue(context,"FIRSTRUN");
        String done = "DONE";
        if(getValue(context,"FIRSTRUN")==null) {
            firstrun(context);
        }

            this.BACKGROUNDIMAGETITLE = getValue(context, "BACKGROUNDIMAGETITLE");
            this.BACKGROUNDIMAGEINTERNALURL = getValue(context, "BACKGROUNDIMAGEINTERNALURL");
            this.BACKGROUNDIMAGEURI = getValue(context, "BACKGROUNDIMAGEURI");

            this.LOGOIMAGETITLE = getValue(context, "LOGOIMAGETITLE");
            this.LOGOIMAGEURI = getValue(context, "LOGOIMAGEURI");
            this.LOGOIMAGEINTERNALURL = getValue(context, "LOGOIMAGEINTERNALURL");
            this.LOGOIMAGESIZERATIO = Double.parseDouble(getValue(context, "LOGOIMAGESIZERATIO"));
            this.LOGOIMAGELEFTMARGIN= Double.parseDouble(getValue(context, "LOGOIMAGELEFTMARGIN"));
            this.LOGOIMAGETOPMARGIN = Double.parseDouble(getValue(context, "LOGOIMAGETOPMARGIN"));

            this.FACEBOOKIMAGETITLE = getValue(context, "FACEBOOKIMAGETITLE");
            this.FACEBOOKIMAGEURI = getValue(context, "FACEBOOKIMAGEURI");
            this.FACEBOOKIMAGEINTERNALURL = getValue(context, "FACEBOOKIMAGEINTERNALURL");
            this.FACEBOOKIMAGESIZERATIO = Double.parseDouble(getValue(context, "FACEBOOKIMAGESIZERATIO"));
            this.FACEBOOKIMAGELEFTMARGIN = Double.parseDouble(getValue(context, "FACEBOOKIMAGELEFTMARGIN"));
            this.FACEBOOKIMAGETOPMARGIN = Double.parseDouble(getValue(context, "FACEBOOKIMAGETOPMARGIN"));
            this.FACEBOOKFQLCALLURL = getValue(context, "FACEBOOKFQLCALLURL");

            this.LASTKNOWNFACEBOOKCOUNT = getValue(context, "LASTKNOWNFACEBOOKCOUNT");
            this.COUNTERTEXTSIZE = getValue(context, "COUNTERTEXTSIZE");
            this.COUNTERTEXTLEFTMARGIN = Double.parseDouble(getValue(context, "COUNTERTEXTLEFTMARGIN"));
            this.COUNTERTEXTTOPMARGIN = Double.parseDouble(getValue(context, "COUNTERTEXTTOPMARGIN"));
            this.COUNTERUPDATEINTEVAL = Double.parseDouble(getValue(context, "COUNTERUPDATEINTEVAL"));
            this.FIXEDALLOCATIONCOUNTERFB = Boolean.valueOf(getValue(context, "FIXEDALLOCATIONCOUNTERFB"));

            this.QRCODEIMAGETITLE = getValue(context, "QRCODEIMAGETITLE");
            this.QRCODEIMAGEINTERNALURL = getValue(context, "QRCODEIMAGEINTERNALURL");
            this.QRCODEIMAGEURI = getValue(context, "QRCODEIMAGEURI");
            this.QRCODEIMAGESIZERATIO = Double.parseDouble(getValue(context, "QRCODEIMAGESIZERATIO"));
            this.QRCODEIMAGELEFTMARGIN = Double.parseDouble(getValue(context, "QRCODEIMAGELEFTMARGIN"));
            this.QRCODEIMAGETOPMARGIN = Double.parseDouble(getValue(context,"QRCODEIMAGETOPMARGIN"));

            this.TEXTCONTENTCENTER = getValue(context, "TEXTCONTENTCENTER");
            this.TEXTCONTENTCENTERLEFTMARGIN = Double.parseDouble(getValue(context, "TEXTCONTENTCENTERLEFTMARGIN"));
            this.TEXTCONTENTCENTERTOPMARGIN = Double.parseDouble(getValue(context, "TEXTCONTENTCENTERTOPMARGIN"));
            this.TEXTCONTENTCENTERFONTSIZE =  getValue(context, "TEXTCONTENTCENTERFONTSIZE");
            this.TEXTCONTENTBUTTOM = getValue(context, "TEXTCONTENTBUTTOM");
            this.TEXTCONTENTBUTTOMLEFTMARGIN = Double.parseDouble(getValue(context, "TEXTCONTENTBUTTOMLEFTMARGIN"));
            this.TEXTCONTENTBUTTOMTOPMARGIN = Double.parseDouble(getValue(context, "TEXTCONTENTBUTTOMTOPMARGIN"));
            this.TEXTCONTENTBUTTOMFONTSIZE = getValue(context, "TEXTCONTENTBUTTOMFONTSIZE");
            this.TEXTCONTENTBUTTOMHYPERLINK = Boolean.valueOf(getValue(context, "TEXTCONTENTBUTTOMHYPERLINK"));

            this.LANGUAGE = getValue(context, "LANGUAGE");
            this.FIRSTRUN = getValue(context,"FIRSTRUN");


        // Use instance field for listener
        splistener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences mPrefs, String key) {
                // Implementation
                Log.d(TAG,"something changed in shared preferences: key=>"+key+" SharedPreferences=> "+mPrefs.toString());

                TextView mCounterView = (TextView) hostactivity.findViewById(R.id.counter_content);

                switch(key){

                    case "BACKGROUNDIMAGETITLE":
                        Log.d(TAG, "Background updated to image: " + mPrefs.getString(key, null));
                        break;
                    case "BACKGROUNDIMAGEINTERNALURL":
                        Log.d(TAG, "Background image internal url was changed to: "+mPrefs.getString(key,null));
                        break;
                    case "BACKGROUNDIMAGEURI":
                        if(mPrefs.getString(key, null)!=null){
                            //start progressdialog => end in onpostexecute
                            fragmentcontext = SettingsActivity.backgroundfragmentcontext;
                            reactToImageUriChanges(mPrefs, key);
                            Log.d(TAG, "Background image uri was changed: "+mPrefs.getString(key,null));
                        }
                        break;
                    case "LOGOIMAGETITLE":
                        Log.d(TAG, "Logo updated to image: " + mPrefs.getString(key, null));
                        break;
                    case "LOGOIMAGEURI":
                        if(mPrefs.getString(key, null)!=null) {
                            fragmentcontext = SettingsActivity.logofragmentcontext;
                            reactToImageUriChanges(mPrefs, key);
                            Log.d(TAG, "Logo image uri was changed: "+mPrefs.getString(key,null));
                        }
                        break;
                    case "LOGOIMAGESIZERATIO":
                        Log.d(TAG, "Logo image size ratio was changed: "+mPrefs.getString(key,null));
                        break;
                    case "LOGOIMAGELEFTMARGIN":
                        Log.d(TAG, "Logo image left margin was changed: "+mPrefs.getString(key,null));
                        break;
                    case "FACEBOOKIMAGETITLE":
                        Log.d(TAG, "Facebook image title was changed: "+mPrefs.getString(key,null));
                        break;
                    case "FACEBOOKIMAGEURI":
                        if(mPrefs.getString(key, null)!=null) {
                            fragmentcontext = SettingsActivity.facebookfragmentcontext;
                            reactToImageUriChanges(mPrefs, key);
                        }
                        Log.d(TAG, "Facebook image uri was changed: "+mPrefs.getString(key,null));
                        break;
                    case "FACEBOOKIMAGEINTERNALURL":
                        Log.d(TAG, "Facebook image internal url was changed: "+mPrefs.getString(key,null));
                        break;
                    case "FACEBOOKIMAGESIZERATIO":
                        Log.d(TAG, "Facebook image size ratio was changed: "+mPrefs.getString(key,null));
                        break;
                    case "FACEBOOKIMAGELEFTMARGIN":
                        Log.d(TAG, "Facebook image left margin was changed: "+mPrefs.getString(key,null));
                        break;
                    case "FACEBOOKIMAGETOPMARGIN":
                        Log.d(TAG, "Facebook image top margin was changed: "+mPrefs.getString(key,null));
                        break;
                    case "FACEBOOKFQLCALLURL":
                        Log.d(TAG, "Facebook fql call was changed: "+mPrefs.getString(key,null));
                        break;
                    case "COUNTERTEXTSIZE":
                        Log.d(TAG, "Counter text size was changed: "+mPrefs.getString(key,null));
                        break;
                    case "COUNTERTEXTLEFTMARGIN":
                        Log.d(TAG, "Counter text left margin was changed: "+mPrefs.getString(key,null));
                        break;
                    case "COUNTERTEXTTOPMARGIN":
                        Log.d(TAG, "Counter text top margin was changed: "+mPrefs.getString(key,null));
                        break;
                    case "LASTKNOWNFACEBOOKCOUNT":
                        Log.d(TAG, "FB counter updated to: " + mPrefs.getString(key,null));
                        break;
                    case "COUNTERUPDATEINTEVAL":
                        break;
                    case "FIXEDALLOCATIONCOUNTERFB":
                        Log.d(TAG, "Value of fixed allocation counter fb was changed: "+mPrefs.getString(key,null));
                        break;
                    case "QRCODEIMAGETITLE":
                        Log.d(TAG, "QR code title was changed: "+mPrefs.getString(key,null));
                        break;
                    case "QRCODEIMAGEINTERNALURL":
                        Log.d(TAG, "QR code image internal url was changed: "+mPrefs.getString(key,null));
                        break;
                    case "QRCODEIMAGEURI":
                        if(mPrefs.getString(key, null)!=null) {
                            fragmentcontext = SettingsActivity.qrcodefragmentcontext;
                            reactToImageUriChanges(mPrefs, key);
                        }
                        Log.d(TAG, "QR code image uri was changed: "+mPrefs.getString(key,null));
                        break;
                    case "QRCODEIMAGESIZERATIO":
                        Log.d(TAG, "QR code image size ratio was changed: "+mPrefs.getString(key,null));
                        break;
                    case "QRCODEIMAGELEFTMARGIN":
                        Log.d(TAG, "QR code image left margin was changed: "+mPrefs.getString(key,null));
                        break;
                    case "QRCODEIMAGETOPMARGIN":
                        Log.d(TAG, "QR code image top margin was changed: "+mPrefs.getString(key,null));
                        break;
                    case "TEXTCONTENTCENTER":
                        Log.d(TAG, "text content center was changed: "+mPrefs.getString(key,null));
                        break;
                    case "TEXTCONTENTCENTERLEFTMARGIN":
                        Log.d(TAG, "text content center left margin was changed: "+mPrefs.getString(key,null));
                        break;
                    case "TEXTCONTENTCENTERTOPMARGIN":
                        Log.d(TAG, "text content center top margin was changed: "+mPrefs.getString(key,null));
                        break;
                    case "TEXTCONTENTCENTERFONTSIZE":
                        Log.d(TAG, "text content center font size was changed: "+mPrefs.getString(key,null));
                        break;
                    case "TEXTCONTENTBUTTOM":
                        Log.d(TAG, "text content buttom was changed: "+mPrefs.getString(key,null));
                        break;
                    case "TEXTCONTENTBUTTOMLEFTMARGIN":
                        Log.d(TAG, "text content buttom left margin was changed: "+mPrefs.getString(key,null));
                        break;
                    case "TEXTCONTENTBUTTOMTOPMARGIN":
                        Log.d(TAG, "text content buttom top margin was changed: "+mPrefs.getString(key,null));
                        break;
                    case "TEXTCONTENTBUTTOMFONTSIZE":
                        Log.d(TAG, "text content buttom font size was changed: "+mPrefs.getString(key,null));
                        break;
                    case "TEXTCONTENTBUTTOMHYPERLINK":
                        Log.d(TAG, "text content buttom hyperlink value was changed: "+mPrefs.getString(key,null));
                        break;
                    case "FIRSTRUN":
                        Log.d(TAG, "first run value was changed: "+mPrefs.getString(key,null));
                        break;
                    default:
                }

            }
        };

        mPrefs.registerOnSharedPreferenceChangeListener(splistener);
    }

    private String formatcount(String formatedcount) {
        char[] input = formatedcount.toCharArray();
        char[] prepareoutput = new char[6];
        int j = 0;
        //fill up to 6 digits
        for (int i=0; i<prepareoutput.length;++i){
            if(prepareoutput.length-(input.length+i)>0){
                prepareoutput[i]='0';
            }else{
                prepareoutput[i]=input[j];
                ++j;
            }
        }
        //insert : into digits
        char[]output = new char[8];
        int k =0;
        for (int i=0; i<output.length;++i){
            if(i==2 || i==5){
                output[i]=':';
            }else{
                output[i]=prepareoutput[k];
                ++k;
            }
        }
        return new String (output);
    }

    private String saveToInternalStorage(Bitmap bitmap, String imgTitle, String mkey) {
        ContextWrapper cw = new ContextWrapper(hostactivity);
        // path to /data/data/yourapp/app_data/imageDir
        String temp_directory = String.valueOf(cw.getDir("imageDir", Context.MODE_PRIVATE)+"/"+mkey);
        File directory = new File (temp_directory);
        // Create sub imageDir
        if(!directory.exists()){
            directory.mkdir();
        }
        File mypathFile=new File(directory,imgTitle);
        String URLTag = null;
        String TitleTag = null;
        String MimeType = getMimeType(directory.getAbsolutePath() + "/" + imgTitle);

        switch(mkey) {
            case "BACKGROUNDIMAGEURI":
                URLTag = "BACKGROUNDIMAGEINTERNALURL";
                TitleTag = "BACKGROUNDIMAGETITLE";
                break;
            case "LOGOIMAGEURI":
                URLTag = "LOGOIMAGEINTERNALURL";
                TitleTag = "LOGOIMAGETITLE";
                break;
            case "FACEBOOKIMAGEURI":
                URLTag = "FACEBOOKIMAGEINTERNALURL";
                TitleTag = "FACEBOOKIMAGETITLE";
                break;
            case "QRCODEIMAGEURI":
                URLTag = "QRCODEIMAGEINTERNALURL";
                TitleTag = "QRCODEIMAGETITLE";
                break;
            default:
        }

        if(mypathFile.exists()){
            Toast.makeText(hostactivity, "File already exists in storage", Toast.LENGTH_SHORT).show();
            save(hostactivity, URLTag, directory.getAbsolutePath());
            save(hostactivity,TitleTag, imgTitle);
            return directory.getAbsolutePath();
        }else{
            if(MimeType == "image/jpg" || MimeType == "image/jpeg"){
                try {
                    FileOutputStream fos = null;
                    fos = new FileOutputStream(mypathFile);
                    // Use the compress method on the BitMap object to write image to the OutputStream
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    Toast.makeText(hostactivity, imgTitle+" was stored (JPEG)", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(hostactivity, "something went wrong", Toast.LENGTH_SHORT).show();
                }
                //only change URL and Title if the input was successful
                save(hostactivity, URLTag, directory.getAbsolutePath());
                save(hostactivity,TitleTag,imgTitle);
            }else{
                if(MimeType=="image/png"){
                    try {
                        FileOutputStream fos = null;
                        fos = new FileOutputStream(mypathFile);
                        // Use the compress method on the BitMap object to write image to the OutputStream
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                        fos.close();
                        Toast.makeText(hostactivity, imgTitle+" was stored (PNG)", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(hostactivity, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                    //only change URL and Title if the input was successful
                    save(hostactivity, URLTag, directory.getAbsolutePath());
                    save(hostactivity,TitleTag,imgTitle);
                }else{
                    try {
                        FileOutputStream fos = null;
                        fos = new FileOutputStream(mypathFile);
                        // Use the compress method on the BitMap object to write image to the OutputStream
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                        fos.close();
                        Toast.makeText(hostactivity, imgTitle+" was stored (PNG)", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(hostactivity, "something went wrong with mimetype", Toast.LENGTH_SHORT).show();
                    }
                    //only change URL and Title if the input was successful
                    save(hostactivity, URLTag, directory.getAbsolutePath());
                    save(hostactivity,TitleTag,imgTitle);
                }
            }

            return directory.getAbsolutePath();
        }
    }

    public static String getMimeType(String url){
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if(extension!=null){
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    private Bitmap getBitmapFromUri(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = hostactivity.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        //save preload to get dimensions
        onlyBoundsOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        DisplayMetrics metrics = new DisplayMetrics();
        hostactivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if(onlyBoundsOptions.outHeight > metrics.heightPixels || onlyBoundsOptions.outWidth > metrics.widthPixels){
            onlyBoundsOptions.inSampleSize = calculateInSampleSize(onlyBoundsOptions,metrics.widthPixels,metrics.heightPixels);
        }
        input.close();
        input = hostactivity.getContentResolver().openInputStream(uri);
        onlyBoundsOptions.inJustDecodeBounds = false;
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(input,null,onlyBoundsOptions);
        input.close();
        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private void firstrun(Activity context) {
        save(context, "LASTKNOWNFACEBOOKCOUNT", "10000");
        Log.d(TAG, "LASTKNOWNFACEBOOKCOUNT set to 10000");

        save(context, "BACKGROUNDIMAGETITLE", "background_init");
        Log.d(TAG, "BACKGROUNDIMAGETITLE set to background_init");

        save(context, "LOGOIMAGESIZERATIO", "0.43");
        Log.d(TAG, "LOGOIMAGESIZERATIO set to 0.43 (x)");

        save(context, "LOGOIMAGETITLE", "logo_init");
        Log.d(TAG, "LOGOIMAGETITLE set to logo_init");

        save(context, "LOGOIMAGELEFTMARGIN", "0.5");
        Log.d(TAG, "LOGOIMAGETITLE set to 0.5");

        save(context, "LOGOIMAGETOPMARGIN", "0.02");
        Log.d(TAG, "LOGOIMAGETOPMARGIN set to 0.02");

        save(context, "FACEBOOKIMAGETITLE", "facebook_thumb_init");
        Log.d(TAG, "FACEBOOKIMAGETITLE set to facebook_thumb_init");

        save(context, "FACEBOOKIMAGESIZERATIO", "0.10");
        Log.d(TAG, "FACEBOOKIMAGESIZERATIO set to 0.10");

        save(context, "FACEBOOKIMAGELEFTMARGIN", "0.18");
        Log.d(TAG, "FACEBOOKIMAGELEFTMARGIN set to 0.18");

        save(context, "FACEBOOKIMAGETOPMARGIN", "0.5");
        Log.d(TAG, "FACEBOOKIMAGETOPMARGIN set to 0.5");

        save(context, "FACEBOOKFQLCALLURL", "https://api.facebook.com/method/fql.query?query=select%20like_count,%20total_count,%20share_count,%20click_count%20from%20link_stat%20where%20url=%22www.facebook.com/TeamEscapeDE%22");
        Log.d(TAG, "FACEBOOKFQLCALLURL set to https://api.facebook.com/method/fql.query?query=select%20like_count,%20total_count,%20share_count,%20click_count%20from%20link_stat%20where%20url=%22www.facebook.com/TeamEscapeDE%22");

        save(context, "COUNTERTEXTSIZE", "60");
        Log.d(TAG, "COUNTERTEXTSIZE set to 60");

        save(context, "COUNTERTEXTLEFTMARGIN", "0.5");
        Log.d(TAG, "COUNTERTEXTLEFTMARGIN set to 0.5");

        save(context, "COUNTERTEXTTOPMARGIN", "0.5");
        Log.d(TAG, "COUNTERTEXTTOPMARGIN set to 0.5");

        save(context, "COUNTERUPDATEINTEVAL", "1000");
        Log.d(TAG, "COUNTERUPDATEINTEVAL set to 1000ms");

        save(context, "FIXEDALLOCATIONCOUNTERFB", "true");
        Log.d(TAG, "FIXEDALLOCATIONCOUNTERFB set to true");

        save(context, "QRCODEIMAGETITLE", "qrcode_init");
        Log.d(TAG, "QRCODEIMAGETITLE set to qrcode_init");

        save(context, "QRCODEIMAGESIZERATIO", "0.1");
        Log.d(TAG, "QRCODEIMAGESIZERATIO set to 0.1");

        save(context, "QRCODEIMAGELEFTMARGIN", "0.93");
        Log.d(TAG, "QRCODEIMAGELEFTMARGIN set to 0.93");

        save(context, "QRCODEIMAGETOPMARGIN", "0.95");
        Log.d(TAG, "QRCODEIMAGETOPMARGIN set to 0.95");

        save(context, "TEXTCONTENTCENTER", "Hat es euch gefallen? \n Dann einfach direkt auf Facebook liken!");
        Log.d(TAG, "TEXTCONTENTCENTER set to: Hat es euch gefallen? \n Dann einfach direkt auf Facebook liken!");

        save(context, "TEXTCONTENTCENTERLEFTMARGIN", "0.5");
        Log.d(TAG, "TEXTCONTENTCENTERLEFTMARGIN set to 0.5");

        save(context, "TEXTCONTENTCENTERTOPMARGIN", "0.7");
        Log.d(TAG, "TEXTCONTENTCENTERTOPMARGIN set to 0.7");

        save(context, "TEXTCONTENTCENTERFONTSIZE", "20");
        Log.d(TAG, "TEXTCONTENTCENTERFONTSIZE set to 20");

        save(context, "TEXTCONTENTBUTTOM", "www.facebook.com/TeamEscapeDE");
        Log.d(TAG, "TEXTCONTENTBUTTOM set to: www.facebook.com/TeamEscapeDE");

        save(context, "TEXTCONTENTBUTTOMLEFTMARGIN", "0.5");
        Log.d(TAG, "TEXTCONTENTBUTTOMLEFTMARGIN set to 0.5");

        save(context, "TEXTCONTENTBUTTOMTOPMARGIN", "0.9");
        Log.d(TAG, "TEXTCONTENTBUTTOMTOPMARGIN set to 0.9");

        save(context, "TEXTCONTENTBUTTOMFONTSIZE", "20");
        Log.d(TAG, "TEXTCONTENTBUTTOMFONTSIZE set to 20");

        save(context, "TEXTCONTENTBUTTOMHYPERLINK", "false");
        Log.d(TAG, "TEXTCONTENTBUTTOMHYPERLINK set to false");

        save(context, "LANGUAGE", "DE");
        Log.d(TAG, "LANGUAGE set to DE");

        save(context, "FIRSTRUN", "DONE");
        Log.d(TAG, "FIRSTRUN set to DONE");
    }

    public void save(Context context,String TEFBC_KEY, String text) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(TEFBC_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(TEFBC_KEY, text); //3

        editor.commit(); //4

    }

    public String getValue(Context context, String TEFBC_KEY) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(TEFBC_NAME, Context.MODE_PRIVATE);
        text = settings.getString(TEFBC_KEY, null);
        return text;
    }

    /////////////////////////sharedproperties update Section///////////////////////////


    private void reactToImageUriChanges(SharedPreferences mPrefs, final String key) {
        String mPath = getPath(hostactivity, Uri.parse(mPrefs.getString(key, null)));
        //TODO Filename from Google drive
        final String[] filename = {mPath.substring((mPath != null ? mPath.lastIndexOf("/") : 0) + 1)};
        Log.d(TAG, "Image Path: " + mPath);
        Log.d(TAG, "Filename: " + filename[0]);

        AsyncTask<Uri, Void, Bitmap> imageLoadAsyncTask = new AsyncTask<Uri, Void, Bitmap>(){
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                progressDialog = new ProgressDialog(fragmentcontext);
                progressDialog.setMessage("Please wait, image is loading");
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
            }

            @Override
            protected Bitmap doInBackground(Uri... uris) {
                try {
                    return getBitmapFromUri(uris[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap){
                try{
                    String storageDirectory = saveToInternalStorage(bitmap, filename[0], key);
                    Log.d(TAG, filename[0] + " was stored to: " + storageDirectory);
                    progressDialog.dismiss();
                }catch(Exception e){
                    Log.d(TAG, "onPostExecute: "+e.toString());
                    progressDialog.dismiss();
                }
            }
        };

        imageLoadAsyncTask.execute(Uri.parse(this.mPrefs.getString(key, null)));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                if(isGoogleDriveUri(uri)){
                    //TODO somehow the right name should be returned
                    return uri.getLastPathSegment();
                }

                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return null;
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            if(isGoogleDriveUri(uri))
            {
                //TODO somehow the right name should be returned
                return uri.getLastPathSegment();
            }

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
    * @return Whether the Uri authority is Google Drive Photo.
    */
    public static boolean isGoogleDriveUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority());
    }
}