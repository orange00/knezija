<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:c="http://java.sun.com/jsp/jstl/core">
	<ul>
		<c:forEach items="${collection}" var="subCollection">
			<li><script>console.log(${subCollection.title}); </script> <ui:include
					src="/WEB-INF/jsp/recursive.jsp">
					<ui:param name="collection" value="${subCollection}" />
				</ui:include></li>
		</c:forEach>
	</ul>
</ui:composition>