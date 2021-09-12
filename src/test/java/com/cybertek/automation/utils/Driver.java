package com.cybertek.automation.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Driver {
    private static AppiumDriver<MobileElement> driver;
    private static URL url;

    private Driver() {
    }

    public static AppiumDriver<MobileElement> getDriver() {
        String platform = ConfigurationReader.getProperty("platform");
        if (Objects.isNull(driver)) {
            switch (platform) {
                case "android":
                    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                    desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.0");
                    desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_2");
                    desiredCapabilities.setCapability(MobileCapabilityType.APP, "https://cybertek-appium.s3.amazonaws.com/etsy.apk");
                    try {
                        url = new URL("http://localhost:4723/wd/hub");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    driver = new AndroidDriver<>(url, desiredCapabilities);
                    break;
                case "android-remote":
                    DesiredCapabilities caps = new DesiredCapabilities();

                    // Set your access credentials
                    caps.setCapability("browserstack.user", "ct_IM5lkP");
                    caps.setCapability("browserstack.key", "MxbBDpfoqdW54Tx73qfq");

                    // Set URL of the application under test
                    caps.setCapability("app", "bs://5bdf45a351b55e761af6e6dbbd5804ef0a6d4469");

                    // Specify device and os_version for testing
                    caps.setCapability("device", "OnePlus 8");
                    caps.setCapability("os_version", "10.0");
                    caps.setCapability("realMobile", "true");

                    // Set other BrowserStack capabilities
                    caps.setCapability("project", "My test appium automation");
                    caps.setCapability("build", "Java Android");
                    caps.setCapability("name", "Regression");

                    // Initialise the remote Webdriver using BrowserStack remote URL
                    // and desired capabilities defined above
                    try {
                        driver = new AndroidDriver<>(new URL("http://hub.browserstack.com/wd/hub"), caps);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        return driver;
    }

    public static void closeDriver() {
        if (Objects.nonNull(driver)) {
            driver.closeApp();
            driver = null;
        }
    }
}
