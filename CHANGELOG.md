# Changelog

## 0.2.5 - SNAPSHOT

* Allow the ClientSocketChannelFactory and ExecutionContext to be given at the connections instead of
 always using the driver provided ones - #38

## 0.2.4 - 2013-07-06

* Mysql driver fails for null TIMESTAMP
* Mysql driver fails for big strings (TEXT)
* Support (auto convert) Option in prepared stmt parameters - @magro - #30
* Allow database connections strings without port, username and password - @magro - #32
* Support 'postgres' protocol in heroku like db urls - @magro - #33
* Fix jdbc:postgresql url format to use 'user' instead 'username' - @magro - #35


## 0.2.3 - 2013-05-21

* Upgraded Netty to 3.6.6.Final
* Added support for `timestamp(n)` values on MySQL
* Updated docs to reference MySQL's 5.6 support for microseconds - @theon
* MySQL driver returns DateTime instead of LocalDateTime when in text protocol - #24

## 0.2.2 - 2013-05-18

* Implement MySQL support, should be able to execute common statements, prepared statements and login with password (#9)
* Concurrency problem for multiple queries - @fwbrasil - #18
* Support prepared statement with more than 64 characters on PostgreSQL - @fwbrasil - #16
* Do not accept returned connections to pool that aren't ready for query - @fwbrasil - #15
* Multiple executions of a prepared statement that doesn't return rows fail - @fwbrasil - #13
* Optimize match/cases to `@switch` (#10)
* Reimplement the PostgreSQLMD5Digest.java in Scala - (#8)

## 0.1.1 - 2013-04-30

* Query promises fulfilled before cleaning up the query promise cause the futures to either hang forever or fail with a
  "query already running" message (#2)
* Optimize MessageEncoder to use a match instead of a map (#3)
* MessageDecoder should validate sizes and correctly handle negative or too large messages (#4)
* Move generic pool classes to the com.github.mauricio.async.db package (#9)

## 0.1.0 - 2013-04-29

* First public release
