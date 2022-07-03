package com.example.pathfind;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Grid {
    float width;
    float height;
    float size;
    int numCols;
    int numRows;
    float offsetX, offsetY;
    Paint gridPaint;
    Paint startCellPaint, endCellPaint, pathCellPaint, blockedPaint, triedPathPaint;


    public class Cell {
        int id;
        boolean isEmpty = true;
        Paint color = null;

        public Cell(int id){ this.id = id; }

        public int getId() {
            return id;
        }

        public boolean isEmpty() {
            return isEmpty;
        }

        public void clearColor(){color = null; }
    }

    List<Cell> cells = new ArrayList<>();

    public class AdjCell {
        Cell cell;
        List<Integer> adjCells = new ArrayList<Integer>();

        public AdjCell(int id){
            cell = new Cell(id);
        }
    }


    public Grid(float width, float height, float size, float offsetX, float offsetY){
        gridPaint = new Paint();
        gridPaint.setStrokeWidth(3.0f);
        gridPaint.setColor(Color.WHITE);
        gridPaint.setAntiAlias(true);

        startCellPaint = new Paint();
        startCellPaint.setColor(Color.BLUE);

        endCellPaint = new Paint();
        endCellPaint.setColor(Color.GREEN);

        pathCellPaint = new Paint();
        pathCellPaint.setColor(Color.YELLOW);

        blockedPaint = new Paint();
        blockedPaint.setColor(Color.RED);

        triedPathPaint = new Paint();
        triedPathPaint.setColor(Color.GRAY);
        //triedPathPaint.setAlpha(50);

        this.height = height;
        this.width = width;
        this.size = size;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        this.numRows = (int) (height / size);
        this.numCols = (int) (width / size);

        for (int i = 0; i < numRows * numCols; i++){
            Cell cell = new Cell(i);
            cells.add(cell);
        }
    }

    public void render(Canvas canvas){

        for (int r = 0; r <= numRows; r++){
            float sy = offsetY + (float) r * size;
            canvas.drawLine(offsetX, sy, offsetX + numCols * size, sy, gridPaint);
        }

        for (int c = 0; c <= numCols; c++){
            float sx = offsetX + (float) c * size;
            canvas.drawLine(sx, offsetY, sx, offsetY + numRows * size, gridPaint);
        }

        for (int i = 0; i < cells.size(); i++){
            Cell _cell = cells.get(i);

            if (_cell.color != null){
                int _r = getRowFromCell(_cell);
                int _c = getColFromCell(_cell);

                Rect _rect = new Rect();
                _rect.left = (int) (offsetX + _c * size);
                _rect.right = (int) (_rect.left + size);
                _rect.top = (int)(offsetY + _r * size);
                _rect.bottom = (int)(_rect.top + size);
                canvas.drawRect(_rect, _cell.color);
            }
        }
    }

    public AdjCell[] getAdjacentList(){

        AdjCell[] adjList = new AdjCell[cells.size()];

        for (int i = 0; i < cells.size(); i++){
            adjList[i] = new AdjCell(cells.get(i).getId());

            int _r = getRowFromCell(cells.get(i));
            int _c = getColFromCell(cells.get(i));

            if (_r > 0) {
                Cell _cell = getCellFromPos(_r - 1, _c);
                if (_cell.isEmpty()) adjList[i].adjCells.add(_cell.getId());
            }

            if (_r < numRows - 1){
                Cell _cell = getCellFromPos(_r+1, _c);
                if (_cell.isEmpty()) adjList[i].adjCells.add(_cell.getId());
            }

            if (_c > 0){
                Cell _cell = getCellFromPos(_r, _c-1);
                if (_cell.isEmpty()) adjList[i].adjCells.add(_cell.getId());
            }

            if (_c < numCols - 1){
                Cell _cell = getCellFromPos(_r, _c + 1);
                if (_cell.isEmpty()) adjList[i].adjCells.add(_cell.getId());
            }
        }

        return adjList;
    }


    public int getRowFromCell(Cell cell){
        return cell.getId() / numCols;
    }

    public int getColFromCell(Cell cell){
        return cell.getId() % numCols;
    }

    public Cell getCellFromPos(int row, int col){
        int id = row * numCols + col;
        return cells.get(id);
    }

    public Cell getCellAt(int idx){
        return cells.get(idx);
    }

    public Cell getCellOnPosition(float x, float y){

        if (x > offsetX && x < offsetX + width &&
            y > offsetY && y < offsetY + height){

            int col = (int) ((x - offsetX) / size);
            int row = (int) ((y - offsetY) / size);

            return getCellFromPos(row, col);
        }
        return null;
    }

    public void clearPath(){
        for (int i = 0; i < cells.size(); i++){
            cells.get(i).color = null;
        }
    }

    public void setStartColor(int cellID){
        cells.get(cellID).color = startCellPaint;
    }

    public void setEndColor(int cellID){
        cells.get(cellID).color = endCellPaint;
    }

    public void makePath(Cell start, Cell end){
        AIHelper aiHelper = new AIHelper();

        Queue<Cell> path = aiHelper.BFS(this, start, end);

        if (path != null) {
            while (!path.isEmpty()) {
                Cell _p = cells.get(path.remove().getId());

                if (_p == start || _p == end) {
                    continue;
                }

                _p.color = pathCellPaint;
            }
        }
    }

    public void setBlocked(int cellIdx){
        cells.get(cellIdx).isEmpty = false;
        cells.get(cellIdx).color = blockedPaint;
    }

    public void setTriedPath(int cellIdx){
        cells.get(cellIdx).color = triedPathPaint;
    }

}
