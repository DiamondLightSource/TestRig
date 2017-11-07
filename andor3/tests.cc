#include <limits.h>
#include <stdlib.h>
#include <iostream>
#include "atcore.h"
#include "gtest/gtest.h"

const int DETECTOR_ADDRESS = 0;
const char* ANDOR_DETECTOR_MODEL = "DC-152Q-C00-FI";

TEST(ANDOR, DEVICE_PRESENT) {
    int iErr = AT_InitialiseLibrary();
    if (iErr != AT_SUCCESS) {
        std::cout << "Error from AT_Initialise : " << iErr << std::endl;
    }

    AT_H Hndl  = AT_HANDLE_UNINITIALISED;
    iErr = AT_Open(DETECTOR_ADDRESS, &Hndl);
    if (iErr != AT_SUCCESS) {
        std::cout << "Error from AT_Open() : " << iErr << std::endl;
    }
    AT_WC CameraModel[128];
    iErr = AT_GetString(Hndl, L"Camera Model", CameraModel, 128);

    if (iErr == AT_SUCCESS) {
        char szCamModel[128];
        wcstombs(szCamModel, CameraModel, 64);
        EXPECT_STREQ(ANDOR_DETECTOR_MODEL, szCamModel);
    }

    iErr = AT_Close(Hndl);
    if (iErr != AT_SUCCESS) {
        std::cout << "Error from AT_Close() : " << iErr << std::endl;
    }

    AT_FinaliseLibrary();
}
