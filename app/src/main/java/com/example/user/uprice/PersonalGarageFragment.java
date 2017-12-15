package com.example.user.uprice;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.user.uprice.base.BaseFragment;


/**
 * Created by user on 2016/12/21.
 */

public class PersonalGarageFragment extends BaseFragment {
    View myView;
    private OnFragmentInteractionListener Listener;



    public PersonalGarageFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_iformation, container, false);
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
