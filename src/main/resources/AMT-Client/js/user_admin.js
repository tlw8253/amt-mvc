"use strict";

var sURL_Host =  "http://ec2-18-117-158-53.us-east-2.compute.amazonaws.com:8080/";
var sURL_App = "amt-mvc/";
var sURL_ToUse = sURL_Host + sURL_App;

let getByUsernameButton = document.getElementById('btn_get_by_username_req');
let getLoginButton = document.getElementById('btn_login_req');



function btn_get_by_username_req(event){
    event.preventDefault();

    console.log("btn_get_by_username_req: event");

    var sUsername = document.getElementById("get_by_username").value;
    console.log("sUsername: [" + sUsername + "]");

    getUserByUserName(sUsername);

};

function btn_login_req(event){
    event.preventDefault();

    console.log("btn_login_req: event");

    var sUsername = document.getElementById("login_username").value;
    console.log("sUsername: [" + sUsername + "]");
    var sPassword = document.getElementById("login_password").value;
    console.log("sPassword: [" + sPassword + "]");

    loginRequest(sUsername,sPassword);

}

function loginRequest(sUsername,sPassword){
    console.log("sUsername(" + sUsername +")");
    console.log("sPassword(" + sPassword +")");
    var sUsingURL = sURL_ToUse + 'amt_login';
    console.log("sUsingURL: [" + sUsingURL +"]");

    const loginInfo = {
        'username': sUsername,
        'password': sPassword
    };

    fetch(sUsingURL, {
        method: 'POST',
        credentials: 'include', // this specifies that when you receive cookies,
        // you should include them in future requests.
        headers: {
            'Content-Type': 'application/json' // application/json is a MIME type
        },
        body: JSON.stringify(loginInfo)}).then((response) => {
        if (response.status === 200) {
            console.log("response.status === 200");
        } else if (response.status === 400) {
            displayInvalidLogin();
        }
    })

};




function getUserByUserName(sUsername){
    console.log("getUserByUserName(" + sUsername +")");
    console.log("sURL_ToUse: [" + sURL_ToUse +"]");
    var sUsingURL = sURL_ToUse + 'amt_user/' + sUsername;
    console.log("sUsingURL: [" + sUsingURL +"]");

    fetch(sUsingURL, {
        'credentials': 'include',
        'method': 'GET'
    }).then((response) => {
        if (response.status === 401) {
             window.location.href = '/index.html'
        } else if (response.status === 200) {
            return response.json();
        }
    }
    ).then((user) => {
        console.log("user.username: [" + user.userId + "]");
        console.log("user.username: [" + user.username + "]");
        console.log("user.username: [" + user.password + "]");
        console.log("user.username: [" + user.passwordSalt + "]");
        console.log("user.username: [" + user.firstName + "]");
        console.log("user.username: [" + user.lastName + "]");
        console.log("user.username: [" + user.email + "]");
        console.log("user.username: [" + user.employeeRole.employeeRole + "]");
        console.log("user.username: [" + user.employeeRole.employeeRoleDesc + "]");
        console.log("user.username: [" + user.userType.userType + "]");
        console.log("user.username: [" + user.userType.userTypeDesc + "]");

//      usernameOutput.value = user.username;
//      usernroleOutput.value = user.userRole.userRole;
//      userPwd = user.password;
    }
    )    
    };
   

    function getUsercredentials(){
        fetch('http://localhost:3015/ers_current_user', {
          'credentials': 'include',
          'method': 'GET'
      }).then((response) => {
          if (response.status === 401) {
               window.location.href = '/index.html'
          } else if (response.status === 200) {
              return response.json();
          }
      }
      ).then((user) => {
        usernameOutput.value = user.username;
        usernroleOutput.value = user.userRole.userRole;
        userPwd = user.password;
      }
      )
    };
    


  getByUsernameButton.addEventListener('click', btn_get_by_username_req);
  getLoginButton.addEventListener('click', btn_login_req);
