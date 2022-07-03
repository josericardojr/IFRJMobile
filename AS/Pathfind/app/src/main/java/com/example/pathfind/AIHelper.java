package com.example.pathfind;

import android.os.Debug;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AIHelper {

    public class Node {
        public Node parent = null;
        public boolean isVisited = false;
        public Grid.Cell refCell = null;

        public List<Node> child = new ArrayList<>();
    }


    public Queue<Grid.Cell> BFS(Grid grid, Grid.Cell start, Grid.Cell end){

        Grid.AdjCell[] adjList = grid.getAdjacentList();

        Node[] graph = new Node[adjList.length];

        for (int i = 0; i < graph.length; i++) graph[i] = new Node();

        for (int i = 0; i < adjList.length; i++){
            graph[i].refCell = adjList[i].cell;

            for (int j = 0; j < adjList[i].adjCells.size(); j++){
                Grid.Cell _cCell = grid.getCellAt(adjList[i].adjCells.get(j));
                graph[i].child.add(graph[_cCell.getId()]);
            }
        }

        Node startNode = graph[start.getId()];
        Node endNode = graph[end.getId()];

        Queue<Node> queue = new LinkedList<>();
        queue.add(startNode);

        while (!queue.isEmpty()){

            Node _curNode = queue.remove();

            if (_curNode == endNode){
                break;
            }

            if (_curNode != startNode) grid.setTriedPath(_curNode.refCell.getId());

            List<Node> notVisited = new ArrayList<>();
            for (int j = 0; j < _curNode.child.size(); j++){
                Node _nChild = _curNode.child.get(j);

                if (_nChild.isVisited == false) notVisited.add(_nChild);
            }

            for (int j = 0; j < notVisited.size(); j++){
                Node _nChild = notVisited.get(j);
                _nChild.parent = _curNode;
                _nChild.isVisited = true;
                queue.add(_nChild);
            }

            Log.d("Pathfind", "Size: " + queue.size());
        }

        if (endNode.parent == null) return null;

        Queue<Grid.Cell> result = new LinkedList<>();

        Node _traverse = endNode;
        do {
            result.add(_traverse.refCell);
            _traverse = _traverse.parent;
        } while (_traverse != startNode);

        return result;

    }
}
