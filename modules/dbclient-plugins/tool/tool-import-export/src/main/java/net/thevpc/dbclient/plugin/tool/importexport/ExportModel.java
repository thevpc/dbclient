package net.thevpc.dbclient.plugin.tool.importexport;

public interface ExportModel {
    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     */
    public int getColumnCount();

    /**
     * Returns the name of the column at <code>columnIndex</code>.  This is used
     * to initialize the table's column header name.  Note: this name does
     * not need to be unique; two columns in a table can have the same name.
     *
     * @return the name of the column
     * @param    columnIndex    the index of the column
     */
    public String getColumnName(int columnIndex);

    /**
     * Returns the most specific superclass for all the cell values
     * in the column.  This is used by the <code>JTable</code> to set up a
     * default renderer and editor for the column.
     *
     * @param columnIndex the index of the column
     * @return the common ancestor class of the object values in the model.
     */
    public Class<?> getColumnClass(int columnIndex);

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param    columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    public Object getValue(int columnIndex);

    public boolean next();
}
