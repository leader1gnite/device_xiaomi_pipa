#
# Copyright (C) 2021 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

# Inherit from sm8250-common
include device/xiaomi/sm8250-common/BoardConfigCommon.mk

# Inherit from proprietary files for miuicamera
-include vendor/xiaomi/pipa-miuicamera/products/board.mk

DEVICE_PATH := device/xiaomi/pipa

# Board
TARGET_BOARD_INFO_FILE := $(DEVICE_PATH)/board-info.txt

# Display
TARGET_SCREEN_DENSITY := 440

# Kernel
TARGET_KERNEL_CONFIG += vendor/xiaomi/pipa.config

# Properties
TARGET_SYSTEM_PROP += $(DEVICE_PATH)/system.prop
TARGET_VENDOR_PROP += $(DEVICE_PATH)/vendor.prop

# Sepolicy
BOARD_VENDOR_SEPOLICY_DIRS += $(DEVICE_PATH)/sepolicy/vendor

# Wi-Fi
SOONG_CONFIG_XIAOMI_KONA_WIFI_SYMLINK_VERSION := v2

# Inherit from the proprietary version
include vendor/xiaomi/pipa/BoardConfigVendor.mk
