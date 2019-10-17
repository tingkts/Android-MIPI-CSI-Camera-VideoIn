git:

- vendor/nxp-opensource/kernel-imx

- vendor/nxp-opensource/imx

feature:

- main(back) camera sensor ov5640

- camera flash light

note:

- camera sensor ov5640 is imx6 native supports CIS driver, however the supported features are relatively weak and not supported flash light.
- Use V4L2 int-device and i.MX ISP video architecture(called imx "ipu" architecture).


test:

 ```
# v4l2-ctl -d /dev/video1 \
    --set-fmt-video=width=640,height=480,pixelformat=UYVY \
    --stream-mmap=3 \
    --stream-skip=3 \
    --stream-to=/sdcard/frame.raw \
    --stream-count=1 \
    --stream-poll

	// but frame quality is not good, ref: frame.raw

# camera_test -ow 640 -oh 480 -x 1
```
&nbsp;&nbsp;[frame.raw](./frame.raw)

misc.:

- the difference of imx6-android7 and imx6-android9

| imx6 android-7        | imx6 android-9  |
| :--------:   | :-----:  |
| api1      | api2   |
| hal3        |   hal3   |
| Camera2Client        |    CameraDeviceClient    |

- video device note

```
  /sys/class/video4linux/video0/name:Mxc Camera    // camera: ov5640
  /sys/class/video4linux/video1/name:Mxc Camera    // video-in
  /sys/class/video4linux/video16/name:DISP3 BG
  /sys/class/video4linux/video17/name:DISP3 FG
```