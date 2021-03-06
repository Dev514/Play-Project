// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:6
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def index: Call = {
      
      Call("GET", _prefix)
    }
  
    // @LINE:7
    def explore: Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "explore")
    }
  
    // @LINE:8
    def tutorial: Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "tutorial")
    }
  
    // @LINE:9
    def hello: Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "hello")
    }
  
  }

  // @LINE:10
  class ReverseUserController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def getUsers: Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "user")
    }
  
    // @LINE:11
    def addUser(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "adduser")
    }
  
    // @LINE:12
    def delete(id:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "delete/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("id", id)))
    }
  
    // @LINE:13
    def update(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "update")
    }
  
  }

  // @LINE:17
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:17
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
