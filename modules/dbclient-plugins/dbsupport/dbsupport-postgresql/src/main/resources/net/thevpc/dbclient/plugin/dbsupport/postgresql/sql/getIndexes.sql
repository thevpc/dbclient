select
'' as CATALOG_NAME,
v.schemaname as SCHEMA_NAME,
v.tablename as TABLE_NAME,
v.indexname as INDEX_NAME
From pg_catalog.pg_indexes v
  where
  v.schemaname Like ${schema?}
  and v.tablename Like ${table?}
  and v.indexname Like ${index?}