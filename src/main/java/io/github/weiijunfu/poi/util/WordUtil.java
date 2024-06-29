package io.github.weiijunfu.poi.util;

import io.github.weiijunfu.poi.entity.Image;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @title  : POI Word 工具类
 * @author : ijunfu <ijunfu@163.com>
 * @date   : 2024/6/28 21:16
 * @version: 1.0
 * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
public class WordUtil {

    /*正则表达式： 匹配以`{{`开头，以`}}`结尾的字符串*/
    public final static String REGEX = "\\{\\{(.+?)\\}\\}";

    public static Pattern compile() {
        return Pattern.compile(REGEX);
    }

    /**
     * @title  : 填充Word模板
     * @param	: inputStream word模板输入流
	 * @param	: map 待填充的数据
     * @return : org.apache.poi.xwpf.usermodel.XWPFDocument 填充完成后的word模板
     * @author : ijunfu <ijunfu@163.com>
     * @date   : 2024/6/29 10:21
     * @version: 1.0
     * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
     */
    public static XWPFDocument fill(InputStream inputStream, Map<String, Object> map) {
        try {
            XWPFDocument document = new XWPFDocument(inputStream);

            for(XWPFParagraph paragraph : document.getParagraphs()) {       // 获取所有段落

                for(XWPFRun run : paragraph.getRuns()) {                    // 获取所有文档中具有相同格式属性（如字体、颜色、大小等）的连续文本【片段】
                    String text = run.getText(0);

                    if(Objects.isNull(text)) {  // 如果内容为空，则跳过，避免空指针错误
                        continue;
                    }

                    Matcher matcher = compile().matcher(text);
                    if(matcher.find()) {   // 匹配成功
                        run.setText(null, 0);    // 清空占位符

                        String key = matcher.group(1);  // 获取匹配成功的字符串

                        Object value = map.get(key);    // 获取待填充的内容
                        if(value != null) {
                            setRun(run, value);         // 进行数据填充
                        }
                    }
                }
            }

            return document;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @title  :  填充word中表格内容
     * @param	: inputStream word模板输入流
	 * @param	: map 待填充内容
     * @return : org.apache.poi.xwpf.usermodel.XWPFDocument
     * @author : ijunfu <ijunfu@163.com>
     * @date   : 2024/6/29 10:34
     * @version: 1.0
     * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
     */
    public static XWPFDocument fillTable(InputStream inputStream, Map<String, Object> map) {

        try {
            XWPFDocument document = new XWPFDocument(inputStream);

            for(XWPFTable table : document.getTables()) {       // 读取所有表格

                for(XWPFTableRow row : table.getRows()) {       // 读取表格中的所有行

                    for(XWPFTableCell cell : row.getTableCells()) {     // 读取行中所有的列
                        String text = cell.getText();   // 单元格文本
                        Matcher matcher = compile().matcher(text);
                        if(matcher.find()) {    // 匹配成功
                            cleanCell(cell);    // 清空单元格内容

                            String key = matcher.group(1);  // 获取匹配成功的字符串， 例如：{{name}}, 取值为name

                            Object value = map.get(key);
                            if(value != null) {
                                setCell(cell, value);      // 进行数据填充
                            }
                        }
                    }
                }

            }

            return document;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @title  : 清空单元格内容
     * @param	: cell 待清空的单元格
     * @return : void
     * @author : ijunfu <ijunfu@163.com>
     * @date   : 2024/6/29 10:33
     * @version: 1.0
     * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
     */
    private static void cleanCell(XWPFTableCell cell) {
        for(int paragraphIndex = 0; paragraphIndex< cell.getParagraphs().size(); paragraphIndex++) {
            cell.removeParagraph(paragraphIndex);
        }
    }

    /**
     * @title  : 填充word中表格内容
     * @param	: inputStream   word模板输入流
	 * @param	: dataList 待填充的数据
     * @return : org.apache.poi.xwpf.usermodel.XWPFDocument
     * @author : ijunfu <ijunfu@163.com>
     * @date   : 2024/6/29 10:42
     * @version: 1.0
     * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
     */
    public static XWPFDocument fillTable(InputStream inputStream, List<Map<String, Object>> dataList) {
        Map<Integer, String> columnMap = new HashMap<>();
        int startRowIndex = 0;
        Boolean startFill = Boolean.FALSE;

        try {
            XWPFDocument document = new XWPFDocument(inputStream);

            List<XWPFTable> tables = document.getTables();

            for(int tableIndex=0; tableIndex<tables.size(); tableIndex++) {                         // 处理每张表格
                XWPFTable table = tables.get(tableIndex);

                for(int rowIndex = 0; rowIndex < table.getRows().size(); rowIndex++) {              // 处理每行

                    XWPFTableRow row = table.getRow(rowIndex);

                    for(int columnIndex = 0; columnIndex < row.getTableCells().size(); columnIndex++) { // 处理每列

                        XWPFTableCell cell = row.getCell(columnIndex);

                        String text = cell.getText();

                        Matcher matcher = compile().matcher(text);

                        if(matcher.find()) {
                            cleanCell(cell);

                            String content = matcher.group(1);

                            columnMap.put(columnIndex, content);    // 记录列索引和待填充的属性名称

                            if(!startFill) {    // 匹配成功后，记录并开始填充
                                startFill = Boolean.TRUE;
                            }

                            // 填充单元格
                            fillCell(dataList, startRowIndex, columnMap, columnIndex, cell);
                        }

                    }

                    if(startFill) { // 开始填充之后，记录开始填充的行索引
                        startRowIndex++;
                    }
                }

                if(startRowIndex < dataList.size()) {   // 填充剩余数据
                    for(int rowIndex=startRowIndex; rowIndex<dataList.size(); rowIndex++) {
                        XWPFTableRow row = table.createRow();

                        for(int columnIndex=0; columnIndex<columnMap.size(); columnIndex++) {

                            // 处理特殊的第一列
                            XWPFTableCell cell = columnIndex == 0 ? row.getCell(0) : row.addNewTableCell();

                            fillCell(dataList, rowIndex, columnMap, columnIndex, cell);
                        }
                    }
                }
            }

            return document;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    /**
     * @title  : 填充单元格
     * @param	: dataList      数据列表
	 * @param	: rowIndex      行索引
	 * @param	: columnMap     记录的列索引和属性名称
	 * @param	: columnIndex   列索引
	 * @param	: cell          单元格
     * @return : void
     * @author : ijunfu <ijunfu@163.com>
     * @date   : 2024/6/29 10:49
     * @version: 1.0
     * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
     */
    private static void fillCell(List<Map<String, Object>> dataList, int rowIndex, Map<Integer, String> columnMap, int columnIndex, XWPFTableCell cell) throws InvalidFormatException, IOException {
        Object o = dataList.get(rowIndex).get(columnMap.get(columnIndex));

        setCell(cell, o);
    }

    /**
     * @title  :  设置单元格内容
     * @param	: cell  待设置的单元格
	 * @param	: obj   待设置的内容
     * @return : void
     * @author : ijunfu <ijunfu@163.com>
     * @date   : 2024/6/29 10:51
     * @version: 1.0
     * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
     */
    private static void setCell(XWPFTableCell cell, Object obj) throws IOException, InvalidFormatException {
        if(obj instanceof Image) {  // 插入图片
            Image image = (Image) obj;

            XWPFParagraph paragraph = cell.addParagraph();
            XWPFRun run = paragraph.createRun();
            setImage(run, image);

        } else {    // 插入文本
            cell.setText(obj.toString());
        }
    }

    /**
     * @title  : 填充段落内容
     * @param	: run   被替换的一段文本
	 * @param	: obj   待填充的内容
     * @return : void
     * @author : ijunfu <ijunfu@163.com>
     * @date   : 2024/6/29 10:55
     * @version: 1.0
     * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
     */
    private static void setRun(XWPFRun run, Object obj) {
       if(obj instanceof Image) {   // 插入图片
           setImage(run, (Image) obj);
       } else  {    // 插入文本
           run.setText(obj.toString(), 0);
       }
    }

    /**
     * @title  : 设置图片
     * @param	: xwpfRun 文档中具有相同格式属性（如字体、颜色、大小等）的连续文本片段
     * @param	: image 需要插入的图片
     * @return : void
     * @author : ijunfu <ijunfu@163.com>
     * @date   : 2024/6/29 10:11
     * @version: 1.0
     * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
     */
    private static void setImage(XWPFRun xwpfRun, Image image) {
        try {
            xwpfRun.addPicture(
                    image.getImageData(),
                    image.getType(),
                    image.getName(),
                    Units.toEMU(image.getWidth()),
                    Units.toEMU(image.getHeight())
            );
        } catch (InvalidFormatException e) {

            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private WordUtil(){}
}
