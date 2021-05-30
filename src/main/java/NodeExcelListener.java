import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class NodeExcelListener extends AnalysisEventListener {
    private List<Object> datas = new ArrayList<>();
    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(Object data, AnalysisContext context) {
        datas.add(data);
    }

    //所以的数据解析完了调用
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("finish");
    }

}
