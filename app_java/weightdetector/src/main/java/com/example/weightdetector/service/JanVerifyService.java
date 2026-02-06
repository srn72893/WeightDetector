package com.example.weightdetector.service;
import org.springframework.stereotype.Service;

import com.example.weightdetector.dto.JanVerifyRequest;
import com.example.weightdetector.dto.JanVerifyResponse;
import com.example.weightdetector.entity.Product;
import com.example.weightdetector.repository.ProductRepository;

/**
 * 商品情報検索に関する Service クラス
 */
@Service
public class JanVerifyService {
    private final ProductRepository productRepository;

    //コンストラクタ
    public JanVerifyService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * JANコードから商品を特定する
     * @param request
     * @return DTO（JanVerifyResponse）クラスのインスタンス
     */
    public JanVerifyResponse findProduct(JanVerifyRequest request) {
        String janCode = request.getJanCode();
        //JAN コードの形式が正しいかチェックする
        if (!isValidJanFormat(janCode)) {
            throw new IllegalArgumentException("JANコードの形式が不正です");
        }
        //findByJanCode() の戻り値が Optional.empty() のとき例外として処理
        //値があれば Product（Entityクラス）オブジェクトに入る
        Product product = productRepository.findByJanCode(janCode)
        .orElseThrow(() -> new IllegalArgumentException("商品が見つかりません"));

        //例外として処理されなかったらここまで来る
        return new JanVerifyResponse(
            product.getJanCode(),
            product.getProductName()
        );
    }

    /**
     * JAN コードが正常な形式のものかチェックする
     * @param janCode
     * @return
     */
    public boolean isValidJanFormat (String janCode) {
        //入力が 8文字 または 13文字 の数字かチェック
        return janCode != null && 
        (janCode.matches("\\d{8}") || janCode.matches("\\d{13}"));
    }
}
