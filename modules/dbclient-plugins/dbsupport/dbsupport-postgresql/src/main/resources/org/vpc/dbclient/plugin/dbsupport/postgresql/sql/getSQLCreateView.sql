Select
'CREATE VIEW ${view} AS \n' || v.definition as VIEW_DEFINITION,
'' as CATALOG_NAME,
v.schemaname as SCHEMA_NAME,
v.viewname as VIEW_NAME
From pg_catalog.pg_views v
where
  v.schemaname Like ${schema?}
  and v.viewname Like ${view?}
