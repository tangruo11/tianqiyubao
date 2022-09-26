package com.example.tianqiyubao;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tianqiyubao.databinding.ActivityMoreBinding;
import com.example.tianqiyubao.db.DBManager;

import static java.util.ResourceBundle.clearCache;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMoreBinding binding;
    private SharedPreferences bg_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        String versionName=getVersionName();
        binding.version.setText("当前版本:   v"+versionName);
        setRgListner();
        bg_pref = getSharedPreferences("bg_pref", MODE_PRIVATE);


    }

    private void setRgListner() {
        /*设置改变背景图片单选按钮的监听*/
        binding.moreBg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //因为下次进入要显示上次的壁纸，所以需要记录所选的是哪个壁纸
                int bg = bg_pref.getInt("bg", 1);
                SharedPreferences.Editor edit = bg_pref.edit();
                Intent intent=new Intent(MoreActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                switch (checkedId) {
                    case R.id.more_rb_blue:
                        if (bg==0) {
                            Toast.makeText(MoreActivity.this,"您选择的正是当前背景，无需改变",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        edit.putInt("bg",0);
                        edit.commit();
                        break;
                    case R.id.more_rb_green:
                        if (bg==1) {
                            Toast.makeText(MoreActivity.this,"您选择的正是当前背景，无需改变",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        edit.putInt("bg",1);
                        edit.commit();
                        break;
                    case R.id.more_rb_pink:
                        if (bg==2) {
                            Toast.makeText(MoreActivity.this,"您选择的正是当前背景，无需改变",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        edit.putInt("bg",2);
                        edit.commit();
                        break;
                }

                startActivity(intent);
            }
        });

    }

    private String getVersionName() {
        String versionname=null;
        PackageManager manager=getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            versionname=info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionname;
    }

    private void initView() {
        binding.moreTvBack.setOnClickListener(this);
        binding.clear.setOnClickListener(this);
        binding.share.setOnClickListener(this);
        binding.exchangebg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_tv_back:
                finish();
                break;
            case R.id.clear:
               clear();
                break;
            case R.id.share:
                shareSoftware("说天气app是一款超萌超可爱的天气预报软件，画面简约，播报天气精准，快来下载吧");

                break;
            case R.id.exchangebg:
                if (binding.moreBg.getVisibility()== View.VISIBLE) {
                    binding.moreBg.setVisibility(View.GONE);
                }else{
                    binding.moreBg.setVisibility(View.VISIBLE);
                }

                break;
        }



    }

    private void shareSoftware(String text) {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");//表示是文本类型
        intent.putExtra(Intent.EXTRA_TEXT,text);//内容
        startActivity(Intent.createChooser(intent,"说天气"));//标题


    }

    private void clear() {
        /*清除缓存*/
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示信息")
                .setMessage("确定要清除所有缓存吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager.deleteAllDataFromInfo();
                        Toast.makeText(MoreActivity.this,"已删除所有信息",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MoreActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        //开启新栈，其他活动被销毁，只保留新跳转的活动
                        startActivity(intent);
                    }
                }).setNegativeButton("取消",null);
        builder.show();
    }

}