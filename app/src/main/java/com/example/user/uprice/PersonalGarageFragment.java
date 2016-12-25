package com.example.user.uprice;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.user.uprice.DBHelper.DBHelper;
import com.example.user.uprice.DBHelper.IforFragment;

/**
 * Created by user on 2016/12/21.
 */

public class PersonalGarageFragment extends Fragment {
    View myView;
    private OnFragmentInteractionListener Listener;
    private ListViewCompat clv;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private SimpleCursorAdapter sca;
    private Cursor contentListenCursor;
    private SwipeRefreshLayout swrLayout;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private PersonalGarageFragment pFrag;
    private IforFragment iFrag;
    public Button button_ok;
    private FloatingActionButton fab;
    public PersonalGarageFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.personalgarage_layout, container, false);
        getActivity().setTitle("個人車庫");
        fab = (FloatingActionButton) myView.findViewById(R.id.Button_Add);
        clv = (ListViewCompat) myView.findViewById(R.id.content);
        clv.setEmptyView(myView.findViewById(R.id.empty_list_view));
        swrLayout = (SwipeRefreshLayout)myView.findViewById(R.id.swipe_refreash_layout);

        return myView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        clv.setOnItemClickListener(new OnContentListItemClick());

        swrLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contentListenCursor = db.rawQuery("select * from apps",null);
                if(contentListenCursor != null){
                    sca = new SimpleCursorAdapter(getActivity(),R.id.info_item,contentListenCursor,
                            new String[]{"name","number","date"},new int[]{R.id.info_item_name,
                            R.id.info_item_number,R.id.info_item_date}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                    clv.setAdapter(sca);
                    sca.notifyDataSetChanged();

                }
                swrLayout.setRefreshing(false);
            }
        });

        dbHelper =  new DBHelper(getActivity(),"Info",3);
        db = dbHelper.getWritableDatabase();
        onReflashListView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.content_main,
                                new IforFragment()).commit();

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pFrag = new PersonalGarageFragment();
        iFrag = new IforFragment();
        fm = getFragmentManager();

    }

    public void onReflashListView(){
        contentListenCursor = db.rawQuery("select *from apps",null);
        if(contentListenCursor != null){
            sca = new SimpleCursorAdapter(getActivity(),R.id.info_item,contentListenCursor,
                    new String[]{"name","number","date"},new int[]{R.id.info_item_name,
                    R.id.info_item_number,R.id.info_item_date}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            clv.setAdapter(sca);
            sca.notifyDataSetChanged();

        }

    }

    public void onStop(){
        contentListenCursor.close();
        db.close();
        super.onStop();
    }

    private class OnContentListItemClick implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            //String item = (String) parent.getItemAtPosition(position);
            //Toast.makeText(getView().getContext(), item, Toast.LENGTH_SHORT).show();
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PersonalGarageFragment.OnFragmentInteractionListener) {
            Listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onButtonPressed(String str) {
        if (Listener != null) {
            Listener.onFragmentPInteraction(str);
        }
    }

    public interface OnFragmentInteractionListener {
        void  onFragmentPInteraction(String str);
    }

}
