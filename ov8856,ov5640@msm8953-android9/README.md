#### Porting camera sensor OV8856, OV5640

8M camera sensor OV8856 as the rear camera </br>
5M camera sensor OV5640 as the front camera

Source :

- LA.UM.7.6.2/LINUX/android/kernel/msm-4.9/drivers/media/platform/msm/camera_v2
- LA.UM.7.6.2/LINUX/android/vendor/qcom/proprietary/mm-camera
- LA.UM.7.6.2/LINUX/android/hardware/qcom/camera/QCamera2

</br>

#### Dump YUV frame

set properties :

```
ms57693_64:/ # setprop persist.vendor.camera.global.debug 7        <== enabled debug
ms57693_64:/ # setprop persist.vendor.camera.isp.dump 2            <== dump preview top 10 images
```

output YUV frame :

```shell
cd /data/vendor/camera
ls -al
ms57693_64:/data/vendor/camera # ls -al
total 26408
drwxrwx---  2 camera       camera    4096 2021-01-25 07:30 .
drwxrwx--x 40 root         root      4096 1970-01-01 00:00 ..
-rwx------  1 cameraserver audio  2457600 2021-01-25 07:30 isp_dump_2_preview_1280_960_10.yuv
-rwx------  1 cameraserver audio  2457600 2021-01-25 07:30 isp_dump_2_preview_1280_960_11.yuv
-rwx------  1 cameraserver audio  2457600 2021-01-25 07:30 isp_dump_2_preview_1280_960_12.yuv
-rwx------  1 cameraserver audio  2457600 2021-01-25 07:30 isp_dump_2_preview_1280_960_13.yuv
-rwx------  1 cameraserver audio  2457600 2021-01-25 07:30 isp_dump_2_preview_1280_960_14.yuv
-rwx------  1 cameraserver audio  2457600 2021-01-25 07:30 isp_dump_2_preview_1280_960_4.yuv
-rwx------  1 cameraserver audio  2457600 2021-01-25 07:30 isp_dump_2_preview_1280_960_5.yuv
-rwx------  1 cameraserver audio  2457600 2021-01-25 07:30 isp_dump_2_preview_1280_960_6.yuv
-rwx------  1 cameraserver audio  2457600 2021-01-25 07:30 isp_dump_2_preview_1280_960_7.yuv
-rwx------  1 cameraserver audio  2457600 2021-01-25 07:30 isp_dump_2_preview_1280_960_8.yuv
-rwx------  1 cameraserver audio  2457600 2021-01-25 07:30 isp_dump_2_preview_1280_960_9.yuv
```

convert yuv raw to jpg :

```shell
ffmpeg -s 1280x960 -pix_fmt yuyv422 -i isp_dump_2_preview_1280_960_13.yuv isp_dump_2_preview_1280_960_13.jpg
```

[isp_dump_2_preview_1280_960_13.yuv](./assets/isp_dump_2_preview_1280_960_13.yuv) </br>
[isp_dump_2_preview_1280_960_13.jpg](./assets/isp_dump_2_preview_1280_960_13.jpg)



</br>

#### Export sysfs node of R/W camera sensor register

sysfs node :

```shell
msm8953_64:/sys/devices/platform/soc/1b0c000.qcom,cci # ls -al
total 0
drwxr-xr-x  10 root root    0 1970-01-01 00:00 .
drwxr-xr-x 269 root root    0 1970-01-01 00:00 ..
drwxr-xr-x   3 root root    0 1970-01-01 00:00 1b0c000.qcom,cci:qcom,actuator@0
drwxr-xr-x   3 root root    0 1970-01-01 00:00 1b0c000.qcom,cci:qcom,actuator@1
drwxr-xr-x   5 root root    0 1970-01-01 00:00 1b0c000.qcom,cci:qcom,camera@0
drwxr-xr-x   5 root root    0 1970-01-01 00:00 1b0c000.qcom,cci:qcom,camera@2
drwxr-xr-x   3 root root    0 1970-01-01 00:00 1b0c000.qcom,cci:qcom,eeprom@0
drwxr-xr-x   3 root root    0 1970-01-01 00:00 1b0c000.qcom,cci:qcom,eeprom@1
drwxr-xr-x   3 root root    0 1970-01-01 00:00 1b0c000.qcom,cci:qcom,eeprom@2
lrwxrwxrwx   1 root root    0 1970-01-01 00:00 driver -> ../../../../bus/platform/drivers/msm_cci
-rw-r--r--   1 root root 4096 1970-01-01 00:00 driver_override
-r--r--r--   1 root root 4096 1970-01-01 00:00 modalias
lrwxrwxrwx   1 root root    0 1970-01-01 00:00 of_node -> ../../../../firmware/devicetree/base/soc/qcom,cci@1b0c000
drwxr-xr-x   2 root root    0 1970-01-01 00:00 power
lrwxrwxrwx   1 root root    0 1970-01-01 00:00 subsystem -> ../../../../bus/platform
-rw-r--r--   1 root root 4096 1970-01-01 00:00 uevent

msm8953_64:/sys/devices/platform/soc/1b0c000.qcom,cci/1b0c000.qcom,cci:qcom,camera@2 # ls -al
total 0
drwxr-xr-x  5 root root    0 1970-01-01 00:00 .
drwxr-xr-x 10 root root    0 1970-01-01 00:00 ..
-rw-r--r--  1 root root 4096 1970-01-01 00:00 cs_rw_reg
-rw-r--r--  1 root root 4096 1970-01-01 00:00 cs_test
lrwxrwxrwx  1 root root    0 1970-01-01 00:00 driver -> ../../../../../bus/platform/drivers/qcom,camera
-rw-r--r--  1 root root 4096 1970-01-01 00:00 driver_override
drwxr-xr-x  3 root root    0 1970-01-01 00:00 media2
-r--r--r--  1 root root 4096 1970-01-01 00:00 modalias
lrwxrwxrwx  1 root root    0 1970-01-01 00:00 of_node -> ../../../../../firmware/devicetree/base/soc/qcom,cci@1b0c000/qcom,camera@2
drwxr-xr-x  2 root root    0 1970-01-01 00:00 power
lrwxrwxrwx  1 root root    0 1970-01-01 00:00 subsystem -> ../../../../../bus/platform
-rw-r--r--  1 root root 4096 1970-01-01 00:00 uevent
drwxr-xr-x  3 root root    0 1970-01-01 00:00 video4linux
```

R/W register :

```shell
# select ov5640's node
cd ./sys/devices/platform/soc/1b0c000.qcom,cci/1b0c000.qcom,cci:qcom,camera@2

# read a single register
echo "0x5194" > cs_rw_reg ; cat cs_rw_reg ;

# read a range of registers
echo "0x5188 0x519d" > cs_rw_reg ; cat cs_rw_reg ;

# write a single register
echo "0x503d 0x80" > cs_rw_reg ; cat cs_rw_reg ;

# write a range of registers
echo "0x5188 0x0a" > cs_rw_reg ; \
echo "0x5189 0x75" > cs_rw_reg ; \
echo "0x518a 0x52" > cs_rw_reg ; \
echo "0x518b 0xea" > cs_rw_reg ; \
echo "0x518c 0xa8" > cs_rw_reg ; \
echo "0x518d 0x42" > cs_rw_reg ; \
echo "0x518e 0x38" > cs_rw_reg ; \
echo "0x518f 0x56" > cs_rw_reg ; \
echo "0x5190 0x42" > cs_rw_reg ; \
echo "0x5191 0xf8" > cs_rw_reg ; \
echo "0x5192 0x04" > cs_rw_reg ; \
echo "0x5193 0x70" > cs_rw_reg ; \
echo "0x5194 0xf0" > cs_rw_reg ; \
echo "0x5195 0xf0" > cs_rw_reg ; \
echo "0x5196 0x03" > cs_rw_reg ; \
echo "0x5197 0x01" > cs_rw_reg ; \
echo "0x5198 0x04" > cs_rw_reg ; \
echo "0x5199 0x12" > cs_rw_reg ; \
echo "0x519a 0x04" > cs_rw_reg ; \
echo "0x519b 0x00" > cs_rw_reg ; \
echo "0x519c 0x06" > cs_rw_reg ; \
echo "0x519d 0x82" > cs_rw_reg ;

# check the kernel log of msm_camera_cci_i2c.c
[   16.053320] msm_camera_cci_i2c.c msm_camera_cci_i2c_read addr = 0x300a data: 0x5640
```




</br>

#### Market Camera Apps for reference

Camera App generally locks the orientation of portrait or landscape.
When user rotate the phone, only the UI (or called View) will change the orientation, and the Window will not rotate.

Portrait :
- [com.simplemobiletools.camera](https://play.google.com/store/apps/details?id=com.simplemobiletools.camera)
- [Camera360](https://play.google.com/store/apps/details?id=vStudio.Android.Camera360)
- [LINE Camera](https://play.google.com/store/apps/details?id=jp.naver.linecamera.android)

Landscape :
- [Open Camera](https://play.google.com/store/apps/details?id=net.sourceforge.opencamera)
- [ProCam X Lite](https://play.google.com/store/apps/details?id=com.intermedia.hd.camera.professional)



