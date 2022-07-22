package apacheCommonsCli;

import lombok.SneakyThrows;
import org.apache.commons.cli.*;

import java.util.Objects;
import java.util.Properties;

/**
 * @author litao34
 * @ClassName CommandClie
 * @Description TODO
 * @CreateDate 2022/7/21-9:43 AM
 **/
public class CommandCli {

    public static void main(String[] args) {
        /**
         *@part1 创建选项组
         */
        // 直接创建选项组
        Options options = new Options();
        // Boolean属性
        // 将选项添加到选项组中, 第一个参数代表选项的值 第二个参数代表该选项是否有参数 第三个参数是改选项的描述信息
        options.addOption("t",false,"显示当前时间");
        // 有参数的属性
        options.addOption("c",true,"显示当前时区");

        // 分离创建 先创建选项 在添加到选项组中
        // 使用构造函数创建 如果没有属性可以省略hasArg参数
        Option help = new Option("h","展示帮助信息");
        // 构建更为复杂的参数 使用build模式
        Option logFile = Option.builder("logFile")
                .argName("file")
                .hasArg()
                .desc("执行日志文件地址")
                .build();

        Option dParam = Option.builder("D")
                .hasArg()
                .valueSeparator('=')
                .argName("value=properties")
                .desc("指定键值对")
                .build();

        options.addOption(help);
        options.addOption(logFile);
        options.addOption(dParam);

        /**
         * @part2 创建命令解析器 解析命令
         * @Since 1.5 提供了精确匹配的参数 如果没有开启精确匹配,那么如果-de参数在只会被解析为-d参数 如果开启了精确匹配 就会被解析为-d和-e
         */
        CommandLineParser commandLineParser = new PosixParser();

        // 将选项组和参数添加到命令解析器中生成命令解析数据
        CommandLine commandLine = null;
        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**
         * @Part3 解析参数
         */
        // 创建帮助信息
        if (commandLine.hasOption('h')){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("testDemo",options,true);
            return;
        }

        if (commandLine.hasOption("t")){
            System.out.println("the args contain t");
        }

        // 如果解析带有参数的选项时 不能解析参数中的空格 如 "this is a test args" 只能解析出this
        String cValue = commandLine.getOptionValue('c');
        if (Objects.nonNull(cValue)){
            System.out.println("-c 参数 " + cValue);
        }

        // 解析参数 2
        if (commandLine.hasOption("lofFile")){
            String logFileValue = commandLine.getOptionValue("logFile");
            System.out.println("包含参数 logFile" + logFileValue);
        }

        if (commandLine.hasOption('D')){
            Properties d = commandLine.getOptionProperties("D");
            for (Object o : d.keySet()) {
                System.out.println(o);
            }
        }

    }
}
