#include "andor3.h"

/** Address of the detector in the Andor SDK's incremental addressing system **/
const int DETECTOR_ADDRESS = 0;

void AndorDriverTest::HandleAndorResultCode(int andor_result_code) {
    ASSERT_EQ(0, andor_result_code);
}

std::string AndorDriverTest::ConvertAndorWideStringToString(AT_WC* andor_wide_string) {
    std::wstring wide_string = std::wstring(andor_wide_string);
    return std::string(wide_string.begin(), wide_string.end());
}
