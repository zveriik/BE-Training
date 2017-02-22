package servlet.listeners;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Created by Aleksey_Zverkov on 22.02.2017.
 */
public class RequestListener implements ServletRequestListener {

    public void requestDestroyed(ServletRequestEvent event) {
        System.out.println("request being sent to " + event.getServletRequest().getRemoteAddr());
    }

    public void requestInitialized(ServletRequestEvent event) {
        System.out.println("now initializing request" + event.getServletRequest().getRemoteAddr());
    }
}
