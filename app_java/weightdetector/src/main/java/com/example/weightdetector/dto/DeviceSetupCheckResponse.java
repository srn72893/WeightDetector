package com.example.weightdetector.dto;
import com.example.weightdetector.domain.SetupStatus;

import lombok.Getter;
/**
 * IoTデバイス状態チェック結果 DTO
 * デバイスのセットアップ状態チェック結果データを保持するクラス
 */
@Getter
public class DeviceSetupCheckResponse {
    private String deviceId;      //IoTデバイスID
    private SetupStatus status;   //デバイスのセットアップ状態
    private String message;       //処理結果メッセージ

    //コンストラクタ
    public DeviceSetupCheckResponse(String deviceId, SetupStatus status, String message) {
        this.deviceId = deviceId;
        this.status = status;
        this.message = message;
    }
}
