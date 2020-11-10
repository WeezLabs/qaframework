from .base_page import BasePage
from selenium.webdriver.common.by import By


class BlogPage(BasePage):

    # Page url
    main_page_url = "https://distillery.com/blog"

    # Page script locator
    mouseflow_script = (By.CSS_SELECTOR,
                        "script[src='https://cdn.mouseflow.com/projects/a9bd6d6f-d15a-4b0f-ad0f-b674fe7f6e0c.js']")
