git:

- vendor/nxp-opensource/kernel-imx



feature:

- tw9992 video-in driver


note:

- imx8qm doesn't support videoin natively.


test:

```
# v4l2-ctl -d /dev/video1  \
    --set-standard=pal \
    --set-fmt-video=width=720,height=576,pixelformat=YUYV \
    --stream-mmap=3 \
    --stream-skip=3 \
    --stream-to=/sdcard/tw9992.raw \
    --stream-count=1 --stream-poll

# mx8_v4l2_cap_drm -cam 1 -fmt YUYV -num 30000
```
- &nbsp;&nbsp;[tw9992.raw](./tw9992.raw)
- &nbsp;&nbsp;[ISL79987 and adv7180 de-interlace driver for iMX8QXP boards](https://community.nxp.com/docs/DOC-344823)