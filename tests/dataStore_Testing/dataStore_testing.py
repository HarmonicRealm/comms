import unittest, sqlite3
import system.dataStore
import webserver.main


class database(unittest.TestCase):
    def test_databaseLoad(self):
        connection = sqlite3.connect('./location.db')
        self.assertTrue(connection)
        connection.close()


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
        db = sqlite3.connect('./location.db')
        # Create a cursor to navigate database
        cursor = db.cursor()
        # Count number of rows before adding
        preRows = cursor.rowcount;

        # Insert a false entry
        a = -1
        b = 0
        c = 30
        d = 1
        e = 300
        #sqlite3_query = "INSERT INTO 'location_values' ('location_id', 'tdate', 'ttime', 'tph', 'ttemperature', 'tturbidity', 'tdepth') VALUES ({}, date('now'), time('now'), {}, {}, {}, {});".format("temp", "temp", "temp", "temp", "temp")
        sqlite3_query = "INSERT INTO 'location_values' ('location_id', 'tdate', 'ttime', 'tph', 'ttemperature', 'tturbidity', 'tdepth') VALUES ({}, date('now'), time('now'), {}, {}, {}, {});".format(
            a, b, c, d, e)

        # Put into database
        cursor.execute(sqlite3_query)
        db.commit()

        # Gets the ID of the committed entry, is value None if entry unsuccessful
        self.assertTrue(cursor.lastrowid != None)
        # Close cursor and database
        cursor.close()
        db.close()


    def test_dataBaseRemove(self):
        db = sqlite3.connect('./location.db')
        cursor = db.cursor()

        sqlite3_delete = "DELETE from location_values where location_id = -1"
        cursor.execute(sqlite3_delete)
        db.commit()

        self.assertTrue(cursor.lastrowid != None)
        cursor.close()
        db.close()

if __name__ == '__main__':
    unittest.main()
    sqlite3.connect('./location.db')
