package hackathon.nomadworker.auth;

import com.mysql.cj.util.Base64Decoder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.*;

@Component
public class JwtProvider{

    private static Key key = Keys.hmacShaKeyFor("NOMADWORKER0NOMADWORKER0NOMADWORKER".getBytes(StandardCharsets.UTF_8));

    public static String createRandomnum(){

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 40;
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());


        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();


          //String encodedURL = Base64Utils.encodeToUrlSafeString(generatedString.getBytes());
        //jwt 생성시 인코딩을 해준다.

        return generatedString;
    }

    public static String createToken(String u_nickname) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofDays(1000).toMillis());

        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");


        String randstring = createRandomnum();
        //payload 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("KEY", randstring);
        payloads.put("NickName",u_nickname);


        //Key key = Keys.hmacShaKeyFor(createRandomnum().getBytes(StandardCharsets.UTF_8));


        // 토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setIssuedAt(now) // 발급시간(iat)
                .setExpiration(expiration) // 만료시간(exp)
                .setSubject("AccessToken") // 토큰 용도
                .signWith(key, SignatureAlgorithm.HS256)
                .compact(); // 토큰 생성

        System.out.println(jwt);

        return jwt;
    }

    //==JWT 토큰 유효성 체크 메서드==//
    public static Claims parseJwtToken(String token) {
        token = BearerRemove(token);
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

    }

    //==토큰 Bearer 제거 메서드==//
    private static String BearerRemove(String token) {
        return token.substring("Bearer".length());
    }

}
