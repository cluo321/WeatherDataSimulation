# WeatherDataSimulation

# All the following operations should be conducted at the directory where build.sbt locates. 
# For this program, the location should be under WeatherDataSimulation directory.  

# compile

sbt compile

# test

sbt test

# run Simulation
# [arg0]: the number of simulated weather data. arg0 should be between 1 and 15 if non-duplicate cases are desired.
# [arg1]: the start date for simulated weather. Format: YYYY/MM/DD.
# [arg2]: the end date for simulated weather. Format: YYYY/MM/DD.
# For example, sbt "run 10 2016/10/1 2016/11/10"

sbt "run [arg0] [arg1] [arg2]"  

