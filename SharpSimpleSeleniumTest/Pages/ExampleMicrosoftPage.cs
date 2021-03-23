using OpenQA.Selenium;
using SharpSimpleSeleniumTest.Base;
using System;
using System.Collections.Generic;
using SeleniumExtras.PageObjects;

namespace SharpSimpleSeleniumTest.Pages
{
    class ExampleMicrosoftPage
    {
        public IWebDriver Driver;

        private readonly SeleniumWrapper sW = new SeleniumWrapper();

        public WebDriverFactory driverFactory = new WebDriverFactory();

        private readonly string URL = "https://www.microsoft.com/en-us/";

        public ExampleMicrosoftPage()
        {
            this.Driver = driverFactory.ChromeDriverConnection();
            sW.Driver = this.Driver;
            PageFactory.InitElements(this.Driver, this);
        }
        #region [Locators] 

        [FindsBy(How = How.Id, Using = "shellmenu_0")]
        [CacheLookup]
        private readonly IWebElement ms360MenuOption;

        [FindsBy(How = How.Id, Using = "shellmenu_1")]
        [CacheLookup]
        private readonly IWebElement msOfficeMenuOption;

        [FindsBy(How = How.Id, Using = "shellmenu_2")]
        [CacheLookup]
        private readonly IWebElement msWindowsMenuOption;

        [FindsBy(How = How.Id, Using = "shellmenu_3")]
        [CacheLookup]
        private readonly IWebElement msSurfaceOption;

        [FindsBy(How = How.Id, Using = "shellmenu_5")]
        [CacheLookup]
        private readonly IWebElement msDealsOption;

        [FindsBy(How = How.Id, Using = "l1_support")]
        [CacheLookup]
        private readonly IWebElement msSupportOption;

        [FindsBy(How = How.Id, Using = "c-shellmenu_54")]
        [CacheLookup]
        private readonly IWebElement msWindows10Option;

        [FindsBy(How = How.Id, Using = "uhf-g-nav")]
        [CacheLookup]
        private readonly IWebElement msWindows10DropDown;
       
        #endregion

        #region [Methods]
        public void OpenMSPage()
        {
            sW.Visit(URL);
            sW.MaxWindow();
            sW.WaitImplicit(6);
        }
        public bool CheckMS360Menu()
        {
            sW.WaitExplicit(ms360MenuOption);
            return sW.IsDispalyed(ms360MenuOption);
        }
        public bool CheckOfficeMenu()
        {
            sW.WaitExplicit(msOfficeMenuOption);
            return sW.IsDispalyed(msOfficeMenuOption);
        }
        public bool CheckWindowsMenu()
        {
            sW.WaitExplicit(msWindowsMenuOption);
            return sW.IsDispalyed(msWindowsMenuOption);
        }
        public bool CheckSurfaceMenu()
        {
            sW.WaitExplicit(msSurfaceOption);
            return sW.IsDispalyed(msSurfaceOption);
        }
        public bool CheckDealsMenu()
        {
            sW.WaitExplicit(msDealsOption);
            return sW.IsDispalyed(msDealsOption);
        }
        public bool CheckSupportMenu()
        {
            sW.WaitExplicit(msSupportOption);
            return sW.IsDispalyed(msSupportOption);
        }

        public void GotoWindows()
        {
            sW.Click(msWindowsMenuOption);
        }
        public bool CheckWindows10SubMenu()
        {
            sW.WaitExplicit(msWindows10Option);
            return sW.IsDispalyed(msWindows10Option);
        }

        public void ClickWindows10()
        {
            sW.Click(msWindows10Option);
        }

        public void PrintDropDownElements()
        {
            sW.WaitImplicit(2);
            Console.WriteLine("-----Windows10 menu options-----");
            if (sW.IsDispalyed(msWindows10DropDown))
            {
                IReadOnlyCollection<IWebElement> options = msWindows10DropDown.FindElements(By.TagName("a"));
                foreach (IWebElement option in options)
                {
                    if (option.Text.Length > 0)
                    {
                        Console.WriteLine(option.Text);
                    }
                }
            }
        }

        public void Exit()
        {
            sW.Close();
        }
        #endregion
    }
}
