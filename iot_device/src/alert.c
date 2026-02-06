#include <stdio.h>
#include "alert.h"

void
initAlertData(struct alertData * a)
{
    a->cumDecrease = 0.0;
    a->threshold = 0.0;
    a->shouldAlert = false;
    a->needReset = false;
}

//再設定時に値リセットする
void
resetAlertData(struct alertData * a)
{
    a->cumDecrease = 0.0;
    a->shouldAlert = false;
    a->needReset = false;
}

//閾値判定（アラートフラグを立てるか）
bool
alertChk(struct alertData * a)
{
    //累積減少量が閾値を超えたら true を返す
    return (a->cumDecrease > a->threshold);
}

//アラート停止ボタンの入力を受け取る
bool
stopButtonPressed(void)
{
    //仮想 stopボタン
    int input = 0;
    printf("停止 = 1 ");
    scanf_s("%d", &input);

    //入力値が 1 なら true、それ以外は fauls を返す
    return (input == 1);
}

//アラートを鳴らすか決める
void
controlAlert(struct alertData * a, bool stopPressed)
{
    //アラート停止ボタン押されているとき
    if (stopPressed) {
        a->shouldAlert = false;
        alertOff();
        //リセット要求する
        a->needReset = true;
    }
    //閾値より大きい
    else if (alertChk(a)) {
        // 判定 true ならフラグを立てアラートを鳴らす
        a->shouldAlert = true;
        alertOn();
    }
    //閾値以下
    else {
        a->shouldAlert = false;
        alertOff();
    }
}

//アラートON 出力
void
alertOn(void)
{
    //仮想
    printf("alertOn\n");
}

//アラートOFF 出力
void
alertOff(void)
{
    //仮想
    printf("alertOff\n");
}
