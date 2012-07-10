java -version

SET DERBY_HOME=derby
SET derby.system.home=../runtime/derby
SET DERBY_OPTS=-Dderby.system.home=../runtime/derby

CALL %DERBY_HOME%\ij.bat db_scripts/Derby_DDL.sql
CALL %DERBY_HOME%\ij.bat db_scripts/Derby_DML_MasterData.sql
