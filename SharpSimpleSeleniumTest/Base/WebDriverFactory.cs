using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;


namespace SharpSimpleSeleniumTest.Base
{
    class WebDriverFactory
    {
        public IWebDriver Driver { get; set; }
        //we can modify this class in the future for crossbrowser testing
        public IWebDriver ChromeDriverConnection()
        {
            this.Driver = new ChromeDriver();
            return Driver;
        }
    }
}
