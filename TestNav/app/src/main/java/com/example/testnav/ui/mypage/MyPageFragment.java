package com.example.testnav.ui.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.testnav.MainActivity;
import com.example.testnav.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyPageFragment extends Fragment {

    private MyPageViewModel myPageViewModel;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myPageViewModel = ViewModelProviders.of(this).get(MyPageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mypage, container, false);

        final TextView textView = root.findViewById(R.id.text_myPage);
        Button buttonSignOut = root.findViewById(R.id.buttonSignOut);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser= firebaseAuth.getCurrentUser();

        myPageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "See u!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        return root;
    }
}