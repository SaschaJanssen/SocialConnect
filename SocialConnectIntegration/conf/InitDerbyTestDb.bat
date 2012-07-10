java -version

SET DERBY_HOME=derby
SET derby.system.home=../target/runtime/derby
SET DERBY_OPTS=-Dderby.system.home=../target/runtime/derby

CALL %DERBY_HOME%\ij.bat db_scripts/Derby_DDL.sql
CALL %DERBY_HOME%\ij.bat db_scripts/Derby_DML_MasterData.sql
CALL %DERBY_HOME%\ij.bat db_scripts/Derby_DML_DemoData.sql
