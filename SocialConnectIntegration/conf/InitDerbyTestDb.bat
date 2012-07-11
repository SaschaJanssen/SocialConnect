@echo off

SET DERBY_HOME=conf\derby
SET derby.system.home=target\runtime\derby
SET DERBY_OPTS=-Dderby.system.home=target\runtime\derby


CALL %DERBY_HOME%\ij.bat conf\db_scripts\Derby_DDL.sql
CALL %DERBY_HOME%\ij.bat conf\db_scripts\Derby_DML_MasterData.sql
CALL %DERBY_HOME%\ij.bat conf\db_scripts\Derby_DML_DemoData.sql
