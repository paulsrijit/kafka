@echo off
setlocal enabledelayedexpansion

REM Kafka CLI and topic configuration
set KAFKA_HOME=c:\kafka-standalone
set TOPIC=agent
set FILE_PATH=agent-data.txt

REM Infinite loop to continuously read the file
:loop
for /f "delims=" %%A in (%FILE_PATH%) do (
    REM Publish the line to the Kafka topic
    echo %%A | "%KAFKA_HOME%\bin\windows\kafka-console-producer.bat" --broker-list localhost:9092 --topic %TOPIC%
    echo Published: %%A
    REM Wait for 1 second
    timeout /t 1 >nul
)

REM Repeat the loop
goto loop