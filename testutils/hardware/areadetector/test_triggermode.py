class TestTriggerMode:
    def test_cannot_set_trigger_mode_to_empty_string(self):
        self.assert_set_trigger_mode_raises_malcolm_response_error("")

    def test_cannot_set_trigger_mode_to_invalid_string(self):
        self.assert_set_trigger_mode_raises_malcolm_response_error("a")

    def test_can_set_trigger_mode_to_internal(self):
        self.assert_set_trigger_mode_sets_trigger_mode("Internal")

    def test_can_set_trigger_mode_to_external(self):
        self.assert_set_trigger_mode_sets_trigger_mode("External")

    def assert_set_trigger_mode_raises_malcolm_response_error(self, demand_value):
        trigger_mode = self._camera.triggerMode
        self.assert_set_attribute_raises_malcolm_response_error(trigger_mode, demand_value)

    def assert_set_trigger_mode_sets_trigger_mode(self, expected_readback_value, demand_value=None):
        trigger_mode = self._camera.triggerMode
        self.assert_set_attribute_sets_attribute(trigger_mode, expected_readback_value, demand_value)
