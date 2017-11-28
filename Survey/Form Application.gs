
function instructorSurvey() {
  
   var form = FormApp.create('Instructor Form');
   var item = form.addCheckboxItem();
  item.setTitle('What disasters are relevant to your area?');
  item.setChoices([
        item.createChoice('Volcano'),
        item.createChoice('Tornado'),
        item.createChoice('Tsunami')
    ]);
  
  Logger.log('Published URL: ' + form.getPublishedUrl());
  form.setLimitOneResponsePerUser(true);
  form.setConfirmationMessage("Thank you for the survey"); 
  form.setCustomClosedFormMessage("Student Survey link will be emailed in few moments");
  
  }
function onSubmit(e) {
  /* Get values entered by filling the form */
  var itemResponses = e.response.getItemResponses();
  //var myWantedValue = itemResponses[COLUMN_NUMBER].getResponse();
  
   var form = FormApp.create('Instructor Form');
   var item = form.addCheckboxItem();
  item.setTitle('What disasters are relevant to your area?');
  item.setChoices([
        item.createChoice('Volcano'),
        item.createChoice('Tornado'),
        item.createChoice('Tsunami')
    ]);
  
  Logger.log('Published URL: ' + form.getPublishedUrl());
  form.setLimitOneResponsePerUser(true);
  form.setConfirmationMessage("Thank you for the survey"); 
  form.setCustomClosedFormMessage("Student Survey link will be emailed in few moments");
}

//function studentSurvey(){
//  
//  var studentForm = FormApp.create('Student Form');
//  var question1 = studentForm.addMultipleChoiceItem(); 
//  question1.setTitle('Are you 18 or older?').setChoices([
//         question1.createChoice('Yes', FormApp.PageNavigationType.CONTINUE),
//         question1.createChoice('NO', FormApp.PageNavigationType.SUBMIT)
//     ]);
//
// 
//  
//   var studentFormLink = studentForm.getPublishedUrl(); 
//  function getReleaventDisatster(response,form){
//  
//    for(var i=0;i<response.length; i++){
//     
//   if(response== 'Volcano'){
//     var item1 = form.addCheckboxItem();
//      item1.setTitle('What will you do during Volcano');
//      item1.setChoices([
//        item1.createChoice('Run'),
//        item1.createChoice('Hide'),
//        item1.createChoice('Do nothing')
//    ]);
//     
//   }
////     if (response[i]== 'Tornado'){
////       
////   var item2 = form.addCheckboxItem();
////item2.setTitle('What will you do during a Tornado' );
////item2.setChoices([
////        item2.createChoice('Run'),
////        item2.createChoice('Hide'),
////        item2.createChoice('Do nothing')
////    ]);
////     
//// }
////     if (response[i]== 'Tsunami'){
////       
////   var item3 = form.addCheckboxItem();
////item3.setTitle('What will you do when a Tsunami is about to hit your area');
////item3.setChoices([
////        item3.createChoice('Run'),
////        item3.createChoice('Hide'),
////        item3.createChoice('Do nothing')
////    ]);
////     
////     }
////   }
//// } // end of studentSurvey function 
////  var cell=2;
////  var row=1;
////  // Open Teacher Survey and import responses to create student Survey 
////   var excel = new ActiveXObject("Excel.Application");
////  var excel_file = excel.Workbooks.Open("https://docs.google.com/spreadsheets/d/16FOKiQwv3RLpU8LVzxzsk7j-Bn9llDzNY6eXZH6PPu8/edit#gid=607471907");
////  var excel_sheet = excel.Worksheets("Sheet1");
////  var data = excel_sheet.Cells(cell,row).Value;
////   var d = new Date.now();
////  while(data!=d)
////  {
////    cell++;
////    data = excel_sheet.Cells(cell,row).Value;
////    
////  }
////   var formResponses = formapp.getResponses();
////  for (var i = 0; i < formResponses.length; i++) {
////   var formResponse = formResponses[i];
////   var itemResponses = formResponse.getItemResponses();
////   for (var j = 0; j < itemResponses.length; j++) {
////     var itemResponse = itemResponses[j];
////     getReleaventDisatster(itemResponse.getResponse(),studentForm);
////     
//    //var form = FormApp.openById('1234567890abcdefghijklmnopqrstuvwxyz');
// var formResponses = formapp.getResponses();
// for (var i = 0; i < formResponses.length; i++) {
//   var formResponse = formResponses[i];
//   var itemResponses = formResponse.getItemResponses();
//   for (var j = 0; j < itemResponses.length; j++) {
//     var itemResponse = itemResponses[j];
//     Logger.log('Response #%s to the question "%s" was "%s"',
//         (i + 1).toString(),
//         itemResponse.getItem().getTitle(),
//         itemResponse.getResponse());
//   }
// }
//   
//   }
// } // end of loop 
// 
//    }
