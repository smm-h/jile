package jile.swing;

import java.util.*;
import java.awt.EventQueue;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class SwingTest extends JFrame {

    private ArrayList<String> numbers = new ArrayList<String>();

    private JButton btnAdd, btnEven, btnList, btnOdd, btnRemove, btnSum;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea;
    private JLabel lblNumber;
    private JTextField txtAnswer, txtNumber;

    public SwingTest() {

        btnSum = new JButton();
        btnOdd = new JButton();
        btnEven = new JButton();
        btnAdd = new JButton();
        btnRemove = new JButton();
        btnList = new JButton();
        lblNumber = new JLabel();
        txtNumber = new JTextField();
        jScrollPane1 = new JScrollPane();
        jTextArea = new JTextArea();
        txtAnswer = new JTextField();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnSum.setText("sum");
        btnSum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSumActionPerformed(evt);
            }
        });

        btnOdd.setText("sum odd");

        btnEven.setText("sum even");

        btnAdd.setText("add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnRemove.setText("remove");
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnList.setText("list");
        btnList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnListActionPerformed(evt);
            }
        });

        lblNumber.setText("enter the number");

        jTextArea.setColumns(20);
        jTextArea.setRows(5);
        jScrollPane1.setViewportView(jTextArea);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING,
                        layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(btnAdd))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(36, 36, 36)
                                .addComponent(lblNumber).addGap(33, 33, 33)
                                .addComponent(txtNumber, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addComponent(btnSum))
                        .addGroup(layout.createSequentialGroup().addGroup(layout
                                .createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup().addGap(17, 17, 17)
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE))
                                .addGroup(GroupLayout.Alignment.TRAILING,
                                        layout.createSequentialGroup()
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtAnswer, GroupLayout.PREFERRED_SIZE, 161,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addGap(59, 59, 59)))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(btnOdd, GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnEven, GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnRemove, GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnList, GroupLayout.Alignment.TRAILING))))
                        .addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout
                        .createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(btnSum)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNumber).addComponent(txtNumber, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(btnOdd)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(btnEven)
                        .addGap(18, 18, 18).addComponent(btnAdd)).addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addComponent(btnRemove)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(btnList)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING,
                                layout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                        .addComponent(txtAnswer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addGap(64, 64, 64)))));

        pack();
    }

    private void btnSumActionPerformed(ActionEvent evt) {
        double answer = 0;
        for (String s : numbers) {
            System.out.println(s);
            answer += Double.parseDouble(s);
        }

        txtAnswer.setText(Double.toString(answer));

    }

    private void btnListActionPerformed(ActionEvent evt) {

        String a;
        String temp = "";
        Iterator<String> stepper = numbers.iterator();
        while (stepper.hasNext()) {
            a = stepper.next();
            temp += a + "\n";
        }

        jTextArea.setText(temp);

    }

    private void btnRemoveActionPerformed(ActionEvent evt) {
        String temp;
        temp = txtNumber.getText();
        numbers.remove(temp);
    }

    private void btnAddActionPerformed(ActionEvent evt) {
        String temp;
        temp = txtNumber.getText();
        numbers.add(temp);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SwingTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(SwingTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SwingTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SwingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SwingTest().setVisible(true);
            }
        });
    }
}
