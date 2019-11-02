package com.example.jgram.ui.addimage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.jgram.R;

public class AddImageFragment extends Fragment {

    private AddImageViewModel addImageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addImageViewModel =
                ViewModelProviders.of(this).get(AddImageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addimage, container, false);
        final TextView textView = root.findViewById(R.id.text_addImage);
        addImageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}