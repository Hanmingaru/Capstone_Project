/*
 * Created by Elliott Rheault on 2022.5.1
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstoneproject.R;

public class InstructionFragment extends Fragment {

    private static final String INSTRUCTION = "instruction";

    private TextView instruction_view;

    private String instruction;

    public InstructionFragment() {
        // Required empty public constructor
    }

    public static InstructionFragment newInstance(String in) {
        InstructionFragment fragment = new InstructionFragment();
        Bundle args = new Bundle();
        args.putString(INSTRUCTION, in);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            instruction = getArguments().getString(INSTRUCTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_instruction, container, false);
        instruction_view = myView.findViewById(R.id.instruction_text);

        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(instruction != null) {
            instruction_view.setText(instruction);
        }
    }

}