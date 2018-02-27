#include <chrono>
#include "andor3.h"

TEST_F(AndorDriverTest, CanSetFramerate) {
    SetFloatAndCheck("FrameRate", 12.5);
}

TEST_F(AndorDriverTest, CanSetFramerateToMaximum) {
    double maximum;
    int get_result = AT_GetFloatMax(camera_handle, L"FrameRate", &maximum);
    HandleAndorResultCode(get_result);
    SetFloatAndCheck("FrameRate", maximum);
}
