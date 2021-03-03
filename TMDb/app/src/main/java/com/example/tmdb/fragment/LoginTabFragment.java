package com.example.tmdb.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tmdb.Activity.MainActivity;
import com.example.tmdb.Model.LoginResponseModel;
import com.example.tmdb.R;
import com.example.tmdb.Retrofit.ApiInterface;
import com.example.tmdb.Retrofit.RetrofitInstance;
import com.example.tmdb.Retrofit.RetrofitLoginInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTabFragment extends Fragment {
    EditText etEmail, etPassword;
    Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password= etPassword.getText().toString();

            ApiInterface apiInterface = RetrofitLoginInstance.getRetrofitInstance().create(ApiInterface.class);
            Call<LoginResponseModel> call = apiInterface.login(email, password);
            call.enqueue(new Callback<LoginResponseModel>() {
                @Override
                public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                    LoginResponseModel loginResponseModel = response.body();
                    if (loginResponseModel.getStatus().equals("Success")){
                        Intent variableintent = new Intent(getContext(), MainActivity.class);
                        startActivity(variableintent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                        etPassword.setText("");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseModel> call, Throwable t) {

                }
            });
        });


    }
}