package com.webside.ofp.common.util;
import com.jacob.activeX.*;
import com.jacob.com.*;

public class Print {
    private static final String PRINT_NAME=new String("FX7AF20D"); 
    /**
     * @param path 打印路径地址，形如 \\XX\\YY.xls
     * @param copies 打印份数
     */
    public static void printExcel(String path,int copies){
        if(path.isEmpty()||copies<1){
            return;
        }
        //初始化COM线程
        ComThread.InitSTA();
        //新建Excel对象
        ActiveXComponent xl=new ActiveXComponent("Excel.Application");
        try { 
            System.out.println("Version=" + xl.getProperty("Version"));
            //设置是否显示打开Excel  
            Dispatch.put(xl, "Visible", new Variant(true));
            //打开具体的工作簿
            Dispatch workbooks = xl.getProperty("Workbooks").toDispatch(); 
            Dispatch excel=Dispatch.call(workbooks,"Open",System.getProperty("user.dir")+path).toDispatch(); 
            
            //设置打印属性并打印
            Dispatch.callN(excel,"PrintOut",new Object[]{Variant.VT_MISSING, Variant.VT_MISSING, new Integer(copies),
                    new Boolean(true),Variant.VT_MISSING, new Boolean(true),Variant.VT_MISSING, ""});
            
            //关闭文档
            //Dispatch.call(excel, "Close", new Variant(false));  
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally{
            //xl.invoke("Quit",new Variant[0]);
            //始终释放资源 
            ComThread.Release(); 
        } 
    }
}