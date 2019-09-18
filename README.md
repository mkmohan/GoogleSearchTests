### Google Search Automation Tests

#### Tools Used
- Java8
- Selenium
- Webdrivermanager
- javax.mail
- Junit
- Cucumber
- Maven
- AssertJ
- Log4j2 with SL4J

#### How to run tests

##### To run tests on Chrome
- mvn clean test
- mvn clean test -DtimeOut=40 (To use 40 sec timeout)

##### To run tests on IE
- mvn clean test -Dbrowser=ie

#### Test reports
- Test reports can be found at: {ProjectHome}\target\reports\html\index.html

#### Known issues
- Not tested on IE because of issues with IE11 in PC. 
- All tests passed on Chrome (Version 77.0.3865.75)
- Sample test report for Chrome is saved to: {ProjectHome}\test_report(chrome).png

