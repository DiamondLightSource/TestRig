#include "andor3.h"

TEST_F(AndorDriverTest, CanSetWidthBinning) {
    SetIntAndCheck("AOIWidth", 128);
}

TEST_F(AndorDriverTest, CanSetHeightBinning) {
    SetIntAndCheck("AOIHeight", 128);
}

TEST_F(AndorDriverTest, CanSetXBinning) {
    SetIntAndCheck("AOILeft", 128);
}

TEST_F(AndorDriverTest, CanSetYBinning) {
    SetIntAndCheck("AOITop", 128);
}

TEST_F(AndorDriverTest, CanSetPixelBinning) {
    SetEnumAndCheck("AOIBinning", "8x8");
}