package com.example.anandundavia.mobiletheftmonitor;


import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment
{


    public Register()
    {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        Button regBtn = (Button) view.findViewById(R.id.register);
        regBtn.setOnClickListener(new RegisterListener(view));
        return view;
    }

    class RegisterListener implements View.OnClickListener
    {
        View localView;

        RegisterListener(View view)
        {
            localView = view;
        }

        @Override
        public void onClick(View v)
        {
            String nameP = ((TextView) localView.findViewById(R.id.name)).getText().toString();
            String password = ((TextView) localView.findViewById(R.id.pass)).getText().toString();
            String confirmPassword = ((TextView) localView.findViewById(R.id.confirm)).getText().toString();
            String nameEm = ((TextView) localView.findViewById(R.id.nameE)).getText().toString();
            String contactNumEm = ((TextView) localView.findViewById(R.id.contactNumE)).getText().toString();
            String emailEm = ((TextView) localView.findViewById(R.id.emailE)).getText().toString();

            if(nameP == null || nameP.equals(""))
            {
                Toast.makeText(getContext(),"Name is empty",Toast.LENGTH_SHORT).show();
                return;
            }
            if(password == null || password.equals(""))
            {
                Toast.makeText(getContext(),"Password is empty",Toast.LENGTH_SHORT).show();
                return;
            }
            if(confirmPassword == null || confirmPassword.equals(""))
            {
                Toast.makeText(getContext(),"Confirm Password is empty",Toast.LENGTH_SHORT).show();
                return;
            }
            if(nameEm == null || nameEm.equals(""))
            {
                Toast.makeText(getContext(),"Emergency Name Name is empty",Toast.LENGTH_SHORT).show();
                return;
            }
            if(contactNumEm == null || contactNumEm.equals(""))
            {
                Toast.makeText(getContext(),"Emergency Contact Number is empty",Toast.LENGTH_SHORT).show();
                return;
            }
            if(emailEm == null || emailEm.equals(""))
            {
                Toast.makeText(getContext(),"Emergency Email is empty",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!password.equals(confirmPassword))
            {
                Toast.makeText(getContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();
                return;
            }

            boolean f = MainActivity.localDB.createUser(nameP,password,nameEm,contactNumEm,emailEm);
            if(f)
            {
                if(!MainActivity.mDevicePolicyManager.isAdminActive(MainActivity.mComponentName)){
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, MainActivity.mComponentName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"Please Unable the admin moode to lock the device remotely");
                    startActivityForResult(intent, MainActivity.ADMIN_INTENT);
                }

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ftm = fm.beginTransaction();
                ftm.replace(R.id.container, new HomeFragment()).commit();
                Toast.makeText(getContext(), "User "+nameP+" created", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getContext(), "There was some problem creating user", Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
