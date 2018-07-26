[![](https://jitpack.io/v/fengwenyi/JavaLib-quartz.svg)](https://jitpack.io/#fengwenyi/JavaLib-quartz)

# JavaLib-quartz

Spring Boot Quartz

### 快速使用

1.添加依赖：

```xml
    <!-- 测试版需要指定仓库 -->
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    
    <dependencies>
        <dependency>
            <groupId>com.github.fengwenyi</groupId>
            <artifactId>JavaLib-quartz</artifactId>
            <version>1.0-alpha</version>
        </dependency>
    </dependencies>
```

2.task/HelloTask.java

```java
import com.fengwenyi.javalib.quartz.QuartzTask;
import org.springframework.stereotype.Component;

/**
 * @author Wenyi Feng
 */
@Component
public class HelloTask extends QuartzTask {
}
```
    
3.job/HelloJob.java

```java
import *.quartz.service.HelloService; // 原始包名被隐藏了
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Wenyi Feng
 */
public class HelloJob extends QuartzJobBean {

    @Autowired
    private HelloService helloService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        helloService.sayHello();
    }
}
```

4.service/HelloService.java

```java
/**
 * @author Wenyi Feng
 */
public interface HelloService {

    void sayHello();

}
```

5.service/impl/HelloServiceImpl.java

```java
import com.fengwenyi.javalib.util.ToastUtil; // https://github.com/fengwenyi/JavaLib
import *.quartz.service.HelloService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Wenyi Feng
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello() {
        ToastUtil.show("Hello : " + new Date());
    }
}
```

6.controller/JobController.java

```java
import com.fengwenyi.javalib.quartz.ScheduleBean;
import *.quartz.job.HelloJob;
import *.quartz.task.HelloTask;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wenyi Feng
 */
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private HelloTask task;

    @RequestMapping("/hello")
    public boolean hello() {
        ScheduleBean scheduleBean = new ScheduleBean();
        scheduleBean.setClazz(HelloJob.class);
        scheduleBean.setTime(3);
        scheduleBean.setJobName("JOB_NAME");
        scheduleBean.setJobGroup("GROUP_1");
        scheduleBean.setTriggerName("TRIGGER_NAME");
        scheduleBean.setTriggerGroup("GROUP_1");
        scheduleBean.setScheduler(scheduler);

        try {
            boolean rs = task.start(scheduleBean);
            return rs;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

}
```

7.目录结构

![package class](./images/package-class.png)

8.运行测试

![run test](./images/run-test.png)

### About Me

```
    ©author Wenyi Feng
```

### Licensed

```
   Copyright 2018 Wenyi Feng(xfsy_2015@163.com)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.