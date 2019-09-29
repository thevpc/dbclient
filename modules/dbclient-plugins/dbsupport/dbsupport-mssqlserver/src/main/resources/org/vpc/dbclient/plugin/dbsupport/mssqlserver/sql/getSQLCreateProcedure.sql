select com.text from ${catalog}.${schema}.syscomments com
inner join ${catalog}.${schema}.sysobjects obj on com.id = obj.id
where obj.xtype = 'P'
and user_name(obj.uid)=${schema?} and obj.name=${procedure?}
order by name,colid
