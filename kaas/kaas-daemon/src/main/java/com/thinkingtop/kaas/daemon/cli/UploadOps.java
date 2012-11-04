package com.thinkingtop.kaas.daemon.cli;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class UploadOps implements Operator{
	@Override
	public void doOps(String args[]) {
		for(int i=0;i<args.length;i++) {
			System.out.println(args[i]+" "+i);
		}
		Options opts = new Options();
		//opts.addOption(opt);
        opts.addOption("h", false, " help ");  
        opts.addOption("u", true, " user");  
        opts.addOption("p", true, " password");  
        BasicParser parser = new BasicParser();  
        CommandLine cl;  
        try {
            cl = parser.parse(opts, args);  
            System.out.println(cl.getOptions().length+"        "+"---");
            if (cl.hasOption('h')) {  
                HelpFormatter hf = new HelpFormatter();  
                hf.printHelp("Options", opts);  
            } else {  
                System.out.println(cl.getOptionValue("u"));  
                System.out.println(cl.getOptionValue("p"));  
            }  
        } catch (ParseException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
	}

}
