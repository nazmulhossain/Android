package com.test;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

import com.facebook.*;

//import com.test.MainActivity.SessionStatusCallback;

public class MainActivity extends Activity {

	private static final String URL_PREFIX_FRIENDS = "https://graph.facebook.com/me/friends?access_token=";

	//facebook login/logout button
    private Button buttonLoginLogout;
    private Session.StatusCallback statusCallback = new SessionStatusCallback();

	//app login button
	Button btnLogIn;
	//register button
	Button btnRegister;
	//Button btnFbLogin;
	
	// constant to determine which sub-activity returns
	private static final int REQUEST_CODE = 10;
	
	private static final String PERMISSION = "publish_actions";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		btnLogIn = (Button) findViewById(R.id.login);
		buttonLoginLogout = (Button) findViewById(R.id.authButton);
		btnRegister = (Button) findViewById(R.id.register);
		//initializes buttons listener
		initializeListener();
		
		
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

        Session session = Session.getActiveSession();
        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(this);
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
            }
        }
        else
        {
        	
        	startActivity(new Intent(getApplicationContext(), MapActivity.class));
        }
        updateView();
        
	}
	
	//initializes buttons listener
	private void initializeListener()
	{
		btnLogIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class); 
				startActivity(mapIntent);
			}
		});
		
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class); 
				startActivity(registerIntent);
			}
		});
	}
	
	@Override
    public void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(statusCallback);
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   if (resultCode == RESULT_OK){// && requestCode == REQUEST_CODE) {
		   if (data.hasExtra("returnkey")) {
			   String result = data.getExtras().getString("returnkey");
			   if (result != null && result.length() > 0) {
				   //Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
			   }
		   }
	   }
	   Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }
    
    //facebook login/logout Button text change function 
    private void updateView() {
        Session session = Session.getActiveSession();
        if (session.isOpened()) {
            //textInstructionsOrLink.setText(URL_PREFIX_FRIENDS + session.getAccessToken());
            buttonLoginLogout.setText("Log out");
            buttonLoginLogout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickLogout(); }
            });
        } else {
            //textInstructionsOrLink.setText(R.string.instructions);
            buttonLoginLogout.setText("Log in with Facebook");
            buttonLoginLogout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickLogin(); }
            });
        }
    }

  //facebook log in
    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        } else {
            Session.openActiveSession(this, false, statusCallback);
        }
    }

    //facebook log out
    private void onClickLogout() {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
    }
    
    
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            if(session.isOpened())
            {
            	startActivity(new Intent(getApplicationContext(), MapActivity.class));
            	
            	//Check for android_key 
//            	try{ //logger.debug("Checking signs");
//	                PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
//	                for (Signature signature : info.signatures) {
//	                    MessageDigest md = MessageDigest.getInstance("SHA");
//	                    md.update(signature.toByteArray());
//	                    System.out.println(Base64.encodeToString(md.digest(), Base64.DEFAULT));
//	                }
//	            } catch (NameNotFoundException e) {
//	                e.printStackTrace();
//	                System.out.println(e.getMessage());
//	            } catch (NoSuchAlgorithmException e) {
//	                e.printStackTrace();
//	                System.out.println(e.getMessage());
//	            }
            }
            updateView();
        }

        //get package name
//		private String getPackageName() {
//			return getApplicationContext().getPackageName();
//		}
    }
}

