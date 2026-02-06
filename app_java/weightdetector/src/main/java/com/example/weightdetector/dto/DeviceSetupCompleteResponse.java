package com.example.weightdetector.dto;

import lombok.Getter;

/**
 * デバイスセットアップ結果 DTO
 * セットアップ処理結果のデータを所持する
 */
@Getter
public class DeviceSetupCompleteResponse {
    private String deviceId;    //デバイスID
    private String janCode;     //JANコード
    private String message;     //処理結果メッセージ

    //コンストラクタ
    public DeviceSetupCompleteResponse(String deviceId, String janCode, String message) {
        this.deviceId = deviceId;
        this.janCode = janCode;
        this.message = message;
    }
}
