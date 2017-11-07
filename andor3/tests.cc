#include <limits.h>
#include <stdlib.h>
#include <iostream>
#include "atcore.h"
#include "gtest/gtest.h"

const int DETECTOR_ADDRESS = 0;
const char* ANDOR_DETECTOR_MODEL = "DC-152Q-C00-FI";

void handleAndorResultCode(int andor_result_code) {
    EXPECT_EQ(0, andor_result_code);
}

void initializeAndorSdk() {
    int andor_result_code = AT_InitialiseLibrary();
    handleAndorResultCode(andor_result_code);
}

AT_H& openCameraConnection() {
    AT_H Hndl  = AT_HANDLE_UNINITIALISED;
    int andor_result_code = AT_Open(DETECTOR_ADDRESS, &Hndl);
    handleAndorResultCode(andor_result_code);
    return Hndl;
}

AT_H& ConnectToCamera() {
    initializeAndorSdk();
    return openCameraConnection();
}

void closeCameraConnection(AT_H& camera_handle) {
    int andor_result_code = AT_Close(camera_handle);
    handleAndorResultCode(andor_result_code);
}

void cleanUpAndorSdk() {
    int andor_result_code = AT_FinaliseLibrary();
    handleAndorResultCode(andor_result_code);
}

void DisconnectFromCamera(AT_H& camera_handle) {
    closeCameraConnection(camera_handle);
    cleanUpAndorSdk();
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
    handleAndorResultCode(andor_result_code);

    double exposure_time;
    andor_result_code = AT_GetFloat(camera_handle, L"ExposureTime", &exposure_time);
    handleAndorResultCode(andor_result_code);

    EXPECT_NEAR(0.01, exposure_time, 0.001);

    DisconnectFromCamera(camera_handle);
}