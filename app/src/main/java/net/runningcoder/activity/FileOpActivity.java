package net.runningcoder.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.util.L;
import net.runningcoder.util.PathUtil;
import net.runningcoder.widget.BtsSheroChatView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileOpActivity extends BasicActivity {
    private BtsSheroChatView chatView;
    private LottieAnimationView lottieAnimationView;
    private List<String> data = new ArrayList<>();
    Handler mHandler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                L.i("loop->" + data.get(idx));
                chatView.setText(data.get(idx));
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                idx = 0;
                chatView.setText(data.get(idx));
            }
            idx++;
            startLoop();
        }
    };
    private int idx;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        final TextView textView = findViewById(R.id.v_textview);
        findViewById(R.id.v_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create("test");
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dest = new File(getFile().getAbsolutePath() + "111");
                getFile().renameTo(dest);
                dest.delete();
                create("test22");
                createDialog();
//                startLoop();
            }
        });

        for (int i = 0; i < 10; i++) {
            data.add("test string ->" + i);
        }

    }

    private void createDialog() {
        AppCompatDialog dialog = new AppCompatDialog(this);
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_file_op, null);
        dialog.setContentView(rootView,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        final Window window = dialog.getWindow();
        if (window != null) {


            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            window.setDimAmount(.40f); //设置背景透明度
//            window.setWindowAnimations(R.style.BtsFullMenuEnterAnimatio);
        }

        dialog.setCancelable(true);
        dialog.show();

        chatView = rootView.findViewById(R.id.chat);
        chatView.setBackground(R.drawable.bts_icon_shero_chat_bg);

        rootView.findViewById(R.id.v_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoop();
            }
        });
    }

    private void startLoop() {
        mHandler.postDelayed(runnable, 3 * 1000);
    }

    private void create(String name) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }

        String dir = PathUtil.getInstance().getCacheRootPath("file");
        File file = new File(dir, name);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile() {

        return new File(PathUtil.getInstance().getCacheRootPath("file"), "test");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                create("test");
            }
        }
    }

    @Override
    public int getContentViewID() {
        return R.layout.activity_file_op;
    }
}
