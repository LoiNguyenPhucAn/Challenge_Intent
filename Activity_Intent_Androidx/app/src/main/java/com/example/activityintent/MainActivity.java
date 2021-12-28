package com.example.activityintent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    Button sendToActivity1;
    TextView receiveFromAct1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.text_field_main);
        sendToActivity1 = findViewById(R.id.sendToActivity1);

        /**https://developer.android.com/training/basics/intents/result#register
         * StartActivityForResult: Deprecated in Android X
         * You need to create a launcher variable inside onAttach or onCreate or global, i.e, before the activity is displayed
         * registerForActivityResult() used to Register a request to start an activity for result, designated by the given contract
         *  registerForActivityResult() takes an ActivityResultContract and an ActivityResultCallback
         *  and returns an ActivityResultLauncher which you’ll use to launch the other activity.
         *
         *  An ActivityResultContract defines the input type needed to produce a result along with the output type of the result.
         *  The APIs provide default contracts for basic intent actions like taking a picture, requesting permissions, and so on. You can also create your own custom contracts.
         *  https://developer.android.com/reference/androidx/activity/result/contract/ActivityResultContract#ActivityResultContract()
         *  https://developer.android.com/reference/androidx/activity/result/contract/ActivityResultContracts.StartActivityForResult
         *
         *  ActivityResultCallback is a single method interface with an onActivityResult() method that takes an object of the output type defined in the ActivityResultContract
         *  If you have multiple activity result calls that either use different contracts or want separate callbacks, you can call registerForActivityResult() multiple times to register multiple ActivityResultLauncher instances.
         **/

        //create a launcher  variable
        // https://viblo.asia/p/activity-result-apis-trong-androidx-djeZ1w985Wz
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult resultCode) {
                        receiveFromAct1 = findViewById(R.id.receiveFromActivity1);
                        if (resultCode.getResultCode() == Activity.RESULT_OK) {
                            Intent data = resultCode.getData();
                            String result = data.getStringExtra("replaced");
                            receiveFromAct1.setText(result);
                        }
                    }
                });


        sendToActivity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, activity_1.class);
                intent.putExtra("source", inputText.getText().toString());
                /* *
                 * Gọi launch() bắt đầu quá trình tạo ra kết quả. Khi người dùng hoàn tất activity tiếp theo và quay trở lại,
                 * onActivityResult () từ ActivityResultCallback sau đó sẽ được thực thi
                 * */
                activityResultLauncher.launch(intent);
            }
        });
    }
}