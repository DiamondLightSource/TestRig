from testutils.hardware.malcolmtest import MalcolmTestCase, make_block_factory_from_connection
from test_acquire import TestAcquire
from test_exposure import TestExposure
from test_acquireperiod import TestAcquirePeriod
from test_imagemode import TestImageMode
from test_numimages import TestNumImages

ANDOR_DEFAULTS_SAVE = "ANDOR-DEFAULTS"


class AndorDetectorTestCase(TestAcquire, TestExposure, TestAcquirePeriod, TestImageMode,
                            TestNumImages):
    @classmethod
    def set_up_blocks(cls):
        block_factory = make_block_factory_from_connection(cls._malcolm)
        block_factory.load_yaml("config/hardware-control-config/bl99p-ea-det-01.yaml")
        cls._detector = block_factory.block_view("ANDOR")
        cls._camera = block_factory.block_view("ANDOR:DRV")

    @classmethod
    def save_state(cls):
        cls._detector.save(ANDOR_DEFAULTS_SAVE)

    @classmethod
    def restore_state(cls):
        # arrayCounter is a non-standard value as it is changed by the camera and not the user.
        # It's not treated as configurable by malcolm, so it isn't saved like other variables.
        # Jira: http://jira.diamond.ac.uk/browse/BC-505
        cls._camera.arrayCounter.put_value(0)
        cls._detector.design.put_value(ANDOR_DEFAULTS_SAVE)


