from main.pages.result import DuckDuckGoResultPage
from main.pages.search import SearchHelper


def test_basic_duckduckgo_search(browser):
    # Set up test case data
    phrase = 'panda'
    # Search for the phrase
    search_page = SearchHelper(browser)
    search_page.go_to_site()
    search_page.search(phrase)
    # Verify that results appear
    result_page = DuckDuckGoResultPage(browser)
    assert result_page.link_div_count() > 0
    assert result_page.phrase_result_count(phrase) > 0
    assert result_page.search_input_value() == phrase
