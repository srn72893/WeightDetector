//エラーハンドリング未実装のため未使用
#include "error.h"

//uint8_t 8ビット符号なし整数（最大 8 種類のエラー管理）
//フラグ以外に渡す情報を増やすなら構造体で
static uint8_t error_flags = 0;

//エラーフラグをセットする
void
errorSet(enum ErrorFlag err)
{
    //ビットフラグの論理和をとる → フラグを追加していける
    error_flags |= err;
}

//エラーフラグを取得する
uint8_t
getErrorFlags(void)
{
    return error_flags;
}

//エラーフラグを消す
void
errorClear(enum ErrorFlag err)
{
    //ビット反転し、論理積をとる → フラグ部分を 0 にクリアできる
    error_flags &= ~err;
}
