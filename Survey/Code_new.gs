function myFunction() {
   var studentForm = FormApp.create('Student Form -2');
  
 function studfunction(item1Response,studentForm){
   for(var i=0;i<item1Response.length; i++){
   if(item1Response[i]== 'Volcano'){
     var item1 = studentForm.addCheckboxItem();
item1.setTitle('What will you do during '+ itemResponse[i]);
item1.setChoices([
        item1.createChoice('Run'),
        item1.createChoice('Hide'),
        item1.createChoice('Do nothing')
    ]);
     
   }
     if (item1Response[i]== 'Tornado'){
       
   var item2 = studentForm.addCheckboxItem();
item2.setTitle('What will you do during a '+ itemResponse[i]);
item2.setChoices([
        item2.createChoice('Run'),
        item2.createChoice('Hide'),
        item2.createChoice('Do nothing')
    ]);
     
 }
     
     if (item1Response[i]== 'Tsunami'){
       
   var item3 = studentForm.addCheckboxItem();
item3.setTitle('What will you do when a '+ itemResponse[i]+'is about to hit your area');
item3.setChoices([
        item3.createChoice('Run'),
        item3.createChoice('Hide'),
        item3.createChoice('Do nothing')
    ]);
     
 }
   }
 } 
 
  var form = FormApp.openByUrl('https://docs.google.com/a/u.boisestate.edu/forms/d/1GUE4w5FjTN9m7p6Wc9HNEheEY_Mz3c7Qj4PmjnS6rxk/viewform');
 var formResponses = form.getResponses();
 for (var i = 0; i < formResponses.length; i++) {
   var formResponse = formResponses[i];
  var user_email =  formResponse.getRespondentEmail(); 
//   if(user_email == 'fatimahalmubarak@u.boisestate.edu') {
   var itemResponses = formResponse.getItemResponses();
  
   for (var j = 0; j < itemResponses.length; j++) {
     var itemResponse = itemResponses[j];
    
    studfunction(itemResponse.getResponse(),studentForm);
   }
 }
}
 
   
 
