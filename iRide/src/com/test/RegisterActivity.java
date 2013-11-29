package com.test;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class RegisterActivity extends Activity {

	Button btnRegister;
	Dialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		btnRegister = (Button) findViewById(R.id.register1);
		//register button initializer
		btnRegister.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//finish();
				showDialog();
				
			}
		});
	}
	
	public void showDialog()
	{
		final Dialog dialog  = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 
	    dialog.setContentView(R.layout.custom_dialog);
	 
	    Button btnOk = (Button)dialog.findViewById(R.id.btnOk);
	 
	    btnOk.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	       dialog.dismiss();
	       finish();
	       //whatever code you want to execute on restart
	    }
	    });
		dialog.show();
	}
	
	@Override
	public void finish() {
	    Intent intent = new Intent();
	    //EditText editText= (EditText) findViewById(R.id.returnValue);
	    //String string = editText.getText().toString();
	    intent.putExtra("returnkey", "Registered");
	    setResult(RESULT_OK, intent);
	    super.finish();
	}
	
	protected Dialog onCreateDialog() {
	    Context context=RegisterActivity.this;
	    dialog=new Dialog(context);
	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 
	    dialog.setContentView(R.layout.custom_dialog);
	 
	    Button btnOk = (Button)dialog.findViewById(R.id.btnOk);
	 
	    btnOk.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	       dialog.dismiss();
	       finish();
	       //whatever code you want to execute on restart
	    }
	    });
	    
	    return dialog;
	}
}
