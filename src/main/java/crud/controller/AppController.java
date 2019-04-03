package crud.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import crud.model.Role;
import crud.model.User;
import crud.service.RoleService;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@Controller
public class AppController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AppController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(String error, String logout, ModelAndView modelAndView) {
        if (error != null) {
            modelAndView.addObject("error", "Username or password is incorrect.");
        }
        if (logout != null) {
            modelAndView.addObject("message", "Logged out successfully.");
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/admin")
    public ModelAndView startPageAdmin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @RequestMapping(value = "/user")
    public ModelAndView helloPageUser(ModelAndView modelAndView) {
        modelAndView.setViewName("user");
        return modelAndView;
    }


    @RequestMapping(value = "/vkAuth")
    public String VKAuthorization() {
        final int clientId = 6914326;
        final String display = "page";
        final String redirectUri = "http://localhost:8080/vk";
        final String scope = "offline";
        final String responseType = "code";
        final String v = "5.52";
        StringBuilder url = new StringBuilder();
        url.append("https://oauth.vk.com/authorize?client_id=")
                .append(clientId)
                .append("&display=")
                .append(display)
                .append("&redirect_uri=")
                .append(redirectUri)
                .append("&scope=")
                .append(scope)
                .append("&response_type=")
                .append(responseType)
                .append("&v=")
                .append(v);
        return "redirect:" + url.toString();
    }

    @RequestMapping(value = "/vk")
    public String VKAuthorization(@RequestParam("code") String code) throws ClientException, ApiException {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
        UserAuthResponse authResponse = vk.oauth()
                .userAuthorizationCodeFlow(6914326, "NxxWRvAd143qPtGUp2D5", "http://localhost:8080/vk", code)
                .execute();
        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        Role role = roleService.getRoleById(2);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        User newUser = new User(actor.getId().toString(), actor.getId().toString(), "pass", roleSet);
        Authentication auth = new UsernamePasswordAuthenticationToken(newUser, null, newUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/user";
    }

    @RequestMapping(value = "/googleAuth")
    public String GoogleAuthorization() {
        final String redirectUri = "http://localhost:8080/google";
        final String clientId = "745973723376-l1rnc10fjbak01e8ddutarjroo1pi070.apps.googleusercontent.com";
        final String responseType = "code";
        final String scope = "https://www.googleapis.com/auth/userinfo.email";
        StringBuilder url = new StringBuilder();
        url.append("https://accounts.google.com/o/oauth2/auth?redirect_uri=")
                .append(redirectUri)
                .append("&response_type=")
                .append(responseType)
                .append("&client_id=")
                .append(clientId)
                .append("&scope=")
                .append(scope);
        return "redirect:" + url.toString();
    }

    @RequestMapping(value = "/google")
    public String GoogleAuthorization(@RequestParam("code") String code) throws IOException {
        final String clientId = "745973723376-l1rnc10fjbak01e8ddutarjroo1pi070.apps.googleusercontent.com";
        final String clientSecret = "vjSaNvKmJ9HTu6dW0Pt9Z63-";
        final String redirectUri = "http://localhost:8080/google";
        final HttpTransport transport = new NetHttpTransport();
        final JacksonFactory jsonFactory = new JacksonFactory();

        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(transport, jsonFactory,
                clientId, clientSecret, code, redirectUri).execute();

        Role role = roleService.getRoleById(2);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        User newUser = new User(tokenResponse.getIdToken().substring(15,45), tokenResponse.getIdToken().substring(5,15), "pass", roleSet);
        userService.addUser(newUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(newUser, null, newUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/user";
    }
}