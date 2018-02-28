#include "andor3.h"

/** Expected model, so we can verify that we have the right detector **/
const std::string EXPECTED_DETECTOR_MODEL = "DC-152Q-C00-FI";

TEST_F(AndorDriverTest, DevicePresent) {
    AT_WC camera_model_wide[128];
    int andor_result_code = AT_GetString(
            camera_handle, L"Camera Model", camera_model_wide, 128);
    HandleAndorResultCode(andor_result_code);
    std::string camera_model
            = ConvertAndorWideStringToString(camera_model_wide);
    ASSERT_EQ(EXPECTED_DETECTOR_MODEL, camera_model);
}