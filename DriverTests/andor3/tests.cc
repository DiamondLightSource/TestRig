#include "connection.hpp"
#include "gtest/gtest.h"

const int DETECTOR_ADDRESS = 0;
const char* ANDOR_DETECTOR_MODEL = "DC-152Q-C00-FI";


class AndorDriverTest : public testing::Test {
protected:
    virtual void SetUp() {
        connection_ = new Connection(DETECTOR_ADDRESS);
        connection_->Open();
    }

    virtual void TearDown() {
        connection_->Close();
        delete(connection_);
    }

    Connection* connection_;
};

TEST_F(AndorDriverTest, DevicePresent) {
    AT_H camera_handle = connection_->ConnectionHandle();

    AT_WC CameraModel[128];
    AT_GetString(camera_handle, L"Camera Model", CameraModel, 128);
    char szCamModel[128];
    wcstombs(szCamModel, CameraModel, 64);
    EXPECT_STREQ(ANDOR_DETECTOR_MODEL, szCamModel);
}

TEST_F(AndorDriverTest, CanSetExposure) {
    AT_H camera_handle = connection_->ConnectionHandle();

    double target_exporsure_time = 0.01;
    AT_SetFloat (camera_handle, L"ExposureTime", target_exporsure_time);
    double exposure_time;
    AT_GetFloat(camera_handle, L"ExposureTime", &exposure_time);
    EXPECT_NEAR(target_exporsure_time, exposure_time, 0.001);
}
