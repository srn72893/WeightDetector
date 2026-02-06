package com.example.weightdetector.dto;

import lombok.Getter;
import lombok.Setter;
/**
 * IoTデバイス状態取得要求 DTO
 * IoTデバイスのセットアップ状態を
 * サーバから取得するためのデータを保持するクラス
 */
@Getter
@Setter
public class DeviceSetupCheckRequest {
    private String deviceId;    //IoTデバイスID
}