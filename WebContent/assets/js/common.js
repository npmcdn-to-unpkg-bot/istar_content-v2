/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function validateNumeric(event) {
    var key = window.event ? event.keyCode : event.which;
    if (event.keyCode == 8 || event.keyCode == 46
            || event.keyCode == 37 || event.keyCode == 39) {
        return true;
    }
    else if (key < 48 || key > 57) {
        return false;
    }
    else
        return true;
}


function validateInputs(frm, hasDate) {
    var form = document.getElementById(frm);
    var inputs = form.getElementsByTagName('input');
    var flag = false;

    for (var x = 0; x < inputs.length; x++) {
        if (inputs[x].type == 'text') {
            if (inputs[x].value == '') {
                flag = false;
                break;
            } else {
                flag = true;
            }
        }
    }
    if (flag) {
        if (!hasDate) {
            document.getElementById(frm).submit();
        } else {
           checkDate();
        }
    } else {
        alert('Please fill up all the fields');
    }




}


