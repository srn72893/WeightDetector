//TODO
//累積減少量 = 0 （初期状態）のテスト
//累積減少量 < 0 （品出し時） 変数初期化されているか、アラート鳴らないか テスト

#include "alert.h"
#include <stdio.h>
#include <assert.h>


int
main(void)
{
    struct alertData a;
    runTest(&a);
    return 0;
}


void
runTest(struct alertData * a)
{
    //TEST_1 : 累積減少量 < 閾値、停止ボタン押されてない
    printf("---------- TEST_1 : cumDecrease < threshold, stopPressed = false\n");
    initAlertData(a);
    a->cumDecrease = 100.0;
    a->threshold = 300.0;
    controlAlert(a, false);
    assert(a->shouldAlert == false);
    assert(a->needReset == false);

    //TEST_2 : 累積減少量 < 閾値、停止ボタン押された
    printf("---------- TEST_2 : cumDecrease < threshold, stopPressed = true\n");
    initAlertData(a);
    a->cumDecrease = 100.0;
    a->threshold = 300.0;
    controlAlert(a, true);
    assert(a->shouldAlert == false);
    assert(a->needReset == true);

    //TEST_3 : 累積減少量 = 閾値、停止ボタン押されてない
    printf("---------- TEST_3 : cumDecrease = threshold, stopPressed = false\n");
    initAlertData(a);
    a->cumDecrease = 300.0;
    a->threshold = 300.0;
    controlAlert(a, false);
    assert(a->shouldAlert == false);
    assert(a->needReset == false);

    //TEST_4 : 累積減少量 = 閾値、停止ボタン押された
    printf("---------- TEST_4 : cumDecrease = threshold, stopPressed = true\n");
    initAlertData(a);
    a->cumDecrease = 300.0;
    a->threshold = 300.0;
    controlAlert(a, true);
    assert(a->shouldAlert == false);
    assert(a->needReset == true);

    //TEST_5 : 累積減少量 > 閾値、停止ボタン押されてない
    printf("---------- TEST_5 : cumDecrease > threshold, stopPressed = false\n");
    initAlertData(a);
    a->cumDecrease = 500.0;
    a->threshold = 300.0;
    controlAlert(a, false);
    assert(a->shouldAlert == true);
    assert(a->needReset == false);

    //TEST_6 : 累積減少量 > 閾値、停止ボタン押された
    printf("---------- TEST_6 : cumDecrease > threshold, stopPressed = true\n");
    initAlertData(a);
    a->cumDecrease = 500.0;
    a->threshold = 300.0;
    controlAlert(a, true);
    assert(a->shouldAlert == false);
    assert(a->needReset == true);
}
