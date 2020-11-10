from .base_page import BasePage
from selenium.webdriver.common.by import By
from pages.blog_page import BlogPage


class MainPage(BasePage):
    # Page url
    main_page_url = "https://distillery.com/"

    # Page Locators
    header_locator = (By.CSS_SELECTOR, ".jumbotron-home__header")
    learn_about_us_button = (By.CSS_SELECTOR, "#focus-features-btn [href='https://distillery.com/about/']")
    blog_button = (By.CSS_SELECTOR, ".navigation-menu__container [data-number='7']")

    # Page info
    main_page_header_text = "DISTILLERY IS A\nFULL−SERVICE SOFTWARE\nDESIGN AND DEVELOPMENT COMPANY"

    def __init__(self, *args, **kwargs):
        super(MainPage, self).__init__(*args, **kwargs)

    def open(self):
        self.navigate_to(self.main_page_url)
        return self

    def open_blog(self):
        self.get_element(self.blog_button).click()
        return BlogPage(self.driver)
