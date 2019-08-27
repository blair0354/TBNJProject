package util;

import org.apache.log4j.Logger;

public class LogUtil {
	static Logger logger = Logger.getLogger(LogUtil.class);	
    static public Logger getLogger(){
        return logger;
    }	
}
