#ifndef ERROR_H
#define ERROR_H
#include <stdint.h>

//エラーの種類
enum ErrorFlag
{
    ERR_NONE = 0,
    ERR_SENSOR_FAIL = 1 << 0,     //00000001    センサー異常
    ERR_BUTTON_STUCK = 1 << 1,    //00000010    ボタン異常
    ERR_WEIGHT_ABNORMAL = 1 << 2, //00000100    重量異常
};

//プロトタイプ
void errorSet(enum ErrorFlag err);
uint8_t getErrorFlags(void);
void errorClear(enum ErrorFlag err);

#endif  //ERROR_H