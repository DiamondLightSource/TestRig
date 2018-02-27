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

    /**
     * Opposite of ConvertAndorWideStringToString, converts
     * an AT_WC* or Andor wide string
     * @param andor_wide_string The C++ string to convert
     * @return A converted string of type AT_WC*
     */
    const AT_WC* ConvertStringToAndorWideString(std::string input_string);

    /**
     * Sets an integer feature on the camera and checks that it was set
     * successfully.
     * @param andor_feature_name The name of the feature to set
     * @param demand_value The value to test
     */
    void SetIntAndCheck(std::string andor_feature_name, int demand_value);

    /**
     * Sets a float feature on the camera and checks that it was set
     * successfully.
     * @param andor_feature_name The name of the feature to set
     * @param demand_value The value to test
     */
    void SetFloatAndCheck(std::string andor_feature_name, double demand_value);

    /**
     * Sets an integer feature on the camera and checks that it was set
     * successfully.
     * @param andor_feature_name The name of the feature to set
     * @param demand_value The value to test
     */
    void SetEnumAndCheck(std::string andor_feature_name, std::string);
};

#endif //DRIVERTESTS_ANDOR3_H
