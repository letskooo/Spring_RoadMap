package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    // 세션 아이디, 회원 객체를 저장하는 Map
    // 동시에 여러 요청을 처리할 때는 ConcurrentHashMap을 써야함
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();
    private static final String SESSION_COOKIE_NAME = "mySessionId";

    /**
     * 세션 생성
     */
    public void createSession(Object value, HttpServletResponse response){

        // 세션 아이디를 생성하고 값을 세션에 저장

        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        // 쿠키 생성

        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request){

        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null)
            return null;
        return sessionStore.get(sessionCookie.getValue());
    }

    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request){

        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null)
            sessionStore.remove(sessionCookie.getValue());
    }



    public Cookie findCookie(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
}
