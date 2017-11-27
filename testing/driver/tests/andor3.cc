#include <stdlib.h>
#include <iostream>
#include <memory>
#include "atcore.h"
#include "gtest/gtest.h"

const int DETECTOR_ADDRESS = 0;
const std::string ANDOR_DETECTOR_MODEL = "DC-152Q-C00-FI";

inline void HandleAndorResultCode(int andor_result_code) {
    EXPECT_EQ(0, andor_result_code);
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

std::string ConvertAndorWideStringToString(AT_WC* andor_wide_string) {
    std::wstring wide_string = std::wstring(andor_wide_string);
    return std::string(wide_string.begin(), wide_string.end());
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

class AndorDriverTest : public testing::Test {
protected:
    virtual void SetUp() {
        camera_handle = new AT_H(AT_HANDLE_UNINITIALISED);
        ConnectToCamera(*camera_handle);
    }

    virtual void TearDown() {
        DisconnectFromCamera(*camera_handle);
        delete(camera_handle);
    }

    AT_H* camera_handle;
};

TEST_F(AndorDriverTest, DevicePresent) {
    std::string camera_model = GetCameraModel(*camera_handle);
    ASSERT_EQ(ANDOR_DETECTOR_MODEL, camera_model);
}
