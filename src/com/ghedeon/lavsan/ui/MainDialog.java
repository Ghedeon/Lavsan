package com.ghedeon.lavsan.ui;

import com.ghedeon.lavsan.Id;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MainDialog extends DialogWrapper {

    private JPanel contentPane;
    private JTable jTable;
    private JScrollPane jScrollPane;
    private TableModel tableModel;

    public MainDialog(Project project) {
        super(project);
        init();
        setModal(true);
        setTitle("Lavsan");
        contentPane.setPreferredSize(new Dimension(700, 250));
        tableModel = new TableModel();
        jTable.setModel(tableModel);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(26);
        jTable.getColumnModel().getColumn(0).setMaxWidth(26);
        jTable.getColumnModel().getColumn(1).setMaxWidth(160);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        jTable.setShowGrid(false);
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);
        jTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
//        jTable.setCellSelectionEnabled(true);

//        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//        jTable.setDefaultRenderer(String.class, centerRenderer);


        ((DefaultCellEditor) jTable.getDefaultEditor(String.class)).setClickCountToStart(1);

        jTable.getColumnModel().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = jTable.getSelectedRow();
                    int col = jTable.getSelectedColumn();
                    System.out.println(row + " " + col);

/*                    // Make sure we start with legal values.
                    while(col < 0) col++;
                    while(row < 0) row++;
                    // Find the next editable cell.
                    while(!jTable.isCellEditable(row, col) || jTable.getEditorComponent() instanceof JCheckBox)
                    {
                        col++;
                        if(col > jTable.getColumnCount()-1)
                        {
                            col = 1;
                            row = (row == jTable.getRowCount()-1) ? 1 : row+1;
                        }
                    }
                    // Select the cell in the table.
                    final int r = row, c = col;
                    EventQueue.invokeLater(new Runnable()
                    {
                        public void run()
                        {
                            jTable.changeSelection(r, c, false, false);
                        }
                    });
                    // Edit.
                    if(jTable.isCellEditable(row, col))
                    {
                        jTable.editCellAt(row, col);
                        ((JTextField)jTable.getEditorComponent()).selectAll();
                        jTable.getEditorComponent().requestFocusInWindow();
                    }*/
                }

            }
        });

    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return jTable;
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
            MainDialog dialog = new MainDialog(null);
            List<Id> data = Arrays.asList(new Id[]{new Id("TextView", "R.id.text_id", "textId", true), new Id("TextView", "R.id.image_id", "imageId", true)});
            dialog.bind(data);
            dialog.show();
            System.exit(0);
        });
    }


}
