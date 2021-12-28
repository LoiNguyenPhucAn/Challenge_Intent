package com.example.activityintent;

import androidx.activity.result.ActivityResult;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class activity_1 extends AppCompatActivity {
    EditText target,replacement;
    Button sendToMain;
    TextView source;
    String sourceString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        target=findViewById(R.id.target);
        replacement=findViewById(R.id.replacement);
        sendToMain=findViewById(R.id.sendToMain);
        source=findViewById(R.id.receiveFromMain);
        sourceString = getIntent().getStringExtra("source");
        source.setText(sourceString);
        sendToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = null;
                if (sourceString!=null && !sourceString.equals("")){
                    result = sourceString.replace(target.getText().toString(),replacement.getText().toString());
                }else{
                    Toast.makeText(activity_1.this, "Source String is null or blank", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent();
                intent.putExtra("replaced",result);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }

}