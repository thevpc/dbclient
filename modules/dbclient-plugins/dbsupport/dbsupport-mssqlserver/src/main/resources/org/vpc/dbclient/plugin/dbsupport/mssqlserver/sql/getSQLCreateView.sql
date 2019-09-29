select com.text from ${catalog}.${schema}.syscomments com
                inner join ${catalog}.${schema}.sysobjects obj on com.id = obj.id
                where obj.xtype = 'V'
                and user_name(obj.uid)=${schema?} and obj.name=${view?}
                order by name,colid
                