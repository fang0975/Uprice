package com.example.user.uprice;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.uprice.xmltojsonlib.XmlToJson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by user on 2016/12/21.
 */

public class GasStationFragment extends Fragment{
    View myView;
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/soap+xml; charset=utf-8");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.gasstation_layout, container , false);
        getActivity().setTitle("加油站");
        String postBody = "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" +
                "  <soap12:Body>" +
                "    <QueryStation xmlns=\"http://tmtd.cpc.com.tw/\" />\n" +
                "  </soap12:Body>" +
                "</soap12:Envelope>";
        final RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://vipmember.tmtd.cpc.com.tw/CPCSTN/STNWebService.asmx")
                .post(body)
                .addHeader("Content-Type", "application/soap+xml; charset=utf-8") //Notice this request has header if you don't need to send a header just erase this part
                .addHeader("Content-Length", "length")
                .build();

        //Call call = client.newCall(request);
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("HttpService", "onFailure() Request was: " );

                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("Response", str.length() + "");
                new GSF().execute(str);

            }
        });
        return myView;
    }


    private class GSF extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {

            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XmlToJson xmlToJson = new XmlToJson.Builder(s).build();
            JSONObject jsonObject = xmlToJson.toJson();
            // convert to a Json String
            String jsonString = xmlToJson.toString();
            // convert to a formatted Json String
            String formatted = xmlToJson.toFormattedString();

            try {
                JSONObject JSObject = new JSONObject(formatted);
                JSONObject tmp = JSObject.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("QueryStationResponse").getJSONObject("QueryStationResult").getJSONObject("diffgr:diffgram").getJSONObject("NewDataSet");
                JSONArray tmd = tmp.getJSONArray("tbTable");
                //Log.d("JSON",tmp.toString());
                Log.d("JSON", tmd.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            /*Log.d("JSON", "jsonObject:" + jsonObject.toString());
            Log.d("JSON", "jsonString:" + jsonString);*/
            Log.d("JSON", "formatted:" + formatted);
        }
    }
}
