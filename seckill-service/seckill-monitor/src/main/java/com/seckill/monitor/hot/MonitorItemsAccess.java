package com.seckill.monitor.hot;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class MonitorItemsAccess {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DruidDataSource dataSource;

    /**
     * 定义热点数据标准：
     * 1.某一件商品访问量>N
     * 2.最近N小时
     */
    public List<String> loadData() throws Exception {
        //获取连接对象
        Connection connection = dataSource.getConnection();
        //Statement
        Statement statement = connection.createStatement();

        //执行查询
        ResultSet resultSet = statement.executeQuery(druidSQL());

        //解析结果集
        List<String> ids = new ArrayList<>();
        while (resultSet.next()) {
            //获取uri,格式：/web/items/S1235433012716498944.html
            String uri = resultSet.getString("uri");
            //处理掉/web/items/和.html
            uri = uri.replace("/web/items/", "").replace(".html", "");
            //记录ID
            ids.add(uri);
        }

        //关闭资源
        resultSet.close();
        statement.close();
        connection.close();
        return ids;
    }


    /**
     * SQL组装
     */
    public String druidSQL() {
        //SQL语句
        String prefix = "SELECT uri, COUNT(*) viewCount FROM logsitems " +
                // Druid模式是UTC时区 所有的时间进行 -8 处理
                "WHERE __time >= CURRENT_TIMESTAMP + INTERVAL '7' HOUR ";
        //后部分
        String suffix = "GROUP BY uri HAVING viewCount >2";

        //SQL中间部分  AND uri NOT IN ('/web/items/S1235433012716498944.html')
        //SKU_S1235433012716498944
        String sql = "";

        //基于Redis中存的热点商品的key来过滤排除要查询的数据(排除redis中已有的热点商品)
        Set<String> keys = redisTemplate.keys("SKU_*");//所有以SKU_开始的key全部查询出来
        if (keys != null && keys.size() > 0) {
            sql = " AND uri NOT IN (";
            for (String key : keys) {
                sql += "'/web/items/" + key.substring(4) + ".html',";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
        }
        return prefix + sql + suffix;
    }
}