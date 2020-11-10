import pytest
import constants
import settings
from selenium import webdriver
from selenium.webdriver import ChromeOptions
from selenium.webdriver import FirefoxOptions


def pytest_addoption(parser):
    parser.addoption("--browser", action="store", default="chrome")
    parser.addoption("--browser_ver", action="store", default="")
    parser.addoption("--headless", action="store", default=False)
    parser.addoption("--remote", action="store", default=False)
    parser.addoption("--language", action="store", default="en")


@pytest.fixture()
def config(request):
    browser = request.config.getoption("--browser")
    version = request.config.getoption("--browser_ver")
    headless = False
    remote = False
    if request.config.getoption("--headless"):
        headless = True
    if request.config.getoption("--remote"):
        remote = True

    return {"remote": remote,
            "version": version,
            "browser": browser,
            "headless": headless,
            }


def create_remote_driver():
    capabilities = {"browserName": "chrome",
                    "enableVNC": True,
                    "enableVideo": False}
    return webdriver.Remote(command_executor=settings.REMOTE_SERVER_ADDRESS + "wd/hub",
                            options=ChromeOptions(),
                            desired_capabilities=capabilities)


def create_local_driver(config):
    driver = None
    if config["browser"] == "chrome":
        options = ChromeOptions()
        driver = webdriver.Chrome(options=options)
    elif config["browser"] == "firefox":
        options = FirefoxOptions()
        driver = webdriver.Firefox(options=options)
    return driver


@pytest.fixture()
def driver(request, config):
    driver = None
    if config["remote"]:
        driver = create_remote_driver()
        driver.maximize_window()
    else:
        driver = create_local_driver(config)
        driver.maximize_window()

    driver.set_page_load_timeout(constants.Timeouts.EXTREME_TIMEOUT)
    driver.implicitly_wait(constants.Timeouts.SMALL_TIMEOUT)

    def tear_down():
        driver.quit()

    request.addfinalizer(tear_down)

    yield driver
    driver.quit()
