using NUnit.Framework;
using OpenQA.Selenium;
using SharpSimpleSeleniumTest.Pages;
using System;


namespace SharpSimpleSeleniumTest.Tests
{
    class ExampleTest
    {
        IWebDriver WebDriver;
        ExampleMicrosoftPage msPage;
        [OneTimeSetUp]
        public void Init()
        {
            msPage = new ExampleMicrosoftPage();
        }
        [TestCase]
        public void TestMS()
        {
                //Validation for every Menu option available in the Microsoft home page. 
                msPage.OpenMSPage();
                Assert.IsTrue(msPage.CheckMS360Menu(), "The MS 365 Option is not displayed");
                Assert.IsTrue(msPage.CheckOfficeMenu(), "The MS Office Option is not displayed");
                Assert.IsTrue(msPage.CheckDealsMenu(), "The MS Deals Option is not displayed");
                Assert.IsTrue(msPage.CheckWindowsMenu(), "The MS Windows Option is not displayed");
                Assert.IsTrue(msPage.CheckSupportMenu(), "The MS Support Option is not displayed");
                Assert.IsTrue(msPage.CheckSurfaceMenu(), "The MS 365 is not displayed");
                Assert.IsTrue(msPage.CheckDealsMenu(), "The MS Deals Option is not displayed");
                Console.WriteLine("All the Menu Options are Present!!");
                msPage.GotoWindows();
                Assert.IsTrue(msPage.CheckWindows10SubMenu());
                msPage.ClickWindows10();
                msPage.PrintDropDownElements();
                Console.WriteLine("-----END OF TEST------");
        }
        [OneTimeTearDown]
        public void FinishTest()
        {
            msPage.Exit();
        }
    }
}
