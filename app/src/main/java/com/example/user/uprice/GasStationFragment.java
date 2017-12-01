package com.example.user.uprice;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.uprice.xmltojsonlib.XmlToJson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

public class GasStationFragment extends Fragment {
    View myView;
    private ArrayList<ArrayList<String>> TAIWAN = new ArrayList<>();
    private ArrayList<String> COUNTRY = new ArrayList<>();
    private int COUNTRY_INDEX = -1;
    private TextView textViewGS1;
    private TextView textViewGS2;
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/soap+xml; charset=utf-8");
    private ArrayList<StationModel> list = new ArrayList<>();
    private RecyclerView my_recycler_view;
    private ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = inflater.inflate(R.layout.gasstation_layout, container, false);
        textViewGS1 = (TextView) myView.findViewById(R.id.tv_city);
        textViewGS2 = (TextView) myView.findViewById(R.id.tv_Village);
        textViewGS1.setText("請選擇縣市");
        textViewGS2.setText("請選擇地區");
        pd = new ProgressDialog(getActivity());

        getData();
        my_recycler_view = (RecyclerView) myView.findViewById(R.id.my_recycler_view);
        String str = getResources().getString(R.string.source);
        //Log.d("Source", str);
        try {
            JSONObject taiwan = new JSONObject(str);
            Iterator keys = taiwan.keys();
            while (keys.hasNext()) {
                String dynamicKey = (String) keys.next();
                //Log.d("Taiwan",dynamicKey);
                COUNTRY.add(dynamicKey);
                JSONObject object = taiwan.getJSONObject(dynamicKey);
                sear(object.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        textViewGS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog_list = new AlertDialog.Builder(getActivity());
                dialog_list.setTitle("請選擇縣市");
                String s[] = new String[COUNTRY.size()];
                s = COUNTRY.toArray(s);
                dialog_list.setItems(s, new DialogInterface.OnClickListener() {
                    @Override
                    //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        COUNTRY_INDEX = which;
                        textViewGS1.setText(COUNTRY.get(which));
                        textViewGS2.setText("請選擇地區");
                        ArrayList<StationModel> tmp = new ArrayList<StationModel>();
                        for (StationModel i : list) {
                            if (i.country.equals(COUNTRY.get(which))) {
                                tmp.add(i);
                                Log.d("Model",i.country);
                            }
                            Log.d("Model",i.country);
                        }
                        int V= 0;
                        ContactAdapter adapter = new ContactAdapter(tmp);
                        my_recycler_view.setAdapter(adapter);
                        my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                });
                dialog_list.show();
            }
        });
        textViewGS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog_list = new AlertDialog.Builder(getActivity());
                dialog_list.setTitle("請選擇地區");
                String s[];
                if (COUNTRY_INDEX != -1) {
                    ArrayList<String> tmp;
                    s = new String[TAIWAN.get(COUNTRY_INDEX).size()];
                    tmp = TAIWAN.get(COUNTRY_INDEX);
                    int x = 0;
                    for (String i : tmp) {
                        s[x++] = i;
                    }
                } else s = null;
                dialog_list.setItems(s, new DialogInterface.OnClickListener() {
                    @Override
                    //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (COUNTRY_INDEX != -1) {
                            textViewGS2.setText(TAIWAN.get(COUNTRY_INDEX).get(which));
                            ArrayList<StationModel> tmp = new ArrayList<StationModel>();
                            for (StationModel i : list) {
                                if (i.section.equals(TAIWAN.get(COUNTRY_INDEX).get(which))) {
                                    tmp.add(i);
                                    Log.d("Model",i.section);
                                }
                                Log.d("Model",i.section);
                            }
                            ContactAdapter adapter = new ContactAdapter(tmp);
                            my_recycler_view.setAdapter(adapter);
                            my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                        }
                    }
                });
                dialog_list.show();
            }
        });
        return myView;
    }

    public void sear(String json) {
        try {
            ArrayList<String> tmp = new ArrayList<>();
            //Log.d("json",json);
            JSONObject object = new JSONObject(json);
            Iterator keys = object.keys();
            while (keys.hasNext()) {
                String dynamicKey = (String) keys.next();
                String line = object.optString(dynamicKey);
                tmp.add(dynamicKey);
                //Log.d(dynamicKey,line);
            }
            TAIWAN.add(tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getData() {
        String a, b;
        pd.setTitle("載入中....");
        pd.show();
        a = (textViewGS1.getText().toString().equals("請選擇縣市")) ? "" : textViewGS1.getText().toString();
        b = (textViewGS2.getText().toString().equals("請選擇地區")) ? "" : textViewGS2.getText().toString();
        String postBody = "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" +
                "  <soap12:Body>" +
                "    <QueryStation xmlns=\"http://tmtd.cpc.com.tw/\" />" +
                "<City> " + a + " </City>" +
                "<Village> " + b + " </Village>" +
                "<Types></Types>" +
                "<open24></open24>" +
                "<queryitems></queryitems>" +
                " </soap12:Body>" +
                "</soap12:Envelope>";
        Log.e("postBody", postBody);
        final RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("http://vipmember.tmtd.cpc.com.tw/CPCSTN/STNWebService.asmx").post(body).addHeader("Content-Type", "application/soap+xml; charset=utf-8") //Notice this request has header if you don't need to send a header just erase this part
                .addHeader("Content-Length", "length").build();

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
                new GSF().execute(str);

            }
        });
    }


    public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

        private List<StationModel> contactList;

        public ContactAdapter(List<StationModel> contactList) {
            this.contactList = contactList;
        }

        @Override
        public int getItemCount() {
            return contactList.size();
        }

        @Override
        public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
            StationModel ci = contactList.get(i);
            contactViewHolder.vName.setText(ci.name);
            contactViewHolder.vAddress.setText("地址："+ci.address);
            contactViewHolder.vTime.setText("營業時間："+ci.time);
            //contactViewHolder.vTitle.setText(ci.name + " " + ci.surname);
        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.cardview_layout, viewGroup, false);

            return new ContactViewHolder(itemView);
        }

        public class ContactViewHolder extends RecyclerView.ViewHolder {
            TextView vName;
            TextView vAddress;
            TextView vTime;

            public ContactViewHolder(View v) {
                super(v);
                vName = (TextView) v.findViewById(R.id.tv_gsname);
                vAddress = (TextView) v.findViewById(R.id.tv_gsaddress);
                vTime = (TextView) v.findViewById(R.id.tv_gstime);
            }
        }
    }

    private class GSF extends AsyncTask<String, String, String> {
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
            list.clear();
            try {
                JSONObject JSObject = new JSONObject(formatted);
                JSONObject tmp = JSObject.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("QueryStationResponse").getJSONObject("QueryStationResult").getJSONObject("diffgr:diffgram").getJSONObject("NewDataSet");
                JSONArray tmd = tmp.getJSONArray("tbTable");
                for (int i = 0; i < tmd.length(); i++) {
                    JSONObject model = tmd.getJSONObject(i);
                    String name = model.optString("站名");
                    String address = model.optString("地址");
                    String time = model.optString("營業時間");
                    String country = model.optString("縣市");
                    String section = model.optString("鄉鎮區");


                    StationModel model1 = new StationModel();
                    model1.name = name;
                    model1.address = address;
                    model1.time = time;
                    model1.country = country;
                    model1.section = section;
                    list.add(model1);
                }
                //Log.d("JSON",tmp.toString());
                //Log.d("JSON", tmd.toString());
                ContactAdapter adapter = new ContactAdapter(list);
                my_recycler_view.setAdapter(adapter);
                my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            pd.dismiss();
            /*Log.d("JSON", "jsonObject:" + jsonObject.toString());
            Log.d("JSON", "jsonString:" + jsonString);*/
            Log.d("JSON", "formatted:" + formatted);
        }
    }
}
