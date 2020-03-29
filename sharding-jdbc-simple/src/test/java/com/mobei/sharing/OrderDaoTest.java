package com.mobei.sharing;

import com.mobei.sharding.ShardingJdbcSimpleApplication;
import com.mobei.sharding.dao.DictDao;
import com.mobei.sharding.dao.OrderDao;
import com.mobei.sharding.dao.UserDao;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ShardingJdbcSimpleApplication.class})
public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private DictDao dictDao;

    @Test
    public void testInsertOrder() {
        for (int i = 0; i < 10; i++) {
            orderDao.insertOrder(new BigDecimal((i + 1) * 5), 1L, "WAIT_PAY");
        }
//    for (int i = 0 ; i<10; i++){
//        orderDao.insertOrder(new BigDecimal((i+1)*10),2L,"WAIT_PAY");
//    }
    }

    @Test
    public void testSelectOrderbyIds() {
        List<Long> ids = new ArrayList<>();
        //如果id全为奇数或者偶数,则只会查询一张表,否则会查询两张表
        ids.add(451066287620096000L);
//    ids.add(451066287771090944L);
        ids.add(451066287687204865L);
        List<Map> maps = orderDao.selectOrderbyIds(ids);
        System.out.println(maps);
    }

    @Test
    public void testSelectOrderbyUserAndIds() {
        List<Long> orderIds = new ArrayList<>();
        orderIds.add(451126529955463168L);
        orderIds.add(451126530123235328L);
        //查询条件中包括分库的键user_id
        int user_id = 1;
        List<Map> orders = orderDao.selectOrderbyUserAndIds(user_id, orderIds);
        JSONArray jsonOrders = new JSONArray(orders);
        System.out.println(jsonOrders);
    }

    @Test
    public void testInsertUser() {
        for (int i = 0; i < 10; i++) {
            Long id = i + 1L;
            userDao.insertUser(id, "姓名" + id);
        }
    }

    @Test
    public void testSelectUserbyIds() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);
        userIds.add(2L);
        List<Map> users = userDao.selectUserbyIds(userIds);
        System.out.println(users);
    }

    @Test
    public void testInsertDict() {
        dictDao.insertDict(1L, "user_type", "0", "管理员");
        dictDao.insertDict(2L, "user_type", "1", "操作员");
    }

    @Test
    public void testDeleteDict() {
        dictDao.deleteDict(1L);
        dictDao.deleteDict(2L);
    }

    @Test
    public void testSelectUserInfobyIds() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);
        userIds.add(2L);
        List<Map> users = userDao.selectUserInfobyIds(userIds);
        JSONArray jsonUsers = new JSONArray(users);
        System.out.println(jsonUsers);
    }

}