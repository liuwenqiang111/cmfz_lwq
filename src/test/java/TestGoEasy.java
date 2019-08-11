import com.alibaba.fastjson.JSONObject;
import com.baizhi.CmfzLwqApplication;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzLwqApplication.class)
public class TestGoEasy {

    @Test
    public void TestGoEasys(){

        //发布消息  发布地址，appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-1b70900f99f24948b721262727495efb");
        //参数: 管道(标识)名称,发布的内容
        goEasy.publish("myChannel158", "Hello, 158 GoEasy!");
    }

    @Test
    public void TestGoEasyEcharts(){

        for (int i = 0; i < 10; i++) {

            //获取随机数
            Random random = new Random();
            random.nextInt(10);

            //将随机数放入数组
            int[] randoms ={random.nextInt(500),random.nextInt(100),random.nextInt(900)};

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("month",Arrays.asList("1月","2月","3月"));
            jsonObject.put("boys",randoms);
            jsonObject.put("girls",randoms);

            //将对象转为json格式字符串
            String content = jsonObject.toJSONString();

            //发布消息  发布地址，appkey
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-1b70900f99f24948b721262727495efb");
            //参数: 管道(标识)名称,发布的内容
            goEasy.publish("myChannel158", content);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
