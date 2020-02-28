package com.ghedeon.lavsan.ui;

import com.ghedeon.lavsan.Id;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class LavsanDialog extends DialogWrapper {
    private JPanel contentPane;
    private JBTable jTable;
    private TableModel tableModel;

    public LavsanDialog(Project project) {
        super(project);
        init();
        setModal(true);
        setTitle("Lavsan");
        contentPane.setPreferredSize(new Dimension(700, 250));

        tableModel = new TableModel();
        jTable.setModel(tableModel);

        jTable.getColumnModel().getColumn(0).setPreferredWidth(26);
        jTable.getColumnModel().getColumn(0).setMaxWidth(26);
        jTable.getColumnModel().getColumn(1).setMaxWidth(160);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        jTable.setShowGrid(false);
        jTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        jTable.setCellSelectionEnabled(true);
        jTable.setColumnSelectionAllowed(false);
        jTable.setRowSelectionAllowed(false);
        jTable.getTableHeader().setReorderingAllowed(false);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ((DefaultCellEditor) jTable.getDefaultEditor(String.class)).setClickCountToStart(1);

    }

    public void bind(List<Id> data) {
        tableModel.setData(data);
    }


    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPane;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LavsanDialog dialog = new LavsanDialog(null);
            List<Id> data = Arrays.asList(new Id[]{new Id("TextView", "R.id.text_id", "textId", true), new Id("TextView", "R.id.image_id", "imageId", true)});
            dialog.bind(data);
            dialog.pack();
            dialog.show();
            System.exit(0);
        });
    }

    private void createUIComponents() {
        jTable = new JBTable() {
            @Override
            public void changeSelection(int row, int col, boolean b, boolean b1) {
/*                if (isCellEditable(row, col)) {
                    super.changeSelection(row, col, b, b1);
                    editCellAt(row, col);
                    ((JTextField)jTable.getEditorComponent()).selectAll();
                    return;
                }*/

                while (!isCellEditable(row, col) || col == 0) {
                    col++;
                    if (col > jTable.getColumnCount() - 1) {
                        col = 1;
                        row = (row == jTable.getRowCount() - 1) ? 0 : row + 1;
                    }
                }

                super.changeSelection(row, col, b, b1);
                editCellAt(row, col);
                ((JTextField) jTable.getEditorComponent()).selectAll();
            }
        };
    }
}
