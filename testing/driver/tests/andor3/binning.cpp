#include "andor3.h"

TEST_F(AndorDriverTest, CanSetWidthBinning) {
    SetIntAndCheck("AOIWidth");
}

TEST_F(AndorDriverTest, CanSetHeightBinning) {
    SetIntAndCheck("AOIHeight");
}

TEST_F(AndorDriverTest, CanSetXBinning) {
    SetIntAndCheck("AOILeft");
}

TEST_F(AndorDriverTest, CanSetYBinning) {
    SetIntAndCheck("AOITop");
}