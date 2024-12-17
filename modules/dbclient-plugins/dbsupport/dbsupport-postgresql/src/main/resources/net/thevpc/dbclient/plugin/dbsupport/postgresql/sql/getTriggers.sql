select
trigger_name as TRIGGER_NAME,
trigger_catalog as TRIGGER_CATALOG,
trigger_schema as TRIGGER_SCHEMA,
event_object_table as TRIGGER_TABLE
from information_schema.triggers v
where
  v.trigger_schema Like ${schema?}
  and v.trigger_catalog Like ${catalog?}
  and v.event_object_table Like ${table?}
  and v.trigger_name Like ${trigger?}
