package util;
public class UserOrgUtil {
        public static final ThreadLocal orgVal =
                                new ThreadLocal();

        public static String getCurrentOrg()  {
              String s = (String) orgVal.get();
              return s;
        }
        public static void setCurrentOrg(String orgName) {
			orgVal.set(orgName);
        }
}
