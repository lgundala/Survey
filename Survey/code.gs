var address = '';
var rootPwd = '';
var user = '';
var userPwd = '';
var db = 'testDB';

var root = 'root';
var instanceUrl = 'jdbc:mysql://' + address;
var dbUrl = instanceUrl + '/' + db;

// Create a new database within a Cloud SQL instance.
function createDatabase() {
  var url = 'jdbc:mysql://132.178.226.68:22:4964';
  var conn = Jdbc.getConnection(url ,'','');
  
  conn.createStatement().execute('CREATE DATABASE ' + db);
}

// Create a new user for your database with full privileges.
function createUser() {
  var conn = Jdbc.getConnection(dbUrl, root, rootPwd);

  var stmt = conn.prepareStatement('CREATE USER ? IDENTIFIED BY ?');
  stmt.setString(1, user);
  stmt.setString(2, userPwd);
  stmt.execute();

  conn.createStatement().execute('GRANT ALL ON `%`.* TO ' + user);
}

// Create a new table in the database.
function createTable() {
  var conn = Jdbc.getConnection(dbUrl, user, userPwd);
  conn.createStatement().execute('CREATE TABLE entries '
      + '(guestName VARCHAR(255), content VARCHAR(255), '
      + 'entryID INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(entryID));');
}

function writeOneRecord() {
  var conn = Jdbc.getConnection(dbUrl, user, userPwd);

  var stmt = conn.prepareStatement('INSERT INTO entries '
      + '(guestName, content) values (?, ?)');
  stmt.setString(1, 'First Guest');
  stmt.setString(2, 'Hello, world');
  stmt.execute();
}