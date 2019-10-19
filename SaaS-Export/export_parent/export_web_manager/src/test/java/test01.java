import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

/**
 * 测试类
 */
public class test01 {

    @Test
    public void md5() {
        System.out.println(new Md5Hash("123456").toString());
    }

    // 加密加盐
    @Test
    public void md5Salt() {
        // 用户名
        String username = "admin@export.com";
        // 密码
        String password = "123456";

        // 参数1：密码  参数2：盐；把用户名作为盐
        Md5Hash encodePassword = new Md5Hash(password, username);

        // e221d4d67d5e5e697424e80a50387a8b
        System.out.println("根据用户名作为盐,加密加盐后的结果是:" + encodePassword);
    }

}
