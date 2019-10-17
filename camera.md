# The quick notes of android camera
self pen notes regarding camera module porting on android 9 rk3368, imx6/imx8, msm8953 SoC platform.



## Command
```
// list /dev/video*
grep -H '' /sys/class/video4linux/video*/name
adb shell cat /sys/class/video4linux/*/name

// show media framework topology graph of /dev/media*
media-ctl -p /dev/media0

// set format and dump frames
v4l2-ctl -d /dev/video0 \
    --set-fmt-video=width=720,height=480,pixelformat=NV12 \
    --stream-mmap=3 \
    --stream-skip=3 \
    --stream-to=/sdcard/frame.out \
    --stream-count=1 \
    --stream-poll

// dump native serivce of media.camera
adb shell dumpsys media.camera

// quick launch camera app
// aosp camera
am start -n com.android.camera2/com.android.camera.CameraActivity

// qualcomm camera, SnapdragonCamera.apk
am start -n org.codeaurora.snapcam/com.android.camera.CameraLauncher

```
> ref: [media-ctl](https://github.com/tingkts/Android-Camera-Video-In/blob/master/utils/linux%20v4l2%20tool%20media-ctrl%2C%20v4l2-ctl/media-ctl), [v4l2-ctl](https://github.com/tingkts/Android-Camera-Video-In/blob/master/utils/linux%20v4l2%20tool%20media-ctrl%2C%20v4l2-ctl/v4l2-ctl) are the binary executable files built on android 9 platform.


</br>

## Process

```
    <Java>      Camera App : com.android.camera2

    <Java>      media.camera

    <native>    cameraserver : cameraserver

    <native>    hidl_service : android.hardware.camera.provider@2.4-service


user space

--------------------------------------------------------------------------------

kernel space

    camera sensor driver + V4l2 framework + Media Controller framework
```

> Ref: [Rockchip-isp1 - Rockchip open source Document](http://opensource.rock-chips.com/wiki_Rockchip-isp1) </br>
> Ref: [Qualcomm Camera Subsystem driver](https://www.kernel.org/doc/html/v4.18/media/v4l-drivers/qcom_camss.html)



</br>

## Source

- [CameraX](https://developer.android.com/jetpack/androidx/releases/camera) @ [Jetpack](https://android.googlesource.com/platform/frameworks/support/)
    - [androidx.camera.camera2](https://developer.android.com/reference/androidx/camera/camera2/package-summary)
    - [androidx.camera.core](https://developer.android.com/reference/androidx/camera/core/package-summary)
    - [androidx.camera.extensions](https://developer.android.com/reference/androidx/camera/extensions/package-summary)
    - [androidx.camera.lifecycle](https://developer.android.com/reference/androidx/camera/lifecycle/package-summary)
    - [androidx.camera.view](https://developer.android.com/reference/androidx/camera/view/package-summary)

- Camera API1/API2 : [frameworks/base/core/java/android/hardware](http://androidxref.com/9.0.0_r3/xref/frameworks/base/core/java/android/hardware/)
    - [Camera.java](http://androidxref.com/9.0.0_r3/xref/frameworks/base/core/java/android/hardware/Camera.java)
    - [CameraInfo.java](http://androidxref.com/9.0.0_r3/xref/frameworks/base/core/java/android/hardware/CameraInfo.java)
    - [CameraStatus.java](http://androidxref.com/9.0.0_r3/xref/frameworks/base/core/java/android/hardware/CameraStatus.java)
    - [camera2/](http://androidxref.com/9.0.0_r3/xref/frameworks/base/core/java/android/hardware/camera2/)

- media.camera / media.camera.proxy :
    - [CameraServiceProxy.java](http://androidxref.com/9.0.0_r3/xref/frameworks/base/services/core/java/com/android/server/camera/)


- cameraserver :
    - client : [frameworks/av/camera](http://androidxref.com/9.0.0_r3/xref/frameworks/av/camera/)
    - server : [frameworks/av/services/camera](http://androidxref.com/9.0.0_r3/xref/frameworks/av/services/camera/)


- Camera metedata : [system/media/camera](http://androidxref.com/9.0.0_r3/xref/system/media/camera/)
    - [camera_metadata.h](http://androidxref.com/9.0.0_r3/xref/system/media/camera/include/system/camera_metadata.h)
    - [camera_metadata_tags.h](http://androidxref.com/9.0.0_r3/xref/system/media/camera/include/system/camera_metadata_tags.h)
    - [camera_vendor_tags.h](http://androidxref.com/9.0.0_r3/xref/system/media/camera/include/system/camera_vendor_tags.h)

- Camera HIDL : [hardware/interfaces/camera](http://androidxref.com/9.0.0_r3/xref/hardware/interfaces/camera/)
    - [common](http://androidxref.com/9.0.0_r3/xref/hardware/interfaces/camera/common/)
    - [device](http://androidxref.com/9.0.0_r3/xref/hardware/interfaces/camera/device/)
    - [metadata](http://androidxref.com/9.0.0_r3/xref/hardware/interfaces/camera/metadata/)
    - [provider](http://androidxref.com/9.0.0_r3/xref/hardware/interfaces/camera/provider/)

- Legacy Camera HAL:
    - definition: [hardware/libhardware/include/hardware]()
        - [camera.h](http://androidxref.com/9.0.0_r3/xref/hardware/libhardware/include/hardware/camera.h) : HAL 1
        - [camera2.h](http://androidxref.com/9.0.0_r3/xref/hardware/libhardware/include/hardware/camera2.h) : HAL 2 &ensp;&nbsp;- *deprecated*
        - [camera3.h](http://androidxref.com/9.0.0_r3/xref/hardware/libhardware/include/hardware/camera3.h) : HAL 3
        - [camera_common.h](http://androidxref.com/9.0.0_r3/xref/hardware/libhardware/include/hardware/camera_common.h)
    - implementation: [hardware/libhardware/modules/camera/]( http://androidxref.com/9.0.0_r3/xref/hardware/libhardware/modules/camera/)
        - [3.0](http://androidxref.com/9.0.0_r3/xref/hardware/libhardware/modules/camera/3_0/)
        - [3.4](http://androidxref.com/9.0.0_r3/xref/hardware/libhardware/modules/camera/3_4/)

- Vendor Camera HAL :
    - imx6/imx8 : vendor/nxp-opensource/imx/libcamera3
    - rk3368 :
        - hardware/rockchip/camera :  rockchip camera HAL
        - hardware/rockchip/camera_engine_rkisp :  rockchip 3A engine
    - msm8953 :
        - hardware/qcom/camera :  qualcomm camera HAL, called QCCamera2
        - vendor/qcom/proprietary/mm-camera :  qualcomm camera engine, called mm-camera2

- Kernel : (take ov5640 for example)
  - imx6 : vendor/nxp-opensource/kernel_imx/drivers/media/platform/mxc/capture/ov5640_mipi_v2.c
  - imx8 : vendor/nxp-opensource/kernel_imx/drivers/media/platform/imx8/ov5640_mipi_v3.c
  - rk3368 :
    - kernel/drivers/media/platform/rockchip/isp1 :  rkisp1
    - kernel/drivers/media/i2c/ov5640.c
  - msm8953 : kernel/msm-4.9/drivers/media/platform/msm/camera_v2




</br>

## Pixel format, Media Bus format

- [videodev2.h](https://github.com/torvalds/linux/blob/master/include/uapi/linux/videodev2.h)
- [media-bus-format.h](https://github.com/torvalds/linux/blob/master/include/uapi/linux/media-bus-format.h)



</br>

## Miscellaneous

##### ■ The mipi csi settings of the sensor driver need to match the settings of SoC mipi csi side.

##### ■ enable ALOGD, ALOGV
```
// define it in the top line of c/c++ source fiie
#define LOG_NDEBUG 0

// append it in CFALGS of makefile
LOCAL_CFLAGS +=: -DNDEBUG=0		// but seems not work?
```

> ref:  [ANDROID中C++层打开ALOGV打印的LOG开关](https://blog.csdn.net/yu741677868yu/article/details/80682182)


##### ■ RGB, YUV
- [RGB format](https://linuxtv.org/downloads/v4l-dvb-apis/uapi/v4l/pixfmt-rgb.html#pixfmt-rgb)
- [YuV format](https://linuxtv.org/downloads/v4l-dvb-apis/uapi/v4l/yuv-formats.html#yuv-formats)



