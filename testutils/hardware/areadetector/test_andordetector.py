from testutils.hardware.malcolmtest import ProcessTestCase, DeviceTestCase
from testutils.hardware.areadetector.test_imageacquire import TestFixedImageAcquire
from testutils.hardware.areadetector.test_exposure import TestExposure
from testutils.hardware.areadetector.test_acquireperiod import TestAcquirePeriod
from testutils.hardware.areadetector.test_imagemode import TestImageMode
from testutils.hardware.areadetector.test_numimages import TestNumImages
from testutils.hardware.areadetector.test_triggermode import TestTriggerMode
from testutils.hardware.areadetector.test_acquirereadout import TestAcquireReadout

ANDOR_DEFAULTS_SAVE = "ANDOR-DEFAULTS"


class AndorDetectorProcessTestCase(ProcessTestCase, DeviceTestCase, TestExposure, TestAcquirePeriod, TestImageMode,
                                   TestTriggerMode, TestNumImages, TestAcquireReadout, TestFixedImageAcquire):
    def setup_blocks(self, block_viewer):
        self._detector = block_viewer.block_view("ANDOR")
        self._camera = block_viewer.block_view("ANDOR:DRV")

    def save_state(self):
        self._detector.save(ANDOR_DEFAULTS_SAVE)

    def restore_state(self):
        self._camera.stop()
        # arrayCounter is a non-standard value as it is changed by the camera and not the user.
        # It's not treated as configurable by malcolm, so it isn't saved like other variables.
        # Jira: http://jira.diamond.ac.uk/browse/BC-505
        self._camera.arrayCounter.put_value(0)
        self._detector.design.put_value(ANDOR_DEFAULTS_SAVE)
