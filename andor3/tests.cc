#include <limits.h>
#include <stdlib.h>
#include <iostream>
#include "atcore.h"
#include "gtest/gtest.h"

const int DETECTOR_ADDRESS = 0;
const char* ANDOR_DETECTOR_MODEL = "DC-152Q-C00-FI";

inline void HandleAndorResultCode(int andor_result_code) {
    EXPECT_EQ(0, andor_result_code);
}

void InitializeAndorSdk() {
    int andor_result_code = AT_InitialiseLibrary();
    HandleAndorResultCode(andor_result_code);
}

AT_H& OpenCameraConnection() {
    AT_H Hndl  = AT_HANDLE_UNINITIALISED;
    int andor_result_code = AT_Open(DETECTOR_ADDRESS, &Hndl);
    HandleAndorResultCode(andor_result_code);
    return Hndl;
}

AT_H& ConnectToCamera() {
    InitializeAndorSdk();
    return OpenCameraConnection();
}

void CloseCameraConnection(AT_H &camera_handle) {
    int andor_result_code = AT_Close(camera_handle);
    HandleAndorResultCode(andor_result_code);
}

void CleanUpAndorSdk() {
    int andor_result_code = AT_FinaliseLibrary();
    HandleAndorResultCode(andor_result_code);
}

void DisconnectFromCamera(AT_H& camera_handle) {
    CloseCameraConnection(camera_handle);
    CleanUpAndorSdk();
}

TEST(Andor, DevicePresent) {
    AT_H camera_handle = ConnectToCamera();

    AT_WC CameraModel[128];
    int andor_result_code = AT_GetString(camera_handle, L"Camera Model", CameraModel, 128);
    if (andor_result_code == AT_SUCCESS) {
        char szCamModel[128];
        wcstombs(szCamModel, CameraModel, 64);
        EXPECT_STREQ(ANDOR_DETECTOR_MODEL, szCamModel);
    }

    DisconnectFromCamera(camera_handle);
}

TEST(Andor, CanSetExposure) {
    AT_H camera_handle = ConnectToCamera();

    int andor_result_code = AT_SetFloat (camera_handle,  L"ExposureTime", 0.01);
    HandleAndorResultCode(andor_result_code);

    double exposure_time;
    andor_result_code = AT_GetFloat(camera_handle, L"ExposureTime", &exposure_time);
    HandleAndorResultCode(andor_result_code);

    EXPECT_NEAR(0.01, exposure_time, 0.001);

    DisconnectFromCamera(camera_handle);
}