import unittest

from malcolmtest import MalcolmTestCase, make_block_factory_from_connection

TIMEOUT_A_FEW_FRAMES = 5
ANDOR_DEFAULTS_SAVE = "ANDOR-DEFAULTS"


class AndorDetectorTestCase(MalcolmTestCase):
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

    def test_set_exposure_to_zero(self):
        self.do_set_exposure_check(0)

    def test_set_exposure_to_positive(self):
        self.do_set_exposure_check(0.1)

    def test_set_exposure_to_negative(self):
        self.do_set_exposure(0, -0.1)

    @unittest.skip(
        "Skipping this due to a known bug. Jira:http://jira.diamond.ac.uk/browse/P99-7?jql=project%20%3D%20P99")
    def test_set_acquisition_period_to_zero(self):
        self.do_set_acquisition_period_and_exposure(0.5, 0.5, 0)

    @unittest.skip(
        "Skipping this due to a known bug. Jira:http://jira.diamond.ac.uk/browse/P99-7?jql=project%20%3D%20P99")
    def test_set_acquisition_period_to_negative(self):
        self.do_set_acquisition_period_and_exposure(0.5, 0.5, -0.5)

    def test_set_acquisition_period_greater_than_exposure_limit(self):
        self.do_set_acquisition_period_and_exposure(0.5, 0.51, 0.6)

    def test_set_acquisition_period_within_exposure_limit(self):
        self.do_set_acquisition_period_and_exposure(0.5, 0.501, 0.501)

    def test_set_acquisition_period_less_than_exposure(self):
        self.do_set_acquisition_period_and_exposure(0.5, 0.5, 0.4)

    def test_set_num_images_less_than_zero(self):
        self.do_set_num_images(-1, -1)

    def test_set_num_images_to_zero(self):
        self.do_set_num_images(0, 0)

    def test_set_num_images_to_more_than_zero(self):
        self.do_set_num_images(1, 1)

    def test_acquire_zero_images(self):
        self.do_set_num_images(0, 0)
        self.do_acquire_frames(1, TIMEOUT_A_FEW_FRAMES)

    def test_acquire_negative_images(self):
        self.do_set_num_images(-1, -1)
        self.do_acquire_frames(1, TIMEOUT_A_FEW_FRAMES)

    def test_acquire_one_image(self):
        self.do_set_num_images(0, 0)
        self.do_acquire_frames(1, TIMEOUT_A_FEW_FRAMES)

    def test_acquire_multiple_images(self):
        self.do_set_num_images(2, 2)
        self.do_acquire_frames(2, TIMEOUT_A_FEW_FRAMES)

    def do_acquire_frames(self, expected_num_frames, timeout):
        self._camera.start()
        self.assert_array_counter_wait(expected_num_frames, timeout)
        self._camera.stop()

    def do_set_num_images(self, expected, actual):
        self._camera.numImages.put_value(actual)
        self.assertEqual(expected, self._camera.numImages.value)

    def do_set_acquisition_period_and_exposure(self, exposure, expected_acquisition_period, actual_acquisition_period):
        self.do_set_exposure_check(exposure)
        self.do_set_acquisition_period(expected_acquisition_period, actual_acquisition_period)

    def do_set_acquisition_period(self, expected, actual):
        self._camera.acquirePeriod.put_value(actual)
        self.assert_acquisition_period(expected)

    def do_set_exposure_check(self, value):
        self.do_set_exposure(value, value)

    def do_set_exposure(self, expected, value):
        self._camera.exposure.put_value(value)
        self.assert_exposure(expected)

    def assert_acquisition_period(self, expected):
        self.assert_almost_equal(expected, self._camera.acquirePeriod.value)

    def assert_exposure(self, expected):
        self.assert_almost_equal(expected, self._camera.exposure.value)

    def assert_array_counter_wait(self, expected_num_frames, timeout):
        self._camera.when_value_matches("arrayCounter", expected_num_frames, timeout=timeout)
        self.assert_array_counter(expected_num_frames)

    def assert_array_counter(self, expected):
        self.assertEqual(expected, self._camera.arrayCounter.value)
