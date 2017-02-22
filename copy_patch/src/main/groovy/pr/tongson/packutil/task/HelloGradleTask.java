package pr.tongson.packutil.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2017/2/22<br>
 * <b>Author:</b> mmc_Kongming_Tongson<br>
 * <b>Description:</b> <br>
 */
public class HelloGradleTask extends DefaultTask {

    @TaskAction
        // 加上这个action的作用是当执行这个task的时候会自动执行这个方法
    void sayHello(){
        System.out.println("Hello Gradle Custom Task");
    }
}
