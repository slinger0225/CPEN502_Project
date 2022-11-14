# CPEN502-Projects

*Oct 2022*

This is the collaborative project of UBC CPEN 502 Achitecture for Learning System

## Author

Jiahe Liu 

Linger Shen

## How to run

- [Open the existing maven project](https://www.jetbrains.com/help/idea/maven-support.html#maven_import_project_start)
- [Create Test case with JUnit](https://www.jetbrains.com/help/idea/junit.html)

#### Run with robocode

- Load all the dependencies in maven pom.xml

- Run robocode with your IDE

  - Set the VM option 

    ```
    -DNOSECURITY = true -Dsun.io.useCanonCaches=false 
    ```

- Add this line to *$your_robocode_directory/config/robocode.properties* , to prevent partial logging to file.  If you cannot, or can only save part of your data to local files, it may be because the total size of your saved files exceeds the default disk space quota (200, 000) by Robocode. Here is a temporary solution that sets the default filesystem quota for robots:

  ```
  robocode.robot.filesystem.quota=2000000
  ```

  [Reference](https://piazza.com/class/l6490p6pqyu637/post/76)

- Compile your java code, with 

  ```
  mvn clean
  mvn compile
  mvn package
  ```

  Remember to recompile your code everytime you modify something.

- After the robocode opens, select "Options --> Preferences --> Developer options"

  - Add your robot class path to the dialog,

    e.g. mine is, 

    ```
    $CPEN502_Project/target/classes
    ```

- Start Battle

  In robocode, 

  - select "Battle --> New", 
  - cmd + R to refresh (remember to refresh, sometime it might not appear at first)
  - Select your robot and "sample.MyFirstRobot",  then Click "Next"

  - Number of rounds = 100000
  - Click "Start Battle"
  - Then you can get back to sleep 😇 Good Luck with your robots (ps. It takes me 1.5h+ to run 100000 rounds)
  
- Draw graphs

  - The output log will be in *$CPEN502_Project/target/classes/robotRunnerLUT.data*

  - You can move those output logs to *$CPEN502_Project/outputs*/robotRunnerLUT.data

    (Since everytime you "mvn clean", the target directory will be deleted)

  - Add your code in *$CPEN502_Project/outputs/robotRunnerLUT.data/drawFig.py* to draw a graph

