package com.example.hoyoung.fairy_commie_admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by HoYoung on 2016-05-13.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    EditText id;
    EditText pw;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = (EditText)findViewById(R.id.login_id);
        pw = (EditText)findViewById(R.id.login_pwd);
        Button bt_login = (Button)findViewById(R.id.bt_Login);

        bt_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_Login:

                String login_id = id.getText().toString();
                String login_pw = pw.getText().toString();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                break;

        }
    }
}
