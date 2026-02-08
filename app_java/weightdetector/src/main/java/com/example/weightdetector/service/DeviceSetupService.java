package com.example.weightdetector.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.weightdetector.domain.SetupStatus;
import com.example.weightdetector.dto.DeviceSetupCheckRequest;
import com.example.weightdetector.dto.DeviceSetupCheckResponse;
import com.example.weightdetector.dto.DeviceSetupCompleteResponse;
import com.example.weightdetector.entity.Device;
import com.example.weightdetector.entity.Product;
import com.example.weightdetector.repository.DeviceRepository;
import com.example.weightdetector.repository.ProductRepository;
/**
 * 入力されたデバイス ID のセットアップに関する処理を行う Service クラス
 */
@Service
public class DeviceSetupService {
    @Autowired
    private final DeviceRepository deviceRepository;
    private final ProductRepository productRepository;

    //コンストラクタ
    public DeviceSetupService(DeviceRepository deviceRepository, ProductRepository productRepository) {
        this.deviceRepository = deviceRepository;
        this.productRepository = productRepository;
    }

    /**
     * デバイスの存在とセットアップ状態を調べる
     * @param 
     * @return DeviceSetupResponse オブジェクト
     */
    public DeviceSetupCheckResponse checkSetupAvailability(DeviceSetupCheckRequest request) {
        //deviceId を使って DB から情報取得
        //戻り値が empty なら例外処理をする
        Device device = deviceRepository.findByDeviceId(request.getDeviceId()).orElseThrow(
            () -> new IllegalArgumentException("対象デバイスが存在しません : " + request.getDeviceId()));
        
        //デバイスがセットアップ済のとき
        if (device.isAlreadySetup()) {
            return new DeviceSetupCheckResponse(
                device.getDeviceId(),
                SetupStatus.ALREADY_SETUP,
                "このデバイスはセットアップ済みです"
            );
        }
        //成功時
        return new DeviceSetupCheckResponse(
            device.getDeviceId(),
            SetupStatus.AVAILABLE,
            "デバイスが見つかりました"
        );
    }

    /**
     * デバイスのセットアップを行う
     * @return
     */
    @Transactional
    public DeviceSetupCompleteResponse completeSetup(String deviceId, String janCode, String userId, String storeId) {
        //もう一度セットアップ状態を確認する
        Device device = deviceRepository.findByDeviceId(deviceId).orElseThrow(
            () -> new IllegalArgumentException("対象デバイスが存在しません : " + deviceId));
        //セットアップ済のとき → 例外スロー
        if (device.isAlreadySetup()) {
            throw new IllegalArgumentException("このデバイスはセットアップ済みです");
        }

        //もう一度商品情報の確認
        Product product = productRepository.findByJanCode(janCode).orElseThrow(
            () -> new IllegalArgumentException("商品が存在しません" + janCode));

        //DB に書き込み、セットアップ処理を行う
        device.completeSetup(product);
        //JPA から継承した save() で永続化
        //主キー（device_id が存在すれば UPDATE, なければ INSERT）
        //@Transactional 入れたら本来はなくてもいい（ポートフォリオのため明示）
        deviceRepository.save(device);
        //成功レスポンス返す
        return new DeviceSetupCompleteResponse(
            device.getDeviceId(),
            product.getJanCode(), 
            "デバイスのセットアップが完了しました");
    }
}
