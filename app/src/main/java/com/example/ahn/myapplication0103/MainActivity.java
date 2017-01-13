package com.example.ahn.myapplication0103;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity{

    EditText EText1;
    TextView TView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        EText1 = (EditText)findViewById(R.id.EText1);
        TView1 = (TextView)findViewById(R.id.TView1);

        EText1.addTextChangedListener(new TextWatcher() {
            String str1="";
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                //입력하기 전에
                /*CharSequence CharSequence : 현재 에디트텍스트에 입력된 문자열을 담고 있다.
                  int start : s 에 저장된 문자열 내에 새로 추가될 문자열의 위치값을 담고있다.
                  int count : s 에 담긴 문자열 가운데 새로 사용자가 입력할 문자열에 의해 변경될 문자열의 수가 담겨있다.
                  int after : 새로 추가될 문자열의 수*/
                //str = charSequence.toString();
                EText1.setFilters(new InputFilter[]{
                        new InputFilter() {

                            @Override
                            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                                String expected = new String();
                                expected += spanned.subSequence(0,i2);
                                expected += charSequence.subSequence(i,i1);
                                expected += spanned.subSequence(i3, spanned.length());

                                int keep = calculateMaxLength(expected)-(spanned.length()-(i3-i2));

                                Log.i("filter", "바이트값3: " + spanned.length());
                                Log.i("filter", "바이트값(i3-i2): " + (i3-i2));

                                Log.i("filter", "charSequence: " + keep);
                                if(keep <=0){
                                    Log.i("filter", "charSequence2: " + keep);
                                    return "";
                                }else if(keep >= i3 - i2){
                                    Log.i("filter", "charSequence3: " + keep);
                                    return null;
                                }else{
                                    Log.i("filter", "charSequence4: " + keep);
                                    return charSequence.subSequence(i2, i2+keep);
                                }

                            }

                            protected int calculateMaxLength(String expected){
                                Log.i("filter", "바이트값: " + getByteLength(expected));
                                Log.i("filter", "바이트값1: " + expected.length());
                                return 80 - (getByteLength(expected)-expected.length());
                            }

                            private int getByteLength(String str){
                                try{
                                    return str.getBytes("KSC5601").length;
                                }catch(UnsupportedEncodingException e){
                                    //e.printStackTrace();
                                }
                                return 0;
                            }
                        }
                });
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                //입력되는 텍스트에 변화가 있을 때
                /*CharSequence s : 사용자가 새로 입력한 문자열을 포함한 에디트텍스트의 문자열이 들어있음
                  int start : 새로 추가된 문자열의 시작 위치의 값
                  int before : 새 문자열 대신 삭제된 기존 문자열의 수가 들어 있다
                  int count : 새로 추가된 문자열의 수가 들어있다.*/
                String test = String.valueOf(charSequence.toString().getBytes().length);
                str1 = String.valueOf(test);
                TView1.setText(str1+" / 80바이트");

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //입력이 끝났을 때
            }
        });
    }
}
