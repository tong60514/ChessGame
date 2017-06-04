/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aibreakthroughchessgame;


import static aibreakthroughchessgame.AIBrain.WallTable;
import aibreakthroughchessgame.ChessBitBoard.RowColumnTable;
import aibreakthroughchessgame.DataSructure.Lambda_Generator;
import aibreakthroughchessgame.DataSructure.LeaveChess;
import aibreakthroughchessgame.DataSructure.StructBoard;
import aibreakthroughchessgame.DataSructure.StructList;
import java.awt.Point;

/**
 *
 * @author user
 */
public class Transform {
    public final static RowColumnTable RowColTable = new RowColumnTable();
    public static final int F = 10000;
    public static String[] RowIndexTable =       {"1","2","3","4","5","6","7","8"};
    public static String[] ColumnIndexTable =     {"A","B","C","D","E","F","G","H"};
    public static int[] distrubuteScore_BlackBoard = {  0,  0,  0,  0,  0,  0,  0,  0,
                                                    10,  10,  10,  10,  10,  10,  10,  10,
                                                    10,  10,  10,  10,  10,  10,  10,  10,  
                                                    20,  20,  20,  20,  20,  20,  20,  20,
                                                    30,  30,  30,  30,  30,  30,  30,  30,
                                                    40,  40,  40,  40,  40,  40,  40,  40,
                                                    50,  30,  40,  50,  50,  40,  30,  50,
                                                    F,  F,  F,  F,  F,  F,  F, F,
    };
    
    public static int[] distrubuteScore_WhiteBoard = {  F, F, F, F, F, F, F,F,
                                                    40,  40,  40,  40,  40,  40,  40,  40,
                                                    30,  30,  30,  30, 30,  30,   30,  30,  
                                                    20,  20,  20,  20,  20,  20,  20,  20,
                                                    10,  10,  10,  10,  10,  10,  10,  10,
                                                    10,  10,  10,  10,  10,  10,  10,  10,
                                                    10,  10,  10,  10, 10,  10,  10,  10,
                                                    10,  10,  10,  10, 10,  10,  10,  10
    };
    private static Lambda_Generator legalmove = ()->{
        
    };
    private static Lambda_Generator legalcapture = ()->{
        
    };
    private static Lambda_Generator illegalmove = ()->{
        
    };
    private static Lambda_Generator illegalcapture = ()->{
        
    };
    private static Lambda_Generator legal = ()->{
        
    };
    private static Lambda_Generator illegal = ()->{
        
    };
    
    
    public long[] indexBoard = InitIndexBitBoard();
    static Point getRowColumn(int Index){
        return new Point(Index%8,Index/8);
    }
    public static void main(String[] arg){
        long test = 0b10L;
        long tst1 = 0b10000001L;
        long []  tstt ={0L,test,tst1};
        Transform t =new Transform();
        System.out.println("before capture : " + Long.toBinaryString(tstt[1]));
        System.out.println("before capture : " + Long.toBinaryString(tstt[2]));
        BitBoardCapture(tstt,1,t.indexBoard[62],t.indexBoard[63]);
        System.out.println("after capture : " + Long.toBinaryString(tstt[1]));
        System.out.println("after capture : " + Long.toBinaryString(tstt[2]));
        BitBoardDeCapture(tstt,2,t.indexBoard[63],t.indexBoard[62]);
        System.out.println("after decapture : " + Long.toBinaryString(tstt[1]));
        System.out.println("after decapture : " + Long.toBinaryString(tstt[2]));
        
        
    }
    public static int getOppsiteColor(int color){
        return 3-color;
    }
    public static long[] InitIndexBitBoard(){
        long[] AllPoint = new long[64];
        for(int i=0;i<AllPoint.length;i++){
            AllPoint[i] = (1L<<IndexToBitBoardShiftValue(i));
        }
        return AllPoint;
    }
    private static int IndexToBitBoardShiftValue(int index){
        return (64-index-1);
    }
    public final static long BitBoardMove(long board , long from, long to){
        return (board|to)&~from;
    }
    private final static long BitBoardDelete(long board , long at){
        return board&~at;
    }
    public final static void BitBoardCapture(long[] BitBoard, int CaptureColor,long from, long to){
        BitBoard[CaptureColor] = BitBoardMove(BitBoard[CaptureColor],from,to);
        BitBoard[getOppsiteColor(CaptureColor)] =  BitBoardDelete(BitBoard[getOppsiteColor(CaptureColor)],to);
    }
    private final static long resurrect(long board , long at){
        return board|at;
    }
    public final static void BitBoardDeCapture(long[] BitBoard, int deCaptureColor,long from, long to){
        BitBoard[Transform.getOppsiteColor(deCaptureColor)] = BitBoardMove(BitBoard[Transform.getOppsiteColor(deCaptureColor)],
                from,to);
        BitBoard[deCaptureColor] = resurrect(BitBoard[deCaptureColor],from);
    }
    public static int Evaluate(StructBoard board,long[] Bitboard){
        int Total;
        int blackTotal;
        int whiteTotal;
        blackTotal =  distrubuteScore_Black(board.structList[2],Bitboard) + board.structList[2].Size()*300;
        whiteTotal  = distrubuteScore_White(board.structList[1]) + board.structList[1].Size()*300;
        Total = blackTotal-whiteTotal;
        return Total;
    }
    public static int distrubuteScore_Black(StructList list,long[] bitboard){
        int score = 0 ;
        for(int i=0;i<list.Size();i++){
            int index = list.getCurrent().getIndex();
            long bit = list.getCurrent().getBitBoard();
            int distruScore = distrubuteScore_BlackBoard[list.getCurrent().getIndex()];
            int leftprotectScore = Blackleftcapture(bit,index,bitboard)*distrubuteScore_BlackBoard[index+9]*2;
            int rightprotectScore = Blackrightcapture(bit,index,bitboard)*(distrubuteScore_BlackBoard[index+7])*2;
            score+= (distrubuteScore_BlackBoard[list.getCurrent().getIndex()]);
        }
        return score;
    }
    
    public static int distrubuteScore_White(StructList list){
        int score = 0 ;
        for(int i=0;i<list.Size();i++){
            score+= (distrubuteScore_WhiteBoard[list.getCurrent().getIndex()]);
        }
        return score;
    }
    public static int Blackleftcapture(long Src,int SrcIndex,long[] TwoBitBoard) {
            return (int) ( ((Src>>>9)&TwoBitBoard[ChessBoard.WhitePawn]&~(WallTable.getColumnBitBoard(0)) )>>>(63-SrcIndex-9) );
    }
    public static int Blackrightcapture(long Src,int SrcIndex,long[] TwoBitBoard) {
        return (int) ( ((Src>>>7)&TwoBitBoard[ChessBoard.WhitePawn]&~(WallTable.getColumnBitBoard(7)))>>>(63-SrcIndex-7) );
    }
    public static int Whiteleftcapture(long Src,int SrcIndex,long[] TwoBitBoard) {
        return (int) ( ((Src<<9)&TwoBitBoard[ChessBoard.BlackPawn]&~(WallTable.getColumnBitBoard(0)) )>>>(63-SrcIndex+9) );
    }
    public static int Whiterightcapture(long Src,int SrcIndex,long[] TwoBitBoard) {
        return (int) ( ((Src<<7)&TwoBitBoard[ChessBoard.BlackPawn]&~(WallTable.getColumnBitBoard(7)))>>>(63-SrcIndex+7) );
    }
    
    
}
