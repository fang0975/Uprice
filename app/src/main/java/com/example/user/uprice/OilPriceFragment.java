package com.example.user.uprice;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class OilPriceFragment extends Fragment {
    private Context context;
    private View myView;
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/soap+xml; charset=utf-8");
    private TextView textView_oil1;
    private TextView textView_oil2;
    private TextView textView_oil3;
    private TextView textView_oil4;
    private TextView textView_oil5;
    private TextView textView_oil6;
    private TextView textView_oil7;
    private TextView textView_oil8;
    private TextView textView_oil9;
    private TextView textView_oil10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.oilprice_layout, container, false);
        textView_oil1 = (TextView) myView.findViewById(R.id.tv_oilname1);
        textView_oil2 = (TextView) myView.findViewById(R.id.tv_oilname2);
        textView_oil3 = (TextView) myView.findViewById(R.id.tv_oilname3);
        textView_oil4 = (TextView) myView.findViewById(R.id.tv_oilname4);
        textView_oil5 = (TextView) myView.findViewById(R.id.tv_oilname5);
        textView_oil6 = (TextView) myView.findViewById(R.id.tv_oilprice1);
        textView_oil7 = (TextView) myView.findViewById(R.id.tv_oilprice2);
        textView_oil8 = (TextView) myView.findViewById(R.id.tv_oilprice3);
        textView_oil9 = (TextView) myView.findViewById(R.id.tv_oilprice4);
        textView_oil10 = (TextView) myView.findViewById(R.id.tv_oilprice5);
        getActivity().setTitle("油價");
        context = getActivity();
        String postBody = "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" +
                "  <soap12:Body>" +
                "    <getCPCMainProdListPrice xmlns=\"http://tmtd.cpc.com.tw/\" />\n" +
                "  </soap12:Body>" +
                "</soap12:Envelope>";
        final RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("http://vipmember.tmtd.cpc.com.tw/OpenData/ListPriceWebService.asmx").post(body).addHeader("Content-Type", "application/soap+xml; charset=utf-8") //Notice this request has header if you don't need to send a header just erase this part
                .addHeader("Content-Length", "length").build();

        //Call call = client.newCall(request);
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("HttpService", "onFailure() Request was: ");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("Response", str.length() + "");
                new JOB().execute(str);
            }
        });

        return myView;
    }


    private class JOB extends AsyncTask<String,String,String>{
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
                JSONObject tmp = JSObject.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("getCPCMainProdListPriceResponse").getJSONObject("getCPCMainProdListPriceResult").getJSONObject("diffgr:diffgram").getJSONObject("NewDataSet");
                JSONArray tmd = tmp.getJSONArray("tbTable");
                for (int i = 0; i < 5; i++) {
                    JSONObject model = tmd.getJSONObject(i);
                    String name = model.optString("產品名稱");
                    String price = model.optString("參考牌價");
                    if (i == 0) {
                        textView_oil1.setText(name);
                        textView_oil6.setText(price);
                    } else if (i == 1) {
                        textView_oil2.setText(name);
                        textView_oil7.setText(price);
                    } else if (i == 2) {
                        textView_oil3.setText(name);
                        textView_oil8.setText(price);
                    } else if (i == 3) {
                        textView_oil4.setText(name);
                        textView_oil9.setText(price);
                    } else  {
                        textView_oil5.setText(name);
                        textView_oil10.setText(price);
                    }

                    Log.d("JSON", name + " = " + price);

                }
                //Log.d("JSON",tmp.toString());
                //Log.d("JSON", tmd.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
                /*Log.d("JSON", "jsonObject:" + jsonObject.toString());
                Log.d("JSON", "jsonString:" + jsonString);
                Log.d("JSON", "formatted:" + formatted);*/

        }
    }


}
