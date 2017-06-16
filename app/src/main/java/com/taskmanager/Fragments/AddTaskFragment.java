package com.taskmanager.Fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.taskmanager.Models.Task;
import com.taskmanager.R;
import com.taskmanager.Utils.DatabaseHelper;
import com.taskmanager.Utils.Utility;

import java.sql.Time;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int query_type;
    public static final int UPDATE = 1;
    public static final int INSERT = 2;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText etName;
    private EditText etDescription;
    private TextView tvAddTask;
    private Task task;


    public AddTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTaskFragment newInstance(String param1, String param2) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        query_type =getArguments().getInt("query_type");
        if(query_type == UPDATE){
            task = (Task) getArguments().getSerializable("task");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_add_task, container, false);
        etName = (EditText) rootView.findViewById(R.id.etName);
        etDescription = (EditText) rootView.findViewById(R.id.etDescription);
        tvAddTask = (TextView) rootView.findViewById(R.id.tvAddTask);
        if(query_type == UPDATE){
            etName.setText(task.getName());
            etDescription.setText(task.getDescription());
            tvAddTask.setText("Update Task");
        }
        tvAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((Utility.isEmptyString(etName) || Utility.isEmptyString(etDescription)) == true){
                    Snackbar.make(rootView,"Please fill Name and Description of the task.",Snackbar.LENGTH_LONG).show();
                    return;
                }
                Task task = new Task();
                task.setDescription(etDescription.getText().toString());
                task.setName(etName.getText().toString());
                task.setModifiedDate(Utility.getDateTime());
                if(query_type == INSERT) {
                    task.setCreatedDate(Utility.getDateTime());
                    new DatabaseHelper(getContext()).createTask(task);
                    Snackbar.make(rootView, "Successfully added your Task.", Snackbar.LENGTH_LONG).show();
                }
                else if(query_type == UPDATE){
                    task.setId(AddTaskFragment.this.task.getId());
                    task.setCreatedDate(AddTaskFragment.this.task.getCreatedDate());
                    new DatabaseHelper(getContext()).editTask(task);
                    Snackbar.make(rootView, "Successfully updated your Task.", Snackbar.LENGTH_LONG).show();
                }
                Utility.hideKeyboard(getContext());
                getFragmentManager().popBackStack();
            }
        });
        return rootView;
    }

}
