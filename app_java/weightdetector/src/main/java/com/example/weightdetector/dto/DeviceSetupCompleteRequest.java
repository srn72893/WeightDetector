package com.example.weightdetector.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * デバイスのセットアップ要求 DTO
 * セットアップに入力されるデータを所持する
 */
@Getter
@Setter
public class DeviceSetupCompleteRequest {
    private String janCode; //JANコード
}
