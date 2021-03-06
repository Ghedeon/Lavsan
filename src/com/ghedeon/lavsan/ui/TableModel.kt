package com.ghedeon.lavsan.ui

import com.ghedeon.lavsan.Id
import javax.swing.table.AbstractTableModel

private class TableModel : AbstractTableModel() {

    private val cols = arrayOf("", "Type", "ID", "Alias")
    private val columnClass = arrayOf(Boolean::class.javaObjectType, String::class.java, String::class.java, String::class.java)

    var data: List<Id> = emptyList()
        set(value) {
            field = value
            fireTableDataChanged()
        }

    override fun getRowCount(): Int = data.size

    override fun getColumnCount(): Int = cols.size

    override fun getColumnName(column: Int): String = cols[column]

    override fun getColumnClass(col: Int): Class<*> = columnClass[col]

    override fun isCellEditable(row: Int, col: Int): Boolean = when (col) {
        ENABLED_COL, ALIAS_COL -> true
        else -> false
    }

    override fun getValueAt(row: Int, col: Int): Any =
            with(data[row]) {
                when (col) {
                    ENABLED_COL -> isEnabled
                    TYPE_COL -> type
                    ID_COL -> resId
                    ALIAS_COL -> alias
                    else -> throw IllegalStateException("Nonexistent column index: $col")
                }
            }

    override fun setValueAt(value: Any?, row: Int, col: Int) {
        when (col) {
            ENABLED_COL -> data[row].isEnabled = !data[row].isEnabled
            ALIAS_COL -> data[row].alias = value as String
        }
    }
}

private const val ENABLED_COL = 0
private const val TYPE_COL = 1
private const val ID_COL = 2
private const val ALIAS_COL = 3
