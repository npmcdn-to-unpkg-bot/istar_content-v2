<%-- 
    Document   : create_edit_test
    Created on : 29 Mar, 2016, 4:26:24 PM
    Author     : Kunal Chakravertti
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create/Edit Test</title>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
        <script>
            $(function () {
                $("#datepicker").datepicker({dateFormat: 'dd/mm/yy'});
            });</script>

        <script type="text/javascript">
            function checkDate(val) {
                var flag = true;
//                var todayDate = new Date();
//                //need to add one to get current month as it is start with 0
//                var todayMonth = todayDate.getMonth() + 1;
//                var todayDay = todayDate.getDate();
//                var todayYear = todayDate.getFullYear();
//                var todayDateText = todayDay + "/" + todayMonth + "/" + todayYear;
//                var inputDateText = document.getElementById('datepicker').value;
//                var inputToDate = Date.parse(inputDateText);
//
//                var todayToDate = Date.parse(todayDateText);
//                if (inputToDate > todayToDate) {
//                    flag = true;
//
//                } else {
//                    alert("the input is earlier than today");
//                }
//                else if (inputToDate < todayToDate) {
//                    
//                }
//                else {
//                    alert("the input is same as today");
//                }
                if (flag) {
                    if (val == '1') {
                        document.getElementById("myform").action = "tests_controller?_action=chooseobjectives";
                    } else {
                        document.getElementById("myform").action = "tests_controller?_action=chooseobjectives&id=${testObject.id}";
                    }
                    document.getElementById("myform").method = "post";
                    document.getElementById("myform").submit();
                }
            }
        </script>
    </head>
    <body>
        <c:if test="${action == 'create'}">
            <form id="myform">
                <h1>Create Test</h1>
                <p>Test Title : <input type="text"  name="test_title" /> </p>
                <p>Test Type : <select name="test_type">
                        <option value="STATIC">Static</option>
                        <option value="ADAPTIVE">Adaptive</option>
                        <option value="TREE">Tree</option>
                        <option value="RANDOM">Random</option>
                    </select> 
                </p>
                <p>Question Count: <input type="text" name="question_count" /></p>
                <p>Test Date: <input type="text" id="datepicker" name="test_date" />(dd/MM/yyyy) &nbsp;&nbsp;&nbsp; Hour : ${hourdrop} &nbsp;&nbsp;&nbsp; Minute : &nbsp;&nbsp;&nbsp; ${mindrop} </p>
                <p>Duration:  HH: ${durationhrdrop} MM: ${durationmindrop} </p>
                <p><input type="button" value="Next" onclick="checkDate('1');"/></p>
            </form>                  
        </c:if>
        <c:if test="${action == 'edit'}">
            <form id ="myform">
                <h1>Edit Test</h1>
                <p>Test Title : <input type="text"  name="test_title" value="${testObject.assessmentTitle}"/> </p>
               
                <p>Test Type: 
                    <c:choose>
                        <c:when test="${testObject.assessmentType == null || testObject.assessmentType == ''}">
                            <select name="test_type">
                                <option value="STATIC">Static</option>
                                <option value="RANDOM">Random</option>
                                <option value="ADAPTIVE">Adaptive</option>
                                <option value="TREE">Tree</option>
                            </select> 
                        </c:when>
                        <c:when test="${testObject.assessmentType == 'STATIC'}">
                            <select name="test_type">
                                <option value="STATIC" selected="selected">Static</option>
                                <option value="ADAPTIVE">Adaptive</option>
                                <option value="TREE">Tree</option>
                                <option value="RANDOM">Random</option>
                            </select> 
                        </c:when>
                        <c:when test="${testObject.assessmentType == 'ADAPTIVE'}">
                            <select name="test_type">
                                <option value="ADAPTIVE" selected="selected">Adaptive</option>
                                <option value="STATIC">Static</option>
                                <option value="TREE">Tree</option>
                                <option value="RANDOM">Random</option>
                            </select> 
                        </c:when>
                        <c:when test="${testObject.assessmentType == 'TREE'}">
                            <select name="test_type">
                                <option value="TREE" selected="selected">Tree</option>
                                <option value="STATIC">Static</option>
                                <option value="ADAPTIVE">Adaptive</option>
                                <option value="RANDOM">Random</option>
                            </select> 
                        </c:when>
                        <c:when test="${testObject.assessmentType == 'RANDOM'}">
                            <select name="test_type">
                                <option value="RANDOM" selected="selected">Random</option>
                                <option value="STATIC">Static</option>
                                <option value="ADAPTIVE">Adaptive</option>
                                <option value="TREE">Tree</option>
                            </select> 
                        </c:when>
                    </c:choose>

                </p>
                     <p>Question Count: <input type="text" name="question_count" value="${testObject.numberOfQuestions}"/></p>           

                <p>Test Date: <input type="text" id="datepicker" name="test_date" value='<fmt:formatDate pattern="dd/MM/yyyy" value="${testObject.assessmentDate}"/>'/>(dd/MM/yyyy) &nbsp;&nbsp;&nbsp; Hour : ${hourdrop} &nbsp;&nbsp;&nbsp; Minute : &nbsp;&nbsp;&nbsp; ${mindrop} </p>
                <p>Duration:  HH: ${durationhrdrop} MM: ${durationmindrop} </p>
                <input type="hidden" name="test_id" value="${testObject.id}"/>
                <p><input type="button" value="Next" onclick="checkDate('2');"/></p>
            </form>
        </c:if>
    </body>
</html>
