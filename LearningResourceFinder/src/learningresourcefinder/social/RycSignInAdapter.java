package learningresourcefinder.social;



import learningresourcefinder.model.User;
import learningresourcefinder.model.User.AccountConnectedType;
import learningresourcefinder.service.LoginService;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.web.ContextUtil;
import learningresourcefinder.web.UrlUtil;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
@Component
public class RycSignInAdapter implements SignInAdapter {

    @Override
    public String signIn(String localId, Connection<?> connection, NativeWebRequest request) {
        User user = null;
        
        try {
            LoginService loginService = ContextUtil.getSpringBean(LoginService.class);
            // this value is setup in logincontroller (/ajax/autologin)
            Boolean autologin = loginService.readAutoLogin(request);                          
            user = loginService.login(null, null, autologin, Long.parseLong(localId), AccountConnectedType.getProviderType(connection.getKey().getProviderId()));
                     
            NotificationUtil.addNotificationMessage("Vous êtes a présent connecté sur "+UrlUtil.getWebSiteName()+" avec votre compte "+connection.getKey().getProviderId());                                           
            if(user != null)
            return loginService.getPageAfterLogin(user);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
          
     
        return null;
    }
    
    
    

}
