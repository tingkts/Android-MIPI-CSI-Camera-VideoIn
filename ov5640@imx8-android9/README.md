git:

- vendor/nxp-opensource/kernel-imx



feature:

- main(back) camera sensor ov5640


note:

- camera sensor ov5640 is also imx8 native supports CIS driver, however the supported features are relatively weak and not supported flash light.
- Use V4L2 sub-devices and introduce the media controller framework, the IMX ISI V4L2 video media-entity graph architecture.

test:

```
# v4l2-ctl -d /dev/video0 \
    --set-fmt-video=width=1920,height=1080,pixelformat=UYVY2X8 \
    --stream-mmap=3 \
    --stream-skip=3 \
    --stream-to=/sdcard/ov5640.raw \
    --stream-count=1 \
    --stream-poll
```
&nbsp;&nbsp;[ov5640.raw](./ov5640.raw)
