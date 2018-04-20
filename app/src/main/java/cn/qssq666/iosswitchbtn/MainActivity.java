package cn.qssq666.iosswitchbtn;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.qssq666.switchbtn.QSSQSwitchBtn;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QSSQSwitchBtn switchBtn = (QSSQSwitchBtn) findViewById(R.id.btn_switch_btn);
//        switchBtn.setChecked(true);

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Toast.makeText(MainActivity.this, "check " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        QSSQSwitchBtn qssqSwitchBtn = new QSSQSwitchBtn(this);

        qssqSwitchBtn.setBackgroundColor(Color.BLACK);
        ((ViewGroup) findViewById(R.id.line)).addView(qssqSwitchBtn, new LinearLayout.LayoutParams(150, 150));

    }
}
