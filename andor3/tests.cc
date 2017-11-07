#include <limits.h>
#include <stdlib.h>
#include <iostream>
#include "atcore.h"
#include "gtest/gtest.h"

const int DETECTOR_ADDRESS = 0;
const char* ANDOR_DETECTOR_MODEL = "DC-152Q-C00-FI";

void initializeAndorSdk() {
    int iErr = AT_InitialiseLibrary();
    if (iErr != AT_SUCCESS) {
        std::cout << "Error from AT_Initialise : " << iErr << std::endl;
    }
}

AT_H& openCameraConnection() {
    AT_H Hndl  = AT_HANDLE_UNINITIALISED;
    int iErr = AT_Open(DETECTOR_ADDRESS, &Hndl);
    if (iErr != AT_SUCCESS) {
        std::cout << "Error from AT_Open() : " << iErr << std::endl;
    }
    return Hndl;
}

AT_H& ConnectToCamera() {
    initializeAndorSdk();
    return openCameraConnection();
}

void closeCameraConnection(AT_H& camera_handle) {
    int iErr = AT_Close(camera_handle);
    if (iErr != AT_SUCCESS) {
        std::cout << "Error from AT_Close() : " << iErr << std::endl;
    }
}

void cleanUpAndorSdk() {
    int iErr = AT_FinaliseLibrary();
    if (iErr != AT_SUCCESS) {
        std::cout << "Error from AT_FinaliseLibrary() : " << iErr << std::endl;
    }
}

void DisconnectFromCamera(AT_H& camera_handle) {
    closeCameraConnection(camera_handle);
    cleanUpAndorSdk();
}

TEST(Andor, DevicePresent) {
    AT_H camera_handle = ConnectToCamera();

    AT_WC CameraModel[128];
    int iErr = AT_GetString(camera_handle, L"Camera Model", CameraModel, 128);
    if (iErr == AT_SUCCESS) {
        char szCamModel[128];
        wcstombs(szCamModel, CameraModel, 64);
        EXPECT_STREQ(ANDOR_DETECTOR_MODEL, szCamModel);
    }

    DisconnectFromCamera(camera_handle);
}

TEST(Andor, CanSetExposure) {
    AT_H camera_handle = ConnectToCamera();

    int iErr = AT_SetFloat (camera_handle,  L"ExposureTime", 0.01);
    if (iErr != AT_SUCCESS) {
        std::cout << "Error from AT_SetFloat() : " << iErr << std::endl;
    }

    double exposure_time;
    iErr = AT_GetFloat(camera_handle, L"ExposureTime", &exposure_time);
    if (iErr != AT_SUCCESS) {
        std::cout << "Error from AT_GetFloat() : " << iErr << std::endl;
    }
    
    EXPECT_NEAR(0.01, exposure_time, 0.001);

    DisconnectFromCamera(camera_handle);
}