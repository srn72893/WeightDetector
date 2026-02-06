#ifndef ALERT_H
#define ALERT_H
#include <stdbool.h>    //可読性優先のため bool型 使用

//変数リスト
struct alertData
{
    double cumDecrease;     //累積減少量
    double threshold;       //閾値
    bool shouldAlert;       //アラートフラグ
    bool needReset;         //リセット要求フラグ
};

//プロトタイプ
void initAlertData(struct alertData * a);
void resetAlertData(struct alertData * a);
bool alertChk(struct alertData * a);
bool stopButtonPressed(void);
void controlAlert(struct alertData * a, bool stopPressed);
void alertOn(void);
void alertOff(void);

//テスト用プロトタイプ
void runTest(struct alertData * a);

#endif //ALERT_H
