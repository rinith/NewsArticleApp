package com.example.newsarticleapp.dialogs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.newsarticleapp.R;
import com.example.newsarticleapp.interfaces.OnFilterSelection;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActionBottomDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private static OnFilterSelection onFilterSelections;
    RadioGroup radioGroup;


    public static ActionBottomDialogFragment newInstance(OnFilterSelection onFilterSelection) {
        onFilterSelections = onFilterSelection;
        return new ActionBottomDialogFragment();

    }

    TextView doneBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_filter, container, false);
        radioGroup = view.findViewById(R.id.radioGroup);
        doneBtn = view.findViewById(R.id.doneBtn);
        onRadioButtonClicked();
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFilterSelections.OnFilterSelected(isNewToOld);
                dismiss();
            }
        });
        return view;
    }

    private boolean isNewToOld = true;

    private void onRadioButtonClicked() {
        RadioButton checkedRadioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.newToOld:
                        isNewToOld = true;
                        break;
                    case R.id.oldToNew:
                        isNewToOld = false;
                        break;
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }


}
