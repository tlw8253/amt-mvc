"use strict";
var e = document.getElementById("reimb_status_code");
var userPwd = "";
var fmUsername = "";
var authorUsername = "";


let searchButton = document.getElementById('btn_search');
let clearButton = document.getElementById('btn_clear');

let fmSubmitButton = document.getElementById('btn_submit_fm_action');
let fmClearButton = document.getElementById('btn_clear_fm_action');

let actionStatusOutput = document.getElementById('action_status');

let reimbNumber = document.getElementById('reimbursement_number');
let allRecords = document.getElementById('radio_all_records');
let pendingRecords = document.getElementById('radio_pending_records');
let approvedRecords = document.getElementById('radio_approved_records');
let deniedRecords = document.getElementById('radio_denied_records');

let usernameOutput = document.getElementById('username');
let usernroleOutput = document.getElementById('userrole');


function onPageLoad(){
    getUsercredentials();
    resetPage();        
  };


  function gotoReimbursement(){
    window.location.href = '/reimbursement.html';
  }

  function logout(){
    //document.event.preventDefault();
     
    const logoutInfo = {
        'username': usernameOutput.value,
        'password': userPwd
    };
  
      fetch('http://localhost:3015/ers_logout', {
        method: 'POST',
        credentials: 'include', // this specifies that when you receive cookies,
        // you should include them in future requests.
        headers: {
            'Content-Type': 'application/json' // application/json is a MIME type
        },
        body: JSON.stringify(logoutInfo)}).then(() => {
          window.location.href = '/index.html';
  
    })
  };


  function resetPage(){
    hideFinMgrActionBtn();
    hideFinMgrDenyReason();
    hideFormSearchResults();
    hideFormSelectForSearchReq();
    hideFormReviewReimbRec();
    showFormSelectForSearchReq();
    clearStatus(); 
       
  }

  function clearStatus(){
    actionStatusOutput.value = "";
  }

  function btn_clear_fm_action(event) {
    event.preventDefault();
    resetPage();
  }


  function btn_submit_fm_action(event) {
    event.preventDefault();
 
    const createRequest = {       
        'reimbId': reimbNumber.value,
        'reimbStatus': e.options[e.selectedIndex].text,
        'reimbDenyReason': document.getElementById('finance_reveiw_deny_reason').value
    };
 
    console.log("create(event): e.options[e.selectedIndex].text,: " + e.options[e.selectedIndex].text);
    console.log("create(event): document.getElementById('finance_reveiw_deny_reason').value: " + document.getElementById('finance_reveiw_deny_reason').value);

    clearStatus();
    hideFinMgrActionBtn();

 
    fetch('http://localhost:3015/ers_reimb_fm_update/' + usernameOutput.value, {
     method: 'POST',
     credentials: 'include', //must include on calls
     headers: {
         'Content-Type': 'application/json' 
     },
     body: JSON.stringify(createRequest)}).then((response) => {
     if (response.status === 200) {
       processReimbObj();
    } else if (response.status === 400) {
      actionStatusOutput.value = "Issue performing reimburstment record action.";
     }
 })

 };
 
 function processReimbObj(){
  actionStatusOutput.value = "Reimburstment record action sucessfully.";
};


  function startFinMgrAction(selectObject){
    var value = selectObject.value; 
    console.log("startFinMgrAction(): selectObject.value: [" + value + "]");
    clearStatus();

    //alert("startFinMgrAction()" + value);
    if (value == 'denied'){        
      showFinMgrDenyReason();
    } else {
      hideFinMgrDenyReason();
    }
    
    if (value == 'default'){
      hideFinMgrActionBtn();
    } else {
      showFinMgrActionBtn();
    }
  };

  function showFinMgrActionBtn(){
    document.getElementById("lbl_action_btn").style.display="block";
  }
  function hideFinMgrActionBtn(){
    document.getElementById("lbl_action_btn").style.display="none";
    document.getElementById("reimb_status_code").selectedIndex=0;
  }

  function showFinMgrDenyReason(){
    document.getElementById("finance_reveiw_deny_reason").value = "";
    document.getElementById("finance_reveiw_deny_reason_div").style.display="block"; 
  }
  function hideFinMgrDenyReason(){
    document.getElementById("finance_reveiw_deny_reason_div").style.display="none"; 
  }


  function hideFormSearchResults(){
    document.getElementById("all_reimb_recs").style.display="none";
    document.getElementById("div_all_recs").style.display="none";
  }
  function showFormSearchResults(){
    document.getElementById("all_reimb_recs").style.display="block";
    document.getElementById("div_all_recs").style.display="block";
  }

  function hideFormSelectForSearchReq(){
    document.getElementById("select_for_search_req").style.display="none";
  }
  function showFormSelectForSearchReq(){
    reimbNumber.value="";
    allRecords.checked = false;
    pendingRecords.checked = false;
    approvedRecords.checked = false;
    deniedRecords.checked = false;
    document.getElementById("select_for_search_req").style.display="block";
  }

  function hideFormReviewReimbRec(){
    document.getElementById("review_reimb_record").style.display="none";
  }
  function showFormReviewReimbRec(){
    document.getElementById("review_reimb_record").style.display="block";
  }


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

  function btn_search(event){
    event.preventDefault();
    clearStatus();
     
    if ((reimbNumber.value == "") && (allRecords.checked == false) && (pendingRecords.checked == false)
        && (approvedRecords.checked == false) && (deniedRecords.checked == false)){
      actionStatusOutput.value = "Please enter a search criteria.";
    } 
          
    //all records raido button takes first priority when true
    if (allRecords.checked){
       reimbNumber.value = "";
       getFilteredReimbursementRecords("ALL");
     }
     if (pendingRecords.checked){
      reimbNumber.value = "";
      getFilteredReimbursementRecords("PENDING");
    }
    if (approvedRecords.checked){
      reimbNumber.value = "";
      getFilteredReimbursementRecords("APPROVED");
    }
    if (deniedRecords.checked){
      reimbNumber.value = "";
      getFilteredReimbursementRecords("DENIED");
    }
    if(reimbNumber.value != ""){
        searchByReimbNumber(reimbNumber.value);
      }
  }

  function btn_clear(event){
    event.preventDefault();
    reimbNumber.value = "";
    allRecords.checked = false;
    pendingRecords.checked = false;
    approvedRecords.checked = false;
    deniedRecords .checked = false;
  }

  function getFilteredReimbursementRecords(sStatus){
    console.log("getFilteredReimbursementRecords(" + sStatus +")");
    fetch('http://localhost:3015/ers_reimb_filter/'+sStatus, {
      'credentials': 'include',
      'method': 'GET'
  }).then((response) => {
      if (response.status === 401) {
           window.location.href = '/index.html'
      } else if (response.status === 200) {
          return response.json();
      }
  }
  ).then((reimbursement) => {
    populateReimbTable(reimbursement);
  }
  )

  };  

 
  function populateReimbTable(arrReimbursement) {
 
    
    let tbody = document.querySelector('#all_reimb_recs tbody');
    tbody.innerHTML = "";
 
    for (const reimbursement of arrReimbursement) {
  
      let tr = document.createElement('tr');

        /*
        var xRadio = document.createElement("INPUT");
        xRadio.setAttribute("type", "radio");     
        let radioBthTd = document.createElement('td');
        radioBthTd.innerHTML = xRadio;
        */

        let reimbIdTd = document.createElement('td');
        reimbIdTd.innerHTML = reimbursement.reimbId;

        let authorUsernameTd = document.createElement('td');
        authorUsernameTd.innerHTML = reimbursement.reimbAuthor.username;
        let authorFirstNameTd = document.createElement('td');
        authorFirstNameTd.innerHTML = reimbursement.reimbAuthor.firstName;
        let authorLastNameTd = document.createElement('td');
        authorLastNameTd.innerHTML = reimbursement.reimbAuthor.lastName;
        let authorRoleTd = document.createElement('td');
        authorRoleTd.innerHTML = reimbursement.reimbAuthor.userRole.userRole;


        let reimbTypeTd = document.createElement('td');
        reimbTypeTd.innerHTML = reimbursement.reimbType.reimbType;

        let reimbAmountTd = document.createElement('td');
        reimbAmountTd.innerHTML = reimbursement.reimbAmount;

        let reimbDescriptionTd = document.createElement('td');
        reimbDescriptionTd.innerHTML = reimbursement.reimbDescription;

        let reimbReceiptTd = document.createElement('td');
        reimbReceiptTd.innerHTML = reimbursement.reimbReceipt;

        let reimbSubmittedTd = document.createElement('td');
        reimbSubmittedTd.innerHTML = new Date(reimbursement.reimbSubmitted).toISOString().slice(0, 10); 
        //new Date(reimbursement.reimbSubmitted); full date string

        let reimbStatusTd = document.createElement('td');
        reimbStatusTd.innerHTML = reimbursement.reimbStatus.reimbStatus;

        let resolverUsernameTd = document.createElement('td');
        resolverUsernameTd.innerHTML = reimbursement.reimbResolver.username;

        let resolverMsgTd = document.createElement('td');
        resolverMsgTd.innerHTML = reimbursement.reimbResolverMsg;

        let reimbResolvedTd = document.createElement('td');
        reimbResolvedTd.innerHTML = new Date(reimbursement.reimbResolved).toISOString().slice(0, 10);


        //tr.appendChild(xRadio);
        
        tr.appendChild(reimbIdTd);

        tr.appendChild(authorUsernameTd);
        tr.appendChild(authorFirstNameTd);
        tr.appendChild(authorLastNameTd);
        tr.appendChild(authorRoleTd);

        tr.appendChild(reimbTypeTd);
        tr.appendChild(reimbAmountTd);
        tr.appendChild(reimbDescriptionTd);
        tr.appendChild(reimbReceiptTd);
        tr.appendChild(reimbSubmittedTd);

        tr.appendChild(reimbStatusTd);
        tr.appendChild(resolverUsernameTd);
        tr.appendChild(resolverMsgTd);
        tr.appendChild(reimbResolvedTd);

        tbody.appendChild(tr);
    }

    allRecords.checked = false;
    pendingRecords.checked = false;
    approvedRecords.checked = false;
    deniedRecords.checked = false;
    
    showFormSearchResults();

    //document.getElementById("all_reimb_recs").style.display="block";
}





function searchByReimbNumber(sReimbNum){
 
  console.log("searchByReimbNumber(" + sReimbNum + ")");

    fetch('http://localhost:3015/ers_reimb_id/'+sReimbNum, {
        'credentials': 'include',
        'method': 'GET'
    }).then((response) => {
        if (response.status === 401) {
             window.location.href = '/index.html'
        } else if (response.status === 200) {
            return response.json();
        }
    }
    ).then((Reimbursement) => {
        hideFormSelectForSearchReq();
        document.getElementById('review_rec_id').value = Reimbursement.reimbId;        
        document.getElementById('review_reimb_status').value = Reimbursement.reimbStatus.reimbStatus;
        document.getElementById('review_reimb_type').value = Reimbursement.reimbType.reimbType;
        document.getElementById('review_reimb_amount').value = Reimbursement.reimbAmount;
        document.getElementById('review_reimb_author_un').value = Reimbursement.reimbAuthor.username;
        document.getElementById('review_reimb_author_role').value = Reimbursement.reimbAuthor.userRole.userRole;
        document.getElementById('review_reimb_description').value = Reimbursement.reimbDescription;
        document.getElementById('review_reimb_receipt').value = Reimbursement.reimbReceipt;

        document.getElementById('review_reimb_submitted').value = new Date(Reimbursement.reimbSubmitted);

        fmUsername = usernameOutput.value;
        authorUsername = document.getElementById("review_reimb_author_un").value
       
        if (Reimbursement.reimbStatus.reimbStatus == "DENIED"){
          actionStatusOutput.value = "Reason Denied: [" + Reimbursement.reimbResolverMsg + "]";
        }

        document.getElementById("fin_mgr_actions").style.display="block";
        if(fmUsername == authorUsername){
          actionStatusOutput.value = "You can only view your own Reimbursement Record.";
          document.getElementById("fin_mgr_actions").style.display="none";
        }
        
        if (Reimbursement.reimbStatus.reimbStatus != "PENDING"){
          document.getElementById("fin_mgr_actions").style.display="none";
          alert("Reimbursement is in final state.  Status cannot be changed.");
        }

        showFormReviewReimbRec();    
    }
    )
};


  function checkIfUserCurrentlyLoggedIn(event) {
    event.preventDefault();
};
  searchButton.addEventListener('click', btn_search);
  fmSubmitButton.addEventListener('click', btn_submit_fm_action);
  fmClearButton.addEventListener('click', btn_clear_fm_action);
  window.addEventListener('load', checkIfUserCurrentlyLoggedIn)