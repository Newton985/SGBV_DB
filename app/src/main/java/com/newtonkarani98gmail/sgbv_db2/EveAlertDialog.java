package com.newtonkarani98gmail.sgbv_db2;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;


public class EveAlertDialog extends Dialog implements View.OnClickListener {

   private String type;
   private Context context;
   TextView message;
   Button action;

    public EveAlertDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (type.equals("warning")){
//            setContentView(R.layout.warning_dialog);
//            message=(TextView)findViewById(R.id.warningMessage);
//            action=(Button)findViewById(R.id.warningAction);
//            action.setOnClickListener(this);
//        }
//        if (type.equals("error")){
//            setContentView(R.layout.error_dialog);
//            message=(TextView)findViewById(R.id.errorMessage);
//            action=(Button)findViewById(R.id.errorAction);
//            action.setOnClickListener(this);
//        }
//        if (type.equals("success")){
//            setContentView(R.layout.success_dialog);
//            message=(TextView)findViewById(R.id.successMessage);
//            action=(Button)findViewById(R.id.successAction);
//            action.setOnClickListener(this);
//
//        }
        if (type.equals("progress")){
            setContentView(R.layout.progress_dialog);
            message=(TextView)findViewById(R.id.progressMessage);
            action=(Button)findViewById(R.id.progressAction);
            action.setOnClickListener(this);

        }

    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
    public  void showWarningDialog(){
        this.type="warning";
        show();

    }

    public void showErrorDialog(){
        this.type="error";
        show();
    }

    public void showSuccessDialog(){
        this.type="success";
        show();
    }

    public void showProgressDialog(){
        this.type="progress";
        show();
    }


    public void dismissDialog(){
        dismiss();
    }
}
