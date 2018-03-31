<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="_head.jsp"/>

    <h1 class="text-center header">Operations</h1>
    <hr class="divider"/>

    <div class="form-group">
        <table class="three-td-table">
            <tr>
                <td>
                    <form action=" <c:url value='/j_spring_security_logout'/>" method="post">
                        <button type="submit" class="btn btn-default btn-block">Exit</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </td>
                <td>
                    <a href="<c:url value='/balance'/>" class="btn btn-primary btn-block">Balance</a>
                </td>
                <td>
                    <a href="<c:url value='/withdrawal'/>" class="btn btn-primary btn-block">Withdraw</a>
                </td>
                 <td>
                    <a href="<c:url value='/addBalance'/>" class="btn btn-primary btn-block">Add</a>
                </td>
            </tr>
        </table>
    </div>

<jsp:include page="_foot.jsp"/>
