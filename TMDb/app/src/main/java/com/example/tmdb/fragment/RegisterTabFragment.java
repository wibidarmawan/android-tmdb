package com.example.tmdb.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tmdb.Model.LoginResponseModel;
import com.example.tmdb.R;
import com.example.tmdb.Retrofit.ApiInterface;
import com.example.tmdb.Retrofit.RetrofitLoginInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterTabFragment extends Fragment {
    EditText etEmail, etName, etDob, etAddress, etPassword;
    Button btnRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = view.findViewById(R.id.et_email_register);
        etName = view.findViewById(R.id.et_name_register);
        etDob = view.findViewById(R.id.et_dob_register);
        etAddress = view.findViewById(R.id.et_address_register);
        etPassword = view.findViewById(R.id.et_password_register);
        btnRegister = view.findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v -> {
            String email, name, dob, address, password;
            email = etEmail.getText().toString();
            name = etName.getText().toString();
            dob = etDob.getText().toString();
            address = etAddress.getText().toString();
            password = etPassword.getText().toString();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setPositiveButton("OK", null);

            ApiInterface apiInterface = RetrofitLoginInstance.getRetrofitInstance().create(ApiInterface.class);
            Call<LoginResponseModel> call = apiInterface.register(email, name, dob, address, password);
            call.enqueue(new Callback<LoginResponseModel>() {
                @Override
                public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                    LoginResponseModel loginResponseModel = response.body();
                    if (loginResponseModel.getStatus().equals("Success")) {
                        alertDialog.setMessage("User Register Success!");
                        AlertDialog dialog = alertDialog.create();
                        dialog.show();
                        emptyField();

                    } else {
                        etEmail.setText("");
                        alertDialog.setMessage("Email already registered, input another Email!");
                        AlertDialog dialog = alertDialog.create();
                        dialog.show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseModel> call, Throwable t) {

                }

                public void emptyField() {
                    etEmail.setText("");
                    etName.setText("");
                    etDob.setText("");
                    etAddress.setText("");
                    etPassword.setText("");
                }
            });
        });

    }
}