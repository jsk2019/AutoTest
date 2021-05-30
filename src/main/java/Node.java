import com.alibaba.excel.annotation.ExcelProperty;

public class Node {
    @ExcelProperty(index = 0)
    private String  method;

    @ExcelProperty(index = 1)
    private int  node;

    @ExcelProperty(index = 2)
    private int  parent;

    @ExcelProperty(index = 3)
    private int  height;

    @ExcelProperty(index = 4)
    private int  startx;

    @ExcelProperty(index = 5)
    private int  starty;

    @ExcelProperty(index = 6)
    private int  endx;

    @ExcelProperty(index = 7)
    private int  endy;

    @ExcelProperty(index = 8)
    private String  content;

    public Node() {
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getStartx() {
        return startx;
    }

    public void setStartx(int startx) {
        this.startx = startx;
    }

    public int getStarty() {
        return starty;
    }

    public void setStarty(int starty) {
        this.starty = starty;
    }

    public int getEndx() {
        return endx;
    }

    public void setEndx(int endx) {
        this.endx = endx;
    }

    public int getEndy() {
        return endy;
    }

    public void setEndy(int endy) {
        this.endy = endy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Node{" +
                "method='" + method + '\'' +
                ", node=" + node +
                ", parent=" + parent +
                ", height=" + height +
                ", startx=" + startx +
                ", starty=" + starty +
                ", endx=" + endx +
                ", endy=" + endy +
                ", content='" + content + '\'' +
                '}';
    }
}
