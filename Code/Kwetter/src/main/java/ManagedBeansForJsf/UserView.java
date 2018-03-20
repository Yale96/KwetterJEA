/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeansForJsf;

import Models.User;
import Services.UserService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author yanni
 */
@ManagedBean(name="userView")
@ViewScoped
public class UserView {
    
    @Inject
    private UserService userService;
    
    List<User> users;
    /**
     * Creates a new instance of UserView
     */
    public UserView() {
    }
    
    public List<User> getAllUsers()
    {
        return userService.getUsers();
    }
    
    public String getOtherRole(User user)
    {
        if(user.getRol().equals("Admin"))
        {
            return "User";
        }
        else
            return "Admin";
    }
    
    
}
