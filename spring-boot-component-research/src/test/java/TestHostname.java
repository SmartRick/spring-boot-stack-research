import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Date: 2022/1/11
 * @Author: SmartRick
 * @Description: TODO
 */
@RunWith(JUnit4.class)
public class TestHostname {
    @Test
    public void test1() {
        String redisHost = "redis";
        String realHost = getRealHost(redisHost);
        System.out.println(realHost);
    }
    public String getRealHost(String host){
        if (!isIp(host)) {
            try {
                InetAddress[] allByName = InetAddress.getAllByName(host);
                host = allByName[0].getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return host;
    }

    public boolean isIp(String host) {
        Pattern pattern = Pattern.compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
        Matcher matcher = pattern.matcher(host);
        return matcher.find();
    }
}
