/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame;

/**
 *
 * @author user
 */
public class BackGroundBoard extends javax.swing.JPanel  implements HintItem {
    private Controler controler;
    private boolean Hint = false;
    private int index ;
    /**
     * Creates new form BackGroundBoard
     */
    public BackGroundBoard(Controler controler , int index) {
        initComponents();
        this.controler = controler;
        this.index = index;
    }
    public void turnBlack(){
        setBackground(new java.awt.Color(0, 0, 0));
    }
    public void turnWhite(){
        setBackground(new java.awt.Color(255, 255, 255));
    }
    public void hint(){
        this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0), 5));
        this.Hint = true;
    }
    public void unhint(){
        this.setBorder(null);
        this.Hint = false;
    }
    public boolean tryHint(){
        return this.Hint;
    }
    public void setControler(Controler controler){
        this.controler = controler ; 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(100, 100));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 224, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        if(this.Hint)
            this.controler.PlayerMove(index);
        else
            this.controler.unFocusChess();
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
