package org.smartloli.kafka.eagle.web.sso.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.smartloli.kafka.eagle.common.util.KConstants;
import org.smartloli.kafka.eagle.web.pojo.Signiner;

/**
 * @author lh
 * @since 2018/4/1
 */
public class ShiroUtils {

    public static Signiner getPrincipal()  {
        Subject subject = SecurityUtils.getSubject();
        Session sesison = subject.getSession();
        Signiner signiner = (Signiner) sesison.getAttribute(KConstants.Login.SESSION_USER);
        return signiner;
    }

}
