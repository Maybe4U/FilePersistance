package com.example.android.filepersistancetest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.edit_text);
        String input = load();
        if(!TextUtils.isEmpty(input)){
            editText.setText(input);
            //移动光标到最后
            editText.setSelection(input.length());
            Toast.makeText(this,"加载成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = editText.getText().toString();
        save(inputText);
    }

    private void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter write = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            //Creates an OutputStreamWriter that uses the default character encoding.
            OutputStreamWriter ow = new OutputStreamWriter(out);
            //Creates a buffered character-output stream that uses a default-sized output buffer.
            write = new BufferedWriter(ow);
//            write = new BufferedWriter(new OutputStreamWriter(out));
            write.write(inputText);

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(write!=null){
                    write.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public String load(){
        FileInputStream in = null;
        BufferedReader read = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput("data");
            InputStreamReader ir = new InputStreamReader(in);
            read = new BufferedReader(ir);
            String line = "";
            while ((line = read.readLine()) != null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                read.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return content.toString();
    }
}
