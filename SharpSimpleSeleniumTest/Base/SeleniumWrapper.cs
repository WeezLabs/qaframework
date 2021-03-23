using OpenQA.Selenium;
using OpenQA.Selenium.Interactions;
using OpenQA.Selenium.Support.UI;
using System;


namespace SharpSimpleSeleniumTest.Base
{
    /// <summary>
    /// This class implements all the methods needed for testing a simple page
    /// /// </summary>
    class SeleniumWrapper
    {
        public IWebDriver Driver { get; set; }

        public void Visit(string URL)
        {
            Driver.Navigate().GoToUrl(URL);
        }

        public void Close()
        {
            Driver.Quit();
        }

        public void Type(string text, IWebElement locator)
        {
            locator.SendKeys(text);
        }

        public void Click(IWebElement locator)
        {
            locator.Click();
        }

        public void WaitExplicit(IWebElement locator)
        {
            WebDriverWait wait = new WebDriverWait(Driver, TimeSpan.FromSeconds(5));
            wait.Until(driver => locator.Displayed && locator.Enabled);
        }

        public bool IsDispalyed(IWebElement locator)
        {
            try
            {
                return locator.Displayed;
            }
            catch
            {
                return false;
            }
        }
        public string GetText(IWebElement locator)
        {
            return locator.Text;
        }

        public void WaitImplicit(int seconds)
        {
            Driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(seconds);
        }

        public void MaxWindow()
        {
            Driver.Manage().Window.Maximize();

        }

        public void ClickPerform(IWebElement locator)
        {
            Actions actions = new Actions(Driver);
            actions.MoveToElement(locator).Click().Perform();
        }
    }
}

