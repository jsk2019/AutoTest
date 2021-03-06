import org.junit.Test;

import java.io.IOException;
import java.util.*;


public class test {
    public Graph parseService(List<Node> list) {
        Graph graph = new Graph();
        Node branchNode = null;
        for (int i = 0; i < list.size() - 1; i++) {
            Node node1 = list.get(i);
            Node node2 = list.get(i + 1);
            if (node1.getContent().startsWith("first")) {
                graph.insertEdge(node1.getNode(), node2.getNode());
                branchNode = node2;
                graph.insertEdge(node1.getNode(), list.get(i + 2).getNode());
            } else if (node1.getContent().startsWith("if")) {
                if (node2.getHeight() == node1.getHeight() + 1) {
                    graph.insertEdge(node1.getNode(), node2.getNode());
                    i++;
                    if (i + 2 < list.size()) {
                        if (list.get(i + 2).getHeight() == node1.getHeight() + 1) {
                            graph.insertEdge(node1.getNode(), list.get(i + 1).getNode());
                            i++;
                        }
                    }
                }
            } else if (node1.getContent().length() > 13 && node1.getContent().startsWith("for-statement")) {
//                graph.insertEdge(node1.getNode(),node2.getNode());
//                graph.insertEdge(node2.getNode(),list.get(i+2).getNode());
//                graph.insertEdge(node2.getNode(),list.get(i+3).getNode());
//                graph.insertEdge(list.get(i+3).getNode(), node1.getNode());
                graph.insertEdge(node1.getNode(), node2.getNode());
                graph.insertEdge(node2.getNode(), node1.getNode());
                graph.insertEdge(node2.getNode(), list.get(i + 2).getNode());
                graph.insertEdge(list.get(i + 2).getNode(), list.get(i + 3).getNode());
                graph.insertEdge(list.get(i + 3).getNode(), node1.getNode());


                //  System.out.println("3: "+node1.getNode()+"->"+list.get(i+1).getNode());

                i += 3;
            } else if (node1.getContent().startsWith("return")) {
                if (branchNode != null) {
                    if (branchNode.getStartx() > node1.getStartx()) {
                        graph.insertEdge(list.get(i - 1).getNode(), node1.getNode());
                    } else if (branchNode.getStartx() == node1.getStartx()) {
                        int hight = branchNode.getHeight();
                        for (int j = 0; j < i; j++) {
                            if (list.get(j).getHeight() == hight) {
                                graph.insertEdge(list.get(j).getNode(), node1.getNode());
                            }
                        }
                    } else if (branchNode.getStartx() < node1.getStartx()) {
                        int hight = branchNode.getHeight();
                        for (int j = 0; j < i; j++) {
                            if (list.get(j).getHeight() == hight) {
                                graph.insertEdge(list.get(j).getNode(), branchNode.getNode());
                                graph.insertEdge(branchNode.getNode(), node1.getNode());
                            }
                        }
                    }

                } else {
                    graph.insertEdge(list.get(i - 1).getNode(), node1.getNode());

                }
            }
//            else if (node1.getContent().substring(0,6).equals("return")){
//                if (branchNode!=null){
//                    if (branchNode.getStartx()>node1.getStartx()){
//                        graph.insertEdge(list.get(i-1).getNode(),node1.getNode());
//                    }else if (branchNode.getStartx()== node1.getStartx()){
//                        int hight = branchNode.getHeight();
//                        for (int j =0;j<i;j++){
//                            if (list.get(j).getHeight()==hight){
//                                graph.insertEdge(list.get(j).getNode(),node1.getNode());
//                            }
//                        }
//                    }else if(branchNode.getStartx()<node1.getStartx()){
//                        int hight = branchNode.getHeight();
//                        for (int j =0;j<i;j++){
//                            if (list.get(j).getHeight()==hight){
//                                graph.insertEdge(list.get(j).getNode(), branchNode.getNode());
//                                graph.insertEdge(branchNode.getNode(),node1.getNode());
//                            }
//                        }
//                    }
//
//                }else {
//                    graph.insertEdge(list.get(i-1).getNode(),node1.getNode());
//
//                }
//            }
            if (node1.getContent().startsWith("after")) {
                branchNode = node1;
            }

        }
        Node node = list.get(list.size() - 1);
        if (branchNode != null) {
            if (branchNode.getStartx() < node.getStartx()) {
                if (list.get(list.size() - 2).getHeight() - 1 == branchNode.getHeight()) {
                    graph.insertEdge(list.get(list.size() - 2).getNode(), branchNode.getNode());
                    graph.insertEdge(branchNode.getNode(), node.getNode());
                }
            } else if (branchNode.getStartx() == node.getStartx()) {
                int height = branchNode.getHeight();
                for (int j = 0; j < list.size() - 1; j++) {
                    if (list.get(j).getHeight() - 1 == height) {
                        graph.insertEdge(list.get(j).getNode(), branchNode.getNode());
                    }
                }
            }
        }


        return graph;
    }

    //
    public void init(Graph graph, Graph.Vertex vertex1) {
        Set<Map.Entry<Integer, Graph.Vertex>> set = graph.vertexsMap.entrySet();
        Iterator<Map.Entry<Integer, Graph.Vertex>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Graph.Vertex> entry = iterator.next();
            Graph.Vertex vertex = entry.getValue();
            if (vertex.name < vertex1.name) {
                Graph.Edge edge = vertex.next;
                while (edge != null) {
                    edge.access = false;
                    edge = edge.next;
                }
            }
        }
    }

    //?????????????????????
    public void dfsFind(Graph graph, String print, Graph.Vertex top) {
        print = print + top.name + " ";
        //???if??????
        if (top.outnum == 2) {
            if (top.next.name > top.next.next.name) {
                //???????????????????????????
                if (top.next.access == false) {
                    top.next.access = true;
                    init(graph, graph.getVertexByName(top.name));
                    dfsFind(graph, print, graph.getVertexByName(top.next.name));
//                    init(graph, graph.getVertexByName(top.next.next.name));
                    dfsFind(graph, print, graph.getVertexByName(top.next.next.name));
                } else {

                    dfsFind(graph, print, graph.getVertexByName(top.next.next.name));
                }
            } else {
                if (top.next.next.access == false) {
                    top.next.next.access = true;
                    init(graph, graph.getVertexByName(top.name));
                    dfsFind(graph, print, graph.getVertexByName(top.next.next.name));
//                    init(graph, graph.getVertexByName(top.next.name));
                    dfsFind(graph, print, graph.getVertexByName(top.next.name));
                } else {
                    dfsFind(graph, print, graph.getVertexByName(top.next.name));
                }

            }
        } else if (top.outnum == 1) {
            dfsFind(graph, print, graph.getVertexByName(top.next.name));
        } else if (top.outnum == 0) {
            System.out.println(print);
            return;
        }
    }

    public Graph.Vertex getTop(Graph graph) {
        Set<Map.Entry<Integer, Graph.Vertex>> set = graph.vertexsMap.entrySet();
        Iterator<Map.Entry<Integer, Graph.Vertex>> iterator = set.iterator();
        Graph.Vertex top = null;
//        graph.print();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Graph.Vertex> entry = iterator.next();
            Graph.Vertex vertex = entry.getValue();
            if (vertex.innum == 0) {
                top = vertex;
                break;
            }
        }
        return top;
    }

    //????????????
    public void branchPrint(Graph graph) {
        Graph.Vertex top = getTop(graph);
//        graph.print();
        String record = "";
        dfsFind(graph, record, top);
    }

    //???????????????
    public void mainPrint(Graph graph, String print, Graph.Vertex top) {
        String[] record = print.split("\\s+");
        for (String ss : record) {
            if (ss.equals(String.valueOf(top.name))) {
                return;
            }
        }
        print = print + top.name + " ";
        //???if??????
        if (top.outnum == 2) {
            if (top.next.name > top.next.next.name) {
                //???????????????????????????
                if (top.next.access == false) {
                    top.next.access = true;
                    init(graph, graph.getVertexByName(top.name));
                    mainPrint(graph, print, graph.getVertexByName(top.next.name));
//                    init(graph, graph.getVertexByName(top.next.next.name));
                    mainPrint(graph, print, graph.getVertexByName(top.next.next.name));
                } else {

                    mainPrint(graph, print, graph.getVertexByName(top.next.next.name));
                }
            } else {
                if (top.next.next.access == false) {
                    top.next.next.access = true;
                    init(graph, graph.getVertexByName(top.name));
                    mainPrint(graph, print, graph.getVertexByName(top.next.next.name));
//                    init(graph, graph.getVertexByName(top.next.name));
                    mainPrint(graph, print, graph.getVertexByName(top.next.name));
                } else {
                    mainPrint(graph, print, graph.getVertexByName(top.next.next.name));
                }

            }
        } else if (top.outnum == 1) {
            mainPrint(graph, print, graph.getVertexByName(top.next.name));
        } else if (top.outnum == 0) {
            System.out.println(print);
            return;
        }
    }

    public void circlePrint(Graph graph, String print, Graph.Vertex top) {
        String[] s = print.split("\\s+");
        print = print + top.name + " ";
        //???if??????
        if (top.outnum == 2) {
            if (top.next.name > top.next.next.name) {
                //???????????????????????????
                if (top.next.access == false) {
                    top.next.access = true;
                    circlePrint(graph, print, graph.getVertexByName(top.next.name));
//                    init(graph, graph.getVertexByName(top.next.next.name));
                    circlePrint(graph, print, graph.getVertexByName(top.next.next.name));
                } else {

                    circlePrint(graph, print, graph.getVertexByName(top.next.next.name));
                }
            } else {
                if (top.next.next.access == false) {
                    top.next.next.access = true;
                    circlePrint(graph, print, graph.getVertexByName(top.next.next.name));
//                    init(graph, graph.getVertexByName(top.next.name));
                    circlePrint(graph, print, graph.getVertexByName(top.next.name));
                } else {
                    circlePrint(graph, print, graph.getVertexByName(top.next.next.name));
                }

            }
        } else if (top.outnum == 1) {
            circlePrint(graph, print, graph.getVertexByName(top.next.name));
        } else if (top.outnum == 0) {
            System.out.println(print);
            return;
        }
    }

    public boolean isCircle(Graph graph, int size, Graph.Vertex topV) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(topV.name);
        int cout = 0;
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                int top = queue.poll();
                cout++;
                Graph.Edge edge = graph.getVertexByName(top).next;
                while (edge != null) {
                    Graph.Vertex vertex = graph.getVertexByName(edge.name);
                    vertex.innum--;
                    if (vertex.innum == 0) {
                        queue.offer(vertex.name);
                    }
                    edge = edge.next;
                }
            }
        }
        return cout == size;
    }

    @Test
    public void test() {
        List<List<Node>> listAry = Parse.getList();
        List<Node> list = new ArrayList<Node>();
        for (int i = 0; i < listAry.size(); i++) {
            System.out.println("??????" + i + "????????????");
            List<Integer> recordCircle = new ArrayList<Integer>();
            Graph graph0 = new Graph();
            Graph graph1 = new Graph();
            Graph graph2 = new Graph();
            graph0 = parseService(listAry.get(i));
            graph1 = parseService(listAry.get(i));
            graph2 = parseService(listAry.get(i));
            Graph.Vertex top = getTop(graph0);
            graph0.print();
            System.out.println("????????????");
            branchPrint(graph0);
            if (!isCircle(graph1, listAry.get(i).size(), top)) {
                System.out.println("??????????????????");
                circlePrint(graph1, "", top);
            } else {
                System.out.println("?????????");
            }
            top = getTop(graph2);
            System.out.println("???????????????");
            mainPrint(graph2, "", top);
//
//            Graph.Vertex vertex = graph0.getVertexByName(2);
//            if (vertex!=null){
//                System.out.println(vertex.next.name);
//                System.out.println(vertex.next.next.name);
//            }
        }

    }

    @Test
    public void t() throws IOException {
        Runtime.getRuntime().exec("cmd.exe /k java -jar cfgparser.jar ArrayList.java remove >1.txt ");
    }
}
