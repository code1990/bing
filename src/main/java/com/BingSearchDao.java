package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: bing
 * @Date: 2019-12-28 18:11
 * @Author: code1990
 * @Description:
 */
@Component
public class BingSearchDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MailService mailService;

    private String dropTableSql = "DROP TABLE IF EXISTS hello;";
    private String createTableSql = "CREATE TABLE hello (\n" +
            "\tid bigint(20) PRIMARY KEY AUTO_INCREMENT,\n" +
            "\tpage_num bigint,\n" +
            "\tpage_title longtext,\n" +
            "\tpage_url longtext,\n" +
            "\tcreate_time datetime\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    private String insertTableSql = "INSERT INTO hello(page_num,page_title,page_url,create_time) VALUES (:page_num,:page_title,:page_url,now());";

    public void dropTable(String tableName){
        jdbcTemplate.execute(dropTableSql.replace("hello",tableName));
    }
    public void createTable(String tableName){
        jdbcTemplate.execute(createTableSql.replace("hello",tableName));
    }

    public void batchInsertPage(String tableName, List<Map<String, String>> entities) {
        String sql = insertTableSql.replace("hello", tableName);
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(entities.toArray());
        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    public Long checkKwInfo(int index) {
        String sql = "select count(*) as counts from kw_info_" + index + " where 1=1";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public void updateKwInfo(String kw, int index) {
        String sql = "update kw_info_" + index + " set status='ok' where kw_title='" + kw.trim() + "';";
        jdbcTemplate.execute(sql);
    }

    public void insertKwInfo(List<Map<String, String>> entities, int index) {
        String sql = "INSERT INTO kw_info_" + index + "(kw_num,kw_title,create_time) VALUES (:kw_num,:kw_title,now());";
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(entities.toArray());
        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    public void deleteKwInfo(int index) {
        String sql = "delete from kw_info_" + index + " where 1=1";
        jdbcTemplate.execute(sql);
    }


    public String getMinKwInfo(int index) {
        String sql = "select min(kw_num) from kw_info_" + index + " where  status is null;";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public List<String> getAllKwList(int index) {
        String sql = "select kw_num,kw_title from kw_info_" + index + " where status is null ;";
        List<String> data = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {

                return rs.getString("kw_num") + ":" + rs.getString("kw_title");

            }

        }, null);
        return data;
    }

    public List<String> getAllPageInfoByType(List<String> kwList, String type) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < kwList.size(); i++) {
            String str = kwList.get(i).trim();
            String tableName = "kw_"  + str.replaceAll(" ", "_");
            if (type.equals("drop")) {
                list.add(dropTableSql.replace("hello", tableName));
            } else if (type.equals("create")) {
                list.add(createTableSql.replace("hello", tableName));
            }

        }
        return list;
    }

    public List<Map<String, String>> getKwMapInfo(List<String> kwList) {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < kwList.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("kw_num", (i + 1) + "");
            map.put("kw_title", kwList.get(i).trim());
            list.add(map);
        }
        return list;
    }

    public List<List<Map<String, String>>> getListMapGroup(List<Map<String, String>> taskList, int threadNum) {
        List<List<Map<String, String>>> list = new ArrayList<>();
        int total = taskList.size();
        // 计算出余数
        int remainder = total % threadNum;
        // 计算出商
        int number = total / threadNum;
        // 偏移量
        int offset = 0;
        for (int i = 0; i < threadNum; i++) {
            if (remainder > 0) {
                List<Map<String, String>> subList = taskList.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
                list.add(subList);
            } else {
                List<Map<String, String>> subList = taskList.subList(i * number + offset, (i + 1) * number + offset);
                list.add(subList);
            }
        }
        return list;
    }

    /*public void get*/

}
