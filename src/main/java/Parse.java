import com.alibaba.excel.EasyExcel;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parse {
    public static List<List<Node>> getList(){
        File file = new File("1.txt");// 将读取的txt文件
        File file2 = new File("1.xls");// 将生成的excel表格
        if (file.exists() && file.isFile()) {
            InputStreamReader read = null;
            String line = "";
            BufferedReader input = null;
            WritableWorkbook wbook = null;
            WritableSheet sheet;
            try {
                read = new InputStreamReader(new FileInputStream(file), "utf-8");
                input = new BufferedReader(read);

                wbook = Workbook.createWorkbook(file2);// 根据路径生成excel文件
                sheet = wbook.createSheet("first", 0);// 新标签页

                int m = 0;// excel行数
                int n = 0;// excel列数
                Label t;
                while ((line = input.readLine()) != null) {
                    String[] words = line.split("[ \t]");// 把读出来的这行根据空格或tab分割开

                    for (int i = 0; i < words.length; i++) {
                        if(i==0||i==8||m==0) {
                            if (!words[i].matches("\\s*")) { // 当不是空行时
                                t = new Label(n, m, words[i].trim());
                                sheet.addCell(t);
                                n++;
                            }
                        }
                        else if (i==1||i==2||i==3||i==4||i==5||i==6||i==7){
                            jxl.write.Number number = new jxl.write.Number(n,m,Integer.parseInt(words[i].trim()));
                            sheet.addCell(number);
                            n++;
                        }
                    }
                    n = 0;// 回到列头部
                    m++;// 向下移动一行
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            } finally {
                try {
                    wbook.write();
                    wbook.close();
                    input.close();
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("file is not exists or not a file");
            System.exit(0);
        }
        List<Node> list = new ArrayList<Node>();
        String PATH ="E:\\上课资料\\大三下\\软件测试\\1.xls";
        list = EasyExcel.read(PATH,Node.class,new NodeExcelListener()).sheet().doReadSync();
        List<List<Node>> list1 = new ArrayList<List<Node>>();
        String recordList = list.get(0).getMethod();
        int sumList = 0;
        List<Node> initList= new ArrayList<Node>();
        list1.add(initList);
        for (int i = 0;i<list.size();i++){
            if (recordList==list.get(i).getMethod()){
                list1.get(sumList).add(list.get(i));
            }else {
                List<Node> initList2= new ArrayList<Node>();
                list1.add(initList2);
                sumList++;
                recordList = list.get(i).getMethod();
                list1.get(sumList).add(list.get(i));
            }
        }
//        for (int i =0;i<list1.size();i++){
//            for(int j = 0;j<list1.get(i).size();j++){
//                System.out.println(list1.get(i).get(j));
//            }
//        }
        return list1;
    }
}
