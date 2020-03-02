package com.naver;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DAG {


    public static void main(String[] args) {
        DAG dag = new DAG();

        List<List<Character>> edges = new ArrayList<>();
        List<Character> e1 = new ArrayList<>();
        e1.add('A');
        e1.add('C');
        edges.add(e1);
        List<Character> e2 = new ArrayList<>();
        e2.add('C');
        e2.add('B');
        edges.add(e2);
        List<Character> e3 = new ArrayList<>();
        e3.add('A');
        e3.add('B');
        edges.add(e3);
        //n: the number of nodes, m: the number of edges;
        List<Integer> re = dag.getDAGOrder(3, 3, edges);
        for (Integer r: re) {
            System.out.println((char)(r + 'A' - 1));
        }


        edges = new ArrayList<>();
        e1 = new ArrayList<>();
        e1.add('A');
        e1.add('B');
        edges.add(e1);
        e2 = new ArrayList<>();
        e2.add('B');
        e2.add('C');
        edges.add(e2);
        e3 = new ArrayList<>();
        e3.add('C');
        e3.add('A');
        edges.add(e3);
        re = dag.getDAGOrder(3, 3, edges);
        System.out.println(null == re);
    }

    /**
     * get DAG order
     * return null if can not get the DAG order
     * @param n the number of node
     * @param m the number of edges
     * @param edges
     * @return
     */
    List<Integer> getDAGOrder(int n, int m, List<List<Character>> edges) {

        List<Integer> results = new ArrayList<>();

        Node[] nodes = new Node[n + 1];
        for (List<Character> edge : edges) {
            int a = edge.get(0) - 'A' + 1;
            int b = edge.get(1) - 'A' + 1;
            if (null == nodes[a]) {
                nodes[a] = new Node(a);
            }
            if (null == nodes[b]) {
                nodes[b] = new Node(b);
            }
            nodes[a].nextNodes.add(nodes[b]);
        }

        int[] scores = new int[n + 1];
        for (int i = 1; i < nodes.length; i++) {
            for (Node next : nodes[i].nextNodes) {
                scores[next.value]++;
            }
        }

        Stack<Node> stack = new Stack<>();
        for (int i = 1; i < scores.length; i++) {
            if (0 == scores[i]) {
                stack.push(nodes[i]);
            }
        }

        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            if (1 == cur.visit) {
                return null;
            }
            results.add(cur.value);
            cur.visit = 1;
            for (Node next : cur.nextNodes) {
                scores[next.value]--;
                if (0 == scores[next.value]) {
                    stack.push(nodes[next.value]);
                }
            }
        }
        if (results.size() == 0) return null;
        return results;
    }
}


class Node {

    public Node(int value) {
        this.value = value;
    }

    int value;
    int visit = 0; //0.未访问过，1访问过
    List<Node> nextNodes = new ArrayList<>();
}
