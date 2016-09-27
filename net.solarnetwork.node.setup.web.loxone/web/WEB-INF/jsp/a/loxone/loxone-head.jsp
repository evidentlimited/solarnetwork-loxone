<head>
	<title><fmt:message key="app.name"/> - <fmt:message key="loxone.title"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="csrf" content="${_csrf.token}"/>
	<meta name="csrf_header" content="${_csrf.headerName}"/>
	<c:if test="${not empty(configId)}">
		<meta name="loxone-config-id" content="${configId}"/>
	</c:if>
	<c:if test="${not empty(configIds) }">
		<meta name="loxone-config-ids" content="${configIds}"/>
	</c:if>
	<c:import url="/WEB-INF/jsp/default-head-resources.jsp"/>
	<script type="application/javascript" src="<c:url value='/js/loxone-websocket.js'/>"></script>
</head>
