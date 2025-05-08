package ting.torchdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.hardware.camera2.CameraCharacteristics;
import android.util.Log;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Size;
import android.graphics.SurfaceTexture;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private CameraManager cameraManager;
    private String cameraId;
    private static final int CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        ToggleButton toggleButton = findViewById(R.id.toggleButton);

        // 檢查並請求相機權限
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            initializeCamera();
        }

        // 綁定 ToggleButton 事件
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (cameraId != null) {
                    cameraManager.setTorchMode(cameraId, isChecked); // 直接使用 isChecked 控制手電筒
                }
            } catch (Exception e) {
                showToast("手電筒功能不支持或發生錯誤");
            }
        });
    }

    // 初始化相機（權限通過後調用）
    private void initializeCamera() {
        try {
            String[] cameraIds = cameraManager.getCameraIdList();
            if (cameraIds.length == 0) {
                showToast("設備沒有可用的相機");
                return;
            }

            // 遍歷所有相機並解析資訊
            StringBuilder cameraInfo = new StringBuilder("相機列表:\n");
            for (String id : cameraIds) {
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);
                String cameraName = getHumanReadableCameraName(characteristics);
                boolean hasFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);

                cameraInfo.append(String.format(
                        "ID: %s\n名稱: %s\n閃光燈支援: %s\n---\n",
                        id, cameraName, hasFlash ? "是" : "否"
                ));
            }

            // 輸出到 Logcat 與 Toast (可選)
            Log.d("CameraDebug", cameraInfo.toString());
            // showToast(cameraInfo.toString()); // 若需直接顯示給用戶

            // 預設選擇第一個支援閃光燈的相機
            for (String id : cameraIds) {
                CameraCharacteristics cc = cameraManager.getCameraCharacteristics(id);
                if (cc.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                    cameraId = id;
                    break;
                }
            }

            if (cameraId == null) {
                showToast("未找到支援閃光燈的相機");
            }
        } catch (Exception e) {
            Log.e("CameraInit", "相機初始化失敗", e);
            showToast("相機初始化失敗: " + e.getLocalizedMessage());
        }
    }

    private String getHumanReadableCameraName(CameraCharacteristics cc) {
        // 鏡頭方向解析
        Integer lensFacing = cc.get(CameraCharacteristics.LENS_FACING);
        String facing = "未知鏡頭";
        if (lensFacing != null) {
            switch (lensFacing) {
                case CameraCharacteristics.LENS_FACING_BACK:
                    facing = "後置鏡頭";
                    break;
                case CameraCharacteristics.LENS_FACING_FRONT:
                    facing = "前置鏡頭";
                    break;
                case CameraCharacteristics.LENS_FACING_EXTERNAL:
                    facing = "外接鏡頭";
                    break;
            }
        }

        // 物理焦距解析
        float[] focalLengths = cc.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
        String focal = (focalLengths != null && focalLengths.length > 0) ?
                String.format("%.1fmm", focalLengths[0]) : "未知焦距";

        // 最大預覽分辨率解析
        StreamConfigurationMap configMap = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        String resolutionInfo = "無預覽支援";
        if (configMap != null) {
            Size[] previewSizes = configMap.getOutputSizes(SurfaceTexture.class);
            if (previewSizes != null && previewSizes.length > 0) {
                // 按面積降序排序，取最大分辨率
                Arrays.sort(previewSizes, (a, b) ->
                        Long.signum((b.getWidth() * b.getHeight()) - (a.getWidth() * a.getHeight()))
                );
                Size maxPreview = previewSizes[0];
                resolutionInfo = String.format("最大預覽: %dx%d", maxPreview.getWidth(), maxPreview.getHeight());
            }
        }

        return String.format("%s | %s | %s", facing, focal, resolutionInfo);
    }

    // 權限請求結果處理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeCamera();
            } else {
                showToast("需要相機權限才能使用手電筒");
            }
        }
    }

    // 顯示 Toast 訊息
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}