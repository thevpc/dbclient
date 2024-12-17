select 'CREATE TRIGGER ' || t.TRIGGERNAME||' \n'
            ||(select
                 CASE
                 WHEN t3.FIRINGTIME='B' THEN 'BEFORE'
                 WHEN t3.FIRINGTIME='A' THEN 'AFTER'
                 END
               from SYS.SYSTRIGGERS t3
               where t.TRIGGERID = t3.TRIGGERID)
            || ' '
            ||(select CASE
                 WHEN t2.EVENT='U' THEN 'UPDATE'
                 WHEN t2.EVENT='D' THEN 'DELETE'
                 WHEN t2.EVENT='I' THEN 'INSERT'
                 END
               from SYS.SYSTRIGGERS t2
               where t.TRIGGERID = t2.TRIGGERID)
             ||' ON '
             || ta.TABLENAME || ' \n'
             ||(select
                CASE
                  WHEN t4.REFERENCINGOLD = 0 THEN ''
                  WHEN t4.REFERENCINGOLD = 1
                    THEN ' REFERENCING OLD AS ' || t4.OLDREFERENCINGNAME || ' \n'
                END
                from SYS.SYSTRIGGERS t4
                where t.TRIGGERID = t4.TRIGGERID)
             ||(select
                CASE
                  WHEN t5.REFERENCINGNEW = 0 THEN ''
                  WHEN t5.REFERENCINGNEW = 1
                    THEN ' REFERENCING NEW AS ' || t5.NEWREFERENCINGNAME || ' \n'
                END
                from SYS.SYSTRIGGERS t5
                where t.TRIGGERID = t5.TRIGGERID)
             ||' FOR EACH ROW MODE DB2SQL \n'
             || t.triggerdefinition
from
    SYS.SYSTRIGGERS t
    inner join SYS.SYSTABLES ta on t.TABLEID = ta.TABLEID
    inner join SYS.SYSSCHEMAS s on s.SCHEMAID = t.SCHEMAID
where
        s.SCHEMANAME = ${schema?}
    and t.TRIGGERNAME = ${trigger?}
