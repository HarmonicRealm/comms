import dataCollectortesting, json, unittest

totalErrors = 0;
# Open a premade text file to read from to automatically run tests
filepath = 'ErrorTesting.txt'

try:
    fp = open(filepath, "r")
except OSError:
    print("Could not open the file ", filepath)
    exit()
except:  # handling all other unknown errors for future itterations of code
    print("Unknown error occured")
    exit()

with fp:
    # Read the first line of the text file
    line = fp.readline()
    while line:
        # Change the input value for different error cases
        dataCollectortesting.collected_values = json.loads(line)
        # Load tests from dataCollectesting.py
        suite = unittest.defaultTestLoader.loadTestsFromModule(dataCollectortesting)
        # Run tests
        test = unittest.TextTestRunner(verbosity=3).run(suite)
        # Count the number of errors found in running a line of the file
        # and add them to the total number of errors
        totalErrors += test.failures.__len__()
        # Read the next line of the text file
        line = fp.readline()

# When empty, close the file
fp.close()
# Print total number of errors, vs expected number of errors
print("The total number of errors found in the system is: \n", totalErrors)
print("The number of errors expected to occur is: \n", 27)
print(("Update"))