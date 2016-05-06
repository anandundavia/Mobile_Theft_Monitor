package com.example.anandundavia.mobiletheftmonitor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class LogIn extends Fragment
{


    public LogIn()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        Button loginBtn = (Button) view.findViewById(R.id.loginbtn);
        loginBtn.setOnClickListener(new LogInListener(view));
        return view;
    }

    class LogInListener implements View.OnClickListener
    {
        View localView;

        LogInListener(View v)
        {
            localView = v;
        }

        @Override
        public void onClick(View v)
        {
            String enteredPass = ((EditText) localView.findViewById(R.id.loginPass)).getText().toString();
            String savedPass = MainActivity.localDB.getPassword();
            Fragment frag;
            if (enteredPass.equals(savedPass))
            {
                frag = new HomeFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ftm = fm.beginTransaction();
                ftm.replace(R.id.container, frag);
                ftm.commit();
            } else
            {
                Toast.makeText(getContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
