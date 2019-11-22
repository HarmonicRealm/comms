import unittest, sqlite3
import system.dataStore


class database(unittest.TestCase):
    def test_databaseLoad(self):
        self.assertTrue(sqlite3.connect('./location.db'))

    def test_databaseWrite(self):
        '''
        In order:
        Connect to database
        Count current # of rows
        Insert a false entry
        Count rows
        Assert rows += 1
        *Will remove data in function call right after*
        '''
        # Connect to Database
        db = sqlite3.connect('./location.db');
        # Count current number of rows
        cursor = db.cursor()
        preRows = cursor.rowcount;

        # Insert a false entry
        a = "temp"
        b = 0
        c = 30
        d = 1
        e = 300
        #sqlite3_query = "INSERT INTO 'location_values' ('location_id', 'tdate', 'ttime', 'tph', 'ttemperature', 'tturbidity', 'tdepth') VALUES ({}, date('now'), time('now'), {}, {}, {}, {});".format("temp", "temp", "temp", "temp", "temp")
        sqlite3_query = "INSERT INTO 'location_values' ('location_id', 'tdate', 'ttime', 'tph', 'ttemperature', 'tturbidity', 'tdepth') VALUES ({}, date('now'), time('now'), {}, {}, {}, {});".format(
            a, b, c, d, e)

        cursor.execute(sqlite3_query)
        db.commit()

        postRows = cursor.rowcount;

        self.assertTrue(preRows + 1 == postRows)



        ''''
       def test_databaseWrite(self):
   
           Count rows
           Insert
           Counts Rows
           Delete
       
           # Insert values
           sqlite3.SQLITE_DELETE
       '''
if __name__ == '__main__':
    unittest.main()
    sqlite3.connect('./location.db')
