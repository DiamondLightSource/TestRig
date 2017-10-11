from testutils.hardware.malcolmtest import ProcessTestCase, DeviceTestCase
from test_acquire import TestAcquire
from test_exposure import TestExposure
from test_acquireperiod import TestAcquirePeriod
from test_imagemode import TestImageMode
from test_numimages import TestNumImages

ANDOR_DEFAULTS_SAVE = "ANDOR-DEFAULTS"


class AndorDetectorProcessTestCase(ProcessTestCase, DeviceTestCase, TestAcquire, TestExposure, TestAcquirePeriod, TestImageMode,
                                   TestNumImages):
    def setup_blocks(self, block_viewer):
        self._detector = block_viewer.block_view("ANDOR")
        self._camera = block_viewer.block_view("ANDOR:DRV")

    def save_state(self):
        self._detector.save(ANDOR_DEFAULTS_SAVE)

    def restore_state(self):
        # arrayCounter is a non-standard value as it is changed by the camera and not the user.
        # It's not treated as configurable by malcolm, so it isn't saved like other variables.
        # Jira: http://jira.diamond.ac.uk/browse/BC-505
        self._camera.arrayCounter.put_value(0)
        self._detector.design.put_value(ANDOR_DEFAULTS_SAVE)
