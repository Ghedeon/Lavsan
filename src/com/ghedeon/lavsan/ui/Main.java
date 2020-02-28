package com.ghedeon.lavsan.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    DefaultTableModel model = new DefaultTableModel(new Object[][]{
            {"some", "text"}, {"any", "text"}, {"even", "more"},
            {"text", "strings"}, {"and", "other"}, {"text", "values"}},
            new Object[]{"Column 1", "Column 2"}) {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return i % 2 == 0;
        }
    };

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTable table = new JTable(model) {
            @Override
            public void changeSelection(int row, int col, boolean b, boolean b1) {
                System.out.println("row " + row + " col " + col);
                if (isCellEditable(row, col)) {
//                        getEditorComponent().requestFocusInWindow();
                    super.changeSelection(row, col, b, b1);
                } else {
                    int c = col + 1;
                    int r = row;
                    while (r < getRowCount()) {
                        while (c < getColumnCount()) {
                            if (isCellEditable(r, c)) {
                                super.changeSelection(r, c, b, b1);
                                editCellAt(r, r);
//                                    ((JTextField)jTable.getEditorComponent()).selectAll();
//                                    jTable.getEditorComponent().requestFocusInWindow();
                                return;
                            }
                            c++;
                        }
                        c = 0;
                        r += 1;
                    }
                }
            }
        };
//        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(false);
/*        table.getColumnModel().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                System.out.println(row + " " + col);
            }
        });*/


        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        pack();
    }

    public static void main(String arg[]) {
        new Main().setVisible(true);
    }

    public static class MyOwnFocusTraversalPolicy
            extends FocusTraversalPolicy {
        List<Component> order;

        public MyOwnFocusTraversalPolicy(List<Component> order) {
            this.order = new ArrayList<>(order.size());
            this.order.addAll(order);
        }

        public Component getComponentAfter(Container focusCycleRoot,
                                           Component aComponent) {
            int idx = (order.indexOf(aComponent) + 1) % order.size();
            return order.get(idx);
        }

        public Component getComponentBefore(Container focusCycleRoot,
                                            Component aComponent) {
            int idx = order.indexOf(aComponent) - 1;
            if (idx < 0) {
                idx = order.size() - 1;
            }
            return order.get(idx);
        }

        public Component getDefaultComponent(Container focusCycleRoot) {
            return order.get(0);
        }

        public Component getLastComponent(Container focusCycleRoot) {
            return order.get(order.size() - 1);
        }

        public Component getFirstComponent(Container focusCycleRoot) {
            return order.get(0);
        }
    }
}