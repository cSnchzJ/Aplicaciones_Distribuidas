package Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControl {
    @PostMapping(value = "login")
    public String login(){
        return "login from public endpoint";
    }
    @PostMapping(value="register")
    public String register(){
        return "Register form public endpoint";
    }
}
