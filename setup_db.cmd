@echo off

set PGUSER=admin
set PGPASSWORD=admin

cd C:/opt/pgsql/bin

initdb.exe -D ^"C^:^\opt^\pgsql^\data^" -U admin -W -E UTF8 -A scram-sha-256
pg_ctl.exe -D ^"C^:^\opt^\pgsql^\data^" -l ^"C^:^\opt^\pgsql-data^\logs^" start
createdb.exe --encoding=UTF8 -U admin minegocio
createdb.exe --encoding=UTF8 -U admin minegocio-test
