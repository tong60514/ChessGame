/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame.DataSructure;

import aibreakthroughchessgame.DataSructure.LeaveChess;

/**
 *
 * @author user
 */
public class StructList {
    private LeaveChess current;
    private static final State[] listState = {new nullList(), new point(),new ring()};
    private static final int[] remainTable = {0,1,2,2 
                                            ,2,2,2,2,
                                            2,2,2,2 ,
                                            2,2,2,2,2 };
    private int Color;
    private int Size = 0;
    public StructList(int Color){
        this.Color = Color;
    }
    public LeaveChess getCurrent(){
        LeaveChess c = this.current;
        next();
        return c;
    }
    public static StructList[] cloneStructList(StructList[] list){
        StructList structlist = new StructList(1);
        StructList structlist2 = new StructList(2);
        for(int i=0;i<list[1].Size();i++){
            structlist.add(LeaveChess.cloneLeaveChess(list[1].getCurrent())); // clone
        }
        for(int i=0;i<list[2].Size();i++){
            structlist2.add(LeaveChess.cloneLeaveChess(list[2].getCurrent())); // clone
        }
        StructList[] clone = {null,structlist,structlist2};
        return clone;
    }
    private void next(){
        this.current = this.current.getNEXTref();
    }
    public int getColor(){
        return this.Color;
    }
    public int Size(){
        return this.Size;
    }
    public void setCurrent(LeaveChess current){
        this.current = current;
        
    }
    private void Listadd(LeaveChess target){
        LeaveChess next = this.current.getNEXTref();
        next.setSuper(target);
        target.setNEXT(next);
        this.current.setNEXT(target);
        target.setSuper(this.current);
    }
    public void add(LeaveChess target){
        int CurrentState = StructList.remainTable[this.Size];
        StructList.listState[CurrentState].add(this,target);
    }
    
    private void Listremove(LeaveChess chess){ 
        this.current = chess.remove();
    }
    public void removeChess(LeaveChess target){ 
        int CurrentState = StructList.remainTable[this.Size];
        StructList.listState[CurrentState].remove(this, target);
    }
    
    
    
    
    abstract static class State{
        protected abstract void add(StructList list , LeaveChess target);
        protected abstract void remove(StructList list , LeaveChess target) ;
    }
    static class ring extends State{

        @Override
        protected void add(StructList list , LeaveChess target) {
            list.Listadd(target);
            list.Size++;
        }

        @Override
        protected void remove(StructList list , LeaveChess target) {
           list.Listremove(target);
           list.Size--;
        }
         
    }
    static class point extends State{

        @Override
        protected void add(StructList list, LeaveChess target) {
            list.Listadd(target);
            list.Size++;
        }

        @Override
        protected void remove(StructList list, LeaveChess target) {
            list.current=null;
            list.Size-- ;
        }
        
    }
    
    static class nullList extends State{

        @Override
        protected void add(StructList list , LeaveChess target) {
            list.current = target;
            target.setNEXT(target);
            target.setSuper(target);
            list.Size++;
        }

        @Override
        protected void remove(StructList list , LeaveChess target) {
            
        }
        
    }
}
