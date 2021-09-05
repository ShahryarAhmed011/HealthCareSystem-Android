package com.project.helthcaresystem.ViewComponent;


import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.helthcaresystem.R;

public class ManageAccountDialog {

    private Dialog mDialog;
    private OnUpdateButtonClick onUpdateButtonClick;

    private EditText mDialogEditText;
    private Button mUpdateButton;
    private Button mCancelButton;
    private TextView mFemaleTextView;
    private TextView mMaleTextView;

    private String editTextString;

    private LinearLayout mTopLinearLayout;
    private LinearLayout mBottomLinearLayout;


    public ManageAccountDialog(Context context) {
        mDialog = new Dialog(context);
        if (mDialog.getWindow() != null) {
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        mDialog.setContentView(R.layout.manage_account_dialog);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);


        mDialogEditText  = mDialog.findViewById(R.id.dialog_edit_text_manage_account_dialog);
        mUpdateButton  = mDialog.findViewById(R.id.update_button_manage_account_dialog);
        mCancelButton  = mDialog.findViewById(R.id.cancel_button_manage_account_dialog);

        mFemaleTextView = mDialog.findViewById(R.id.female_text_view_manage_account_dialog);
        mMaleTextView = mDialog.findViewById(R.id.male_text_view_manage_account_dialog);

        mTopLinearLayout = mDialog.findViewById(R.id.topLinearLayout);
        mBottomLinearLayout = mDialog.findViewById(R.id.bottomLinearLayout);

        initialize();
    }


    private void initialize(){

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextString = mDialogEditText.getText().toString().trim();
                if(editTextString.equals("")){
                    mDialogEditText.requestFocus();
                    mDialogEditText.setError("This field is required");
                }else{

                    onUpdateButtonClick.onSuccess(editTextString);
                    dismiss();

                }










            }
        });


        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onUpdateButtonClick.onFailure();
                dismiss();
            }
        });

        mFemaleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateButtonClick.onSuccess("Female");
                dismiss();
            }
        });

        mMaleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateButtonClick.onSuccess("Male");
                dismiss();
            }
        });

    }


    public void show(String type, OnUpdateButtonClick onUpdateButtonClick) {
        this.onUpdateButtonClick = onUpdateButtonClick;
        mDialogEditText.setText("");


        mTopLinearLayout.setVisibility(View.VISIBLE);
        mDialogEditText.setVisibility(View.VISIBLE);
        mCancelButton.setVisibility(View.VISIBLE);
        mUpdateButton.setVisibility(View.VISIBLE);

        mBottomLinearLayout.setVisibility(View.INVISIBLE);
        mFemaleTextView.setVisibility(View.INVISIBLE);
        mMaleTextView.setVisibility(View.INVISIBLE);

        mDialogEditText.setInputType(InputType.TYPE_CLASS_TEXT);

        if (type.equals("DateOfBirth")){
            mDialogEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        if (type.equals("Gender")){
            mDialogEditText.setVisibility(View.INVISIBLE);
            mCancelButton.setVisibility(View.INVISIBLE);
            mUpdateButton.setVisibility(View.INVISIBLE);

            mTopLinearLayout.setVisibility(View.INVISIBLE);
            mBottomLinearLayout.setVisibility(View.VISIBLE);
            mFemaleTextView.setVisibility(View.VISIBLE);
            mMaleTextView.setVisibility(View.VISIBLE);
        }



        mDialog.show();
    }


    public void dismiss() {
        Log.v("ManageAccount","DialogUpdateClicked");
        mDialog.dismiss();
    }

    public interface OnUpdateButtonClick{
        void onSuccess(String string);
        void onFailure();
    }
}