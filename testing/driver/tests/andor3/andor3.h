#ifndef DRIVERTESTS_ANDOR3_H
#define DRIVERTESTS_ANDOR3_H

#include "atcore.h"
#include "gtest/gtest.h"

/**
 * This base class exists in case any more tests are needed, and to keep the test
 * function as small as possible. It connects to and disconnects from the camera
 * safely before and after a test.
 */
class AndorDriverTest : public testing::Test {
protected:
    // Used to identify an open camera
    AT_H camera_handle;

    // Used to identify any camera
    int detector_address;

    virtual void SetUp() {
        camera_handle = AT_HANDLE_UNINITIALISED;
        int initialise_result = AT_InitialiseLibrary();
        HandleAndorResultCode(initialise_result);
        int open_result = AT_Open(detector_address, &camera_handle);
        HandleAndorResultCode(open_result);
    }

    virtual void TearDown() {
        int close_result = AT_Close(camera_handle);
        HandleAndorResultCode(close_result);
        int finalise_result = AT_FinaliseLibrary();
        HandleAndorResultCode(finalise_result);
    }

    /**
     * Takes an Andor SDK result code and fails a test if it is not
     * the success value (defined in atcore.h as AT_SUCCESS)
     * @param andor_result_code Result code to analyse
     */
    void HandleAndorResultCode(int andor_result_code);

    /**
     * Helper function for converting a string of type AT_WC* to
     * a C++ std::string
     * @param andor_wide_string The AT_WC* to convert
     * @return A converted string of type std::string
     */
    std::string ConvertAndorWideStringToString(AT_WC* andor_wide_string);

    const AT_WC* ConvertStringToAndorWideString(std::string input_string);

    void SetIntAndCheck(std::string andor_feature_name);
};

#endif //DRIVERTESTS_ANDOR3_H
