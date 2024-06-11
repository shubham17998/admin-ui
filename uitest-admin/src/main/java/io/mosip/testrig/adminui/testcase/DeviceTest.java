package io.mosip.testrig.adminui.testcase;


import org.openqa.selenium.By;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import io.mosip.testrig.adminui.kernel.util.ConfigManager;
import io.mosip.testrig.adminui.utility.BaseClass;
import io.mosip.testrig.adminui.utility.Commons;

public class DeviceTest extends BaseClass{
   @Test(groups = "D")
  public void deviceCRUD() throws InterruptedException {
	   String validityDate;
	try {
		validityDate = ConfigManager.getvalidityDate();
		 test=extent.createTest("DeviceTest", "verify Login");
	   Commons.click(test,driver,By.id("admin/resources"));
	   Commons.click(test,driver,By.id("/admin/resources/devices"));
	   Commons.click(test,driver,By.id("Create Device"));
	   test.log(Status.INFO, "Click on Create Device");
	   
	   Commons.enter(test,driver, By.id("name"),data);
	   Commons.enter(test,driver, By.id("serialNumber"),data);

	   Commons.enter(test,driver, By.id("macAddress"),"1.1234");
	   test.log(Status.INFO, "Enters MACAddress");
 
	   Commons.enter(test,driver, By.id("ipAddress"),"2.2345");
	//   Commons.enter(test,driver, By.id("validity"),validityDate);
      Commons.calendar(validityDate);
    Commons.dropdown(test,driver,By.id("deviceSpecId"));
    // Commons.dropdown(test,driver,By.id("zone"));
      Commons.dropdown(test,driver,By.id("regCenterId"));
      

      	Commons.createRes(test,driver);
      	test.log(Status.INFO, "Click on Create");
     	Commons.filter(test,driver, By.id("name"), data);
     	

     	Commons.editRes(test,driver,data+1,By.id("name"));
     	Commons.filter(test,driver, By.id("name"), data+1);
     	
     	Commons.activate(test,driver);
     	test.log(Status.INFO, "Click on Activate");
     	Commons.editRes(test,driver,data+2,By.id("name"));
     	Commons.filter(test,driver, By.id("name"), data+2);
     	Commons.deactivate(test,driver);
     	Commons.decommission(test,driver);
     	test.log(Status.INFO, "Click on Decommission");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     	
    }
  }
