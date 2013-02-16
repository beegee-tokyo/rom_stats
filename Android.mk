LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := 

LOCAL_STATIC_JAVA_LIBRARIES := 

# Only compile source java files in this apk.
LOCAL_SRC_FILES := $(call all-java-files-under, src)

LOCAL_PACKAGE_NAME := romstats

#LOCAL_SDK_VERSION := current

include $(BUILD_PACKAGE)
