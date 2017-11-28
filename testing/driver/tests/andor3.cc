#include <memory>
#include "atcore.h"
#include "gtest/gtest.h"

/** Address of the detector in the Andor SDK's incremental addressing system **/
const int DETECTOR_ADDRESS = 0;
/** Expected model, so we can verify that we have the right detector **/
const std::string ANDOR_DETECTOR_MODEL = "DC-152Q-C00-FI";

/**
 * Takes an Andor SDK result code and fails a test if it is not
 * the success value (defined in atcore.h as AT_SUCCESS)
 * @param andor_result_code Result code to analyse
 */
inline void HandleAndorResultCode(int andor_result_code) {
    EXPECT_EQ(0, andor_result_code);
}

/**
 * Helper function for converting a string of type AT_WC* to
 * a higher-level C++ std::string
 * @param andor_wide_string The AT_WC* to convert
 * @return A converted string of type std::string
 */
std::string ConvertAndorWideStringToString(AT_WC* andor_wide_string) {
    std::wstring wide_string = std::wstring(andor_wide_string);
    return std::string(wide_string.begin(), wide_string.end());
}

void InitializeAndorSdk() {
    int andor_result_code = AT_InitialiseLibrary();
    HandleAndorResultCode(andor_result_code);
}

void CloseCameraConnection(AT_H& camera_handle) {
    int andor_result_code = AT_Close(camera_handle);
    HandleAndorResultCode(andor_result_code);
}

void OpenCameraConnection(AT_H& camera_handle) {
    camera_handle = AT_HANDLE_UNINITIALISED;
    int andor_result_code = AT_Open(DETECTOR_ADDRESS, &camera_handle);
    HandleAndorResultCode(andor_result_code);
}


void ConnectToCamera(AT_H& camera_handle) {
    InitializeAndorSdk();
    OpenCameraConnection(camera_handle);
}

std::string GetCameraModel(AT_H& camera_handle) {
    AT_WC camera_model[128];
    int andor_result_code = AT_GetString(
            camera_handle, L"Camera Model", camera_model, 128);
    HandleAndorResultCode(andor_result_code);
    return ConvertAndorWideStringToString(camera_model);
}

void CleanUpAndorSdk() {
    int andor_result_code = AT_FinaliseLibrary();
    HandleAndorResultCode(andor_result_code);
}

void DisconnectFromCamera(AT_H& camera_handle) {
    CloseCameraConnection(camera_handle);
    CleanUpAndorSdk();
}

/**
 * This base class exists in case any more tests are needed, and to keep the test
 * function as small as possible. It connects to and disconnects from the camera
 * safely before and after a test.
 */
class AndorDriverTest : public testing::Test {
protected:
    virtual void SetUp() {
        camera_handle = std::make_unique<AT_H>(AT_HANDLE_UNINITIALISED);
        ConnectToCamera(*camera_handle);
    }

    virtual void TearDown() {
        DisconnectFromCamera(*camera_handle);
        delete(camera_handle);
    }

    std::unique_ptr<AT_H> camera_handle;
};

TEST_F(AndorDriverTest, DevicePresent) {
    std::string camera_model = GetCameraModel(*camera_handle);
    ASSERT_EQ(ANDOR_DETECTOR_MODEL, camera_model);
}
