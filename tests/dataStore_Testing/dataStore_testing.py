import unittest, sqlite3
import system.dataStore

class database(unittest.TestCase):
    def test_databaseLoad(self, database):
        self.assertTrue(sqlite3.connect(database))

    def test_databaseWrite(self):
        '''
        Count rows
        Insert
        Counts Rows
        Delete
        '''
        # Insert values
        sqlite3.SQLITE_DELETE

        self.assertTrue()

    def test_foobar(self):
        self.assertTrue(sqlite3.connect('/location.db').

if __name__ == "__main__":
    unittest.main()
