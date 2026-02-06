//TODO
//単位時間あたりの累積減少量を評価するテスト用機構未実装
//getServerEvent()の中身 エラーの動作追加
//エラーハンドリングのための関数作成
//currentWeight = 0（品切れ）時、通知行くと便利
//売上も見たいなら、品出し時は cumDecrease はリセットしない方がいい

#include <stdio.h>
#include <assert.h>
#include "alert.h"

//変数リスト
struct weightData
{
    double baseWeight;      //商品満タン時の重量
    double currentWeight;   //現在の重量
    double prevWeight;      //前回の重量
    double difference;      //重量差分（前回重量 - 現在重量）
};

struct serverEvent
{
    bool evStart;   //サーバから設定信号を受け取る
    bool evReset;   //サーバから再設定信号を受け取る
};

enum systemState
{
    STATE_WAIT_SETTING, //設定待ち
    STATE_RUNNING,      //稼働中
};

//プロトタイプ
void initWeightData(struct weightData * w);
void initServerEvent(struct serverEvent * s);
struct serverEvent getServerEvent(void);
void pollServerSignal(struct serverEvent * s);
int readWeight(double * outWeight);
int setBaseWeight(struct weightData * w);
int setCurrentWeight(struct weightData * w);
void calcDiff(struct weightData * w);
double calcCumDecrease(struct weightData * w, struct alertData * a);
int fetchThreshold(double * outTreshold);
int setThreshold(struct alertData * a);

//関数利用変数の初期化
void
initWeightData(struct weightData * w)
{
    w->baseWeight = 0.0;
    w->currentWeight = 0.0;
    w->prevWeight = 0.0;
    w->difference = 0.0;
}

//サーバイベント用変数の初期化
void
initServerEvent(struct serverEvent * s)
{
    s->evStart = false;
    s->evReset = false;
}

int
main(void)
{
    struct weightData w;
    struct serverEvent ev;
    struct alertData a;
    enum systemState state = STATE_WAIT_SETTING;

    //電源 ON 変数初期化
    initWeightData(&w);
    initServerEvent(&ev);
    initAlertData(&a);

    while (true) {
        //ユーザーが設定完了したか聞く
        pollServerSignal(&ev);

        switch (state) {
        case STATE_WAIT_SETTING:
            //通信成功前提の動き（エラー時の処理まだ）
            //初期設定時
            if (ev.evStart) {
                //閾値と baseWeight を取得
                setThreshold(&a);
                printf("今乗っている数 ");
                setBaseWeight(&w);
                resetAlertData(&a);
                state = STATE_RUNNING;
            }
            //再設定時
            else if (ev.evReset) {
                //閾値は保持したまま baseWeightを更新
                printf("今乗っている数 ");
                setBaseWeight(&w);
                resetAlertData(&a);
                state = STATE_RUNNING;
            }
            break;
        case STATE_RUNNING:
            while (true) {
                //currentWeight から prevWeight を更新
                printf("商品を取る ");
                setCurrentWeight(&w);

                //累積減少量を計算
                calcCumDecrease(&w, &a);

                //アラートするか
                bool stopPressed = stopButtonPressed(); //停止ボタンが押されたか
                controlAlert(&a, stopPressed);

                //アラート鳴ったらループ抜けて再設定させる
                if (a.needReset) {
                    state = STATE_WAIT_SETTING;
                    break;
                }
            }
            break;
        default:
            break;
        }
    }

    return 0;
}

//サーバから設定・再設定信号を受け取る
struct serverEvent
    getServerEvent(void)
{
    //前回のイベント初期化
    struct serverEvent ev = { 0 };

    //仮想（実際はサーバの信号を受け取る）
    int signal = 0;
    printf("設定 : 1, 再設定 : 2 ");
    scanf_s("%d", &signal);

    if (signal == 1) {
        ev.evStart = true;
    }
    if (signal == 2) {
        ev.evReset = true;
    }
    //エラーの動作いる

    //今回のイベント結果を返す
    return ev;
}

//サーバから受け取った信号を反映
void
pollServerSignal(struct serverEvent * s)
{
    //サーバから信号を受け取る（他の関数で使えるようにポインタで渡す）
    *s = getServerEvent();
}

//重りセンサー
int
readWeight(double * outWeight)
{
    //仮想
    int piece = 0;
    printf("何個？");
    scanf_s("%d", &piece);
    //1個 100.0g とする
    *outWeight = 100.0 * piece;

    return 0;   //正常終了
}

//baseweight の設定（setボタン入力 → セット）
int
setBaseWeight(struct weightData * w)
{
    double weight;
    //readWeight(); が正常終了 重りセンサー入力値代入
    if (readWeight(&weight) == 0) {
        w->baseWeight = weight;
        w->prevWeight = w->baseWeight; //初期値として base を前回値にセット
        return 0;
    }
    else {
        return -1;  //エラー
    }
}

//currentWeight にセンサーからの値をセット
int
setCurrentWeight(struct weightData * w)
{
    double weight;
    //readWeight(); が正常終了 重りセンサー入力値代入
    if (readWeight(&weight) == 0) {
        //テストの便宜上 weight に「取った量」が入るため
        //「デバイスに乗っている量」に加工する
        double tmp;
        tmp = weight;
        weight = w->prevWeight - tmp;   //ここまで仮想環境用処理

        w->currentWeight = weight;
        return 0;
    }
    else
    {
        return -1;  //エラー
    }
}

//重量差分の計算
void
calcDiff(struct weightData * w)
{
    //差分 = 前回重量 - 現在の重量
    w->difference = w->prevWeight - w->currentWeight;
    //prevWeight を currentWeight に更新
    w->prevWeight = w->currentWeight;
}

//累積減少量の計算
double
calcCumDecrease(struct weightData * w, struct alertData * a)
{
    calcDiff(w); //差分を求める
    if (w->difference >= 0) {
        //差分が 正 または 変化なし のとき累積を維持
        return a->cumDecrease += w->difference;
    }
    else if (w->difference < 0) {
        //差分が負のとき リセット
        return a->cumDecrease = 0.0;
    }
}

//閾値をサーバから取る
int
fetchThreshold(double * outThreshold)
{
    //閾値 300.0g とする（実際はサーバからもらう）
    *outThreshold = 300.0;
    return 0;   //正常終了
}

//閾値をセットする
int
setThreshold(struct alertData * a)
{
    double threshold;
    //fetchTreshold(); の戻り値 0 = 正常終了
    if (fetchThreshold(&threshold) == 0) {
        //fetchTreshold(); が正常終了していた時のみ代入
        a->threshold = threshold;
        return 0;
    }
    else {
        return -1;  //エラー
    }
}
