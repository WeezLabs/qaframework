
def assert_element_displayed(element):
    assert element.is_displayed()


def assert_text(actual_text, expected_text):
    assert actual_text.strip().lower() == expected_text.strip().lower(), \
        "Expected is :{}, but actual was :{}".format(expected_text, actual_text)


def assert_script_presented(script):
    assert script, "Element isn't presented on the Page"
