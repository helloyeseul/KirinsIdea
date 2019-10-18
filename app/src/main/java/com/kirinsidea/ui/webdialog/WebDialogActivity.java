package com.kirinsidea.ui.webdialog;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kirinsidea.R;
import com.kirinsidea.databinding.ActivityWebDialogBinding;
import com.kirinsidea.extension.injection.Providers;
import com.kirinsidea.ui.BaseActivity;
import com.kirinsidea.utils.WebUrlUtil;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class WebDialogActivity extends BaseActivity<ActivityWebDialogBinding> implements WebNavigator {

    private String TAG = "WebDialogActivity";
    private String ConnectUrl;
    private Disposable disposable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();
    }

    public void checkPermission(){
        disposable = TedRx2Permission.with(WebDialogActivity.this)
                .setRationaleTitle("저장소 접근 권한 허용")
                .setRationaleMessage("파일 저장소 접근권한이 필요합니다")
                .setDeniedMessage("저장소 권한이 거부되었습니다.\n[설정] > [권한] 에서 권한을 허용하셔야합니다.")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request()
                .subscribe(tedPermissionResult -> {
                    if (tedPermissionResult.isGranted()) {
                        Toast.makeText(this, "권한 허가", Toast.LENGTH_SHORT).show();
                        initViewModel();
                        initViews();
                    } else {
                        Toast.makeText(this,
                                "권한 거부\n" + tedPermissionResult.getDeniedPermissions().toString(), Toast.LENGTH_SHORT)
                                .show();
                    }
                }, throwable -> {
                });
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }

    private void initViewModel() {
        binding.setVm(Providers.getViewModel(this, AddNewBookmarkViewModel.class));
        binding.getVm().setNavigator(this);
        binding.getVm().getStatus().observe(this, status -> {
            if(status.equals("ERROR")){
                Toast.makeText(this, "이미 존재하는 북마크 입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        binding.recyclerFolder.setAdapter(new FolderListAdapter(item -> binding.getVm().addNewBookmark(item.getName())));
    }

      @Override
    public String getWebUrl(){
        final Intent intent = getIntent();
        final String action = intent.getAction();
        final String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String text = intent.getStringExtra(Intent.EXTRA_TEXT);
                ConnectUrl = WebUrlUtil.findUrlInText(text);
                Log.d(TAG, "get connect url: " + ConnectUrl);
            }
        }
        return ConnectUrl;
    }
    @Override
    public void finishWebDialog(){
        finish();
    }
}
