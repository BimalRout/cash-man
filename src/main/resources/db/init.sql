---- start server
-- mysqld -u root

---- connect as root
-- mysql -u root

---- change root password
-- set password for 'root'@'localhost' = password('newpwd');
-- set password for 'root'@'127.0.0.1' = password('newpwd');
-- set password for 'root'@'::1' = password('newpwd');

---- create db
create database if not exists simpleatm character set utf8 collate utf8_general_ci;

---- create and grant user
create user 'simple'@'localhost' identified by 'simple27';
create user 'simple'@'%' identified by 'simple27';
grant all on simpleatm.* to 'simple'@'localhost';
grant all on simpleatm.* to 'simple'@'%';

---- select db
-- use simpleatm;