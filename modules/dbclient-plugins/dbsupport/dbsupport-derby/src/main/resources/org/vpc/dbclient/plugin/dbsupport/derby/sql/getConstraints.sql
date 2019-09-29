    select
    '' as CATALOG_NAME,
    s.SCHEMANAME SCHEMA_NAME,
    t.TABLENAME TABLE_NAME,
    tr.CONSTRAINTNAME CONSTRAINT_NAME,
    CASE
           WHEN tr.TYPE='P' THEN 'PRIMARY_KEY'
           WHEN tr.TYPE='F' THEN 'FOREIGN_KEY'
           WHEN tr.TYPE='U' THEN 'UNIQUE'
           WHEN tr.TYPE='C' THEN 'CHECK'
         END AS CONSTRAINT_TYPE
      from SYS.SYSCONSTRAINTS tr
      inner join SYS.SYSTABLES t on tr.TABLEID = t.TABLEID
      inner join SYS.SYSSCHEMAS s on s.SCHEMAID = t.SCHEMAID
      where
          s.SCHEMANAME Like ${schema?}
      and t.TABLENAME Like ${table?}
      and tr.CONSTRAINTNAME Like ${constraint?}
