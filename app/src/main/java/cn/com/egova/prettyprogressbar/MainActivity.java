package cn.com.egova.prettyprogressbar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import cn.com.egova.progress.ProgressManager;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final int MSG_PROGRESS = 0;
    private ProgressManager progressManager;
    private int progress = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_PROGRESS:
                    //更新进度条.
                    progress++;
                    if (progress == 1) {
                        progressManager.show();
                    }
                    progressManager.getProgressBar().setProgress(progress);
                    if (progress == 100) {
                        progress = 0;                                     //初始化
                        progressManager.dismiss();                        //隐藏
                        progressManager = null;
                        mHandler.removeMessages(MSG_PROGRESS);            //移除消息
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCommon = findViewById(R.id.btn_common);
        Button btnLine = findViewById(R.id.btn_line);
        Button btnRound = findViewById(R.id.btn_round);

        btnCommon.setOnClickListener(this);
        btnLine.setOnClickListener(this);
        btnRound.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_common:
                if (progressManager != null) {
                    progressManager.dismiss();
                    progressManager = null;
                    return;
                }
                progressManager = ProgressManager.makeProgress(this, ProgressManager.STYLE_COMMON);
                progressManager.show();
                break;
            case R.id.btn_line:
                if (progressManager != null) {
                    progressManager.dismiss();
                    progress = 0;
                    mHandler.removeMessages(MSG_PROGRESS);
                    progressManager = null;
                    return;
                }
                progressManager = ProgressManager.makeProgress(this, ProgressManager.STYLE_RATIO_LINE);
                providerProgressData();
                break;
            case R.id.btn_round:
                if (progressManager != null) {
                    progressManager.dismiss();
                    progress = 0;
                    mHandler.removeMessages(MSG_PROGRESS);
                    progressManager = null;
                    return;
                }
                progressManager = ProgressManager.makeProgress(this, ProgressManager.STYLE_RATIO_ROUND);
                providerProgressData();
                break;
        }
    }


    private void providerProgressData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_PROGRESS);
                providerProgressData();
            }
        }, 50);
    }
}
