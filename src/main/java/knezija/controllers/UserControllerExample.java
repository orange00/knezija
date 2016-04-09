package knezija.controllers;

import javax.websocket.server.PathParam;

import knezija.services.IUserManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Class UserController
 */
//@Controller
public class UserControllerExample {
//
//  // ------------------------
//  // PUBLIC METHODS
//  // ------------------------
//
//  /**
//   * Create a new user with an auto-generated id and email and name as passed 
//   * values.
//   */
//  @RequestMapping(value="/create/{id}")
//  @ResponseBody
//  public String create(String email, String name, @PathVariable("id") String id) {
//    try {
//    	System.out.println(id);
//    	userManager.create(email,name);
//    }
//    catch (Exception ex) {
//      return "Error creating the user: " + ex.toString();
//    }
//    return "User succesfully created!";
//  }
//  
//  /**
//   * Delete the user with the passed id.
//   */
//  @RequestMapping(value="/delete")
//  @ResponseBody
//  public String delete(long id) {
//    try {
//      userManager.delete(id);
//    }
//    catch (Exception ex) {
//      return "Error deleting the user: " + ex.toString();
//    }
//    return "User succesfully deleted!";
//  }
//  
////  /**
////   * Retrieve the id for the user with the passed email address.
////   */
////  @RequestMapping(value="/get-by-email")
////  @ResponseBody
////  public String getByEmail(String email) {
////    String userId;
////    try {
////      User user = userManager.getByEmail(email);
////      userId = String.valueOf(user.getId());
////    }
////    catch (Exception ex) {
////      return "User not found: " + ex.toString();
////    }
////    return "The user id is: " + userId;
////  }
////  
////  /**
////   * Update the email and the name for the user indentified by the passed id.
////   */
////  @RequestMapping(value="/update")
////  @ResponseBody
////  public String updateName(long id, String email, String name) {
////    try {
////      User user = userManager.getById(id);
////      user.setEmail(email);
////      user.setName(name);
////      userManager.update(user);
////    }
////    catch (Exception ex) {
////      return "Error updating the user: " + ex.toString();
////    }
////    return "User succesfully updated!";
////  } 
//
//  // ------------------------
//  // PRIVATE FIELDS
//  // ------------------------
//  
//  // Wire the userManager used inside this controller.
//  @Autowired
//  private IUserManager userManager;
//  
} // class UserController
