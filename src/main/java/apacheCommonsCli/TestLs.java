package apacheCommonsCli;

import org.apache.commons.cli.*;

/**
 * @author litao34
 * @ClassName TestLs
 * @Description TODO
 * @CreateDate 2022/7/21-11:17 AM
 **/
public class TestLs {

    public static void main(String[] args) {
        Options options = new Options();
        Option all = Option.builder().argName("a").longOpt("all").desc("de not hide entries starting with.").hasArg(false).build();
        options.addOption("A","almost-all",false,"do not list  implied . and ..");
        options.addOption("b","escape",false,"print octal escapes for non-graphic characters");
        options.addOption(Option.builder("SIZE").longOpt("block-size").desc("use SIZE-Byte blokcs").hasArg().build());
        options.addOption("B","ignore-backups",false,"do not list implied entries ending with ~");

        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine parse = commandLineParser.parse(options, args);
            if (parse.hasOption("block-size")){
                String optionValue = parse.getOptionValue("block-size");
                System.out.println(optionValue);
            }
        } catch (ParseException e) {
            System.out.println("the error param");
        }


    }
}
