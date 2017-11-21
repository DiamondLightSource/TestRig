DEFAULT_ACQUISITION_START_TIMEOUT = 20


class TestAcquireReadout:
    def test_not_acquiring_by_default(self):
        self.assert_camera_is_not_acquiring()

    #def test_acquiring_after_start_command_is_given_in_continuous_mode(self):
    #    self._camera.imageMode.put_value("Continuous")
    #    self._camera.start()
    #    self.assert_camera_starts_acquiring()

    def assert_camera_starts_acquiring(self, timeout=DEFAULT_ACQUISITION_START_TIMEOUT):
        self._camera.when_value_matches("acquiring", True, timeout=timeout)

    def assert_camera_is_not_acquiring(self):
        self.assertFalse(self._camera.acquiring.value)
