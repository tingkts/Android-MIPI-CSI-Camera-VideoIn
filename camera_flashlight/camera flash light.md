#### ANDROID_FLASH_INFO_AVAILABLE

&nbsp;&nbsp;determine whether to support flash

&nbsp;&nbsp;[/system/media/camera/include/system/camera_metadata_tags.h](/system/media/camera/include/system/camera_metadata_tags.h)

```c
687 // ANDROID_FLASH_INFO_AVAILABLE
688 typedef enum camera_metadata_enum_android_flash_info_available {
689     ANDROID_FLASH_INFO_AVAILABLE_FALSE                              , // HIDL v3.2
690     ANDROID_FLASH_INFO_AVAILABLE_TRUE                               , // HIDL v3.2
691 } camera_metadata_enum_android_flash_info_available_t;
692
```

#### ANDROID_FLASH_MODE

&nbsp;&nbsp;determine flash mode :

| enum name              | enum value | meaning            |
|:-----------------------|:----------:|:-------------------|
| off                    |     0      | no flash light     |
| single or called auto  |     1      | auto flash light   |
| torch or called always |     1      | always flash light |

&nbsp;&nbsp;[/system/media/camera/include/system/camera_metadata_tags.h](/system/media/camera/include/system/camera_metadata_tags.h)

```c
670 // ANDROID_FLASH_MODE
671 typedef enum camera_metadata_enum_android_flash_mode {
672     ANDROID_FLASH_MODE_OFF                                          , // HIDL v3.2
673     ANDROID_FLASH_MODE_SINGLE                                       , // HIDL v3.2
674     ANDROID_FLASH_MODE_TORCH                                        , // HIDL v3.2
675 } camera_metadata_enum_android_flash_mode_t;
```

#### KEY_SUPPORTED_FLASH_MODES : flash-mode-values

&nbsp;&nbsp;[flash-mode-values](http://androidxref.com/9.0.0_r3/search?q=flash-mode-values&defs=&refs=&path=-%22.+jar%22&hist=&project=art&project=bionic&project=bootable&project=build&project=compatibility&project=cts&project=dalvik&project=developers&project=development&project=device&project=external&project=frameworks&project=hardware&project=kernel&project=libcore&project=libnativehelper&project=packages&project=pdk&project=platform_testing&project=prebuilts&project=sdk&project=system&project=test&project=toolchain&project=tools)

```
  adb shell dumpsys media.camera

	flash-mode: off
	flash-mode-values: off,auto,on,torch
```

| enum name | meaning                       |
|:----------|-------------------------------|
| off       | camera doesn't apply flash    |
| auto      | camera auto apply flash       |
| on        | camera always apply flash     |
| torch     | use as flashlight application |