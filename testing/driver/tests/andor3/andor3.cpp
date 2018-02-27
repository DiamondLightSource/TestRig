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

const AT_WC* AndorDriverTest::ConvertStringToAndorWideString(std::string input_string) {
    return std::wstring(input_string.begin(), input_string.end()).c_str();
}

void AndorDriverTest::SetIntAndCheck(std::string andor_feature_name) {
    const AT_WC* andor_feature_name_wide = ConvertStringToAndorWideString(andor_feature_name);
    int bin_result_code = AT_SetInt(camera_handle, andor_feature_name_wide, 128);
    HandleAndorResultCode(bin_result_code);
    AT_64 actual;
    int retrieve_result_code = AT_GetInt(camera_handle, andor_feature_name_wide, &actual);
    HandleAndorResultCode(retrieve_result_code);
    ASSERT_EQ(128, actual);
}
