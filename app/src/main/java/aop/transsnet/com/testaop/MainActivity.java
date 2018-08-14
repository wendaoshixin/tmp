package aop.transsnet.com.testaop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.annotation.TimeTrace;
import com.app.annotation.apt.BindView;
import com.transsnet.apt.api.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv1)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(mTextView != null){
            mTextView.setText("Butterknife 生效");
        }
    }

    @TimeTrace(value = "登录")
    public void test1(View view) {
        Toast.makeText(MainActivity.this, "按钮已经点击", Toast.LENGTH_SHORT).show();
       // add();

    }

    public void add(){
        Log.e("MainActivity", "fsfdfsf");
    }
}
