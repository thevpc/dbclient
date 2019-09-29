package net.vpc.dbclient.plugin.dbsupport.mssqlserver.features;

import net.vpc.dbclient.api.sql.objects.SQLObjectTypes;
import net.vpc.dbclient.plugin.system.sql.features.DefaultFeatureGenerateSQLDrop;

/**
 * @author Taha BEN SALAH (taha.bensalah@gmail.com)  alias vpc
 * @creationtime 2009/08/16 01:35:34
 */
public class MSSqlServerGenerateSQLDropFeature extends DefaultFeatureGenerateSQLDrop {

    @Override
    public String getSQLDropProcedure(String catalog, String schema, String parentName, String objectName) {
        return "if exists (select * from sysobjects where id = object_id(N'[" + catalog + "].[" + schema + "].[" + objectName + "]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)\n" +
                "" + super.getSQL(catalog, schema, parentName, objectName, SQLObjectTypes.PROCEDURE);
    }

    @Override
    public String getSQLDropView(String catalog, String schema, String parentName, String objectName) {
        return "if exists (select * from sysobjects where id = object_id(N'[" + catalog + "].[" + schema + "].[" + objectName + "]') and OBJECTPROPERTY(id, N'IsView') = 1)\n" +
                "" + super.getSQL(catalog, schema, parentName, objectName, SQLObjectTypes.VIEW);
    }

    @Override
    public String getSQLDropTable(String catalog, String schema, String parentName, String objectName) {
        return "if exists (select * from sysobjects where id = object_id(N'[" + catalog + "].[" + schema + "].[" + objectName + "]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)\n" +
                "" + super.getSQL(catalog, schema, parentName, objectName, SQLObjectTypes.TABLE);
    }
}
