select
'' as CATALOG_NAME,
s.SCHEMANAME SCHEMA_NAME,
t.TABLENAME TABLE_NAME,
tr.CONGLOMERATENAME INDEX_NAME
  from
  SYS.SYSCONGLOMERATES tr
  inner join SYS.SYSTABLES t on tr.TABLEID = t.TABLEID
  inner join SYS.SYSSCHEMAS s on s.SCHEMAID = t.SCHEMAID
  where
  tr.ISINDEX='true'
  and s.SCHEMANAME Like ${schema?}
  and t.TABLENAME Like ${trigger?}
  and tr.CONGLOMERATENAME Like ${index?}