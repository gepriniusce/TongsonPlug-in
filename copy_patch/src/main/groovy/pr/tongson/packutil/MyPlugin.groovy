package pr.tongson.packutil

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.DefaultDomainObjectSet

/**
 * build完成之后把apk文件拷贝到对应的位置的插件
 */
public class MyPlugin implements Plugin<Project> {
    private File apkDir;
    private File[] apkFiles;
    public static final String EXTENSION_NAME = "TongsonBuildApk";


    @Override
    void apply(Project project) {
        if (project.getPlugins().hasPlugin(AppPlugin)) {
            project.extensions.create(EXTENSION_NAME, BuildFileMoveExtension);
        }
        BuildFileMoveExtension apkExtension = BuildFileMoveExtension.getConfig(project);
        String projectName = apkExtension.projectName;

        project.extensions.create("dateAndTime", DateAndTimePluginExtension)

//        project.task('showTime') << {
//            println "Current time is " + new Date().format(project.dateAndTime.timeFormat)
//        }
//
//        project.tasks.create('showDate') << {
//            println "Current date is " + new Date().format(project.dateAndTime.dateFormat)
//        }

        project.tasks.create('aaa=packPatch') << {
            println "========================copy patch start========================"
            DefaultDomainObjectSet<ApplicationVariant> variants = project.android.applicationVariants;
            variants.all {
                variant ->
                    variant.outputs.each { output ->
                        if (output.outputFile != null && output.outputFile.name.endsWith(".apk") && "release".equals(variant.buildType.name)) {
                            def flavor = variant.flavorName.toString();
//                            println "========================flavor:" + flavor + "========================"
//                            def buildTime = new Date().format("yyyy_MM_dd");
//                            def apkname = projectName;
//                            def apkFileName = "${buildTime}_${apkname}_V${variant.versionName}_${flavor}.apk";
//                            def apkFile = new File(output.outputFile.getParent(), apkFileName);
//                            output.outputFile = apkFile;

                            apkDir = new File("${project.buildDir}/outputs/tinkerPatch/${flavor}/release");
                            if (!apkDir.exists() || !apkDir.isDirectory()) return;

                            File cnApkDestDir = new File("${project.rootDir}/patchApk");
                            if (!cnApkDestDir.exists() || !cnApkDestDir.isDirectory()) {
                                cnApkDestDir.mkdirs();
                            }

                            apkFiles = apkDir.listFiles();

                            for (File file : apkFiles) {
                                println file.name
                                if (isOkApkFile(file)) {
                                    file.renameTo(new File(cnApkDestDir, "${flavor}_" + file.name));
                                }
                            }
                        }
                    }
            }
//            def manifestFile = "${buildDir}/outputs/tinkerPatch/${flavor}/patch_signed.apk"
            println " ========================copy patch end========================"
        }

        project.afterEvaluate {
            System.out.println("afterEvaluate");
        }
    }

    static boolean isOkApkFile(File file) {
        if (file.name.contains("patch_signed")) {
            return true;
        }
        return false;
    }
}