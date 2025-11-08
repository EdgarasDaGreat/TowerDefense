public class Cursor {
    private int cursorX;
    private int cursorY;

    public Cursor(int cursorX, int cursorY){
        this.cursorX = cursorX;
        this.cursorY = cursorY;
    }
    public int getCursorX(){
        return cursorX;
    }
    public int getCursorY(){
        return cursorY;
    }

    public void moveCursorRight(){
        cursorX++;
    }
    public void moveCursorLeft(){
        cursorX--;
    }
    public void moveCursorUp(){
        cursorY--;
    }
    public void moveCursorDown(){
        cursorY++;
    }

    public void clampCursor(Cursor cursor, int columns, int rows) {
        if(cursor.getCursorX() < 0) cursor.moveCursorRight();
        if(cursor.getCursorX() >= columns) cursor.moveCursorLeft();
        if(cursor.getCursorY() < 0) cursor.moveCursorDown();
        if(cursor.getCursorY() >= rows) cursor.moveCursorUp();
    }


}
