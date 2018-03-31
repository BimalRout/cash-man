<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="_head.jsp"/>

    <h1 class="text-center header">Enter PIN</h1>
    <hr class="divider"/>

    <c:if test="${not empty error}">
        <div class="alert alert-danger text-center">
            <c:out value="${error}"/>
        </div>
    </c:if>

    <form id="loginForm"
          name="loginForm"
          role="form"
          action="<c:url value='/j_spring_security_check' />"
          method="POST"
          autocomplete="off">

        <!-- fake fields are a workaround for chrome autofill getting the wrong fields (chrome ignores autocomplete="off") -->
        <input style="display:none" type="text" name="fakeusernameremembered"/>
        <input style="display:none" type="password" name="fakepasswordremembered"/>

        <%-- hidden field for credit card number for spring security to handle login --%>
        <input style="display:none" type='text' name='j_number' value='${number}' autocomplete="off" readonly/>

        <div class="form-group">
            <c:set var="pinInputId" value="pin-input"/>
            <label for="${pinInputId}" class="sr-only">PIN</label>
            <input id="${pinInputId}" class="form-control text-center" type='password' name='j_pin'
                   placeholder="PIN" autocomplete="off" readonly required/>
        </div>

        <jsp:include page="_keypad.jsp"/>
        <script>$(document).ready(function() { startKeypad('#${pinInputId}', 4, false); });</script>

        <div class="form-group">
            <table class="three-td-table">
                <tr>
                    <td>
                        <a href="<c:url value='/?logout'/>" class="btn btn-default btn-block">Exit</a>
                    </td>
                    <td>
                        <a href="<c:url value='/'/>" class="btn btn-default btn-block">Back</a>
                    </td>
                    <td>
                        <button type="submit" class="btn btn-primary btn-block">OK</button>
                    </td>
                </tr>
            </table>
        </div>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    </form>

    <script>
        // prevent submitting if nothing was entered
        $(document).ready(function() {
            $('#loginForm').submit(function() {
                if ($.trim($("#${pinInputId}").val()) === "") {
                    return false;
                }
            });
        });
    </script>

<jsp:include page="_foot.jsp"/>
