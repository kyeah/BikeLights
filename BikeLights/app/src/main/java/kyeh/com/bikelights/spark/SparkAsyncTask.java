package kyeh.com.bikelights.spark;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import kyeh.com.bikelights.R;

/**
 * Created by kyeh on 10/18/14.
 */
public class SparkAsyncTask extends AsyncTask<String, Integer, Integer> {

    private static String ACCESS_TOKEN;
    private static String CORE_ID;
    private static final String TAG = "SparkAsyncTask";

    SparkAsyncTask(Context context) {
        super();
        if (ACCESS_TOKEN == null) {
            ACCESS_TOKEN = context.getResources().getString(R.string.core_access_token);
            CORE_ID = context.getResources().getString(R.string.core_id);
        }
    }

    @Override
    protected Integer doInBackground(String... strings) {
        String addUrl = strings[0];
        String otherParams = strings[1];

        String baseUrl = "https://api.spark.io/v1/devices/" + CORE_ID + "/" + addUrl;
        try {
            URL url = new URL(baseUrl);
            HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
            connect.setRequestMethod("POST");
            connect.setDoOutput(true);
            connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String params = "&access_token=" + ACCESS_TOKEN;

            if(!(otherParams == null || otherParams == "")) {
                params = params + "&params=" + otherParams;
            }

            DataOutputStream wr = new DataOutputStream(connect.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();
            int responseCode = connect.getResponseCode();
            System.out.println(responseCode);
        } catch (Exception e) {
            Log.e(TAG, "Error", e);
            Log.e(TAG, e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }
}
