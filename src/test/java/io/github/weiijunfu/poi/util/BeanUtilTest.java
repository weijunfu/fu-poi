package io.github.weiijunfu.poi.util;

import io.github.weijunfu.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 *
 * @title  : 
 * @author : ijunfu <ijunfu@163.com>
 * @date   : 2024/6/28 22:07
 * @version: 1.0
 * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
class BeanUtilTest {

    @Test
    void toMap() {
        User user = new User("张三", 18, "2024-06-28", "avatar/female.png");

        Map<String, Object> map = BeanUtil.toMap(user);
        System.out.println(map);
    }
}