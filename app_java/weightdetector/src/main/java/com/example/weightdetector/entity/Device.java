package com.example.weightdetector.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * DB 上の対象デバイスのレコードを表すクラス
 * Repositoryクラスが返す実態
 */
@Entity
@Setter
@Getter
@Table(name = "devices")
public class Device {
    //デバイスID（主キー）
    @Id
    @Column(name = "device_id")
    private String deviceId;

    //セットされている商品（外部キー : jan_code）
    @ManyToOne(fetch = FetchType.LAZY)  //多対一の関係を示し、必要なときのみ参照先のカラムを取得する設定にしておく
    @JoinColumn(name = "jan_code", referencedColumnName = "jan_code")   //referenced.. 参照先のカラム名
    private Product product;

    //セットアップ済か
    @Column(name = "already_setup")
    private boolean alreadySetup;

    //デフォルトコンストラクタ
    public Device() {}

    /**
     * セットアップ完了時、DB の device テーブルに
     * デバイスに対応する商品情報をセットする
     */
    public void completeSetup(Product product) {
        //already_setup が true のレコードはセットアップ済みデバイス
        if (this.alreadySetup) {
            throw new IllegalStateException("デバイスがセットアップ済です");
        }
        this.product = product;
        this.alreadySetup = true;
    }
}
