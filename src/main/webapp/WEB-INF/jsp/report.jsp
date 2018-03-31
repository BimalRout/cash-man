<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="_head.jsp"/>

    <h1 class="text-center header">Withdraw Report</h1>
    <hr class="divider"/>

    <form role="form"
          action="<c:url value='/j_spring_security_logout'/>"
          method="POST">
        <div class="form-group">
            <label for="currentDate" class="text-center">Date</label>
            <input id="currentDate" class="form-control text-center" type="text" value="${currentDate}" readonly>
        </div>
        <div class="form-group">
            <label for="number" class="text-center">Credit Card Number</label>
            <input id="number" class="form-control text-center" type="text" value="${number}" readonly>
        </div>
        <div class="form-group">
            <label for="withdrawalAmount" class="text-center">Withdrawal Amount</label>
            <input id="withdrawalAmount" class="form-control text-center" type="text" value="- ${withdrawalAmount}$" readonly>
        </div>
        
         <div class="form-group">
            <label for="twentyNotes" class="text-center">Twenty Type Notes</label>
            <input id="twentyNotes" class="form-control text-center" type="text" value="${twentyNotes}$" readonly>
        </div>
        
         <div class="form-group">
            <label for="fiftyNotes" class="text-center">Fifty Type Notes</label>
            <input id="fiftyNotes" class="form-control text-center" type="text" value="${fiftyNotes}$" readonly>
        </div>
        
        <hr class="divider"/>
        <div class="form-group">
            <label for="balanceAmount" class="text-center">Balance Amount</label>
            <input id="balanceAmount" class="form-control text-center" type="text" value="${balanceAmount}$" readonly>
        </div>

        <div class="form-group">
            <table class="three-td-table">
                <tr>
                    <td>
                        <button type="submit" class="btn btn-default btn-block">Exit</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </td>
                    <td>
                        <a href="<c:url value='/operations'/>" class="btn btn-default btn-block">Back</a>
                    </td>
                    <td></td>
                </tr>
            </table>
        </div>
    </form>

<jsp:include page="_foot.jsp"/>