package io.qase.app.util;

import io.netty.util.internal.StringUtil;
import io.qase.app.ui.util.PropertiesLoader;
import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "dataProviderForInvalidUserName")
    public Object[][] dataProviderForInvalidUserName() {
        return new Object[][]{
                {"abc", PropertiesLoader.loadProperties().getProperty("standard.password"),
                        "Please include an '@' in the email address. 'abc' is missing an '@'."},
                {"abc@.", PropertiesLoader.loadProperties().getProperty("standard.password"),
                        "'.' is used at a wrong position in '.'."},
                {"abc@", PropertiesLoader.loadProperties().getProperty("standard.password"),
                        "Please enter a part following '@'. 'abc@' is incomplete."},
                {StringUtil.EMPTY_STRING, PropertiesLoader.loadProperties().getProperty("standard.password"),
                        "Please fill in this field."},
                {"abc@@", PropertiesLoader.loadProperties().getProperty("standard.password"),
                        "A part following '@' should not contain the symbol '@'."}
        };
    }

    @DataProvider(name = "dataProviderForInvalidLogin")
    public Object[][] dataProviderForInvalidLogin() {
        return new Object[][]{
                {"abc@abc.com", PropertiesLoader.loadProperties().getProperty("standard.password"),
                        "These credentials do not match our records."},
                {PropertiesLoader.loadProperties().getProperty("standard.username"), "12345",
                        "These credentials do not match our records."}
        };
    }
}
