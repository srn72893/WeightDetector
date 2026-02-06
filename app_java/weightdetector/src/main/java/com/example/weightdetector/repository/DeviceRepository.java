package com.example.weightdetector.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.weightdetector.entity.Device;

/**
 * DB の devices テーブルへの処理を行う Repository インタフェース
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
    /**
     * 引数の deviceId と一致するレコードを取得
     * @param deviceId SELECR する device_id の値
     * @return
     */
    Optional<Device> findByDeviceId(String deviceId);

    /**
     * 引数の alreadySetup と一致するレコードを取得
     * 複数のレコードがヒットする場合は戻り値は List で受け取る
     * （未使用）
     * @param alreadySetup SELECT する already_setup の値
     * @return
     */
    List<Device> findByAlreadySetup(boolean alreadySetup);

    /**
     * 引数の janCode と一致するレコードを取得
     * （未使用）
     * @param janCode SELECT する jan_code
     * @return
     */
    List<Device> findByProduct_JanCode(String janCode);
}
