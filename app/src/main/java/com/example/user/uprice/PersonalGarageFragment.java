package com.example.user.uprice;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by user on 2016/12/21.
 */

public class PersonalGarageFragment extends Fragment {
    View myView;
    private OnFragmentInteractionListener Listener;



    public PersonalGarageFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.personalgarage_layout, container, false);
        getActivity().setTitle("個人車庫");



        return myView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
