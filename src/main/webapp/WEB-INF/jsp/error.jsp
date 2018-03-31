<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="_head.jsp"/>

    <c:set var="errorMsg" value="${errorMsg == null ? 'General server error' : errorMsg}"/>

    <h1 class="text-center header">Error</h1>
    <hr class="divider"/>

    <div class="alert alert-danger text-center">
        <c:out value="${errorMsg}"/>
    </div>

    <a href="<c:url value='/'/>" class="btn btn-default btn-block">Back</a>

<jsp:include page="_foot.jsp"/>