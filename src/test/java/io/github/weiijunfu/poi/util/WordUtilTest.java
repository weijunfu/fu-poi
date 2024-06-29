package io.github.weiijunfu.poi.util;

import io.github.weijunfu.entity.User;
import io.github.weiijunfu.poi.entity.Image;
import org.apache.poi.common.usermodel.PictureType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @title  : 
 * @author : ijunfu <ijunfu@163.com>
 * @date   : 2024/6/28 21:52
 * @version: 1.0
 * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
class WordUtilTest {

    @Test
    void fillTable() throws Exception {
        InputStream inputStream = WordUtilTest.class.getClassLoader().getResourceAsStream("templates/user-list-template.docx");

        List<User> userList = new ArrayList<>();
        userList.add(new User("张三", 18, "2024-06-28", "avatar/female.png"));
        userList.add(new User("李四", 19, "2024-06-28", "avatar/male.png"));
        userList.add(new User("王五", 20, "2024-06-28", "logo.png"));

//        String AVATAR = "avatar";

        List<Map<String, Object>> list =
                userList.stream()
                        .map(user -> BeanUtil.toMap(user))
                        .collect(Collectors.toList());

        XWPFDocument document = WordUtil.fillTable(inputStream, list);

        FileOutputStream fileOutputStream = new FileOutputStream("E:/pdf/ddd.docx");

        document.write(fileOutputStream);

        fileOutputStream.flush();
        fileOutputStream.close();

        document.close();
    }

    @Test
    void fillTable_Image() throws Exception {
        ClassLoader classLoader = WordUtilTest.class.getClassLoader();

        InputStream inputStream = WordUtilTest.class.getClassLoader().getResourceAsStream("templates/user-list-template.docx");

        List<User> userList = new ArrayList<>();
        userList.add(new User("张三", 18, "2024-06-28", "avatar/female.png"));
        userList.add(new User("李四", 19, "2024-06-28", "avatar/male.png"));
        userList.add(new User("王五", 20, "2024-06-28", "logo.png"));

        List<Map<String, Object>> list =
                userList.stream()
                        .map(user -> {
                            Map<String, Object> map = BeanUtil.toMap(user);

                            InputStream in = classLoader.getResourceAsStream(user.getAvatar());

                            map.put("avatar", new Image(user.getAvatar(), in, PictureType.PNG, 64, 64));

                            return map;
                        })
                        .collect(Collectors.toList());

//        System.out.println(list);

        XWPFDocument document = WordUtil.fillTable(inputStream, list);

        FileOutputStream fileOutputStream = new FileOutputStream("E:/pdf/ddd-image.docx");

        document.write(fileOutputStream);

        fileOutputStream.flush();
        fileOutputStream.close();

        document.close();
    }

    @Test
    void fillTable2() throws Exception {
        ClassLoader classLoader = WordUtilTest.class.getClassLoader();

        InputStream inputStream = WordUtilTest.class.getClassLoader().getResourceAsStream("templates/user-list-template.docx");

        User user = new User("张三", 18, "2024-06-28", "avatar/female.png");
        Map<String, Object> map = BeanUtil.toMap(user);

        InputStream in = classLoader.getResourceAsStream(user.getAvatar());

        map.put("avatar", new Image(user.getAvatar(), in, PictureType.PNG, 64, 64));


        XWPFDocument document = WordUtil.fillTable(inputStream, map);

        FileOutputStream fileOutputStream = new FileOutputStream("E:/pdf/ddd-image2.docx");

        document.write(fileOutputStream);

        fileOutputStream.flush();
        fileOutputStream.close();

        document.close();
    }

    @Test
    void fillTable3() throws Exception {
        ClassLoader classLoader = WordUtilTest.class.getClassLoader();

        InputStream inputStream = WordUtilTest.class.getClassLoader().getResourceAsStream("templates/user-template.docx");

        User user = new User("张三", 18, "2024-06-28", "avatar/female.png");
        Map<String, Object> map = BeanUtil.toMap(user);

        InputStream in = classLoader.getResourceAsStream(user.getAvatar());

        map.put("avatar", new Image(user.getAvatar(), in, PictureType.PNG, 64, 64));


        XWPFDocument document = WordUtil.fillTable(inputStream, map);

        FileOutputStream fileOutputStream = new FileOutputStream("E:/pdf/ddd-image3.docx");

        document.write(fileOutputStream);

        fileOutputStream.flush();
        fileOutputStream.close();

        document.close();
    }

    @Test
    void fillWord() throws Exception {
        ClassLoader classLoader = WordUtilTest.class.getClassLoader();

        InputStream inputStream = WordUtilTest.class.getClassLoader().getResourceAsStream("templates/user-template2.docx");

        User user = new User("张三", 18, "2024-06-28", "avatar/female.png");
        Map<String, Object> map = BeanUtil.toMap(user);

        InputStream in = classLoader.getResourceAsStream(user.getAvatar());

        map.put("avatar", new Image(user.getAvatar(), in, PictureType.PNG, 64, 64));

        XWPFDocument document = WordUtil.fill(inputStream, map);

        FileOutputStream fileOutputStream = new FileOutputStream("E:/pdf/ddd-image3.docx");

        document.write(fileOutputStream);

        fileOutputStream.flush();
        fileOutputStream.close();

        document.close();
    }

}