package pr.tongson.packutil;

import org.gradle.api.Project;
import org.gradle.api.tasks.Input;

import groovy.transform.CompileStatic;

/**
 * <b>Project:</b> com.mmc.plugin <br>
 * <b>Create Date:</b> 2016/9/10 <br>
 * <b>Author:</b> Devin <br>
 * <b>Address:</b> qingyongai@linghit.com <br>
 * <b>Description:</b>  <br>
 */
@CompileStatic
class BuildFileMoveExtension {

    @Input
    String projectName //输出目录

    @Input
    boolean isgm = false //是否是GM版本

    public static BuildFileMoveExtension getConfig(Project project) {
        BuildFileMoveExtension config = project.getExtensions().findByType(BuildFileMoveExtension.class);
        if (config == null) config = new BuildFileMoveExtension();
        return config;
    }

}
