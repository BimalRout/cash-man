<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="_head.jsp"/>

    <h1 class="text-center header">Add Notes</h1>
    <hr class="divider"/>

    <c:if test="${not empty error}">
        <div class="alert alert-danger text-center">
            <c:out value="${error}"/>
        </div>
    </c:if>

    <form id="addForm"
          name="addForm"
          role="form"
          action="<c:url value='/add'/>"
          autocomplete="off"
          method="POST">
        <div class="form-group">
            <c:set var="twentyDenomesInputId" value="pin-input1"/>
            <label for="${twentyDenomesInputId}" class="sr-only">Add 20 Denominations</label>
            <input id="${twentyDenomesInputId}" type="text" class="form-control text-center" name="twentyDenomes"
                   placeholder="Twenty Dollar Notes" autocomplete="off" readonly required/>
        </div>
        
         <div class="form-group">
            <c:set var="fiftyDenomesInputId" value="pin-input2"/>
            <label for="${fiftyDenomesInputId}" class="sr-only">Add 50 Denominations</label>
            <input id="${fiftyDenomesInputId}" type="text" class="form-control text-center" name="fiftyDenomes"
                   placeholder="Fifty Dollar notes" autocomplete="off" readonly required/>
        </div>

        <jsp:include page="_keypad.jsp">
            <jsp:param name="clearButtonName" value="Delete"/>
        </jsp:include>
        <script>$(document).ready(function() { startKeypad('#${twentyDenomesInputId}', 1, false); });
        $(document).ready(function() { startKeypad('#${fiftyDenomesInputId}', 1, false); });</script>

        <table class="three-td-table">
            <tr>
                <td>
                </td>
                <td>
                    <a href="<c:url value='/operations'/>" class="btn btn-default btn-block">Back</a>
                </td>
                <td>
                    <button type="submit" class="btn btn-primary btn-block">OK</button>
                </td>
            </tr>
        </table>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <table class="three-td-table">
        <tr>
            <td>
                <form action=" <c:url value='/j_spring_security_logout'/>"
                      method="post" class="withdrawal-fix-exit-height">
                    <button type="submit" class="btn btn-default btn-block">Exit</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </td>
            <td></td>
            <td></td>
        </tr>
    </table>

    <script>
        // prevent submitting if nothing was entered
        $(document).ready(function() {
            $('#addForm').submit(function() {
                if ($.trim($("#${twentyDenomesInputId}").val()) === "") {
                    return false;
                }
                if ($.trim($("#${fiftyDenomesInputId}").val()) === "") {
                    return false;
                }
            });
        });
    </script>

<jsp:include page="_foot.jsp"/>
