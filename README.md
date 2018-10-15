# grdb
Dummy DB tester CLI app on the JVM.  
Just a siple script which downloads and uses the DB driver of your choice, runs your Groovy [script](http://docs.groovy-lang.org/latest/html/documentation/#_interacting_with_a_sql_database) (see: [Sql JavaDoc](http://docs.groovy-lang.org/latest/html/api/groovy/sql/Sql.html)) and prints out the result in JSON.

[![asciicast](https://asciinema.org/a/206545.png)](https://asciinema.org/a/206545)

# Command Examples

```
./grdb.groovy --dependency mysql:mysql-connector-java:8.+ --driver com.mysql.cj.jdbc.Driver --url jdbc:mysql://localhost:3306/testDB --username test --password secret
```
```
./grdb.groovy --dependency mysql:mysql-connector-java:8.+ --driver com.mysql.cj.jdbc.Driver --url jdbc:mysql://localhost:3306/testDB --username test --password secret "sql.rows 'SELECT * FROM testTable'"
```
```
./grdb.groovy --dependency net.sourceforge.jtds:jtds:1.+ --driver net.sourceforge.jtds.jdbc.Driver --url jdbc:jtds:sqlserver://localhost:1433/testDB --username test --password secret "sql.rows 'SELECT * FROM testTable'"
```

# Query Examples

```
sql.rows 'select * from table'
sql.firstRow 'select * from table'
sql.execute 'insert into table (name) values ("John Doe")'
```
