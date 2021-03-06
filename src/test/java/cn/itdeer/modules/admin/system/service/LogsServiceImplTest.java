package cn.itdeer.modules.admin.system.service;

import cn.itdeer.ItdeerApp;
import cn.itdeer.common.base.BasePageBuilder;
import cn.itdeer.common.config.ConfigProperties;
import cn.itdeer.modules.admin.system.entity.Logs;
import cn.itdeer.modules.admin.system.repository.LogsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * 描述：系统-日志-Service接口层-测试
 * 创建人：Itdeer
 * 创建时间：2017/10/9 0:08
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class LogsServiceImplTest {


    @Autowired
    private LogsRepository logsRepository;
    @Autowired
    private ConfigProperties configProperties;

    @Test
    public void findByType() throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        //Page<Logs> page = logsRepository.findByType("a",BasePageBuilder.create(1,configProperties.getSystemPagesize(),sort));

        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = dateFormat1.parse("2017-08-17 21:23:41");
        Date endDate = dateFormat1.parse("2017-08-27 21:24:25");

        //Page<Logs> page = logsRepository.findByCreateDateAfterAndCreateDateBefore(startDate,endDate,BasePageBuilder.create(0,10,sort));

        Page<Logs> page = logsRepository.findAll(BasePageBuilder.create(1,5));



        for (Logs logs :page){
            System.out.println("Page1:"+logs.toString());
        }
        System.out.println(page.getTotalPages());       //总页数
        System.out.println(page.getTotalElements());    //总记录数
        System.out.println(page.getNumber());           //当前页数
        System.out.println("---------------------------------------------------------");
        System.out.println(page.isFirst());             //是第一页吗
        System.out.println(page.isLast());              //是最后一页吗

        System.out.println("---------------------------------------------------------");

        System.out.println(page.getSize());             //每页数
        System.out.println(page.hasPrevious());         //有上一页吗
        System.out.println(page.hasNext());             //有下一页吗

    }

    @Test
    public void findAll() throws Exception {
    }

    @Test
    public void save() throws Exception {
    }

    @Test
    public void delete() throws Exception {

        logsRepository.deleteAll();
    }

}