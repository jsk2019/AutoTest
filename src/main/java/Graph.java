import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Graph {

    public Map<Integer, Vertex> vertexsMap;

    public class Vertex{
        //顶点值
        public int name;
        //与这个顶点相邻的边
        public Edge next;
        //入度
        public int innum ;
        //出度
        public int outnum ;
        Vertex(int name, Edge next){
            this.name = name;
            this.next = next;
            this.innum = 0;
            this.outnum = 0;
        }
    }

    public class Edge{
        public int name;
        public Edge next;
        //是否访问过
        public boolean access ;

        Edge(int name, Edge next){
            this.name = name;
            this.next = next;
            this.access = false;
        }
    }

    Graph(){
        this.vertexsMap = new HashMap<>();
    }


    public void insertVertex(int vertexName){
        Vertex vertex = new Vertex(vertexName, null);
        vertexsMap.put(vertexName, vertex);
    }

    //根据边的node数值找到顶点对象
    public Vertex getVertexByName(int num){
        Vertex beginVertex = vertexsMap.get(num);
        return beginVertex;
    }

    //插入边（自动创建顶点，判断有无重复的边）
    public void insertEdge(int begin, int end){
        Vertex beginVertex = vertexsMap.get(begin);
        Vertex endVertex = vertexsMap.get(end);
        if(beginVertex == null){
            beginVertex = new Vertex(begin, null);
            vertexsMap.put(begin, beginVertex);
        }
        if(endVertex == null){
            endVertex = new Vertex(end, null);
            vertexsMap.put(end, endVertex);
        }
        Edge edge = new Edge(end, null);
        if(beginVertex.next == null){
            beginVertex.next = edge;
            beginVertex.outnum++;
            endVertex.innum++;
        }else{
            boolean unique = true;
            Edge lastEdge = beginVertex.next;
            while(lastEdge.next != null){
                if (lastEdge.name==edge.name) unique =false;
                lastEdge = lastEdge.next;
                if (lastEdge.name==edge.name) unique =false;
            }
            if (unique) {
                lastEdge.next = edge;
                beginVertex.outnum++;
                endVertex.innum++;
            }
        }
    }


    public void print(){
        Set<Map.Entry<Integer, Vertex>> set = vertexsMap.entrySet();
        Iterator<Map.Entry<Integer, Vertex>> iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, Vertex> entry = iterator.next();
            Vertex vertex = entry.getValue();
            Edge edge = vertex.next;
            while(edge != null){
                System.out.println(vertex.name + " ---> " + edge.name);
                edge = edge.next;
            }
        }
    }



    public static void main(String[] args) {
        Graph graph = new Graph();
    //    graph.insertVertex(1);
        graph.insertEdge(0,1);
        graph.insertEdge(0,2);
        graph.insertEdge(0,2);
        graph.insertEdge(0,2);

        Vertex vertex = graph.vertexsMap.get(0);
        graph.print();
    }



}