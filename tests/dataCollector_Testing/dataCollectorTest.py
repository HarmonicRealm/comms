import dataCollectortesting, json, unittest

totalErrors = 0
# Open a premade text file to read from to automatically run tests
filepath = 'ErrorTesting.txt'
try:
    fp = open(filepath)
except:
    OSError
print("The file does not exist")

# Read the first line
line = fp.readline()
while line:
    # Change the input value for different error cases
    dataCollectortesting.collected_values = json.loads(line)
    # Load tests from dataCollectesting.py
    suite = unittest.defaultTestLoader.loadTestsFromModule(dataCollectortesting)
    # Run tests
    test = unittest.TextTestRunner(verbosity=3).run(suite)
    totalErrors += test.errors.__len__()
    # Read next line
    line = fp.readline()

print("The total number of errors found running the test suite is: " + totalErrors)
print("The number of errors expected to occur from running this is: 27")