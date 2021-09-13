"use strict";
// need to make sure the URL is: http://localhost:5500/index.html
//http://localhost:3015/index.html
// live server does not do this.
let loginButton = document.getElementById('login');
let usernameInput = document.getElementById('username');
let passwordInput = document.getElementById('password');

var sUsername = "";
var sUserRole = "";

function testFileReader(){

  console.log("testFileReader()");
  if (window.File && window.FileReader && window.FileList && window.Blob) {
    alert('The File APIs are fully supported in this browser.');
  } else {
    alert('The File APIs are not fully supported in this browser.');
  }
};


function login(event) {
   // this will prevent the default behavior of what happens when a button inside a form element is clicked
    event.preventDefault();
    //testFileReader();
   
    const loginInfo = {
        'username': usernameInput.value,
        'password': passwordInput.value
    };

      fetch('http://ec2-18-117-158-53.us-east-2.compute.amazonaws.com:8080/amt_login', {
        method: 'POST',
        credentials: 'include', // this specifies that when you receive cookies,
        // you should include them in future requests.
        headers: {
            'Content-Type': 'application/json' // application/json is a MIME type
        },
        body: JSON.stringify(loginInfo)}).then((response) => {
        if (response.status === 200) {
          getUsercredentials();
          if (sUserRole == "EMPLOYEE"){
            window.location.href = '/reimbursement.html';
          }
        } else if (response.status === 400) {
            displayInvalidLogin();
        }
    })
};

//let text = localStorage.getItem("testJSON");
//let obj = JSON.parse(text);

function getUsercredentials(){
  fetch('http://ec2-18-117-158-53.us-east-2.compute.amazonaws.com:8080/amt-mvc/amt_current_user', {
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
   sUserRole = user.userRole.userRole;
  if (sUserRole == "EMPLOYEE"){
    window.location.href = '/reimbursement.html';
  }else{
    if (sUserRole == "FINANCEMGR"){
      window.location.href = '/finance.html';
    }else{
      alert("Role not defined for WWMS client: " + sUserRole)
    }
  }

}
)};

function displayInvalidLogin() {
    let loginForm = document.getElementById('loginform');

    let p = document.createElement('p');
    p.style.color = 'red';
    p.innerHTML = 'INVALID LOGIN!';

    loginForm.appendChild(p);
};

function checkIfUserCurrentlyLoggedIn(event) {

  /*
  if(sUserRole == "" && iPageCnt > 0){
    getUsercredentials();
  }else{
    if(sUserRole == "EMPLOYEE"){
    window.location.href = '/test.html';
    }
    else{
    }
  }
  */
};

loginButton.addEventListener('click', login);
window.addEventListener('load', checkIfUserCurrentlyLoggedIn)